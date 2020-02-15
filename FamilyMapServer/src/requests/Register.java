package requests;

import models.User;

/**
 *
 * Register is a request version of the User Model. For use between
 * The Handler and the service.
 */
public class Register{
    /**
     * First name of user
     */
    private String firstName;
    /**
     * Last name of user
     */
    private String lastName;
    /**
     * Unique username of the user
     */
    private String userName;
    /**
     * Unique email of the user (PK)
     */
    private String email;
    /**
     * Password of the user
     */
    private String password;
    /**
     * Gender of the user
     */
    private Character gender;
    /**
     * Constructor for register request object
     * @param firstName User's first name
     * @param lastName User's last name
     * @param userName User's desired username
     * @param email unique email address associated with user
     * @param password User's password
     * @param gender Gender of user ("m" or "f")
     */
    public Register(String firstName, String lastName, String userName, String email, String password, Character gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }
}
