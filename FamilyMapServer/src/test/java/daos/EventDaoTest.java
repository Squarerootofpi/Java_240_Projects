package daos;

import models.AuthToken;
import models.Event;
import models.Person;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class EventDaoTest {
    private Database db;
    private Event ev1;
    private Event ev2;
    private Event ev3;
    private Event ev4;

    private Event allNull;

    @BeforeEach
    public void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        db.clearTable("users"); //Clear table creates its own connection and closes it.
        db.clearTable("persons"); //Clear table creates its own connection and closes it.
        db.clearTable("events"); //Clear table creates its own connection and closes it.
        db.clearTable("authTokens"); //Clear table creates its own connection and closes it.

//        db.openConnection();
//        db.clearTable("events"); //Clear table creates its own connection and closes it.
//        db.closeConnection(true);
        //and a new Person with random data
        ev1 = new Event("crap_username", "123_ID",
                "123123", 1.2323f, 3434.4f, "usa", "provo", "marriage", 1234);

        ev2 = new Event("betterUsername", "1234_ID",
                "Ababa", 1.2f, 34.4f, "usa", "provo", "birth", 3321);
        ev3 = new Event("u4", "12345_ID",
                "Ababa", 1.2f, 34.4f, "usa", "provo", "death", 2342);
        ev4 = new Event("u4", "123456_ID",
                "Ababa", 1.2f, 34.4f, "usa", "provo", "christening", 3322);

        allNull = new Event(null, "anID", null, null, null,
                null, null, null, null);


    }


    @AfterEach
    public void tearDown() throws Exception {
        //here we can get rid of anything from our tests we don't want to affect the rest of our program
        //lets clear the tables so that any data we entered for testing doesn't linger in our files
        db.clearTable("users"); //Clear table creates its own connection and closes it.
        db.clearTable("persons"); //Clear table creates its own connection and closes it.
        db.clearTable("events"); //Clear table creates its own connection and closes it.
        db.clearTable("authTokens"); //Clear table creates its own connection and closes it.


        System.out.println("in teardown");
    }

    /**
     * NO UPDATE IMPLEMENTED, SO NO TESTS TO BE DONE.
     */
    /*
    @Test
    void updatePass() {

    }

    @Test
    void updateFail() {
    }
    */

    @Test
    void readPass() throws Exception {
        //We want to make sure insert works
        Event testA = null;
        Event testB = null;
        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            eDao.create(ev1);
            eDao.create(ev2);
            System.out.println("output");
            db.closeConnection(true);
            //So lets use a find method to get the event that we just put in back out
            conn = db.openConnection();
            eDao.setConn(conn);
            testA = eDao.read(ev1.getEventID());
            testB = eDao.read(ev2.getEventID());

            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(testA);
        assertNotNull(testB);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(testA, ev1);
        assertEquals(testB, ev2);
    }

    @Test
    void readFail() throws Exception {
        //Now let's make sure it is empty, by catching an error on find.
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            //let's try to read things that don't exist
            Event event1 = eDao.read(ev1.getEventID());
            Event event2 = eDao.read("123424");
            Event ev3 = eDao.read("434245");
            //If it reads it as a nonnull, it doesn't pass.
            assertNull(event1);
            assertNull(event2);
            assertNull(ev3);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            System.out.println("DB issue with reading and failing.");
        }

    }

    @Test
    void readAllWhereAssociatedUsernamePass() throws Exception {
        try {
            db.openConnection();

//        AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
//        AuthToken a1 = new AuthToken("123414fsd", ev1.getAssociatedUsername(),
//                "123123");
//        AuthToken a2 = new AuthToken("theauth", ev1.getAssociatedUsername(),
//                "Ababa");
//        AuthToken allNull = new AuthToken(null, null, null);
//
//        authTokenDao.create(a1);
//        authTokenDao.create(a2);
//
//
            EventDao eDao = new EventDao(db.getConnection());
            eDao.create(ev2);
            eDao.create(ev1);
            eDao.create(ev3);
            eDao.create(ev4);

            db.closeConnection(true);

            db.openConnection();
            eDao.setConn(db.getConnection());

            Event[] dualEventsA = eDao.readAllWhereAssociatedUsername(ev4.getAssociatedUsername());
            Event[] dualEventsB = eDao.readAllWhereAssociatedUsername(ev3.getAssociatedUsername());
            //make sure these both return 2 events
            assertEquals(2, dualEventsA.length);
            assertEquals(dualEventsA.length, dualEventsB.length);

            Event[] singleEventA = eDao.readAllWhereAssociatedUsername(ev1.getAssociatedUsername());
            Event[] singleEventB = eDao.readAllWhereAssociatedUsername(ev2.getAssociatedUsername());

            assertEquals(1, singleEventA.length);
            assertEquals(singleEventA.length,singleEventB.length);
            assertNotEquals(singleEventA,singleEventB);

            db.closeConnection(true);
        }
        catch (DataAccessException ex) {
            db.closeConnection(false);

            System.out.println(ex.toString());
            ex.printStackTrace();
        }

    }

    @Test
    void readAllWhereAssociatedUsernameFail() throws Exception { //if the auth doesn't exist
        try {
            db.openConnection();

//
            EventDao eDao = new EventDao(db.getConnection());
            eDao.create(ev2);
            eDao.create(ev1);
            eDao.create(ev3);
            eDao.create(ev4);

            db.closeConnection(true);

            db.openConnection();
            eDao.setConn(db.getConnection());


            Event[] events = eDao.readAllWhereAssociatedUsername("Thisisnotausername");

            assertEquals(0, events.length);

            db.closeConnection(true);
        }
        catch (DataAccessException ex) {
            db.closeConnection(false);

            System.out.println(ex.toString());
            ex.printStackTrace();
        }

    }

    @Test
    void createPass() throws Exception {
        //We want to make sure insert works
        Event compareTest = null;
        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            eDao.create(ev1);
            //System.out.println("output");
            //So lets use a find method to get the event that we just put in back out
            compareTest = eDao.read(ev1.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(ev1, compareTest);
    }

    @org.junit.jupiter.api.Test
    void createFail() throws Exception {
        //lets do this test again but this time lets try to make it fail

        // NOTE: The correct way to test for an exception in Junit 5 is to use an assertThrows
        // with a lambda function. However, lambda functions are beyond the scope of this class
        // so we are doing it another way.
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            //if we call the method the first time it will insert it successfully
            eDao.create(ev1);
            //but our sql table is set up so that "eventID" must be unique. So trying to insert it
            //again will cause the method to throw an exception
            eDao.create(ev1);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            //If we catch an exception we will end up in here, where we can change our boolean to
            //false to show that our function failed to perform correctly
            db.closeConnection(false);
            didItWork = false;
        }
        //Check to make sure that we did in fact enter our catch statement
        assertFalse(didItWork);
        //Since we know our database encountered an error, both instances of insert should have been
        //rolled back. So for added security lets make one more quick check using our find function
        //to make sure that our event is not in the database
        //Set our compareTest to an actual event
        Event compareTest = ev1;
        try {
            Connection conn = db.openConnection();

            EventDao eDao = new EventDao(conn);
            //and then get something back from our find. If the event is not in the database we
            //should have just changed our compareTest to a null object
            compareTest = eDao.read(compareTest.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        //Now make sure that compareTest is indeed null
        assertNull(compareTest);
    }

    /**
     * Not implemented, so NOT TESTING CURRENTLY.
     */
    /*@Test
    void deletePass() {
    }

    @Test
    void deleteFail() {
    }
*/

    @Test
    void clearPass() throws Exception {
        Boolean passes = true;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
//          Add 2 persons to DB
            eDao.create(ev1);
            eDao.create(ev2);
            db.closeConnection(true);
            System.out.println("conn closed");
//
//            //Reopen the connection
            eDao.setConn(db.openConnection());
            //Now make sure we can find them both, they will throw errors if not done right.
            eDao.read(ev1.getEventID());
            eDao.read(ev2.getEventID());

            //now clear it.
            eDao.clear();

            db.closeConnection(true);
        } catch (DataAccessException e) {
            //If we catch an exception we will end up in here, where we can change our boolean to
            //false to show that our function failed to perform correctly
            passes = false;
            db.closeConnection(false);
        }
        //Now let's make sure it is empty, by catching an error on find.
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            //let's try to read things that don't exist that we previously retrieved.
            Event diffEvent = eDao.read(ev1.getEventID());
            //If it reads it as a nonnull, it doesn't pass.
            assertNull(diffEvent);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertTrue(passes);
    }

    @Test
    void deleteWhereAssociatedUsernamePass() throws Exception {
        //We want to make sure insert works
        Event compareTest = null;
        Event compareTest2 = null;
        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            eDao.create(ev1);
            eDao.create(ev2);
            db.closeConnection(true);

            conn = db.openConnection();
            eDao.setConn(conn);
            assertNotNull(eDao.read(ev1.getEventID())); //making sure it inserted
            assertNotNull(eDao.read(ev2.getEventID())); //making sure it inserted
            eDao.deleteWhereAssociatedUsername(ev1.getAssociatedUsername());
            db.closeConnection(true);

            conn = db.openConnection();
            eDao.setConn(conn);
            //System.out.println("output");
            //So lets use a find method to get the event that we just put in back out
            compareTest = eDao.read(ev1.getEventID());
            compareTest2 = eDao.read(ev2.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNull(compareTest);
        assertNotNull(compareTest2);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        //assertNotEquals(ev1, compareTest);
        assertEquals(ev2, compareTest2);
    }

    @Test
    void deleteWhereAssociatedUsernameFail() throws Exception {
        //We want to make sure insert works
        Event compareTest = null;
        Event compareTest2 = null;
        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            eDao.create(ev1);
            eDao.create(ev2);
            db.closeConnection(true);

            conn = db.openConnection();
            eDao.setConn(conn);
            assertNotNull(eDao.read(ev1.getEventID())); //making sure it inserted
            assertNotNull(eDao.read(ev2.getEventID())); //making sure it inserted
            //not a username
            eDao.deleteWhereAssociatedUsername("nonononkhjghhgddfhgj");
            db.closeConnection(true);

            conn = db.openConnection();
            eDao.setConn(conn);
            //System.out.println("output");
            //So lets use a find method to get the event that we just put in back out
            compareTest = eDao.read(ev1.getEventID());
            compareTest2 = eDao.read(ev2.getEventID());

            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        assertNotNull(compareTest2);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        //assertNotEquals(ev1, compareTest);

    }
}