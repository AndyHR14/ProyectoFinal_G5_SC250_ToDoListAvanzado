package org.todolist.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Base de datos que estamos utilizando actualmente
    private static final String URL =
            "jdbc:mysql://localhost:3306/tododb";

    private static final String USER = "root";

    private static final String PASSWORD = "";

    public static Connection conectar() throws SQLException {

        try {

            /*
             * TEMPORAL:
             * Cargamos explícitamente el driver de MySQL.
             *
             * Normalmente JDBC lo detecta automaticamente,
             * pero lo hacemos explicito para asegurarnos de que
             * Tomcat esta cargando mysql-connector-j.
             */
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Driver MySQL cargado correctamente.");

        } catch (ClassNotFoundException e) {

            /*
             * Si aparece este mensaje significa que el
             * mysql-connector-j NO está disponible dentro
             * de la aplicación que está ejecutando Tomcat.
             */

            System.err.println(
                    "ERROR: No se encontró el driver de MySQL."
            );

            throw new SQLException(
                    "No se encontró mysql-connector-j en la aplicación.",
                    e
            );
        }

        /*
         * Conexion a MySQL
         */
        Connection conexion =
                DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD
                );

        System.out.println("Conexion exitosa.");

        return conexion;
    }
}