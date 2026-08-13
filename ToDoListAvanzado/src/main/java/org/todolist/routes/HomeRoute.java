package org.todolist.routes;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HomeRoute implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {

            exchange.sendResponseHeaders(405, -1);
            return;
        }

        InputStream inputStream =
                getClass()
                        .getClassLoader()
                        .getResourceAsStream("views/index.html");


        if (inputStream == null) {

            String mensaje =
                    "No se encontró views/index.html";

            byte[] response =
                    mensaje.getBytes(StandardCharsets.UTF_8);

            exchange.sendResponseHeaders(
                    404,
                    response.length
            );

            try (OutputStream output =
                         exchange.getResponseBody()) {

                output.write(response);
            }

            return;
        }


        byte[] html =
                inputStream.readAllBytes();


        exchange.getResponseHeaders()
                .set(
                        "Content-Type",
                        "text/html; charset=UTF-8"
                );


        exchange.sendResponseHeaders(
                200,
                html.length
        );


        try (OutputStream output =
                     exchange.getResponseBody()) {

            output.write(html);
        }
    }
}