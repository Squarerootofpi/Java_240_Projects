package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import results.Response;
import services.AllEventsService;
import services.AllPeopleService;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.List;

public class AllEventsHandler extends BaseHandler {
    private static final int BUFFER_SIZE = 1024;

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

                AllEventsService allEventsService = new AllEventsService();

                Response res = allEventsService.serve(authString);
                String resJson = gson.toJson(res);
                System.out.println(resJson);
                System.out.println(resJson.length());
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
                //OutputStream respBody = exchange.getResponseBody();
                //writeString(resJson, respBody);

                /*OutputStream os = exchange.getResponseBody();
                ByteArrayInputStream inStream = new ByteArrayInputStream(resJson.getBytes("UTF-8"));
                final byte[] buffer = new byte[0x10000];
                int count = 0;
                while ((count = inStream.read(buffer)) >= 0) {
                    os.write(buffer, 0, count);
                    System.out.println(count);
                    inStream.close();
                    os.close();
                }*/

                try (BufferedOutputStream out = new BufferedOutputStream(exchange.getResponseBody())) {
                    try (ByteArrayInputStream bis = new ByteArrayInputStream(resJson.getBytes("UTF-8"))) {
                        byte [] buffer = new byte [BUFFER_SIZE];
                        int count ;
                        while ((count = bis.read(buffer)) != -1) {
                            out.write(buffer, 0, count);
                        }
                        out.close();
                    }
                }

                //exchange.getResponseBody().close();
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
