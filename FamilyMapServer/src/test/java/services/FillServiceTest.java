package services;

import daos.Database;
import models.Event;
import models.Person;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.Load;
import requests.Login;
import requests.Register;
import results.*;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest {
    private Database db;
    private Register regRequest1;
    private  Register regRequest2;
    private Load emptyLoad;
    private  Load fullLoad;
    private Login login1;
    private  Login login2;
    private Login falseLogin;
    private Person person;
    private Person nonNullPerson;
    private User aUser;
    private User bUser;


    private Event ev1;
    private Event ev2;
    private Event ev3; //same username
    private Event ev4; //same username


    @BeforeEach
    void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();

        //db.openConnection();
        db.clearTable("users"); //Clear table creates its own connection and closes it.
        db.clearTable("persons"); //Clear table creates its own connection and closes it.
        db.clearTable("events"); //Clear table creates its own connection and closes it.
        db.clearTable("authTokens"); //Clear table creates its own connection and closes it.

        System.out.println("in teardown");
        //db.closeConnection(true);

        aUser = new User("joseph", "steed",
                "squar", "3@gmail.com", "pi", 'm', "1");
        bUser = new User("james", "dasher", "betterUsername", "g@gmail.com",
                "jo", 'm', "2");
        person = new Person("u4", "123_ID",
                "joseph", "steed", 'm');

        nonNullPerson = new Person("squar", "678_ID",
                "joseph", "steed", 'm');
        nonNullPerson.setSpouseID("1234");
        nonNullPerson.setMotherID("5678");
        nonNullPerson.setFatherID("9012");

        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new Person with random data
        ev1 = new Event("crap_username", "123_ID",
                "123123", 1.2323f, 3434.4f, "usa", "provo", "marriage", 1234);

        ev2 = new Event("betterUsername", "1234_ID",
                "Ababa", 1.2f, 34.4f, "usa", "provo", "birth", 3321);
        ev3 = new Event("u4", "12345_ID",
                "Ababa", 1.2f, 34.4f, "usa", "provo", "death", 2342);
        ev4 = new Event("u4", "123456_ID",
                "Ababa", 1.2f, 34.4f, "usa", "provo", "christening", 3322);
        regRequest1 = new Register("joseph", "steed", "u4", "3@gmail.com",
                "pi", 'm');
        regRequest2 = new Register("james", "dasher", "betterUsername", "g@gmail.com",
                "jo", 'm');

        emptyLoad = new Load(new User[0],new Person[0],new Event[0]);
        Event[] events = new Event[4];
        Person[] persons = new Person[2];
        User[] users = new User[2];
        events[0] = ev1;
        events[1] = ev2;
        events[2] = ev3;
        events[3] = ev4;
        persons[0] = person;
        persons[1] = nonNullPerson;
        users[0] = aUser;
        users[1] = bUser;

        fullLoad = new Load(users,persons,events);

    }

    @AfterEach
    void tearDown() throws Exception {
        //here we can get rid of anything from our tests we don't want to affect the rest of our program
        //lets clear the tables so that any data we entered for testing doesn't linger in our files
        //db.openConnection();
        db.clearTable("users"); //Clear table creates its own connection and closes it.
        db.clearTable("persons"); //Clear table creates its own connection and closes it.
        db.clearTable("events"); //Clear table creates its own connection and closes it.
        db.clearTable("authTokens"); //Clear table creates its own connection and closes it.

        System.out.println("in teardown");
        //db.closeConnection(true);
    }

    @Test
    void servePass() throws Exception {

        try {

            RegisterService registerService = new RegisterService();
            Response r = registerService.serve(regRequest1);
            //This will fill it with people, an authToken, and a user and events.
            assertEquals(r.getClass(), GoodLogin.class);
            String auth = ((GoodLogin) r).getAuthToken();

            FillService fillService = new FillService();
            fillService.serve(regRequest1.getUserName(),2);

            //Let's make sure we filled the events the right amount
            AllEventsService allEventsService = new AllEventsService();
            Response allEvents = allEventsService.serve(auth);
            assertEquals(allEvents.getClass(), AllEvents.class);
            AllEvents allEvents1 = ((AllEvents) allEvents);
            System.out.println(allEvents1.size());
            assertEquals(allEvents1.size(),19);

            AllPeopleService allPeopleService = new AllPeopleService();
            Response allPeople = allPeopleService.serve(auth);
            assertEquals(allPeople.getClass(), AllPeople.class);
            AllPeople allPeople1 = ((AllPeople) allPeople);
            System.out.println(allPeople1.size());
            assertEquals(allPeople1.size(),7);


        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.toString());
        }
    }


    @Test
    void serveFail() throws Exception {
        try {

            RegisterService registerService = new RegisterService();
            Response r = registerService.serve(regRequest1);
            //This will fill it with people, an authToken, and a user and events.
            assertEquals(r.getClass(), GoodLogin.class);
            String auth = ((GoodLogin) r).getAuthToken();

            FillService fillService = new FillService();
            Response response = fillService.serve(regRequest1.getUserName(),-1);
            assertEquals(response.getClass(),ErrorMessage.class);

            response = fillService.serve("This is not a username....",3);
            assertEquals(response.getClass(),ErrorMessage.class);


        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.toString());
        }
    }


}