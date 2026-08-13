package org.todolist.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.todolist.controller.TaskController;
import org.todolist.controller.CategoryController;
import org.todolist.controller.TagController;
import org.todolist.controller.TaskTagController;
import org.todolist.controller.SubTaskController;
import org.todolist.controller.ReminderController;

import org.todolist.model.Task;
import org.todolist.model.Category;
import org.todolist.model.Tag;
import org.todolist.model.SubTask;
import org.todolist.model.Reminder;
import org.todolist.model.User;

import org.todolist.enums.Estado;
import org.todolist.enums.Prioridad;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;


@WebServlet("/tareas")
public class TaskServlet extends HttpServlet {



    ///CONTROLLERS


    private final TaskController taskController =
            new TaskController();

    private final CategoryController categoryController =
            new CategoryController();

    private final TagController tagController =
            new TagController();

    private final TaskTagController taskTagController =
            new TaskTagController();

    private final SubTaskController subTaskController =
            new SubTaskController();

    private final ReminderController reminderController =
            new ReminderController();



    ///OBTENER USUARIO DE LA SESIÓN


    private User obtenerUsuarioSesion(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);

        if (session == null) {

            return null;
        }

        Object usuario =
                session.getAttribute("usuario");

        if (usuario instanceof User) {

            return (User) usuario;
        }

