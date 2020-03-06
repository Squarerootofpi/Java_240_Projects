package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.*;
import models.Event;
import models.Person;
import models.User;
import requests.Login;
import results.ErrorMessage;
import results.Response;
import results.SuccessMessage;

import javax.naming.Name;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Fill Service.
 * Auto fills a user's info with preselected stuff from the DB. Basically
 * fake family history.
 * Only one public function, which handlers call when they have parsed
 * a valid request object.
 * A connection is made within the serve function, and closed within it,
 * so no private data members are needed.
 */
public class FillService {

    private static final int USER_BIRTH_YEAR = 1980;
    private static final int YEARS_BEFORE_MARRIAGE = 21;
    private static final int MARRIED_YEARS_BEFORE_BIRTH = 4;
    private static final int LIFESPAN = 50;
    private static final int YEARS_AFTER_1ST_BIRTH = 25;

    private static final String BIRTH = "birth";
    private static final String DEATH = "death";
    private static final String MARRIAGE = "marriage";

    /**
     * Creates an SQL DB connection, and closes it within the function.
     *Errors: Invalid username or generations parameter, Internal server error
     *
     * @param userName    the name of the user to fill data for
     * @param generations the number of generations to add.
     * @return returns an error response object or success res object
     */
    public Response serve(String userName, Integer generations) throws DataAccessException{
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();


        Database db = new Database();
        //int r = ((int) (2* Math.random()));
        try {
            db.openConnection();

            UserDao userDao = new UserDao(db.getConnection());
            //Make sure userName exists
            User user = userDao.read(userName);
            if (user == null || generations < 1)
            {
                return new ErrorMessage("Invalid username or generations parameter");
            }

            String userPersonID = user.getPersonID();
            //we know username exists now, so clear the stuff in it for person and events.
            PersonDao personDao = new PersonDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());

            //before deleting the person, grab the person associated with the user.
            Person userPerson = personDao.read(userPersonID);
            personDao.deleteWhereAssociatedUsername(userName);
            eventDao.deleteWhereAssociatedUsername(userName);

            db.closeConnection(true);
            db.openConnection();
            //now add them back to the database
            personDao.setConn(db.getConnection());
            personDao.create(userPerson);
            //Now fill it!!!!
            int numPersons = (int) Math.pow(2.0,generations) - 2;
            int numEvents = numPersons * 3 + 1;

            File fnames = new File("FoldersAndSources/json/fnames.json");
            File mnames = new File("FoldersAndSources/json/mnames.json");
            File snames = new File("FoldersAndSources/json/snames.json");
            File locations = new File("FoldersAndSources/json/locations.json");
            //File fnames = new File("FoldersAndSources/json/fnames.json");
            if (! (fnames.isFile() && mnames.isFile() && snames.isFile()
            && locations.isFile()) )
            {
                System.out.println("Failing on files...");
            }
            String theSnames = snames.toString();
            //String theFNames = String.valueOf(fnames);
            String theFNames = new String(Files.readAllBytes((Paths.get("FoldersAndSources/json/fnames.json"))));
            String theSNames = new String(Files.readAllBytes((Paths.get("FoldersAndSources/json/snames.json"))));
            String theMNames = new String(Files.readAllBytes((Paths.get("FoldersAndSources/json/mnames.json"))));
            String theLocations = new String(Files.readAllBytes((Paths.get("FoldersAndSources/json/locations.json"))));

            NameStrings possFNames = gson.fromJson(theFNames,NameStrings.class);
            NameStrings possMNames = gson.fromJson(theMNames,NameStrings.class);
            NameStrings possSNames = gson.fromJson(theSNames,NameStrings.class);
            MultiLocationsObject possLocations = gson.fromJson(theLocations,MultiLocationsObject.class);

            //Now, generate random fill stuff.
            this.fill(possFNames, possMNames, possSNames, possLocations,
                    db.getConnection(), userName, generations
                    );




            db.closeConnection(true);
            return new SuccessMessage("Successfully added " + numPersons + " persons and " + numEvents + " events to the database.");
        }
        catch (Exception ex) {
            db.closeConnection(false);
            return new ErrorMessage("Failed clearing tables");
        }
    }

    private void fill(NameStrings femaleNames, NameStrings maleNames,
                 NameStrings surnames, MultiLocationsObject locations,
                 Connection conn, String userName, int generations)
            throws DataAccessException
    {

        UserDao userDao = new UserDao(conn);
        User rootUser = userDao.read(userName);

        PersonDao personDao = new PersonDao(conn);
        Person rootPerson = personDao.read(rootUser.getPersonID());

        Event userBirth = new Event(userName,UUID.randomUUID().toString(),
                rootPerson.getPersonID(),12.423f,52.24345f,
                "USA",
                "AF",
                "birth",USER_BIRTH_YEAR);


        EventDao eventDao = new EventDao(conn);
        eventDao.create(userBirth);
        //married at 21
        //have you as a kid at 25
        //

        //make marriage, birth, and death events for everyone but rootPerson



        fillHelper(femaleNames,maleNames,surnames,locations,personDao,eventDao,
                rootPerson, generations - 1, USER_BIRTH_YEAR
        );

    }

    private void fillHelper(NameStrings femaleNames,
                            NameStrings maleNames,
                            NameStrings surnames,
                            MultiLocationsObject locations,
                            PersonDao personDao,
                            EventDao eventDao,
                            Person person,
                            int generations,
                            int personBirthYear)
            throws DataAccessException
    {
        if (generations < 1) {
            return;
        }
        
        Person mom = new Person(person.getAssociatedUsername(), UUID.randomUUID().toString(),
                getRandomName(femaleNames),getRandomName(surnames),'f');
        Person dad = new Person(person.getAssociatedUsername(), UUID.randomUUID().toString(),
                getRandomName(maleNames),getRandomName(surnames),'m');
        mom.setSpouseID(dad.getPersonID());
        dad.setSpouseID(mom.getPersonID());

        person.setMotherID(mom.getPersonID());
        person.setFatherID(dad.getPersonID());

        //Now update the database respectively.
        personDao.update(person);
        personDao.create(mom);
        personDao.create(dad);

        //Grab locations to use for the 5 events
        LocationObject momBirthLoc = getRandLocation(locations);
        LocationObject dadBirthLoc = getRandLocation(locations);
        LocationObject momDeathLoc = getRandLocation(locations);
        LocationObject dadDeathLoc = getRandLocation(locations);
        LocationObject marriageLoc = getRandLocation(locations);

        //generate events for them all.
        Event momBirth = new Event(person.getAssociatedUsername(),UUID.randomUUID().toString()
                ,mom.getPersonID(),momBirthLoc.getLatitude(),momBirthLoc.getLongitude(),
                momBirthLoc.getCountry(),momBirthLoc.getCity(),BIRTH,
                personBirthYear - YEARS_BEFORE_MARRIAGE - MARRIED_YEARS_BEFORE_BIRTH);
        Event dadBirth = new Event(person.getAssociatedUsername(),UUID.randomUUID().toString()
                ,dad.getPersonID(),dadBirthLoc.getLatitude(),dadBirthLoc.getLongitude(),
                dadBirthLoc.getCountry(),dadBirthLoc.getCity(),BIRTH,
                personBirthYear - YEARS_BEFORE_MARRIAGE - MARRIED_YEARS_BEFORE_BIRTH);
        Event momDeath = new Event(person.getAssociatedUsername(),UUID.randomUUID().toString()
                ,mom.getPersonID(),momDeathLoc.getLatitude(),momDeathLoc.getLongitude(),
                momDeathLoc.getCountry(),momDeathLoc.getCity(),DEATH,
                personBirthYear + YEARS_AFTER_1ST_BIRTH);
        Event dadDeath = new Event(person.getAssociatedUsername(),UUID.randomUUID().toString()
                ,dad.getPersonID(),dadDeathLoc.getLatitude(),dadDeathLoc.getLongitude(),
                dadDeathLoc.getCountry(),dadDeathLoc.getCity(),DEATH,
                personBirthYear + YEARS_AFTER_1ST_BIRTH);
        Event marriageDad = new Event(person.getAssociatedUsername(),UUID.randomUUID().toString()
                ,dad.getPersonID(),marriageLoc.getLatitude(),marriageLoc.getLongitude(),
                marriageLoc.getCountry(),marriageLoc.getCity(),MARRIAGE,
                personBirthYear - MARRIED_YEARS_BEFORE_BIRTH);
        Event marriageMom = new Event(person.getAssociatedUsername(),UUID.randomUUID().toString()
                ,mom.getPersonID(),marriageLoc.getLatitude(),marriageLoc.getLongitude(),
                marriageLoc.getCountry(),marriageLoc.getCity(),MARRIAGE,
                personBirthYear - MARRIED_YEARS_BEFORE_BIRTH);
        //generations;

        eventDao.create(momBirth);
        eventDao.create(dadBirth);
        eventDao.create(momDeath);
        eventDao.create(dadDeath);
        eventDao.create(marriageDad);
        eventDao.create(marriageMom);

        fillHelper(femaleNames,maleNames,surnames,locations,personDao,eventDao,
                mom, generations - 1, momBirth.getYear()
        );
        fillHelper(femaleNames,maleNames,surnames,locations,personDao,eventDao,
                dad, generations - 1, dadBirth.getYear()
        );
    }


    private String getRandomName(NameStrings names)
    {
        int i = randInt(0,names.size());//((int) (2*Math.random())) % (names.size() - 1);
        return names.at(i);
    }

    private LocationObject getRandLocation(MultiLocationsObject locations)
    {
        int i = randInt(0,locations.size());//= ((int) (2*Math.random())) % (locations.size() - 1);
        return locations.at(i);
    }

    public int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        //
        // In particular, do NOT do 'Random rand = new Random()' here or you
        // will get not very good / not very random results.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt(max - min) + min;

        return randomNum;
    }

}
