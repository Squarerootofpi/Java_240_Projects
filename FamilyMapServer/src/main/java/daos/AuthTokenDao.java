package daos;


import models.AuthToken;
import models.Event;

import java.sql.*;

/**
 * {@inheritDoc}
 * Accessors for AuthToken Table in DB
 */
public class AuthTokenDao implements IDao {
    /**
     * Connection for the Dao to access
     */
    private Connection conn;

    /**
     * Constructor for authDau, stores the connection as a private
     * variable, and references that same connection in its lifetime
     *
     * @param conn the DB connection given to it by the service.
     */
    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * AuthToken update function for the database: updates with a given model.
     *
     * @param authToken the model to identify the row for the Dao to attempt to update
     * @return says whether it updated properly
     */
    public boolean update(AuthToken authToken) {
        return false;
    }

    /**
     * AuthToken table reading function
     *
     * @param id The ID (PK) of the model to find
     * @return the model associated with the ID
     */
    public AuthToken read(String id) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM authTokens WHERE authToken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            rs = stmt.executeQuery();
                /*
                	"authToken"	TEXT NOT NULL UNIQUE,
	"userName"	TEXT NOT NULL,
	"personID"	TEXT NOT NULL,
                 */
            if (rs.next()) {
                //char genderChar = rs.getString("gender").charAt(0);
                authToken = new AuthToken(rs.getString("authToken"),rs.getString("userName"),rs.getString("personID"));
                return authToken;
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
     * Authtoken add-row function, accepts a model to do so.
     *
     * @param authToken the model for the Dao to attempt to create a row from
     * @return indicating if the row was successfully created.
     */
    public boolean create(AuthToken authToken) throws DataAccessException {
        String sql = "INSERT INTO authTokens (authToken, userName, personID) VALUES(?,?,?)";
        /*
        "authToken"	TEXT NOT NULL UNIQUE,
	"userName"	TEXT NOT NULL,
	"personID"	TEXT NOT NULL,
                 */
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUserName());
            stmt.setString(3, authToken.getPersonID());

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
            String sql = "DELETE FROM authTokens";
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

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
