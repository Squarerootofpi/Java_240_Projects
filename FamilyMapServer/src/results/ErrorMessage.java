package results;

/**
 * Error message response class object, for use of service and handler
 */
public class ErrorMessage extends Response {
    /**
     * Message of error
     */
    private String message;

    /**
     * Constructor for error only requires a message of what the error
     * actually is
     * @param message
     */
    public ErrorMessage(String message) {
        this.message = message;
        setSuccess(false);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
