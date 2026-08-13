package org.todolist.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.todolist.controller.SubTaskController;
import org.todolist.controller.TaskController;

import org.todolist.model.SubTask;
import org.todolist.model.Task;

import java.io.IOException;
import java.util.List;

@WebServlet("/subtareas")
public class SubTaskServlet extends HttpServlet {

    private final SubTaskController subTaskController =
            new SubTaskController();

    private final TaskController taskController =
            new TaskController();


    
    /// MOSTRAR SUBTAREAS
    

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion =
                request.getParameter("accion");


        
        /// EDITAR SUBTAREA
        

        if ("editar".equals(accion)) {

            String idTexto =
                    request.getParameter("id");

            if (idTexto != null && !idTexto.isBlank()) {

                int id =
                        Integer.parseInt(idTexto);

                SubTask subTask =
                        subTaskController.buscarSubTaskPorId(id);

                request.setAttribute(
                        "subTaskEditar",
                        subTask
                );
            }
        }


        
        /// OBTENER TODAS LAS SUBTAREAS
        

        List<SubTask> subtareas =
                subTaskController.obtenerTodasLasSubTasks();

        request.setAttribute(
                "subtareas",
                subtareas
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
                "/subtareas.jsp"
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


        
        /// CREAR SUBTAREA
        

        if ("crear".equals(accion)
                || accion == null) {

            String descripcion =
                    request.getParameter("descripcion");

            String idTareaTexto =
                    request.getParameter("idTarea");


            if (idTareaTexto != null
                    && !idTareaTexto.isBlank()) {

                int idTarea =
                        Integer.parseInt(idTareaTexto);


                SubTask subTask =
                        new SubTask();

                subTask.setDescripcion(
                        descripcion
                );

                subTask.setCompletada(
                        false
                );

                subTask.setIdTarea(
                        idTarea
                );


                subTaskController.crearSubTask(
                        subTask
                );
            }
        }


        
        /// CAMBIAR ESTADO COMPLETADA
        

        else if ("toggle".equals(accion)) {

            String idTexto =
                    request.getParameter("id");

            if (idTexto != null
                    && !idTexto.isBlank()) {

                int id =
                        Integer.parseInt(idTexto);

                subTaskController
                        .cambiarEstadoCompletada(id);
            }


            /// Volver directamente a las tareas
            response.sendRedirect(
                    request.getContextPath()
                            + "/tareas"
            );

            return;
        }


        
        /// ACTUALIZAR SUBTAREA
        

        else if ("actualizar".equals(accion)) {

            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            String descripcion =
                    request.getParameter("descripcion");

            String idTareaTexto =
                    request.getParameter("idTarea");

            String completadaTexto =
                    request.getParameter("completada");


            SubTask subTask =
                    subTaskController.buscarSubTaskPorId(id);


            if (subTask != null) {

                subTask.setDescripcion(
                        descripcion
                );


                if (idTareaTexto != null
                        && !idTareaTexto.isBlank()) {

                    subTask.setIdTarea(
                            Integer.parseInt(idTareaTexto)
                    );
                }


                subTask.setCompletada(
                        "true".equals(completadaTexto)
                );


                subTaskController.actualizarSubTask(
                        subTask
                );
            }
        }


        
        /// ELIMINAR SUBTAREA
        

        else if ("eliminar".equals(accion)) {

            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            subTaskController.eliminarSubTask(id);
        }


        
        /// VOLVER A SUBTAREAS
        

        response.sendRedirect(
                request.getContextPath()
                        + "/subtareas"
        );
    }
}