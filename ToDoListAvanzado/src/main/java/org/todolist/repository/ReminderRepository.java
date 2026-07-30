/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.repository;

/**
 *
 * @author Alvm Tech
 */
package org.todolist.repository;

import org.todolist.model.Reminder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReminderRepository {

    ///---Guardar Recordatorio---///
    public void guardar(Reminder reminder) {

        String sql = """
                INSERT INTO recordatorios
                (fecha_hora, mensaje, activo, id_tarea)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setTimestamp(
                    1,
                    Timestamp.valueOf(reminder.getFechaHora())
            );

            statement.setString(
                    2,
                    reminder.getMensaje()
            );

            statement.setBoolean(
                    3,
                    reminder.isActivo()
            );

            statement.setInt(
                    4,
                    reminder.getIdTarea()
            );

            statement.executeUpdate();

            System.out.println("Recordatorio guardado correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    ///---Obtener todos los recordatorios---///
    public List<Reminder> obtenerTodos() {

        List<Reminder> recordatorios = new ArrayList<>();

        String sql = "SELECT * FROM recordatorios";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                recordatorios.add(
                        convertirReminder(resultado)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recordatorios;
    }



    ///---Buscar recordatorio por ID---///
    public Reminder buscarPorId(int id) {

        String sql =
                "SELECT * FROM recordatorios WHERE id_recordatorio = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {

                    return convertirReminder(resultado);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    ///---Actualizar recordatorio---///
    public void actualizar(Reminder reminder) {

        String sql = """
                UPDATE recordatorios
                SET fecha_hora = ?,
                    mensaje = ?,
                    activo = ?,
                    id_tarea = ?
                WHERE id_recordatorio = ?
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setTimestamp(
                    1,
                    Timestamp.valueOf(reminder.getFechaHora())
            );

            statement.setString(
                    2,
                    reminder.getMensaje()
            );

            statement.setBoolean(
                    3,
                    reminder.isActivo()
            );

            statement.setInt(
                    4,
                    reminder.getIdTarea()
            );

            statement.setInt(
                    5,
                    reminder.getIdRecordatorio()
            );

            statement.executeUpdate();

            System.out.println("Recordatorio actualizado correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    ///---Eliminar recordatorio---///
    public void eliminar(int id) {

        String sql =
                "DELETE FROM recordatorios WHERE id_recordatorio = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

            System.out.println("Recordatorio eliminado correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    ///---Convertir el registro de la BD en un objeto Reminder---///
    private Reminder convertirReminder(ResultSet resultado)
            throws SQLException {

        Reminder reminder = new Reminder();

        reminder.setIdRecordatorio(
                resultado.getInt("id_recordatorio")
        );

        Timestamp fechaHora =
                resultado.getTimestamp("fecha_hora");

        if (fechaHora != null) {

            reminder.setFechaHora(
                    fechaHora.toLocalDateTime()
            );
        }

        reminder.setMensaje(
                resultado.getString("mensaje")
        );

        reminder.setActivo(
                resultado.getBoolean("activo")
        );

        reminder.setIdTarea(
                resultado.getInt("id_tarea")
        );

        return reminder;
    }

}