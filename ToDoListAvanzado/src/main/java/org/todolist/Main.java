package org.todolist;

import org.todolist.repository.DatabaseConnection;
import org.todolist.routes.HomeRoute;
import org.todolist.routes.TaskRoute;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        /// ConexiOn a la base de datos
        try {
            DatabaseConnection.conectar();
        } catch (SQLException e) {
            System.err.println(
                    "Error al conectar con la base de datos: "
                            + e.getMessage()
            );
        }

        try {

            HttpServer server =
                    HttpServer.create(
                            new InetSocketAddress(8080),
                            0
                    );

            /// Vista principal
            server.createContext(
                    "/",
                    new HomeRoute()
            );

            /// API de tareas
            server.createContext(
                    "/api/tasks",
                    new TaskRoute()
            );

            server.setExecutor(null);

            server.start();

            System.out.println(
                    "Servidor iniciado en http://localhost:8080"
            );

        } catch (IOException e) {

            System.err.println(
                    "Error al iniciar el servidor: "
                            + e.getMessage()
            );
        }
    }
}