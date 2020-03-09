package services;


import daos.*;
import models.AuthToken;
import models.Person;
import results.ErrorMessage;
import results.PersonRes;
import results.Response;
import results.SuccessMessage;

/**
 * Person request Service.
 * Given an authToken, this service allows users to access information
 * on people in the database.
 * Only one public function, which handlers call when they have parsed
 * a valid request object.
 * A connection is made within the serve function, and closed within it,
 * so no private data members are needed.
 */
public class PersonService {

    /**
     * For users to request info on specific people.
     * Creates an SQL DB connection, and closes it within the function.
     *
     * @param authToken The authToken of the user to serve the request
     * @param personID  the ID of the person the user wants to get info for
     * @return returns an error response object or a personRes object.
     */
    public Response serve(String authToken, String personID) throws DataAccessException {

        Database db = new Database();
        try {
            db.openConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());

            //check to make sure auth is valid
            AuthToken auth = authTokenDao.read(authToken);
            if (auth == null)
            {
                db.closeConnection(false);
                return new ErrorMessage("Invalid auth token, error.");
            }

            //check to make sure person id exists
            PersonDao personDao = new PersonDao(db.getConnection());
            Person person = personDao.read(personID);
            if (person == null)
            {
                db.closeConnection(false);
                return new ErrorMessage("Invalid personID parameter, error.");
            }

            //we know the person exists and the auth is correct, need to make
            //sure it belongs to this user.
            if ( !(person.getAssociatedUsername().equals(auth.getUserName())) )
            {
                db.closeConnection(false);
                return new ErrorMessage("Requested person does not belong to this user, error.");
            }

            db.closeConnection(true);
            return new PersonRes(person);
        }
        catch (Exception ex) {
            db.closeConnection(false);
            return new ErrorMessage("Internal server error.");
        }
    }
}
