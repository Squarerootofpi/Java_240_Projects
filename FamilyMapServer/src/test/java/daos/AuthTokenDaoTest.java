package daos;

import models.AuthToken;
import models.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDaoTest {
    private Database db;
    private AuthToken a1;
    private AuthToken a2;
    private AuthToken allNull;

    @BeforeEach
    public void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        db.clearTable("authTokens"); //Clear table creates its own connection and closes it.

//        db.openConnection();
//        db.clearTable("events"); //Clear table creates its own connection and closes it.
//        db.closeConnection(true);
        //and a new Person with random data
        a1 = new AuthToken("123414fsd", "squar",
                "123123");

        a2 = new AuthToken("theauth", "pooop",
                "Ababa");

        allNull = new AuthToken(null, null, null);

    }

    @AfterEach
    public void tearDown() throws Exception {
        //here we can get rid of anything from our tests we don't want to affect the rest of our program
        //lets clear the tables so that any data we entered for testing doesn't linger in our files
        db.clearTable("authTokens"); //Clear table creates its own connection and closes it.
        System.out.println("in teardown");
    }

    /**
     * NOT IMPLEMENTED, SO NO TESTS MADE FOR #UPDATE AS OF YET
     */
    /*@Test
    void update() {

    }
    */

    @Test
    void readPass() throws Exception {
        //We want to make sure insert works
        AuthToken testA = null;
        AuthToken testB = null;
        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            AuthTokenDao eDao = new AuthTokenDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            eDao.create(a1);
            eDao.create(a2);
            System.out.println("output");
            db.closeConnection(true);
            //So lets use a find method to get the event that we just put in back out
            conn = db.openConnection();
            eDao.setConn(conn);
            testA = eDao.read(a1.getAuthToken());
            testB = eDao.read(a2.getAuthToken());

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
        assertEquals(testA, a1);
        assertEquals(testB, a2);
    }

    @Test
    void readFail() throws Exception {
        //Now let's make sure it is empty, by catching an error on find.
        try {
            Connection conn = db.openConnection();
            AuthTokenDao eDao = new AuthTokenDao(conn);
            //let's try to read things that don't exist
            AuthToken auth1 = eDao.read(a1.getAuthToken());
            AuthToken auth2 = eDao.read("123424");
            AuthToken auth3 = eDao.read("434245");
            //If it reads it as a nonnull, it doesn't pass.
            assertNull(auth1);
            assertNull(auth2);
            assertNull(auth3);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            System.out.println("DB issue with reading and failing.");
        }
    }
        @Test
    void createPass() throws Exception {
            //We want to make sure insert works
            AuthToken compareTest = null;
            try {
                //Let's get our connection and make a new DAO
                Connection conn = db.openConnection();
                AuthTokenDao eDao = new AuthTokenDao(conn);
                //While insert returns a bool we can't use that to verify that our function actually worked
                //only that it ran without causing an error
                eDao.create(a1);
                //System.out.println("output");
                //So lets use a find method to get the event that we just put in back out
                compareTest = eDao.read(a1.getAuthToken());
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
            assertEquals(a1, compareTest);
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
            AuthTokenDao eDao = new AuthTokenDao(conn);
            //if we call the method the first time it will insert it successfully
            eDao.create(a1);
            //but our sql table is set up so that "eventID" must be unique. So trying to insert it
            //again will cause the method to throw an exception
            eDao.create(a1);
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
        AuthToken compareTest = a1;
        try {
            Connection conn = db.openConnection();

            AuthTokenDao eDao = new AuthTokenDao(conn);
            //and then get something back from our find. If the event is not in the database we
            //should have just changed our compareTest to a null object
            compareTest = eDao.read(compareTest.getAuthToken());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        //Now make sure that compareTest is indeed null
        assertNull(compareTest);
    }

    @Test
    void clearPass() throws Exception {
        Boolean passes = true;
        try {
            Connection conn = db.openConnection();
            AuthTokenDao eDao = new AuthTokenDao(conn);
//          Add 2 persons to DB
            eDao.create(a1);
            eDao.create(a2);
            db.closeConnection(true);
            System.out.println("conn closed");
//
//            //Reopen the connection
            eDao.setConn(db.openConnection());
            //Now make sure we can find them both, they will throw errors if not done right.
            eDao.read(a1.getAuthToken());
            eDao.read(a2.getAuthToken());

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
            Event diffEvent = eDao.read(a1.getAuthToken());
            //If it reads it as a nonnull, it doesn't pass.
            assertNull(diffEvent);
            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertTrue(passes);
    }
}