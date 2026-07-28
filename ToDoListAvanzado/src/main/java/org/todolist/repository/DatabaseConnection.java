package org.todolist.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//conexcion para la base de datos, Se uso XAMP, se creo la base de datos en XAMP utilizando MySQL que trae

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/ToDoDB";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() {

        try {

            Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Conexion exitosa.");
            return conexion;

        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        }
    }
}