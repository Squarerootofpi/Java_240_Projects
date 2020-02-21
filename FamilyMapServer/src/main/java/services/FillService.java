package services;

import requests.Login;
import results.Response;

/**
 * Fill Service.
 * Auto fills a user's info with preselected stuff from the DB. Basically
 * fake family history.
 * Only one public function, which handlers call when they have parsed
 * a valid request object.
 * A connection is made within the serve function, and closed within it,
 * so no private data members are needed.
 */
public class FillService {
    /**
     * Creates an SQL DB connection, and closes it within the function.
     *
     * @param userName    the name of the user to fill data for
     * @param generations the number of generations to add.
     * @return returns an error response object or
     */
    public Response serve(String userName, Integer generations) {
        return null;
    }
}
