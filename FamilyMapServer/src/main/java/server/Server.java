package server;

//GSON will convert the string json into an object, and if there are unfilled fields etc., it will throw errors.
//If converts correctly, and it is filled correctly, it's handed to the service.

/**
 * Server for Backend. Calls handlers etc. within the main handle method.
 */
public class Server {

    private static String connectionPath;

    public Server(String connectionPath) {
        this.connectionPath = connectionPath;
    }

    /**
     * This handles the httpRequest that hits the backend,
     * And forwards it to the proper handler, or rejects it
     * if it is not a valid request.
     *
     * @param httpRequest The request sent in the header after the IP address
     * @return returns the httpResponse, in the header string.
     */

    public String handle(String httpRequest) {


        return "not implemented";
    }

    /**
     * Static function to allow any function to request what the path
     * is to the connection db.
     *
     * @return The static string that the connection path is to the db to connect.
     */
    public static String getConnectionPath() {
        return connectionPath;
    }

    public void setConnectionPath(String connectionPath) {
        this.connectionPath = connectionPath;
    }
}
