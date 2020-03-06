package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import models.AuthToken;
import results.Response;
import services.ClearService;
import services.PersonService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;

public class PersonHandler extends BaseHandler {
    private static final int PATH_LENGTH = 2;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get"))
            {

                if (!hasID(exchange))
                {
                    AllPeopleHandler allPeopleHandler = new AllPeopleHandler();
                    allPeopleHandler.handle(exchange);
                }

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Headers h = exchange.getRequestHeaders();
                List<String> authList = h.get("Authorization");
                String authString = authList.get(0);

                //String personID = "nullPersonID";
                OutputStream respBody = exchange.getResponseBody();
                PersonService personService = new PersonService();

                String personID = grabLastURIParameter(exchange);
                Response res = personService.serve(authString,personID);
                String resJson = gson.toJson(res);
                System.out.println(resJson);
                // Start sending the HTTP response to the client, starting with
                // the status code and any defined headers.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,
                        resJson.length());
                writeString(resJson, respBody);
                exchange.getResponseBody().close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }


        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    private Boolean hasID(HttpExchange exchange) {
        URI uri = exchange.getRequestURI();
        String[] path = uri.getPath().split("/");
        //Response res = null;
        if (path.length > PATH_LENGTH)
        {
            return true;
        }
        return false;
    }
    private String grabLastURIParameter(HttpExchange exchange) {
        URI uri = exchange.getRequestURI();
        String[] path = uri.getPath().split("/");
        if (path.length > PATH_LENGTH)
        {
            return path[PATH_LENGTH];
        }
        return path[0];
    }

}