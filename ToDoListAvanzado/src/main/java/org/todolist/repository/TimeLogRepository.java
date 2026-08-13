/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.repository;

/**
 *
 * @author Alvm Tech
 */

import org.todolist.model.TimeLog;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeLogRepository {

    ///---Guardar registro de tiempo---///
    public int guardar(TimeLog registro) {

        String sql = """
                INSERT INTO registro_tiempo
                (inicio, fin, duracion_minutos, id_tarea)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            statement.setTimestamp(
                    1,
                    Timestamp.valueOf(registro.getInicio())
            );

            if (registro.getFin() != null) {

                statement.setTimestamp(
                        2,
                        Timestamp.valueOf(registro.getFin())
                );

            } else {

                statement.setNull(
                        2,
                        Types.TIMESTAMP
                );
            }

            statement.setInt(
                    3,
                    registro.getDuracionMinutos()
            );

            statement.setInt(
                    4,
                    registro.getIdTarea()
            );

            statement.executeUpdate();

            try (ResultSet resultado =
                         statement.getGeneratedKeys()) {

                if (resultado.next()) {

                    int idGenerado =
                            resultado.getInt(1);

                    registro.setIdRegistro(idGenerado);

                    System.out.println(
                            "Registro de tiempo guardado correctamente."
                    );

                    return idGenerado;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }




    ///---Obtener todos los registros---///
    public List<TimeLog> obtenerTodos() {

        List<TimeLog> registros = new ArrayList<>();

        String sql = "SELECT * FROM registro_tiempo";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                registros.add(
                        convertirRegistro(resultado)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return registros;
    }



    ///---Buscar registro por ID---///
    public TimeLog buscarPorId(int id) {

        String sql =
                "SELECT * FROM registro_tiempo WHERE id_registro = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {

                    return convertirRegistro(resultado);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    
    
    public TimeLog buscarRegistroActivoPorTarea(int idTarea) {

        String sql = """
                SELECT *
                FROM registro_tiempo
                WHERE id_tarea = ?
                  AND fin IS NULL
                LIMIT 1
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, idTarea);

            try (ResultSet resultado =
                         statement.executeQuery()) {

                if (resultado.next()) {

                    return convertirRegistro(resultado);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }




    ///---Actualizar registro---///
    public void actualizar(TimeLog registro) {

        String sql = """
                UPDATE registro_tiempo
                SET inicio=?,
                    fin=?,
                    duracion_minutos=?,
                    id_tarea=?
                WHERE id_registro=?
                """;

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setTimestamp(
                    1,
                    Timestamp.valueOf(registro.getInicio())
            );

            if (registro.getFin() != null) {

                statement.setTimestamp(
                        2,
                        Timestamp.valueOf(registro.getFin())
                );

            } else {

                statement.setNull(2, Types.TIMESTAMP);
            }

            statement.setInt(
                    3,
                    registro.getDuracionMinutos()
            );

            statement.setInt(
                    4,
                    registro.getIdTarea()
            );

            statement.setInt(
                    5,
                    registro.getIdRegistro()
            );

            statement.executeUpdate();

            System.out.println("Registro actualizado correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    ///---Eliminar registro---///
    public void eliminar(int id) {

        String sql =
                "DELETE FROM registro_tiempo WHERE id_registro=?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

            System.out.println("Registro eliminado correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    ///---Convertir registro de BD a objeto---///
    private TimeLog convertirRegistro(ResultSet resultado)
            throws SQLException {

        TimeLog registro = new TimeLog();

        registro.setIdRegistro(
                resultado.getInt("id_registro")
        );

        Timestamp inicio =
                resultado.getTimestamp("inicio");

        if (inicio != null) {

            registro.setInicio(
                    inicio.toLocalDateTime()
            );
        }

        Timestamp fin =
                resultado.getTimestamp("fin");

        if (fin != null) {

            registro.setFin(
                    fin.toLocalDateTime()
            );
        }

        registro.setDuracionMinutos(
                resultado.getInt("duracion_minutos")
        );

        registro.setIdTarea(
                resultado.getInt("id_tarea")
        );

        return registro;
    }

}