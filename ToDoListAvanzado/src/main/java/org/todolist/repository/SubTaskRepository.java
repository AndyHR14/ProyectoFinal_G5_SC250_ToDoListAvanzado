/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.repository;

/**
 *
 * @author Alvm Tech
 */

import org.todolist.model.SubTask;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubTaskRepository {

    ///---Guardar SubTarea---///
    public int guardar(SubTask subTask) {

        String sql = """
                INSERT INTO subtareas
                (descripcion, completada, id_tarea)
                VALUES (?, ?, ?)
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            statement.setString(
                    1,
                    subTask.getDescripcion()
            );

            statement.setBoolean(
                    2,
                    subTask.isCompletada()
            );

            statement.setInt(
                    3,
                    subTask.getIdTarea()
            );

            statement.executeUpdate();

            // Obtener ID generado por MySQL
            try (ResultSet resultado =
                         statement.getGeneratedKeys()) {

                if (resultado.next()) {

                    int idGenerado =
                            resultado.getInt(1);

                    // Guardar el ID en el objeto
                    subTask.setIdSubtarea(idGenerado);

                    System.out.println(
                            "Subtarea guardada correctamente."
                    );

                    return idGenerado;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }




    ///---Obtener todas las subtareas---///
    public List<SubTask> obtenerTodas() {

        List<SubTask> subtareas = new ArrayList<>();

        String sql = "SELECT * FROM subtareas";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                subtareas.add(
                        convertirSubTask(resultado)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subtareas;
    }
    
    public List<SubTask> obtenerPorTarea(int idTarea) {

        List<SubTask> subtareas = new ArrayList<>();

        String sql = """
                SELECT *
                FROM subtareas
                WHERE id_tarea = ?
                ORDER BY id_subtarea
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, idTarea);

            try (ResultSet resultado =
                         statement.executeQuery()) {

                while (resultado.next()) {

                    subtareas.add(
                            convertirSubTask(resultado)
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return subtareas;
    }



    ///---Buscar subtarea por id---///
    public SubTask buscarPorId(int id) {

        String sql =
                "SELECT * FROM subtareas WHERE id_subtarea = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {

                    return convertirSubTask(resultado);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    ///---Actualizar subtarea---///
    public void actualizar(SubTask subTask) {

        String sql = """
                UPDATE subtareas
                SET descripcion = ?,
                    completada = ?,
                    id_tarea = ?
                WHERE id_subtarea = ?
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(
                    1,
                    subTask.getDescripcion()
            );

            statement.setBoolean(
                    2,
                    subTask.isCompletada()
            );

            statement.setInt(
                    3,
                    subTask.getIdTarea()
            );

            statement.setInt(
                    4,
                    subTask.getIdSubtarea()
            );

            statement.executeUpdate();

            System.out.println("Subtarea actualizada correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    ///---Eliminar subtarea---///
    public void eliminar(int id) {

        String sql =
                "DELETE FROM subtareas WHERE id_subtarea = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

            System.out.println("Subtarea eliminada correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void actualizarEstado(int idSubtarea, boolean completada) {

        String sql = """
                UPDATE subtareas
                SET completada = ?
                WHERE id_subtarea = ?
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setBoolean(1, completada);
            statement.setInt(2, idSubtarea);

            statement.executeUpdate();

            System.out.println(
                    "Estado de la subtarea actualizado correctamente."
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    


    ///---Convertir el registro de la BD en un objeto SubTask---///
    private SubTask convertirSubTask(ResultSet resultado)
            throws SQLException {

        SubTask subTask = new SubTask();

        subTask.setIdSubtarea(
                resultado.getInt("id_subtarea")
        );

        subTask.setDescripcion(
                resultado.getString("descripcion")
        );

        subTask.setCompletada(
                resultado.getBoolean("completada")
        );

        subTask.setIdTarea(
                resultado.getInt("id_tarea")
        );

        return subTask;
    }
}