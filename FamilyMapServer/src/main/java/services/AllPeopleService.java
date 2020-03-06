package services;

import daos.AuthTokenDao;
import daos.DataAccessException;
import daos.Database;
import daos.PersonDao;
import models.AuthToken;
import models.Person;
import results.AllPeople;
import results.ErrorMessage;
import results.PersonRes;
import results.Response;

/**
 * All People Service.
 * Only one public function, which handlers call when they have parsed
 * a valid request object.
 * A connection is made within the serve function, and closed within it,
 * so no private data members are needed.
 */
public class AllPeopleService {
    /**
     * To get all the people associated with a user.
     * Creates an SQL DB connection, and closes it within the function.
     *
     * @param authToken The authToken of the user to serve the request
     * @return returns an error response object or the people associated
     * with the user.
     */
    public Response serve(String authToken) throws DataAccessException {

        Database db = new Database();
        try {
            db.openConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());

            //check to make sure auth is valid
            AuthToken auth = authTokenDao.read(authToken);
            if (auth == null)
            {
                return new ErrorMessage("Invalid auth token.");
            }

            PersonDao personDao = new PersonDao(db.getConnection());
            AllPeople people = new AllPeople(personDao.
                    readAllWhereAssociatedUsername(auth.getUserName()));

            db.closeConnection(false); //Doesn't need to be true here.
            return people;
        }
        catch (Exception ex) {
            db.closeConnection(false);
            return new ErrorMessage("Internal server error.");
        }
    }
}
