package services;

import daos.*;
import results.ErrorMessage;
import results.Response;
import results.SuccessMessage;

import java.sql.Connection;

/**
 * Clear All Service.
 * Only one public function, which handlers call when they have parsed
 * a valid request object.
 * A connection is made within the serve function, and closed within it,
 * so no private data members are needed.
 * Dumps the entire DB, without authoriziation.
 */
public class ClearService {
    /**
     * Clears the entire database of all data. (tables are still there)
     * Creates an SQL DB connection, and closes it within the function.
     *
     * @return returns an error response object or success response,
     * indicating if successfully cleared
     */
    public Response serve() throws DataAccessException {
        Database db = new Database();
        try {
            Connection conn = db.openConnection();
            UserDao userDao = new UserDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            userDao.clear();
            personDao.clear();
            eventDao.clear();
            authTokenDao.clear();

            db.closeConnection(true);
            return new SuccessMessage("Clear succeeded.");
        }
        catch (Exception ex) {
            db.closeConnection(false);
            ex.printStackTrace();
            System.out.println(ex.toString());
            return new ErrorMessage("Failed clearing tables, error.");
        }
    }
}
