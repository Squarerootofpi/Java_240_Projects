package requests;

/**
 * Login request object for users to access server
 */
public class Login {
    /**
     * Username of attempted login
     */
    private String userName;
    /**
     * Password of attempted Login
     */
    private String password;

    /**
     * Constructor of Register Request object
     *
     * @param userName inputted username
     * @param password inputted password
     */
    public Login(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
