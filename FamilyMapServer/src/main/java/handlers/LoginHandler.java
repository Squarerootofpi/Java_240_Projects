package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import requests.Login;
import requests.Register;
import results.ErrorMessage;
import results.GoodLogin;
import results.Response;
import services.ClearService;
import services.LoginService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoginHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                LoginService loginService = new LoginService();
                InputStream reqBody = exchange.getRequestBody();
                String req = readString(reqBody);
                Login loginRequest = gson.fromJson(req, Login.class);
                Response res = loginService.serve(loginRequest);
                String resJson = gson.toJson(res);
                System.out.println(resJson);

                // Start sending the HTTP response to the client, starting with
                // the status code and any defined headers.
                if(isErrorResponse(res))
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,
                            0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,
                            0);
                }
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
