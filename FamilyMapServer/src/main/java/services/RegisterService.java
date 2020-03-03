package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import daos.DataAccessException;
import daos.Database;
import daos.UserDao;
import models.User;
import requests.Register;
import results.Response;

/**
 * Register Service.
 * People give a register request to the server so they can create
 * a user.
 * Only one public function, which handlers call when they have parsed
 * a valid request object.
 * A connection is made within the serve function, and closed within it,
 * so no private data members are needed.
 */
public class RegisterService {


    /**
     * Creates an SQL DB connection, and closes it within the function.
     *
     * @param register The user request object to register in FMS
     * @return returns an error response object or an authToken Success object
     */
    public Response serve(Register register) {
        Database db = new Database();
        try {
            //turn register request into query using the Daos.
            User userToAdd = new User(register);
            Connection conn = db.openConnection();
            UserDao uD = new UserDao(conn);

            db.closeConnection(true);
        }
        catch (DataAccessException ex)
        {

        }



        String userID = UUID.randomUUID().toString();
        return null;
    }
}
