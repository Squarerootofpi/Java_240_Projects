package daos;

import models.*;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.BeforeEach.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

import static org.junit.jupiter.api.BeforeAll.*;
import static org.junit.jupiter.api.Test.*;


class PersonDaoTest {
    private Database db;
    private Person person;
    private Person person2;
    private Person nonNullPerson;
    private Person nullValuesExceptID;

    // Paul: Working with deadlines.
    @BeforeEach
    public void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new Person with random data
        person = new Person("crap_username", "123_ID",
                "joseph", "steed", 'm');

        nonNullPerson = new Person("squar", "678_ID",
                "joseph", "steed", 'm');
        nonNullPerson.setSpouseID("1234");
        nonNullPerson.setMotherID("5678");
        nonNullPerson.setFatherID("9012");

        person2 = new Person("crap_username", "1234_ID",
                "james", "steed", 'm');



        nullValuesExceptID = new Person("squar", "678_ID",
                "joseph", "steed", 'm');
        nullValuesExceptID.setAssociatedUsername(null);
        nullValuesExceptID.setFirstName(null);
        nullValuesExceptID.setLastName(null);
        nullValuesExceptID.setGender(null);
    }


    @AfterEach
    public void tearDown() throws Exception {
        //here we can get rid of anything from our tests we don't want to affect the rest of our program
        //lets clear the tables so that any data we entered for testing doesn't linger in our files
        //db.openConnection();
        db.clearTable("persons"); //Clear table creates its own connection and closes it.
        System.out.println("in teardown");
        //db.closeConnection(true);
    }

    /**
     * NOT IMPLEMENTED, SO CURRENT UPDATE PASS NOT ACTIVATED
     * @throws Exception
     */
    /*@org.junit.jupiter.api.Test
    void updatePass() throws Exception {

    }

    @org.junit.jupiter.api.Test
    void updateFail() {
    }
    */

    @org.junit.jupiter.api.Test
    void readPass() throws Exception {
        //We want to make sure insert works
        Person testNonNullPerson = null;
        Person testPerson = null;
        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            pDao.create(nonNullPerson);
            pDao.create(person);
            System.out.println("output");
            //So lets use a find method to get the event that we just put in back out
            testNonNullPerson = pDao.read(nonNullPerson.getPersonID());
            testPerson = pDao.read(person.getPersonID());

            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(testNonNullPerson);
        assertNotNull(testPerson);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(nonNullPerson, testNonNullPerson);
        assertEquals(person, testPerson);
    }

    @org.junit.jupiter.api.Test
    void readFail() throws Exception {
        //Now let's make sure it is empty, by catching an error on find.
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //let's try to read things that don't exist
            Person diffPerson = pDao.read(nonNullPerson.getPersonID());
            Person bPerson = pDao.read("123424");
            Person cPerson = pDao.read("434245");
            //If it reads it as a nonnull, it doesn't pass.
            assertNull(diffPerson);
            assertNull(bPerson);
            assertNull(cPerson);
            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            System.out.println("DB issue with reading and failing.");
        }
    }

    @org.junit.jupiter.api.Test
    void createPass() throws Exception {
        //We want to make sure insert works
        Person compareTest = null;
        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            pDao.create(nonNullPerson);
            System.out.println("output");
            //So lets use a find method to get the event that we just put in back out
            compareTest = pDao.read(nonNullPerson.getPersonID());
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
        assertEquals(nonNullPerson, compareTest);
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
            PersonDao pDao = new PersonDao(conn);
            //if we call the method the first time it will insert it successfully
            pDao.create(nonNullPerson);
            //but our sql table is set up so that "eventID" must be unique. So trying to insert it
            //again will cause the method to throw an exception
            pDao.create(nonNullPerson);
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
        Person compareTest = nonNullPerson;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //and then get something back from our find. If the event is not in the database we
            //should have just changed our compareTest to a null object
            compareTest = pDao.read(compareTest.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        //Now make sure that compareTest is indeed null
        assertNull(compareTest);
    }


    @org.junit.jupiter.api.Test
    void clearPass() throws Exception {
        Boolean passes = true;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
//          Add 2 persons to DB
            pDao.create(nonNullPerson);
            pDao.create(person);
            db.closeConnection(true);
            System.out.println("conn closed");
//
//            //Reopen the connection
            pDao.setConn(db.openConnection());
            //Now make sure we can find them both, they will throw errors if not done right.
            pDao.read(nonNullPerson.getPersonID());
            pDao.read(person.getPersonID());

            //now clear it.
            pDao.clear();

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
            PersonDao pDao = new PersonDao(conn);
            //let's try to read things that don't exist that we previously retrieved.
            Person diffPerson = pDao.read(nonNullPerson.getPersonID());
            //If it reads it as a nonnull, it doesn't pass.
            assertNull(diffPerson);
            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertTrue(passes);
    }

    /**
     * NOT IMPLEMENTED, SO DELETE NOT BEING TESTED CURRENTLY.
     */
    /*@org.junit.jupiter.api.Test
    void deletePass() {

    }

    @org.junit.jupiter.api.Test
    void deleteFail() {

    }
*/
    @Test
    void deleteWhereAssociatedUsernamePass() throws Exception {
        //We want to make sure insert works
        Person compareTest = null;
        Person compareTest2 = null;
        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            pDao.create(person);
            pDao.create(nonNullPerson);
            db.closeConnection(true);

            conn = db.openConnection();
            pDao.setConn(conn);
            assertNotNull(pDao.read(person.getPersonID())); //making sure it inserted
            assertNotNull(pDao.read(nonNullPerson.getPersonID())); //making sure it inserted
            pDao.deleteWhereAssociatedUsername(person.getAssociatedUsername());
            db.closeConnection(true);

            conn = db.openConnection();
            pDao.setConn(conn);
            //System.out.println("output");
            //So lets use a find method to get the event that we just put in back out
            compareTest = pDao.read(person.getPersonID());
            compareTest2 = pDao.read(nonNullPerson.getPersonID());
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
        //assertNotEquals(person, compareTest);
        assertEquals(nonNullPerson, compareTest2);
    }

    @Test
    void deleteWhereAssociatedUsernameFail() throws Exception {
        //We want to make sure insert works
        Person compareTest = null;
        Person compareTest2 = null;
        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            pDao.create(person);
            pDao.create(nonNullPerson);
            db.closeConnection(true);

            conn = db.openConnection();
            pDao.setConn(conn);
            assertNotNull(pDao.read(person.getPersonID())); //making sure it inserted
            assertNotNull(pDao.read(nonNullPerson.getPersonID())); //making sure it inserted
            pDao.deleteWhereAssociatedUsername("This is a fake I" +
                    "d");
            db.closeConnection(true);

            conn = db.openConnection();
            pDao.setConn(conn);
            //System.out.println("output");
            //So lets use a find method to get the event that we just put in back out
            compareTest = pDao.read(person.getPersonID());
            compareTest2 = pDao.read(nonNullPerson.getPersonID());
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
        assertEquals(person, compareTest);
        assertEquals(nonNullPerson, compareTest2);
    }


    @Test
    void readAllWhereAssociatedUsernamePass() throws Exception {
        try {
            db.openConnection();

            PersonDao pDao = new PersonDao(db.getConnection());
            pDao.create(person);
            pDao.create(person2);
            pDao.create(nonNullPerson);

            db.closeConnection(true);

            db.openConnection();
            pDao.setConn(db.getConnection());

            //have same username
            Person[] dualPersonsA = pDao.readAllWhereAssociatedUsername(person.getAssociatedUsername());
            Person[] dualPersonsB = pDao.readAllWhereAssociatedUsername(person2.getAssociatedUsername());
            //make sure these both return 2 events
            assertEquals(2, dualPersonsA.length);
            assertEquals(2, dualPersonsB.length);

            Person[] singlePersonA = pDao.readAllWhereAssociatedUsername(nonNullPerson.getAssociatedUsername());

            assertEquals(1, singlePersonA.length);

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
            PersonDao pDao = new PersonDao(db.getConnection());
            pDao.create(person);
            pDao.create(person2);
            pDao.create(nonNullPerson);

            db.closeConnection(true);

            db.openConnection();
            pDao.setConn(db.getConnection());


            Person[] persons = pDao.readAllWhereAssociatedUsername("Thisisnotausername");

            assertEquals(0, persons.length);

            db.closeConnection(true);
        }
        catch (DataAccessException ex) {
            db.closeConnection(false);

            System.out.println(ex.toString());
            ex.printStackTrace();
        }

    }


}