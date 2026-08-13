package org.todolist.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.todolist.controller.TaskController;
import org.todolist.model.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TaskRoute implements HttpHandler {

    private final TaskController taskController;
    private final ObjectMapper objectMapper;

    public TaskRoute() {

        this.taskController = new TaskController();

        this.objectMapper = new ObjectMapper();

        // Permite convertir LocalDate correctamente
        this.objectMapper.registerModule(
                new JavaTimeModule()
        );
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Solo permitimos GET
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {

            sendResponse(
                    exchange,
                    405,
                    "{\"error\":\"Método no permitido\"}"
            );

            return;
        }


        try {

            // Obtener tareas desde Controller
            List<Task> tasks =
                    taskController.obtenerTodasLasTareas();


            // Convertir las tareas a JSON
            String json =
                    objectMapper.writeValueAsString(tasks);


            // Enviar respuesta
            sendResponse(
                    exchange,
                    200,
                    json
            );


        } catch (Exception e) {

            e.printStackTrace();

            sendResponse(
                    exchange,
                    500,
                    "{\"error\":\"Error al obtener las tareas\"}"
            );
        }
    }


    private void sendResponse(
            HttpExchange exchange,
            int statusCode,
            String response
    ) throws IOException {

        byte[] bytes =
                response.getBytes(
                        StandardCharsets.UTF_8
                );


        exchange.getResponseHeaders()
                .set(
                        "Content-Type",
                        "application/json; charset=UTF-8"
                );


        exchange.sendResponseHeaders(
                statusCode,
                bytes.length
        );


        try (var output =
                     exchange.getResponseBody()) {

            output.write(bytes);
        }
    }
}