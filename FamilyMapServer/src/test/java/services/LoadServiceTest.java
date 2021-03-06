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
import results.ErrorMessage;
import results.GoodLogin;
import results.PersonRes;
import results.Response;

import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest {
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
                "u4", "3@gmail.com", "pi", 'm', "1");
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

        login1 = new Login(regRequest1.getUserName(),regRequest1.getPassword());
        login2 = new Login(regRequest2.getUserName(),regRequest2.getPassword());

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
            r = registerService.serve(regRequest1);
            //Make sure an empty load deletes everything.
            LoadService loadService = new LoadService();
            loadService.serve(emptyLoad);

            LoginService loginService = new LoginService();
            r = loginService.serve(login1);
            assertEquals(r.getClass(), ErrorMessage.class);


            loadService.serve(fullLoad);
            //Test if can register, shouldn't be able to.
            r = registerService.serve(regRequest1);
            assertEquals(r.getClass(), ErrorMessage.class);

            //Test if can login. Should be able to.
            r = loginService.serve(login1);
            assertEquals(r.getClass(), GoodLogin.class);
            String auth = ((GoodLogin) r).getAuthToken();

            //PersonService personService = new PersonService();
            //PersonRes comparePerson1 = personService.serve(auth,person.getPersonID());
            PersonService personService = new PersonService();
            Response person1 = personService.serve(auth,person.getPersonID());

            assertEquals(person1.getClass(), PersonRes.class);
            PersonRes eR = ((PersonRes) person1);
            Person comparePerson = new Person(eR.getAssociatedUsername(),eR.getPersonID(), eR.getFirstName(), eR.getLastName(), eR.getGender());
            assertEquals(comparePerson, person);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.toString());
        }
    }

    /**
     * No test for failing of load, since it is straightforward.
     * @throws Exception
     */
    /*@Test
    void serveFail() throws Exception {
        try {
            RegisterService registerService = new RegisterService();
            //make sure that they have to have a username.
            registerService.serve(regRequest1);
            Response r;
            //testing if empty username
            login1.setUserName("");
            LoginService loginService = new LoginService();
            r = loginService.serve(login1);

            assertEquals(r.getClass(), ErrorMessage.class);
            r = registerService.serve(regRequest2);
            assertEquals(r.getClass(), GoodLogin.class);
            String auth = ((GoodLogin) r).getAuthToken();

            login2.setPassword("this is the wrong password");
            //good login, not test to see if we can reregister.
            r = loginService.serve(login2);
            assertEquals(r.getClass(), ErrorMessage.class);

        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.toString());
        }
    }*/
}