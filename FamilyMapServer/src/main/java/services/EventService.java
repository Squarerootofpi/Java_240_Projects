package services;

import daos.*;
import models.AuthToken;
import models.Event;
import models.Person;
import requests.Login;
import results.ErrorMessage;
import results.EventRes;
import results.PersonRes;
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
    public Response serve(String authToken, String eventID) throws DataAccessException {

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

            //check to make sure person id exists
            EventDao eventDao = new EventDao(db.getConnection());
            Event event = eventDao.read(eventID);
            if (event == null)
            {
                return new ErrorMessage("Invalid eventID parameter");
            }

            //we know the person exists and the auth is correct, need to make
            //sure it belongs to this user.
            if ( !(event.getAssociatedUsername().equals(auth.getUserName())) )
            {
                return new ErrorMessage("Requested event does not belong to this user");
            }

            db.closeConnection(true);
            return new EventRes(event);
        }
        catch (Exception ex) {
            db.closeConnection(false);
            return new ErrorMessage("Internal server error.");
        }
    }
}
