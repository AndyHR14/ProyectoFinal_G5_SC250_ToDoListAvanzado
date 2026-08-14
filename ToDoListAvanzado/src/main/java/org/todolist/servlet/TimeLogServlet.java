package org.todolist.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.todolist.controller.TimeLogController;
import org.todolist.model.TimeLog;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/timelog")
public class TimeLogServlet extends HttpServlet {

    private final TimeLogController timeLogController =
            new TimeLogController();



    /// MOSTRAR TIMELOG / PREPARAR EDICION


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion =
                request.getParameter("accion");



        /// EDITAR


        if ("editar".equals(accion)) {

            String idTexto =
                    request.getParameter("id");

            if (idTexto != null && !idTexto.isBlank()) {

                try {

                    int id =
                            Integer.parseInt(idTexto);

                    TimeLog registro =
                            timeLogController.buscarRegistroPorId(id);

                    if (registro != null) {

                        request.setAttribute(
                                "timelogEditar",
                                registro
                        );

                    }

                } catch (NumberFormatException e) {

                    System.err.println(
                            "ID de registro de tiempo inválido: "
                                    + idTexto
                    );

                } catch (IllegalArgumentException e) {

                    System.err.println(
                            "Error al buscar registro: "
                                    + e.getMessage()
                    );
                }
            }
        }



        /// OBTENER TODOS LOS REGISTROS


        List<TimeLog> timelogs =
                timeLogController.obtenerTodosLosRegistros();

        request.setAttribute(
                "timelogs",
                timelogs
        );



        /// MOSTRAR JSP


        request.getRequestDispatcher(
                "/timelog.jsp"
        ).forward(
                request,
                response
        );
    }



    /// CREAR / ACTUALIZAR / ELIMINAR /
    /// INICIAR / FINALIZAR


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion =
                request.getParameter("accion");



        /// CREAR


        if ("crear".equals(accion)) {

            try {

                String inicioTexto =
                        request.getParameter("inicio");

                String finTexto =
                        request.getParameter("fin");

                String idTareaTexto =
                        request.getParameter("idTarea");


                LocalDateTime inicio =
                        LocalDateTime.parse(inicioTexto);


                LocalDateTime fin = null;


                if (finTexto != null &&
                        !finTexto.isBlank()) {

                    fin =
                            LocalDateTime.parse(finTexto);
                }


                int idTarea =
                        Integer.parseInt(idTareaTexto);


                TimeLog registro =
                        new TimeLog();


                registro.setInicio(inicio);

                registro.setFin(fin);

                registro.setDuracionMinutos(0);

                registro.setIdTarea(idTarea);


                timeLogController.crearRegistro(
                        registro
                );


            } catch (NumberFormatException e) {

                System.err.println(
                        "ID de tarea inválido."
                );

            } catch (DateTimeParseException e) {

                System.err.println(
                        "Formato de fecha inválido."
                );

            } catch (IllegalArgumentException e) {

                System.err.println(
                        "Error al crear registro: "
                                + e.getMessage()
                );
            }
        }



        /// ACTUALIZAR


        else if ("actualizar".equals(accion)) {

            try {

                String idTexto =
                        request.getParameter("idRegistro");

                String inicioTexto =
                        request.getParameter("inicio");

                String finTexto =
                        request.getParameter("fin");

                String idTareaTexto =
                        request.getParameter("idTarea");


                int idRegistro =
                        Integer.parseInt(idTexto);


                int idTarea =
                        Integer.parseInt(idTareaTexto);


                LocalDateTime inicio =
                        LocalDateTime.parse(inicioTexto);


                LocalDateTime fin = null;


                if (finTexto != null &&
                        !finTexto.isBlank()) {

                    fin =
                            LocalDateTime.parse(finTexto);
                }


                TimeLog registro =
                        new TimeLog();


                registro.setIdRegistro(
                        idRegistro
                );

                registro.setInicio(
                        inicio
                );

                registro.setFin(
                        fin
                );

                registro.setDuracionMinutos(0);

                registro.setIdTarea(
                        idTarea
                );


                timeLogController.actualizarRegistro(
                        registro
                );


            } catch (NumberFormatException e) {

                System.err.println(
                        "ID inválido."
                );

            } catch (DateTimeParseException e) {

                System.err.println(
                        "Formato de fecha inválido."
                );

            } catch (IllegalArgumentException e) {

                System.err.println(
                        "Error al actualizar registro: "
                                + e.getMessage()
                );
            }
        }



        /// ELIMINAR


        else if ("eliminar".equals(accion)) {

            String idTexto =
                    request.getParameter("id");

            if (idTexto != null && !idTexto.isBlank()) {

                try {

                    int id =
                            Integer.parseInt(idTexto);

                    timeLogController.eliminarRegistro(
                            id
                    );

                } catch (NumberFormatException e) {

                    System.err.println(
                            "ID de registro inválido: "
                                    + idTexto
                    );

                } catch (IllegalArgumentException e) {

                    System.err.println(
                            "Error al eliminar registro: "
                                    + e.getMessage()
                    );
                }
            }
        }



        /// INICIAR CONTEO


        else if ("iniciar".equals(accion)) {

            String idTareaTexto =
                    request.getParameter("idTarea");

            if (idTareaTexto != null &&
                    !idTareaTexto.isBlank()) {

                try {

                    int idTarea =
                            Integer.parseInt(
                                    idTareaTexto
                            );

                    timeLogController.iniciarConteo(
                            idTarea
                    );

                } catch (NumberFormatException e) {

                    System.err.println(
                            "ID de tarea inválido: "
                                    + idTareaTexto
                    );

                } catch (IllegalArgumentException e) {

                    System.err.println(
                            "Error al iniciar conteo: "
                                    + e.getMessage()
                    );

                } catch (IllegalStateException e) {

                    System.err.println(
                            "Error al iniciar conteo: "
                                    + e.getMessage()
                    );
                }
            }
        }



        /// FINALIZAR CONTEO


        else if ("finalizar".equals(accion)) {

            String idRegistroTexto =
                    request.getParameter("idRegistro");

            if (idRegistroTexto != null &&
                    !idRegistroTexto.isBlank()) {

                try {

                    int idRegistro =
                            Integer.parseInt(
                                    idRegistroTexto
                            );

                    timeLogController.finalizarConteo(
                            idRegistro
                    );

                } catch (NumberFormatException e) {

                    System.err.println(
                            "ID de registro inválido: "
                                    + idRegistroTexto
                    );

                } catch (IllegalArgumentException e) {

                    System.err.println(
                            "Error al finalizar conteo: "
                                    + e.getMessage()
                    );

                } catch (IllegalStateException e) {

                    System.err.println(
                            "Error al finalizar conteo: "
                                    + e.getMessage()
                    );
                }
            }
        }



        /// VOLVER A LA LISTA


        response.sendRedirect(
                request.getContextPath() + "/timelog"
        );
    }
}