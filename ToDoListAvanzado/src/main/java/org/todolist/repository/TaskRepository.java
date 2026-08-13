
package org.todolist.repository;

import org.todolist.model.Task;
import org.todolist.enums.Estado;
import org.todolist.enums.Prioridad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private final TaskTagRepository taskTagRepository;



    /// CONSTRUCTOR


    public TaskRepository() {

        this.taskTagRepository =
                new TaskTagRepository();
    }



    /// GUARDAR TAREA


    public void guardar(Task task) {

        String sql = """
            INSERT INTO tareas
            (titulo, descripcion, fecha_creacion, fecha_limite,
             prioridad, estado, progreso, id_usuario, id_categoria)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conexion =
                     DatabaseConnection.conectar();

             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {


            statement.setString(
                    1,
                    task.getTitulo()
            );


            statement.setString(
                    2,
                    task.getDescripcion()
            );


            statement.setDate(
                    3,
                    Date.valueOf(
                            task.getFechaCreacion()
                    )
            );


            if (task.getFechaLimite() != null) {

                statement.setDate(
                        4,
                        Date.valueOf(
                                task.getFechaLimite()
                        )
                );

            } else {

                statement.setNull(
                        4,
                        Types.DATE
                );
            }


            statement.setString(
                    5,
                    task.getPrioridad().name()
            );


            statement.setString(
                    6,
                    task.getEstado().name()
            );


            statement.setInt(
                    7,
                    task.getProgreso()
            );


            statement.setInt(
                    8,
                    task.getIdUsuario()
            );


            if (task.getIdCategoria() != null) {

                statement.setInt(
                        9,
                        task.getIdCategoria()
                );

            } else {

                statement.setNull(
                        9,
                        Types.INTEGER
                );
            }


            statement.executeUpdate();


            System.out.println(
                    "Tarea guardada correctamente."
            );


        } catch (SQLException e) {

            e.printStackTrace();
        }
    }



    /// OBTENER TODAS LAS TAREAS


    public List<Task> obtenerTodas() {

        List<Task> tareas =
                new ArrayList<>();


        String sql =
                "SELECT * FROM tareas";


        try (Connection conexion =
                     DatabaseConnection.conectar();

             PreparedStatement statement =
                     conexion.prepareStatement(sql);

             ResultSet resultado =
                     statement.executeQuery()) {


            while (resultado.next()) {

                Task task =
                        convertirTask(resultado);

                tareas.add(task);
            }


        } catch (SQLException e) {

            e.printStackTrace();
        }


        return tareas;
    }



    /// OBTENER TAREAS POR USUARIO


    public List<Task> obtenerPorUsuario(
            int idUsuario) {

        List<Task> tareas =
                new ArrayList<>();


        String sql =
                "SELECT * FROM tareas WHERE id_usuario = ?";


        try (Connection conexion =
                     DatabaseConnection.conectar();

             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {


            statement.setInt(
                    1,
                    idUsuario
            );


            try (ResultSet resultado =
                         statement.executeQuery()) {


                while (resultado.next()) {

                    Task task =
                            convertirTask(resultado);

                    tareas.add(task);
                }
            }


        } catch (SQLException e) {

            e.printStackTrace();
        }


        return tareas;
    }



    /// BUSCAR TAREA POR ID


    public Task buscarPorId(int id) {

        String sql =
                "SELECT * FROM tareas WHERE id_tarea = ?";


        try (Connection conexion =
                     DatabaseConnection.conectar();

             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {


            statement.setInt(
                    1,
                    id
            );


            try (ResultSet resultado =
                         statement.executeQuery()) {


                if (resultado.next()) {

                    return convertirTask(
                            resultado
                    );
                }
            }


        } catch (SQLException e) {

            e.printStackTrace();
        }


        return null;
    }



    ///ACTUALIZAR TAREA


    public void actualizar(Task task) {

        String sql = """
            UPDATE tareas
            SET titulo = ?,
                descripcion = ?,
                fecha_limite = ?,
                prioridad = ?,
                estado = ?,
                progreso = ?,
                id_usuario = ?,
                id_categoria = ?
            WHERE id_tarea = ?
            """;


        try (Connection conexion =
                     DatabaseConnection.conectar();

             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {


            statement.setString(
                    1,
                    task.getTitulo()
            );


            statement.setString(
                    2,
                    task.getDescripcion()
            );


            if (task.getFechaLimite() != null) {

                statement.setDate(
                        3,
                        Date.valueOf(
                                task.getFechaLimite()
                        )
                );

            } else {

                statement.setNull(
                        3,
                        Types.DATE
                );
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


            if (task.getIdCategoria() != null) {

                statement.setInt(
                        8,
                        task.getIdCategoria()
                );

            } else {

                statement.setNull(
                        8,
                        Types.INTEGER
                );
            }


            statement.setInt(
                    9,
                    task.getId()
            );


            statement.executeUpdate();


            System.out.println(
                    "Tarea actualizada correctamente."
            );


        } catch (SQLException e) {

            e.printStackTrace();
        }
    }



    /// ELIMINAR TAREA


    public void eliminar(int id) {

        taskTagRepository
                .eliminarTodasLasEtiquetas(id);


        String sql =
                "DELETE FROM tareas WHERE id_tarea = ?";


        try (Connection conexion =
                     DatabaseConnection.conectar();

             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {


            statement.setInt(
                    1,
                    id
            );


            statement.executeUpdate();


            System.out.println(
                    "Tarea eliminada correctamente."
            );


        } catch (SQLException e) {

            e.printStackTrace();
        }
    }



    /// CONVERTIR REGISTRO DE BD -> OBJETO TASK


    private Task convertirTask(
            ResultSet resultado)
            throws SQLException {


        Task task =
                new Task();



        /// ID


        task.setId(
                resultado.getInt(
                        "id_tarea"
                )
        );



        /// TITULO


        task.setTitulo(
                resultado.getString(
                        "titulo"
                )
        );



        /// DESCRIPCION


        task.setDescripcion(
                resultado.getString(
                        "descripcion"
                )
        );



        /// FECHA DE CREACION


        Date fechaCreacion =
                resultado.getDate(
                        "fecha_creacion"
                );


        if (fechaCreacion != null) {

            task.setFechaCreacion(
                    fechaCreacion.toLocalDate()
            );
        }



        /// FECHA LIMITE


        Date fechaLimite =
                resultado.getDate(
                        "fecha_limite"
                );


        if (fechaLimite != null) {

            task.setFechaLimite(
                    fechaLimite.toLocalDate()
            );
        }



        /// PRIORIDAD


        String prioridad =
                resultado.getString(
                        "prioridad"
                );


        if (prioridad != null) {

            task.setPrioridad(
                    Prioridad.valueOf(
                            prioridad
                    )
            );
        }



        /// ESTADO


        String estado =
                resultado.getString(
                        "estado"
                );


        if (estado != null) {

            task.setEstado(
                    Estado.valueOf(
                            estado
                    )
            );
        }



        ///PROGRESO


        task.setProgreso(
                resultado.getInt(
                        "progreso"
                )
        );



        /// USUARIO


        task.setIdUsuario(
                resultado.getInt(
                        "id_usuario"
                )
        );



        /// CATEGORIA


        Object categoria =
                resultado.getObject(
                        "id_categoria"
                );


        if (categoria != null) {

            task.setIdCategoria(
                    ((Number) categoria).intValue()
            );

        } else {

            task.setIdCategoria(
                    null
            );
        }


        return task;
    }
}