        return null;
    }



    ///REDIRIGIR AL LOGIN


    private void redirigirLogin(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                        + "/usuario?accion=login"
        );
    }



    ///MOSTRAR TAREAS


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {



        ///VERIFICAR SESIÓN


        User usuario =
                obtenerUsuarioSesion(request);


        if (usuario == null) {

            redirigirLogin(
                    request,
                    response
            );

            return;
        }


        int idUsuario =
                usuario.getId();



        ///ACCIÓN


        String accion =
                request.getParameter("accion");



        ///EDITAR TAREA


        if ("editar".equals(accion)) {

            String idTexto =
                    request.getParameter("id");


            if (idTexto != null && !idTexto.isBlank()) {

                int id =
                        Integer.parseInt(idTexto);


                Task tarea =
                        taskController.buscarTareaPorId(id);


                
                ///VERIFICAR QUE LA TAREA PERTENEZCA AL USUARIO
                

                if (tarea != null &&
                        tarea.getIdUsuario() == idUsuario) {


                    request.setAttribute(
                            "tareaEditar",
                            tarea
                    );


                    ///
                    ///OBTENER ETIQUETAS DE LA TAREA
                    ///

                    List<Integer> etiquetasTarea =
                            taskTagController
                                    .obtenerEtiquetasDeTarea(id);


                    request.setAttribute(
                            "etiquetasTarea",
                            etiquetasTarea
                    );

                } else {

                    request.setAttribute(
                            "error",
                            "No tiene permiso para editar esta tarea."
                    );
                }
            }
        }



        ///OBTENER TAREAS DEL USUARIO


        List<Task> tareas =
                taskController.obtenerTareasPorUsuario(
                        idUsuario
                );


        request.setAttribute(
                "tareas",
                tareas
        );



        ///OBTENER TODAS LAS CATEGORÍAS


        List<Category> categorias =
                categoryController.obtenerCategorias();


        request.setAttribute(
                "categorias",
                categorias
        );



        ///OBTENER TODAS LAS ETIQUETAS


        List<Tag> etiquetas =
                tagController.obtenerTags();


        request.setAttribute(
                "etiquetas",
                etiquetas
        );



        ///OBTENER SUBTAREAS


        List<SubTask> subtareas =
                subTaskController.obtenerTodasLasSubTasks();



        ///ORGANIZAR SUBTAREAS POR TAREA


        Map<Integer, List<SubTask>> subtareasPorTarea =
                new HashMap<>();


        if (subtareas != null) {

            for (SubTask subTask : subtareas) {

                int idTarea =
                        subTask.getIdTarea();


                
                ///SOLO SUBTAREAS DE LAS TAREAS DEL USUARIO
                

                boolean perteneceAlUsuario =
                        false;


                for (Task tarea : tareas) {

                    if (tarea.getId() == idTarea) {

                        perteneceAlUsuario = true;
                        break;
                    }
                }


                if (perteneceAlUsuario) {

                    subtareasPorTarea
                            .computeIfAbsent(
                                    idTarea,
                                    k -> new ArrayList<>()
                            )
                            .add(subTask);
                }
            }
        }


        request.setAttribute(
                "subtareasPorTarea",
                subtareasPorTarea
        );



        ///OBTENER RECORDATORIOS


        List<Reminder> recordatorios =
                reminderController.obtenerTodosLosReminder();



        ///ORGANIZAR RECORDATORIOS


        Map<Integer, List<Reminder>> recordatoriosPorTarea =
                new HashMap<>();


        if (recordatorios != null) {

            for (Reminder reminder : recordatorios) {


                
                ///SOLO RECORDATORIOS ACTIVOS
                

                if (reminder.isActivo()) {

                    int idTarea =
                            reminder.getIdTarea();


                    ///
                    ///SOLO RECORDATORIOS DE TAREAS DEL USUARIO
                    ///

                    boolean perteneceAlUsuario =
                            false;


                    for (Task tarea : tareas) {

                        if (tarea.getId() == idTarea) {

                            perteneceAlUsuario = true;
                            break;
                        }
                    }


                    if (perteneceAlUsuario) {

                        recordatoriosPorTarea
                                .computeIfAbsent(
                                        idTarea,
                                        k -> new ArrayList<>()
                                )
                                .add(reminder);
                    }
                }
            }
        }


        request.setAttribute(
                "recordatoriosPorTarea",
                recordatoriosPorTarea
        );



        ///OBTENER ETIQUETAS DE CADA TAREA


        for (Task tarea : tareas) {

            List<Integer> etiquetasDeTarea =
                    taskTagController
                            .obtenerEtiquetasDeTarea(
                                    tarea.getId()
                            );


            request.setAttribute(
                    "etiquetasTarea_" + tarea.getId(),
                    etiquetasDeTarea
            );
        }



        ///MOSTRAR JSP


        request.getRequestDispatcher(
                "/tareas.jsp"
        ).forward(
                request,
                response
        );
    }



    ///CREAR / ACTUALIZAR / ELIMINAR


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {



        ///VERIFICAR SESIÓN


        User usuario =
                obtenerUsuarioSesion(request);


        if (usuario == null) {

            redirigirLogin(
                    request,
                    response
            );

            return;
        }


        int idUsuario =
                usuario.getId();



        ///ACCIÓN


        String accion =
                request.getParameter("accion");



        ///CREAR TAREA


        if ("crear".equals(accion)
                || accion == null) {


            String titulo =
                    request.getParameter("titulo");


            String descripcion =
                    request.getParameter("descripcion");


            String fechaLimiteTexto =
                    request.getParameter("fechaLimite");


            String prioridadTexto =
                    request.getParameter("prioridad");


            String idCategoriaTexto =
                    request.getParameter("idCategoria");


            String idEtiquetaTexto =
                    request.getParameter("idEtiqueta");


            
            ///FECHA LÍMITE
            

            LocalDate fechaLimite =
                    null;


            if (fechaLimiteTexto != null
                    && !fechaLimiteTexto.isBlank()) {

                fechaLimite =
                        LocalDate.parse(
                                fechaLimiteTexto
                        );
            }


            
            ///CREAR TASK
            

            Task task =
                    new Task();


            task.setTitulo(
                    titulo
            );


            task.setDescripcion(
                    descripcion
            );


            task.setFechaCreacion(
                    LocalDate.now()
            );


            task.setFechaLimite(
                    fechaLimite
            );


            
            ///PRIORIDAD
            

            task.setPrioridad(
                    Prioridad.valueOf(
                            prioridadTexto
                    )
            );


            
            ///ESTADO INICIAL
            

            task.setEstado(
                    Estado.PENDIENTE
            );


            
            ///PROGRESO INICIAL
            

            task.setProgreso(
                    0
            );


            
            ///USUARIO LOGUEADO
            

            task.setIdUsuario(
                    idUsuario
            );


            
            ///CATEGORÍA
            

            if (idCategoriaTexto != null
                    && !idCategoriaTexto.isBlank()) {

                task.setIdCategoria(
                        Integer.parseInt(
                                idCategoriaTexto
                        )
                );

            } else {

                task.setIdCategoria(
                        null
                );
            }


            
            ///GUARDAR TAREA
            

            taskController.crearTarea(
                    task
            );


            
            ///OBTENER TAREAS DEL USUARIO
            

            List<Task> tareas =
                    taskController.obtenerTareasPorUsuario(
                            idUsuario
                    );


            Task tareaCreada =
                    null;


            for (Task tarea : tareas) {

                if (tarea.getTitulo() != null
                        && tarea.getTitulo().equals(titulo)) {

                    if (tareaCreada == null
                            || tarea.getId()
                            > tareaCreada.getId()) {

                        tareaCreada =
                                tarea;
                    }
                }
            }


            
            ///ASIGNAR ETIQUETA
            

            if (tareaCreada != null
                    && idEtiquetaTexto != null
                    && !idEtiquetaTexto.isBlank()) {


                int idEtiqueta =
                        Integer.parseInt(
                                idEtiquetaTexto
                        );


                taskTagController.asignarEtiqueta(
                        tareaCreada.getId(),
                        idEtiqueta
                );
            }
        }



        ///ACTUALIZAR TAREA


        else if ("actualizar".equals(accion)) {


            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );


            String titulo =
                    request.getParameter("titulo");


            String descripcion =
                    request.getParameter("descripcion");


            String fechaLimiteTexto =
                    request.getParameter("fechaLimite");


            String prioridadTexto =
                    request.getParameter("prioridad");


            String estadoTexto =
                    request.getParameter("estado");


            String progresoTexto =
                    request.getParameter("progreso");


            String idCategoriaTexto =
                    request.getParameter("idCategoria");


            String idEtiquetaTexto =
                    request.getParameter("idEtiqueta");


            
            ///BUSCAR TAREA
            

            Task task =
                    taskController.buscarTareaPorId(id);


            
            ///VERIFICAR PROPIEDAD
            

            if (task != null
                    && task.getIdUsuario() == idUsuario) {


                
                ///FECHA
                

                LocalDate fechaLimite =
                        null;


                if (fechaLimiteTexto != null
                        && !fechaLimiteTexto.isBlank()) {

                    fechaLimite =
                            LocalDate.parse(
                                    fechaLimiteTexto
                            );
                }


                
                ///DATOS
                

                task.setTitulo(
                        titulo
                );


                task.setDescripcion(
                        descripcion
                );


                task.setFechaLimite(
                        fechaLimite
                );


                task.setPrioridad(
                        Prioridad.valueOf(
                                prioridadTexto
                        )
                );


                task.setEstado(
                        Estado.valueOf(
                                estadoTexto
                        )
                );


                task.setProgreso(
                        Integer.parseInt(
                                progresoTexto
                        )
                );


                
                ///MANTENER USUARIO
                

                task.setIdUsuario(
                        idUsuario
                );


                
                ///CATEGORÍA
                

                if (idCategoriaTexto != null
                        && !idCategoriaTexto.isBlank()) {

                    task.setIdCategoria(
                            Integer.parseInt(
                                    idCategoriaTexto
                            )
                    );

                } else {

                    task.setIdCategoria(
                            null
                    );
                }


                
                ///ACTUALIZAR TAREA
                

                taskController.actualizarTarea(
                        task
                );


                
                ///ACTUALIZAR ETIQUETA
                

                taskTagController
                        .eliminarTodasLasEtiquetas(id);


                if (idEtiquetaTexto != null
                        && !idEtiquetaTexto.isBlank()) {


                    int idEtiqueta =
                            Integer.parseInt(
                                    idEtiquetaTexto
                            );


                    taskTagController.asignarEtiqueta(
                            id,
                            idEtiqueta
                    );
                }

            } else {

                System.out.println(
                        "Intento de modificar una tarea " +
                                "que no pertenece al usuario."
                );
            }
        }



        ///ELIMINAR TAREA


        else if ("eliminar".equals(accion)) {


            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );


            
            ///BUSCAR TAREA
            

            Task task =
                    taskController.buscarTareaPorId(id);


            
            ///VERIFICAR PROPIEDAD
            

            if (task != null
                    && task.getIdUsuario() == idUsuario) {


                
                ///ELIMINAR ETIQUETAS
                

                taskTagController
                        .eliminarTodasLasEtiquetas(id);


                
                ///ELIMINAR SUBTAREAS
                

                List<SubTask> subtareas =
                        subTaskController
                                .obtenerTodasLasSubTasks();


                if (subtareas != null) {

                    for (SubTask subTask : subtareas) {

                        if (subTask.getIdTarea() == id) {

                            subTaskController
                                    .eliminarSubTask(
                                            subTask.getIdSubtarea()
                                    );
                        }
                    }
                }


                
                ///ELIMINAR RECORDATORIOS
                

                List<Reminder> recordatorios =
                        reminderController
                                .obtenerTodosLosReminder();


                if (recordatorios != null) {

                    for (Reminder reminder : recordatorios) {

                        if (reminder.getIdTarea() == id) {

                            reminderController
                                    .eliminarReminder(
                                            reminder.getIdRecordatorio()
                                    );
                        }
                    }
                }


                
                ///ELIMINAR TAREA
                

                taskController.eliminarTarea(
                        id
                );

            } else {

                System.out.println(
                        "Intento de eliminar una tarea " +
                                "que no pertenece al usuario."
                );
            }
        }



        ///VOLVER A LA LISTA


        response.sendRedirect(
                request.getContextPath()
                        + "/tareas"
        );
    }
}