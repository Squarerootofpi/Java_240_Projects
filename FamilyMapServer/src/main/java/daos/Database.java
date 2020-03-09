package daos;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Stores the information for the database connection, and opens and closes connection.
 * Utilized by dao classes.
 */
public class Database {
    /**
     * The connection to the database; sql
     */
    private Connection conn;

    //Whenever we want to make a change to our database we will have to open a connection and use
    //Statements created by that connection to initiate transactions

    /**
     * Opens the connection to the database.
     * INCLUDES THE CONNECTION_URL for the Database.
     *
     * @return gives the connection to the db, if needed.
     * @throws DataAccessException if it fails to open or access right.
     */
    public Connection openConnection() throws DataAccessException {
        try {

            if (!(conn == null)) {
                throw new DataAccessException("You're an idiot. You tried to open the database a second time.");
            }
            //The Structure for this Connection is driver:language:path
            //The path assumes you start in the root of your project unless given a non-relative path
            final String CONNECTION_URL = "jdbc:sqlite:familymap.db";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    /**
     * Gives the user the connection: and if it's not created it opens the connection.
     *
     * @return gives the connection to the db
     * @throws DataAccessException if it fails to open or access right.
     */
    public Connection getConnection() throws DataAccessException {
        if(conn == null) {
            return openConnection();
        } else {
            return conn;
        }
    }

    //When we are done manipulating the database it is important to close the connection. This will
    //End the transaction and allow us to either commit our changes to the database or rollback any
    //changes that were made before we encountered a potential error.

    //IMPORTANT: IF YOU FAIL TO CLOSE A CONNECTION AND TRY TO REOPEN THE DATABASE THIS WILL CAUSE THE
    //DATABASE TO LOCK. YOUR CODE MUST ALWAYS INCLUDE A CLOSURE OF THE DATABASE NO MATTER WHAT ERRORS
    //OR PROBLEMS YOU ENCOUNTER

    /**
     * Closes the connection and commits or rollsback the transaction.
     *
     * @param commit says whether the program wants to commit or rollback the db.
     * @throws DataAccessException because it couldn't close DB for some reason.
     */
    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (conn == null)
            {
                throw new DataAccessException("Attempted to close a connection which was null");
            }
            if (commit) {
                //This will commit the changes to the database
                conn.commit();
            } else {
                //If we find out something went wrong, pass a false into closeConnection and this
                //will rollback any changes we made during this connection
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    /**
     *
     * @throws DataAccessException
     */
    public void clearTable(String tableName) throws DataAccessException
    {
        Connection tempConn = openConnection();

        try (Statement stmt = tempConn.createStatement()){
            String sql = "DELETE FROM " + tableName;
            System.out.println(sql);
            stmt.executeUpdate(sql);
            closeConnection(true);
        } catch (SQLException e) {
            closeConnection(false);
            throw new DataAccessException("SQL Error encountered while clearing table " + tableName);
        }
    }

}

