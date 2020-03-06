package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import daos.DataAccessException;
import requests.Register;
import results.Response;
import services.ClearService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class ClearHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post"))
            {
//                if ( !(exchange.getRequestURI().equals(null) || exchange.getRequestURI().equals("/")) ) //The filehandler should parse this as a 404 then.
//                {
//                    FileHandler fH = new FileHandler();
//                    fH.handle(exchange);
//                }
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                ClearService clearService = new ClearService();
                Response res = clearService.serve();
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
