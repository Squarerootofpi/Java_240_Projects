package services;

import results.Response;

/**
 * All Events Service.
 * Only one public function, which handlers call when they have parsed
 * a valid request object.
 * A connection is made within the serve function, and closed within it,
 * so no private data members are needed.
 */
public class AllEventsService {
    /**
     * Serves back the requests to get all the events.
     * Creates an SQL DB connection, and closes it within the function.
     *
     * @param authToken The authToken of the user to serve the request
     * @return returns an error response object or an array of events in an object
     */
    public Response serve(String authToken) {
        return null;
    }
}
