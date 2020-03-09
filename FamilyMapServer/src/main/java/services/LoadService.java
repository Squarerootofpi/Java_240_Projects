package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.*;
import models.Event;
import models.Person;
import models.User;
import requests.Load;
import results.ErrorMessage;
import results.Response;
import results.SuccessMessage;

/**
 * Load Service. Clears the DB, and loads it with people, events, and users
 * Only one public function, which handlers call when they have parsed
 * a valid request object.
 * A connection is made within the serve function, and closed within it,
 * so no private data members are needed.
 */
public class LoadService {

    /**
     * Loads a database (clears it first) with all new data.
     * Creates an SQL DB connection, and closes it within the function.
     *
     * @param load The load object containing the data to add to the DB
     * @return returns an error response object or a success response
     */
    public Response serve(Load load) throws DataAccessException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        //Clear everything initially.
        ClearService clearService = new ClearService();
        clearService.serve();

        Database db = new Database();
        try {
            db.openConnection();

            int numUsers = 0;
            int numPersons = 0;
            int numEvents = 0;

            EventDao eventDao = new EventDao(db.getConnection());
            for (Event e: load.getEvents()
                 ) {
                eventDao.create(e);
                ++numEvents;
            }

            PersonDao personDao = new PersonDao(db.getConnection());
            for (Person p: load.getPersons()
            ) {
                personDao.create(p);
                ++numPersons;
            }

            UserDao userDao = new UserDao(db.getConnection());
            for (User u: load.getUsers()
            ) {
                userDao.create(u);
                ++numUsers;
            }

            db.closeConnection(true); //“Successfully added X users, Y persons, and Z events to the database
            return new SuccessMessage("Successfully added " + numUsers + " users, " + numPersons + " persons, and " + numEvents + " events to the database.");
        }
        catch (Exception ex) {
            db.closeConnection(false);
            return new ErrorMessage("Failed clearing tables, error.");
        }
    }
}
