package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import requests.Login;
import results.Response;
import services.AllPeopleService;
import services.LoginService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;

public class AllPeopleHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get"))
            {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();


                Headers h = exchange.getRequestHeaders();
                List<String> authList = h.get("Authorization");
                String authString = authList.get(0);

                AllPeopleService allPeopleService = new AllPeopleService();

                Response res = allPeopleService.serve(authString);
                String resJson = gson.toJson(res);
                System.out.println(resJson);

                // Start sending the HTTP response to the client, starting with
                // the status code and any defined headers.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,
                        resJson.length());
                OutputStream respBody = exchange.getResponseBody();
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
}
