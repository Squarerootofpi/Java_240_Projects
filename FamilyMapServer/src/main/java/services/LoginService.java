package services;

import requests.Login;
import results.*;

/**
 * Login Service.
 * Service to accept login requests and see if login is correct.
 * Only one public function, which handlers call when they have parsed
 * a valid request object.
 * A connection is made within the serve function, and closed within it,
 * so no private data members are needed.
 */
public class LoginService {

    /**
     * Allows users to login and get an authtoken to make calls on the DB.
     * Creates an SQL DB connection, and closes it within the function.
     *
     * @param login the login request object parsed by the handler
     * @return returns an error response object or authToken object.
     */
    public Response serve(Login login) {
        return null;
    }
}
