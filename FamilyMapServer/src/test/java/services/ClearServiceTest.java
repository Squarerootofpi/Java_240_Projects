package services;

import daos.Database;
import models.Event;
import models.Person;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {
    private Database db;
    private User aUser;
    private User bUser;
    private Person person;
    private Person nonNullPerson;
    private Event ev1;


    @BeforeEach
    void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        db.openConnection();
        db.clearTable("users"); //Clear table creates its own connection and closes it.
        db.clearTable("persons"); //Clear table creates its own connection and closes it.
        db.clearTable("events"); //Clear table creates its own connection and closes it.
        db.clearTable("authTokens"); //Clear table creates its own connection and closes it.

        System.out.println("in teardown");
        db.closeConnection(true);

        aUser = new User("joseph", "steed",
                "squarerootofpi", "asdf@gmail.com", "fsddkjf", 'm', "1");
        bUser = new User("brad", "pelvig",
                "sjdkfjkf", "moop@gmail.com", "fjfjfj", 'm', "2");
        person = new Person("crap_username", "123_ID",
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
                "123123", 1.2323f, 3434.4f,"usa","provo","marriage", 1234);



    }

    @AfterEach
    void tearDown() throws Exception {
        //here we can get rid of anything from our tests we don't want to affect the rest of our program
        //lets clear the tables so that any data we entered for testing doesn't linger in our files
        db.openConnection();
        db.clearTable("users"); //Clear table creates its own connection and closes it.
        db.clearTable("persons"); //Clear table creates its own connection and closes it.
        db.clearTable("events"); //Clear table creates its own connection and closes it.
        db.clearTable("authTokens"); //Clear table creates its own connection and closes it.

        System.out.println("in teardown");
        db.closeConnection(true);
    }

    @Test
    void serve() throws Exception {

    }
}