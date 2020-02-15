package services;

import results.Response;

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
    public Response serve() {
        return null;
    }
}
