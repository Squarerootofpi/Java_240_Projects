package handlers;

import com.sun.net.httpserver.*;
import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

//last two slides of dr. wilkerson filehandler
//gson.toJson and fromJson
//Rodham's readfile function in baseHandler
public class FileHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String root = "./FoldersAndSources/FamilyMapServerStudent-master/web/";
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();
        System.out.println("looking for: "+ root + path);

        if (path.equals("/") || path.equals(null)) {// = / or null, then send the index.html.
            path = "/index.html";
        }
        File file = new File(root + path).getCanonicalFile();
        if (!file.isFile()) {
            path = "HTML/404.html";
            file = new File(root + path).getCanonicalFile();
            // Object does not exist or is not a file: reject with 404 error.
            //Serve THEIR 404 page...
            //String response = "404 (Not Found)\n";
            //exchange.sendResponseHeaders(404, response.length());
            //exchange.getResponseBody().close();
        }
            // Object exists and is a file: accept with response code 200.
            String mime = "text/html";
            if(path.substring(path.length()-3).equals(".js")) mime = "application/javascript";
            if(path.substring(path.length()-3).equals("css")) mime = "text/css";


            Headers h = exchange.getResponseHeaders();
            h.set("Content-Type", mime);
            if (path.equals("HTML/404.html")) {
                exchange.sendResponseHeaders(404, 0);
            }
            else {
                exchange.sendResponseHeaders(200, 0);
            }

            OutputStream os = exchange.getResponseBody();
            FileInputStream fs = new FileInputStream(file);
            final byte[] buffer = new byte[0x10000];
            int count = 0;
            while ((count = fs.read(buffer)) >= 0)
            {
                os.write(buffer,0,count);
                System.out.println(count);
            fs.close();
            os.close();
        }
    }
}
