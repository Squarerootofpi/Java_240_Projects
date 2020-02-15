package results;

/**
 * Base class for any response object: just tells handler if it
 * was a successful response.
 * NO CONSTRUCTOR: ALWAYS ASSUME TO SET THE SUCCESS IN SUBCLASSES
 */
public class Response {
    /**
     * Boolean indicating Whether a successful response
     */
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
