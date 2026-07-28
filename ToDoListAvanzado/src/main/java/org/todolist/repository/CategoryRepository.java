package org.todolist.repository;

import org.todolist.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {



    ///---Guardar categoria---///
    public void guardar(Category category) {

        String sql = """
                INSERT INTO categorias
                (nombre, color)
                VALUES (?, ?)
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setString(
                    1,
                    category.getNombre()
            );

            statement.setString(
                    2,
                    category.getColor()
            );

            statement.executeUpdate();
            System.out.println(
                    "Categoria guardada correctamente."
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }



    ///---Obtener todas las categorias---///
    public List<Category> obtenerTodas() {

        List<Category> categorias = new ArrayList<>();

        String sql = "SELECT * FROM categorias";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql);
             ResultSet resultado =
                     statement.executeQuery()) {

            while (resultado.next()) {

                Category category =
                        convertirCategory(resultado);

                categorias.add(category);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return categorias;
    }



    ///---Obtener categoria por id---///
    public Category buscarPorId(int id) {

        String sql =
                "SELECT * FROM categorias " +
                        "WHERE id_categoria = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultado =
                         statement.executeQuery()) {

                if (resultado.next()) {

                    return convertirCategory(resultado);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }



    ///---Actualizar categoria---///
    public void actualizar(Category category) {

        String sql = """
                UPDATE categorias
                SET nombre = ?,
                    color = ?
                WHERE id_categoria = ?
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setString(
                    1,
                    category.getNombre()
            );

            statement.setString(
                    2,
                    category.getColor()
            );

            statement.setInt(
                    3,
                    category.getId()
            );

            statement.executeUpdate();

            System.out.println(
                    "Categoria actualizada correctamente."
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }



    ///---Eliminar categoria---///
    public void eliminar(int id) {

        String sql =
                "DELETE FROM categorias " +
                        "WHERE id_categoria = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

            System.out.println(
                    "Categoria eliminada correctamente."
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }



    ///---Convertir el registro de la BD en un objeto Category---///
    private Category convertirCategory(
            ResultSet resultado
    ) throws SQLException {

        Category category = new Category();

        category.setId(
                resultado.getInt("id_categoria")
        );

        category.setNombre(
                resultado.getString("nombre")
        );

        category.setColor(
                resultado.getString("color")
        );

        return category;
    }
}