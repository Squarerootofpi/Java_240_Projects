package results;

/**
 * Class for holding a basic success response: a bool and a message
 */
public class SuccessMessage extends Response {
    /**
     * Message of success
     */
    private String message;

    /**
     * Constructor for error only requires a message of what the error
     * actually is
     *
     * @param message
     */
    public SuccessMessage(String message) {
        this.message = message;
        setSuccess(true);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
