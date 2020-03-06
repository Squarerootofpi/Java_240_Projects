package daos;

import models.Event;
import models.Person;

import java.sql.*;

/**
 * {@inheritDoc}
 * Accessors for Event Table in DB
 */
public class EventDao implements IDao {
    /**
     * Connection for the Dao to access
     */
    private Connection conn;

    /**
     * EventDao constructor
     *
     * @param conn the passed in connection from the service.
     */
    public EventDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Event update function for the database: updates with a given model.
     *
     * @param event the model to identify the row for the Dao to attempt to update
     * @return says whether it updated properly
     */
    public boolean update(Event event) {
        return false;
    }

    /**
     * Event table reading function
     *
     * @param id The ID (PK) of the model to find
     * @return the model associated with the ID
     */
    public Event read(String id) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM events WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            rs = stmt.executeQuery();
                /*
                	"associatedUsername"	TEXT NOT NULL,
	"eventID"	TEXT NOT NULL UNIQUE,
	"personID"	TEXT NOT NULL,
	"latitude"	NUMERIC NOT NULL,
	"longitude"	NUMERIC NOT NULL,
	"country"	TEXT NOT NULL,
	"city"	TEXT NOT NULL,
	"eventType"	TEXT NOT NULL,
	"year"	INTEGER NOT NULL,
	PRIMARY KEY("eventID")

                 */
            if (rs.next()) {
                //char genderChar = rs.getString("gender").charAt(0);
                event = new Event( rs.getString( "associatedUsername"), rs.getString("eventID"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"),rs.getString("city"),rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
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
     * For getting all events
     */
    public Event[] readAll() {


        return null;
    }

    /**
     * Event add-row function, accepts a model to do so.
     *
     * @param event the model for the Dao to attempt to create a row from
     * @return indicating if the row was successfully created.
     */
    public boolean create(Event event) throws DataAccessException {
        String sql = "INSERT INTO events (associatedUsername, eventID, personID, latitude, longitude, country, " +
                "city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        /*
    "associatedUsername"	TEXT NOT NULL,
	"eventID"	TEXT NOT NULL UNIQUE,
	"personID"	TEXT NOT NULL,
	"latitude"	NUMERIC NOT NULL,
	"longitude"	NUMERIC NOT NULL,
	"country"	TEXT NOT NULL,
	"city"	TEXT NOT NULL,
	"eventType"	TEXT NOT NULL,
	"year"	INTEGER NOT NULL,
	PRIMARY KEY("eventID")
                 */
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getAssociatedUsername());
            stmt.setString(2, event.getEventID());
            stmt.setString(3, event.getPersonID());
            stmt.setString(4, String.valueOf(event.getLatitude()));
            stmt.setString(5, String.valueOf(event.getLongitude()));
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setString(9, String.valueOf(event.getYear()));

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

        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM events";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing table users");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(String id) {
        return false;
    }

    /**
     *
     * @param id the associatedUsername of the user to have events removed.
     * @throws DataAccessException
     */
    public void deleteWhereAssociatedUsername(String id) throws DataAccessException {
        Event event;
        //ResultSet rs = null;
        String sql = "DELETE FROM events WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.execute();
                /*
                	"associatedUsername"	TEXT NOT NULL,
                    "eventID"	TEXT NOT NULL UNIQUE,
                    "personID"	TEXT NOT NULL,
                    "latitude"	NUMERIC NOT NULL,
                    "longitude"	NUMERIC NOT NULL,
                    "country"	TEXT NOT NULL,
                    "city"	TEXT NOT NULL,
                    "eventType"	TEXT NOT NULL,
                    "year"	INTEGER NOT NULL,
                    PRIMARY KEY("eventID")
                 */

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        }
        return;
    }



    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
