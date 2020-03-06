package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import daos.*;
import models.AuthToken;
import models.Person;
import models.User;
import requests.Register;
import results.ErrorMessage;
import results.GoodLogin;
import results.Response;

import javax.rmi.ssl.SslRMIClientSocketFactory;

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
     * Possible Errors: Request property missing or has invalid value,
     *      Username already taken by another user,
     *      Internal server error.
     *
     * @param register The user request object to register in FMS
     * @return returns an error response object or an authToken Success object
     */
    public Response serve(Register register) throws DataAccessException {
        Database db = new Database();
        try {
            //turn register request into query using the Daos.
            User userToAdd = new User(register);
            Connection conn = db.openConnection();
            UserDao uD = new UserDao(conn);

            //find if non-null and valid values
            if (
                    userToAdd == null  //if the whole object is null
                    //if any of the values are null
                    || userToAdd.getEmail() == null || userToAdd.getFirstName() == null
                    || userToAdd.getLastName() == null || userToAdd.getGender() == null
                    || userToAdd.getUserName() == null || userToAdd.getPassword() == null

                //parameters with gender: making sure m or f
                    ||
                         !( userToAdd.getGender().equals('m') || userToAdd.getGender().equals('f') )
                    //parameters should not be empty as well.
                    || userToAdd.getEmail().equals("") || userToAdd.getFirstName().equals("")
                    || userToAdd.getLastName().equals("") || userToAdd.getGender().equals("")
                    || userToAdd.getUserName().equals("") || userToAdd.getPassword().equals("")
            ) {
                System.out.println(userToAdd.getGender());
                System.out.println(!userToAdd.getGender().equals('m'));
                return new ErrorMessage("Request property missing or has invalid value.");
            }

            //make sure not already a username
            User userRead = uD.read(userToAdd.getUserName());
            if (!(userRead == null))
            {
                db.closeConnection(false);
                return new ErrorMessage("Username already taken by another user.");
            }

            //It's fine to create it, so create user, and give authtoken
            uD.create(userToAdd);
            String userPersonID = userToAdd.getPersonID();

            //Add them to the person table
            PersonDao personDao = new PersonDao(conn);
            Person newPerson = new Person(register, userPersonID);
            personDao.create(newPerson);

            //add them to the authtoken table and give them that back.
            AuthToken authToken = new AuthToken(UUID.randomUUID().toString(),userToAdd.getUserName(),userPersonID);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            authTokenDao.create(authToken);

            //We commit early
            db.closeConnection(true);
            //db.openConnection();


            //Now fill with the 4 generations!!! :)
            FillService fillService = new FillService();
            fillService.serve(userToAdd.getUserName(),4);

            //db.closeConnection(true);
            return new GoodLogin(authToken);
        }
        catch (DataAccessException ex)
        {
            db.closeConnection(false);
            System.out.println(ex.toString());
            return new ErrorMessage("Internal server error");
        }
        catch (com.google.gson.JsonSyntaxException ex)
        {
            db.closeConnection(false);
            System.out.println(ex.toString());
            return new ErrorMessage("Request property missing or has invalid value.");
        }
    }
}
