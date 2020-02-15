package services;


import models.AuthToken;
import results.Response;

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
    public Response serve(AuthToken authToken, String personID) {
        return null;
    }
}
