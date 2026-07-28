package org.todolist.repository;

import org.todolist.model.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagRepository {



    ///---Guardar tag---///
    public void guardar(Tag tag) {

        String sql = """
                INSERT INTO etiquetas
                (nombre)
                VALUES (?)
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setString(
                    1,
                    tag.getNombre()
            );

            statement.executeUpdate();

            System.out.println(
                    "Etiqueta guardada correctamente"
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }



    ///---Obtener todas las tag---///
    public List<Tag> obtenerTodas() {

        List<Tag> etiquetas = new ArrayList<>();

        String sql = "SELECT * FROM etiquetas";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql);
             ResultSet resultado =
                     statement.executeQuery()) {

            while (resultado.next()) {

                Tag tag = convertirTag(resultado);

                etiquetas.add(tag);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return etiquetas;
    }



    ///---Buscar tag por ID---///
    public Tag buscarPorId(int id) {

        String sql = """
                SELECT * FROM etiquetas
                WHERE id_etiqueta = ?
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultado =
                         statement.executeQuery()) {

                if (resultado.next()) {

                    return convertirTag(resultado);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }



    ///---Actualizar tag---///
    public void actualizar(Tag tag) {

        String sql = """
                UPDATE etiquetas
                SET nombre = ?
                WHERE id_etiqueta = ?
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setString(
                    1,
                    tag.getNombre()
            );

            statement.setInt(
                    2,
                    tag.getIdEtiqueta()
            );

            statement.executeUpdate();

            System.out.println(
                    "Etiqueta actualizada correctamente."
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }



    ///---Eliminar tag---///
    public void eliminar(int id) {

        String sql = """
                DELETE FROM etiquetas
                WHERE id_etiqueta = ?
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

            System.out.println(
                    "Etiqueta eliminada correctamente."
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }



    ///---Convertir registro de BD a objeto Tag---///
    private Tag convertirTag(ResultSet resultado)
            throws SQLException {

        Tag tag = new Tag();

        tag.setIdEtiqueta(
                resultado.getInt("id_etiqueta")
        );

        tag.setNombre(
                resultado.getString("nombre")
        );

        return tag;
    }
}