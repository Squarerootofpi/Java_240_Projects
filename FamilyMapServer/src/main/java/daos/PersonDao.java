package daos;

import models.Event;
import models.Person;

import java.sql.*;
import java.util.ArrayList;

/**
 * {@inheritDoc}
 * Accessors for Person Table in DB
 */
public class PersonDao implements IDao {
    /**
     * Connection for the Dao to access
     */
    private Connection conn;

    /**
     * PersonDao constructor
     *
     * @param conn the passed in connection from the service.
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Person update function for the database: updates with a given model.
     *
     * NOT IMPLEMENTED, SO NOT BEING TESTED CURRENTLY
     *
     * @param person the model to identify the row for the Dao to attempt to update
     * @return says whether it updated properly
     */
    public boolean update(Person person) {
        return false;
    }

    /**
     * Person table reading function: accesses the db and returns
     * the
     *
     * @param id The ID (PK) of the model to find
     * @return the model associated with the ID
     */
    public Person read(String id)
            throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM persons WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            rs = stmt.executeQuery();
                /*
                "associatedUsername"	TEXT NOT NULL,
	"personID"	TEXT NOT NULL UNIQUE,
	"firstName"	TEXT NOT NULL,
	"lastName"	TEXT NOT NULL,
	"gender"	TEXT NOT NULL,
	"fatherID"	TEXT,
	"motherID"	TEXT,
	"spouseID"	TEXT,
                 */
            if (rs.next()) {
                char genderChar = rs.getString("gender").charAt(0);
                person = new Person(rs.getString("associatedUsername"), rs.getString("personID"),
                        rs.getString("firstName"), rs.getString("lastName"), genderChar);
                person.setFatherID(rs.getString("fatherID"));
                person.setMotherID(rs.getString("motherID"));
                person.setSpouseID(rs.getString("spouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * Read all people NOT IMPLEMENTED COMPLETELY YET!!!
     */
    public Person[] readAllWhereAssociatedUsername(String userName) throws DataAccessException {
        Person person;// = new Person;
        ResultSet rs = null;
        String sql = "SELECT * FROM persons WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            ArrayList<Person> people = new ArrayList<Person>();
            //Person[] peeps = new Person[];
            while (rs.next()) {
                char genderChar = rs.getString("gender").charAt(0);
                person = new Person(rs.getString("associatedUsername"), rs.getString("personID"),
                        rs.getString("firstName"), rs.getString("lastName"), genderChar);
                person.setFatherID(rs.getString("fatherID"));
                person.setMotherID(rs.getString("motherID"));
                person.setSpouseID(rs.getString("spouseID"));
                people.add(person);
            }

            return people.toArray(new Person[people.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * Person add-row function, accepts a model to do so.
     *
     * @param person the model for the Dao to attempt to create a row from
     * @return indicating if the row was successfully created.
     */
    public boolean create(Person person) throws DataAccessException {
        String sql = "INSERT INTO persons (associatedUsername, personID, firstName, lastName, gender, fatherID, " +
                "motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        /*
        "associatedUsername"	TEXT NOT NULL,
	"personID"	TEXT NOT NULL UNIQUE,
	"firstName"	TEXT NOT NULL,
	"lastName"	TEXT NOT NULL,
	"gender"	TEXT NOT NULL,
	"fatherID"	TEXT,
	"motherID"	TEXT,
	"spouseID"	TEXT,
         */
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getAssociatedUsername());
            stmt.setString(2, person.getPersonID());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, String.valueOf(person.getGender()));
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() throws DataAccessException {
        //Database db = new Database();
        //db.clearTable("persons");

        try (Statement stmt = conn.createStatement()) {
            String sql = "DELETE FROM persons";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing table persons");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(String id) {
        return false;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void deleteWhereAssociatedUsername(String id) throws DataAccessException {
        Person person;
        //ResultSet rs = null;
        String sql = "DELETE FROM persons WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.execute();


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        }
        return;
    }
}
