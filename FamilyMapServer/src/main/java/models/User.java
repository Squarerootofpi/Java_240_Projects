package models;


/**
 * Database model of the User Database Table
 */
public class User {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    /**
     * Constructer of User database model in Java
     *
     * @param firstName User's first name
     * @param lastName  User's last name
     * @param userName  User's desired username
     * @param email     unique email address associated with user
     * @param password  User's password
     * @param gender    Gender of user ("m" or "f")
     */
    public User(String firstName, String lastName, String userName, String email, String password, Character gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

}
