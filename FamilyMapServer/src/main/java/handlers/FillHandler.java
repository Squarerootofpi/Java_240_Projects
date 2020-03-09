package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import results.ErrorMessage;
import results.Response;
import services.ClearService;
import services.FillService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

public class FillHandler extends BaseHandler {
    private static final int DEFAULT_GENERATIONS = 4;
    private static final int PATH_LENGTH = 2;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                GsonBuilder gB = new GsonBuilder();
                Gson gson = gB.create();
                /**
                 Errors: Invalid username or generations parameter, Internal server error
                 */
                //String path = exchange.getRequestURI().getPath();
                Boolean valid = false;
                if (exchange.getRequestURI().getPath().equals("") || exchange.getRequestURI().getPath().equals("/"))
                {
                    valid = false;
                    ErrorMessage errorMessage = new ErrorMessage("Invalid username or generations parameter");
                    String errorJson = gson.toJson(errorMessage);
                    //send headers

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                    OutputStream respBody = exchange.getResponseBody();
                    writeString(errorJson, respBody);
                    exchange.getResponseBody().close();
                    return;
                    //write out and close.
                }

                FillService fillService = new FillService();
                //Otherwise, try to get an int and a id.
                URI uri = exchange.getRequestURI();
                String[] path = uri.getPath().split("/");
                Response res = null;
                if (path.length > (2 + PATH_LENGTH))
                {
                    valid = false;
                }
                else if (path.length == (1 + PATH_LENGTH)) {
                    res = fillService.serve(path[PATH_LENGTH],DEFAULT_GENERATIONS);
                    valid = (!isErrorResponse(res));
                }
                else //pathlength == 2 + PATH_LENGTH
                {
                    res = fillService.serve(path[PATH_LENGTH],Integer.parseInt(path[PATH_LENGTH + 1]));
                    valid = (!isErrorResponse(res));;
                }

                //Now tojson it if it's valid.
                if (valid)
                {
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
                    res = new ErrorMessage("Invalid username or generations parameter");
                    String resJson = gson.toJson(res);
                    System.out.println(resJson);
                    // Start sending the HTTP response to the client, starting with
                    // the status code and any defined headers.
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,
                            resJson.length());
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(resJson, respBody);
                    exchange.getResponseBody().close();
                }

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
