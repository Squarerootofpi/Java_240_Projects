package services;

import requests.Login;
import results.Response;

/**
 * Single Event Service.
 * Only one public function, which handlers call when they have parsed
 * a valid request object.
 * A connection is made within the serve function, and closed within it,
 * so no private data members are needed.
 */
public class EventService {
    /**
     * Serves requests to get event information from an EventID
     * Creates an SQL DB connection, and closes it within the function.
     *
     * @param authToken The authToken of the user to serve the request
     * @param eventID   The unique ID of the event to identify it.
     * @return returns an error response object or an event response object
     */
    public Response serve(String authToken, String eventID) {
        return null;
    }
}
