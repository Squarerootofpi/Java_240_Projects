package server;

import java.sql.Connection;

//GSON will convert the string json into an object, and if there are unfilled fields etc., it will throw errors.
//If converts correctly, and it is filled correctly, it's handed to the service.

public class Server {

    //private Connection conn;
    /**
     * This handles the httpRequest that hits the backend,
     * And forwards it to the proper handler, or rejects it
     * if it is not a valid request.
     *
     * @param httpRequest The request sent in the header after the IP address
     * @return returns the httpResponse, in the header string.
     */
    public String handle(String httpRequest){


        return "not implemented";
    }
}
