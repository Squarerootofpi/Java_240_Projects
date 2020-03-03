package models;


/*
Questions:
How much time???
How much

 */

import java.util.Objects;

/**
 * Database model of the AuthToken Database Table
 */
public class AuthToken {

    /**
     * The PK authtoken for a person.
     */
    private String authToken;
    /**
     * name of user auth token is connected to
     */
    private String userName;
    /**
     * ID; Foreign Key, of auth Token
     */
    private String personID;

    /**
     * Constructor
     *
     * @param authToken The unique string identifying the session
     * @param userName  The name of the user the authToken belongs to
     * @param personID  The ID (unique to the person) identifying the user
     */
    public AuthToken(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return authToken.equals(authToken1.authToken) &&
                userName.equals(authToken1.userName) &&
                personID.equals(authToken1.personID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken, userName, personID);
    }
}
