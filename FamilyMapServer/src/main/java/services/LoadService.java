package services;

import requests.Load;
import results.Response;

/**
 * Load Service. Clears the DB, and loads it with people, events, and users
 * Only one public function, which handlers call when they have parsed
 * a valid request object.
 * A connection is made within the serve function, and closed within it,
 * so no private data members are needed.
 */
public class LoadService {

    /**
     * Loads a database (clears it first) with all new data.
     * Creates an SQL DB connection, and closes it within the function.
     *
     * @param load The load object containing the data to add to the DB
     * @return returns an error response object or a success response
     */
    public Response serve(Load load) {
        return null;
    }
}
