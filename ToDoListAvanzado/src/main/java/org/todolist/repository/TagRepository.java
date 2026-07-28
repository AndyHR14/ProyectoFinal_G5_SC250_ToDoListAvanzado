package org.todolist.repository;

import org.todolist.model.Category;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TagRepository {

    public void guardar(Category category) {

        String sql = """
                INSERT INTO categoria
                (id, nombre, color)
                VALUES (?,?,?)
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {





        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}





