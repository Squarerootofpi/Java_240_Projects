package handlers;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import results.ErrorMessage;
import results.Response;

import java.io.IOException;

public class BaseHandler implements HttpHandler {
    /**
     * Default base handler sends an error response message.
     *
     * @param exchange the exchange of http headers etc...
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //exchange.getResponseHeaders();
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        exchange.getResponseBody().close();
    }

    Boolean isErrorResponse(Response res) {
    	return (res.getClass().equals(ErrorMessage.class));
	}

    /**
     * The readString method shows how to read a String from an InputStream.
     *
     * @param is the input stream
	*/
	String readString(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStreamReader sr = new InputStreamReader(is);
		char[] buf = new char[1024];
		int len;
		while ((len = sr.read(buf)) > 0) {
			sb.append(buf, 0, len);
		}
		return sb.toString();
	}

	/**
	  * The writeString method shows how to write a String to an OutputStream.
	*/
	void writeString(String str, OutputStream os) throws IOException {
		OutputStreamWriter sw = new OutputStreamWriter(os);
		sw.write(str);
		sw.flush();
	}
}
