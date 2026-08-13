package org.todolist.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskTagRepository {



    /// ASIGNAR UNA ETIQUETA A UNA TAREA


    public void asignarEtiqueta(int idTarea, int idEtiqueta) {

        String sql = """
                INSERT INTO tarea_etiqueta
                (id_tarea, id_etiqueta)
                VALUES (?, ?)
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, idTarea);
            statement.setInt(2, idEtiqueta);

            statement.executeUpdate();

            System.out.println(
                    "Etiqueta asignada correctamente."
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }



    /// OBTENER LAS ETIQUETAS DE UNA TAREA


    public List<Integer> obtenerEtiquetasDeTarea(int idTarea) {

        List<Integer> etiquetas = new ArrayList<>();

        String sql = """
                SELECT id_etiqueta
                FROM tarea_etiqueta
                WHERE id_tarea = ?
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, idTarea);

            try (ResultSet resultado =
                         statement.executeQuery()) {

                while (resultado.next()) {

                    etiquetas.add(
                            resultado.getInt("id_etiqueta")
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return etiquetas;
    }



    /// ELIMINAR UNA ETIQUETA DE UNA TAREA


    public void eliminarEtiqueta(
            int idTarea,
            int idEtiqueta) {

        String sql = """
                DELETE FROM tarea_etiqueta
                WHERE id_tarea = ?
                AND id_etiqueta = ?
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, idTarea);
            statement.setInt(2, idEtiqueta);

            statement.executeUpdate();

            System.out.println(
                    "Etiqueta eliminada de la tarea."
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }



    /// ELIMINAR TODAS LAS ETIQUETAS DE UNA TAREA


    public void eliminarTodasLasEtiquetas(int idTarea) {

        String sql = """
                DELETE FROM tarea_etiqueta
                WHERE id_tarea = ?
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, idTarea);

            statement.executeUpdate();

            System.out.println(
                    "Etiquetas de la tarea eliminadas correctamente."
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}