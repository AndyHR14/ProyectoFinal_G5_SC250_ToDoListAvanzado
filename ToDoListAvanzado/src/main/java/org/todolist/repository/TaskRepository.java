package org.todolist.repository;

import org.todolist.model.Task;
import org.todolist.enums.Estado;
import org.todolist.enums.Prioridad;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    // CREATE
    public void guardar(Task task) {

        String sql = """
                INSERT INTO tareas
                (titulo, fecha_creacion, fecha_limite, prioridad,
                 estado, progreso, id_usuario, id_categoria)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, task.getTitulo());

            statement.setDate(
                    2,
                    Date.valueOf(task.getFechaCreacion())
            );

            if (task.getFechaLimite() != null) {
                statement.setDate(
                        3,
                        Date.valueOf(task.getFechaLimite())
                );
            } else {
                statement.setNull(3, Types.DATE);
            }

            statement.setString(
                    4,
                    task.getPrioridad().name()
            );

            statement.setString(
                    5,
                    task.getEstado().name()
            );

            statement.setInt(
                    6,
                    task.getProgreso()
            );

            statement.setInt(
                    7,
                    task.getIdUsuario()
            );

            statement.setInt(
                    8,
                    task.getIdCategoria()
            );

            statement.executeUpdate();

            System.out.println("Tarea guardada correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //  Obtener todas las tareas
    public List<Task> obtenerTodas() {

        List<Task> tareas = new ArrayList<>();

        String sql = "SELECT * FROM tareas";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                Task task = convertirTask(resultado);

                tareas.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tareas;
    }


    // Buscar una tarea por ID
    public Task buscarPorId(int id) {

        String sql = "SELECT * FROM tareas WHERE id_tarea = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {

                    return convertirTask(resultado);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // UPDATE
    public void actualizar(Task task) {

        String sql = """
                UPDATE tareas
                SET titulo = ?,
                    fecha_limite = ?,
                    prioridad = ?,
                    estado = ?,
                    progreso = ?,
                    id_usuario = ?,
                    id_categoria = ?
                WHERE id_tarea = ?
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, task.getTitulo());

            if (task.getFechaLimite() != null) {

                statement.setDate(
                        2,
                        Date.valueOf(task.getFechaLimite())
                );

            } else {

                statement.setNull(2, Types.DATE);
            }

            statement.setString(
                    3,
                    task.getPrioridad().name()
            );

            statement.setString(
                    4,
                    task.getEstado().name()
            );

            statement.setInt(
                    5,
                    task.getProgreso()
            );

            statement.setInt(
                    6,
                    task.getIdUsuario()
            );

            statement.setInt(
                    7,
                    task.getIdCategoria()
            );

            statement.setInt(
                    8,
                    task.getId()
            );

            statement.executeUpdate();

            System.out.println("Tarea actualizada correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // DELETE
    public void eliminar(int id) {

        String sql = "DELETE FROM tareas WHERE id_tarea = ?";

        try (Connection conexion = DatabaseConnection.conectar();

             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Tarea eliminada correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Método auxiliar para convertir un registro de BD en un objeto Task
    private Task convertirTask(ResultSet resultado) throws SQLException {

        Task task = new Task();

        task.setId(
                resultado.getInt("id_tarea")
        );

        task.setTitulo(
                resultado.getString("titulo")
        );

        Date fechaCreacion =
                resultado.getDate("fecha_creacion");

        if (fechaCreacion != null) {

            task.setFechaCreacion(
                    fechaCreacion.toLocalDate()
            );
        }

        Date fechaLimite =
                resultado.getDate("fecha_limite");

        if (fechaLimite != null) {

            task.setFechaLimite(
                    fechaLimite.toLocalDate()
            );
        }

        task.setPrioridad(
                Prioridad.valueOf(
                        resultado.getString("prioridad")
                )
        );

        task.setEstado(
                Estado.valueOf(
                        resultado.getString("estado")
                )
        );

        task.setProgreso(
                resultado.getInt("progreso")
        );

        task.setIdUsuario(
                resultado.getInt("id_usuario")
        );

        task.setIdCategoria(
                resultado.getInt("id_categoria")
        );

        return task;
    }
}