package services;

import daos.*;
import models.AuthToken;
import results.AllEvents;
import results.AllPeople;
import results.ErrorMessage;
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
    public Response serve(String authToken)throws DataAccessException {

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

            EventDao eventDao = new EventDao(db.getConnection());
            AllEvents events = new AllEvents(eventDao.
                    readAllWhereAssociatedUsername(auth.getUserName()));

            db.closeConnection(false); //Doesn't need to be true here.
            return events;
        }
        catch (Exception ex) {
            db.closeConnection(false);
            return new ErrorMessage("Internal server error.");
        }
    }
}
