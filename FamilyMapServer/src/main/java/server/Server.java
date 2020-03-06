package server;

import java.io.*;
import java.net.*;

import com.sun.net.httpserver.*;
import handlers.*;

//GSON will convert the string json into an object, and if there are unfilled fields etc., it will throw errors.
//If converts correctly, and it is filled correctly, it's handed to the service.

/**
 * Server for Backend. Calls handlers etc. within the main handle method.
 */
public class Server {

    private static String connectionPath;
    private static final int MAX_WAITING_CONNECTIONS = 12;

    private HttpServer server;


    public Server(String connectionPath) {
        this.connectionPath = connectionPath;
    }
    public Server()
    {
        this.setConnectionPath(null);
    }
    /**
     * This handles the httpRequest that hits the backend,
     * And creates the running server.
     *
     * @param portNumber The port the server will be running off of.
     * @return returns the httpResponse, in the header string.
     */

    private void run(String portNumber) {

        // Since the server has no "user interface", it should display "log"
        // messages containing information about its internal activities.
        // This allows a system administrator (or you) to know what is happening
        // inside the server, which can be useful for diagnosing problems
        // that may occur.
        System.out.println("Initializing HTTP Server");

        try {
            // Create a new HttpServer object.
            // Rather than calling "new" directly, we instead create
            // the object by calling the HttpServer.create static factory method.
            // Just like "new", this method returns a reference to the new object.
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Indicate that we are using the default "executor".
        // This line is necessary, but its function is unimportant for our purposes.
        server.setExecutor(null);

        // Log message indicating that the server is creating and installing
        // its HTTP handlers.
        // The HttpServer class listens for incoming HTTP requests.  When one
        // is received, it looks at the URL path inside the HTTP request, and
        // forwards the request to the handler for that URL path.
        System.out.println("Creating contexts");

        // Create and install the HTTP handler for the "/games/list" URL path.
        // When the HttpServer receives an HTTP request containing the
        // "/games/list" URL path, it will forward the request to ListGamesHandler
        // for processing.
        server.createContext("/clear", new ClearHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/person/", new PersonHandler());
        server.createContext("/event/", new EventHandler());
        //a specific person
        server.createContext("/person", new AllPeopleHandler());
        //a specific event
        server.createContext("/event", new AllEventsHandler());
        //The Filehandler context
        server.createContext("/", new FileHandler());

        // Log message indicating that the HttpServer is about the start accepting
        // incoming client connections.
        System.out.println("Starting server");

        // Tells the HttpServer to start accepting incoming client connections.
        // This method call will return immediately, and the "main" method
        // for the program will also complete.
        // Even though the "main" method has completed, the program will continue
        // running because the HttpServer object we created is still running
        // in the background.
        server.start();

        // Log message indicating that the server has successfully started.
        System.out.println("Server started");
    }

    // "main" method for the server program
    // "args" should contain one command-line argument, which is the port number
    // on which the server should accept incoming client connections.
    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
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
