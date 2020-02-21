package services;

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
    public Response serve(String authToken) {
        return null;
    }
}
