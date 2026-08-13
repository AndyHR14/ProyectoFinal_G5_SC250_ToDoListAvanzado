
package org.todolist.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.todolist.controller.ReminderController;
import org.todolist.controller.TaskController;

import org.todolist.model.Reminder;
import org.todolist.model.Task;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/recordatorios")
public class ReminderServlet extends HttpServlet {

    private final ReminderController reminderController =
            new ReminderController();

    private final TaskController taskController =
            new TaskController();

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");


    
    /// MOSTRAR RECORDATORIOS
    

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion =
                request.getParameter("accion");


        
        /// EDITAR RECORDATORIO
        

        if ("editar".equals(accion)) {

            String idTexto =
                    request.getParameter("id");

            if (idTexto != null && !idTexto.isBlank()) {

                int id =
                        Integer.parseInt(idTexto);

                Reminder reminder =
                        reminderController.buscarReminderPorId(id);

                request.setAttribute(
                        "reminderEditar",
                        reminder
                );
            }
        }


        
        /// OBTENER TODOS LOS RECORDATORIOS
        

        List<Reminder> recordatorios =
                reminderController.obtenerTodosLosReminder();

        request.setAttribute(
                "recordatorios",
                recordatorios
        );


        
        /// OBTENER TODAS LAS TAREAS
        

        List<Task> tareas =
                taskController.obtenerTodasLasTareas();

        request.setAttribute(
                "tareas",
                tareas
        );


        
        /// MOSTRAR JSP
        

        request.getRequestDispatcher(
                "/recordatorios.jsp"
        ).forward(
                request,
                response
        );
    }


    
    /// CREAR / ACTUALIZAR / ELIMINAR / TOGGLE
    

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion =
                request.getParameter("accion");


        
        /// CREAR RECORDATORIO
        

        if ("crear".equals(accion)
                || accion == null) {

            String fechaHoraTexto =
                    request.getParameter("fechaHora");

            String mensaje =
                    request.getParameter("mensaje");

            String idTareaTexto =
                    request.getParameter("idTarea");


            if (fechaHoraTexto != null
                    && !fechaHoraTexto.isBlank()
                    && idTareaTexto != null
                    && !idTareaTexto.isBlank()) {

                LocalDateTime fechaHora =
                        LocalDateTime.parse(
                                fechaHoraTexto,
                                formatter
                        );

                int idTarea =
                        Integer.parseInt(idTareaTexto);


                Reminder reminder =
                        new Reminder();

                reminder.setFechaHora(
                        fechaHora
                );

                reminder.setMensaje(
                        mensaje
                );

                reminder.setActivo(
                        true
                );

                reminder.setIdTarea(
                        idTarea
                );


                reminderController.crearReminder(
                        reminder
                );
            }
        }


        
        /// ACTUALIZAR RECORDATORIO
        

        else if ("actualizar".equals(accion)) {

            String idTexto =
                    request.getParameter("id");

            String fechaHoraTexto =
                    request.getParameter("fechaHora");

            String mensaje =
                    request.getParameter("mensaje");

            String idTareaTexto =
                    request.getParameter("idTarea");

            String activoTexto =
                    request.getParameter("activo");


            if (idTexto != null
                    && !idTexto.isBlank()) {

                int id =
                        Integer.parseInt(idTexto);

                Reminder reminder =
                        reminderController.buscarReminderPorId(id);


                if (reminder != null) {

                    if (fechaHoraTexto != null
                            && !fechaHoraTexto.isBlank()) {

                        LocalDateTime fechaHora =
                                LocalDateTime.parse(
                                        fechaHoraTexto,
                                        formatter
                                );

                        reminder.setFechaHora(
                                fechaHora
                        );
                    }


                    reminder.setMensaje(
                            mensaje
                    );


                    if (idTareaTexto != null
                            && !idTareaTexto.isBlank()) {

                        reminder.setIdTarea(
                                Integer.parseInt(idTareaTexto)
                        );
                    }


                    reminder.setActivo(
                            "true".equals(activoTexto)
                    );


                    reminderController.actualizarReminder(
                            reminder
                    );
                }
            }
        }


        
        /// ACTIVAR / DESACTIVAR RECORDATORIO
        

        else if ("toggle".equals(accion)) {

            String idTexto =
                    request.getParameter("id");


            if (idTexto != null
                    && !idTexto.isBlank()) {

                int id =
                        Integer.parseInt(idTexto);

                Reminder reminder =
                        reminderController.buscarReminderPorId(id);


                if (reminder != null) {

                    reminder.setActivo(
                            !reminder.isActivo()
                    );

                    reminderController.actualizarReminder(
                            reminder
                    );
                }
            }
        }


        
        /// ELIMINAR RECORDATORIO
        

        else if ("eliminar".equals(accion)) {

            String idTexto =
                    request.getParameter("id");


            if (idTexto != null
                    && !idTexto.isBlank()) {

                int id =
                        Integer.parseInt(idTexto);

                reminderController.eliminarReminder(id);
            }
        }


        
        /// VOLVER A RECORDATORIOS
        

        response.sendRedirect(
                request.getContextPath()
                        + "/recordatorios"
        );
    }
}
