package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import requests.Load;
import requests.Register;
import results.ErrorMessage;
import results.Response;
import services.LoadService;
import services.RegisterService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoadHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        /*

         */
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                LoadService loadService = new LoadService();

                InputStream reqBody = exchange.getRequestBody();
                String req = readString(reqBody);
                Load loadRequest = gson.fromJson(req, Load.class);
                Response res = loadService.serve(loadRequest);
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
        }
        catch (com.google.gson.JsonSyntaxException e) {
            System.out.println(e.toString());
            GsonBuilder gB = new GsonBuilder();
            Gson gson = gB.create();
            String resJson = gson.toJson(new ErrorMessage("Request property missing or has invalid value."));
            System.out.println(resJson);

            // Start sending the HTTP response to the client, starting with
            // the status code and any defined headers.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,
                    resJson.length());
            OutputStream respBody = exchange.getResponseBody();
            writeString(resJson, respBody);
            exchange.getResponseBody().close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}
