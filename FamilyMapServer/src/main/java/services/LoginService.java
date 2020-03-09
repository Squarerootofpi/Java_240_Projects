package services;

import daos.*;
import models.AuthToken;
import models.Person;
import models.User;
import requests.Login;
import results.*;

import java.sql.Connection;
import java.util.UUID;

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
    public Response serve(Login login) throws DataAccessException {
        Database db = new Database();
        try {
            //turn register request into query using the Daos.
            //User userToAdd = new User(register);
            Connection conn = db.openConnection();
            UserDao uD = new UserDao(conn);

            //find if non-null and valid values
            if (
                    login == null  //if the whole object is null
                            //if any of the values are null
                            || login.getUserName() == null || login.getPassword() == null

                            //parameters should not be empty as well.
                            || login.getUserName().equals("") || login.getPassword().equals("")
            ) {
                db.closeConnection(false);
                return new ErrorMessage("Request property missing or has invalid value, error");
            }

            //make sure it is a username
            User userRead = uD.read(login.getUserName());
            if (userRead == null)
            {
                db.closeConnection(false);
                return new ErrorMessage("Request property missing or has invalid value, error");
            }
            if (!userRead.getPassword().equals(login.getPassword()))
            {
                db.closeConnection(false);
                return new ErrorMessage("Request property missing or has invalid value, error");
            }

            //It's fine to create it, so create user, and give authtoken
            //uD.create(userToAdd);
            //String userPersonID = userToAdd.getPersonID();

            //Add them to the person table
            //PersonDao personDao = new PersonDao(conn);
            //Person newPerson = new Person(register, userPersonID);
            //personDao.create(newPerson);

            //add them to the authtoken table and give them that back.
            AuthToken authToken = new AuthToken(UUID.randomUUID().toString(),userRead.getUserName(),userRead.getPersonID());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            authTokenDao.create(authToken);


            db.closeConnection(true);
            return new GoodLogin(authToken);
        }
        catch (DataAccessException ex)
        {
            db.closeConnection(false);
            System.out.println(ex.toString());
            return new ErrorMessage("Internal server error");
        }
    }
}
