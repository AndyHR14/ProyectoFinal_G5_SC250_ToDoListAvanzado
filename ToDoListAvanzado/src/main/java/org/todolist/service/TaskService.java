
package org.todolist.service;

import org.todolist.model.Task;
import org.todolist.repository.TaskRepository;

import java.util.List;

public class TaskService {

    private final TaskRepository taskRepository;


    public TaskService() {

        this.taskRepository =
                new TaskRepository();
    }


    
    ///CREAR TAREA
    

    public void crearTarea(Task task) {

        validarTarea(task);

        taskRepository.guardar(task);
    }


    
    ///OBTENER TODAS LAS TAREAS
    

    public List<Task> obtenerTodasLasTareas() {

        return taskRepository.obtenerTodas();
    }


    
    ///OBTENER TAREAS POR USUARIO
    

    public List<Task> obtenerTareasPorUsuario(
            int idUsuario) {

        if (idUsuario <= 0) {

            throw new IllegalArgumentException(
                    "El ID del usuario debe ser mayor que 0"
            );
        }

        return taskRepository.obtenerPorUsuario(
                idUsuario
        );
    }


    
    ///BUSCAR UNA TAREA
    

    public Task buscarTareaPorId(int id) {

        if (id <= 0) {

            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0"
            );
        }

        return taskRepository.buscarPorId(id);
    }


    
    ///ACTUALIZAR TAREA
    

    public void actualizarTarea(Task task) {

        validarTarea(task);

        taskRepository.actualizar(task);
    }


    
    ///ELIMINAR TAREA
    

    public void eliminarTarea(int id) {

        if (id <= 0) {

            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0"
            );
        }

        Task tarea =
                taskRepository.buscarPorId(id);

        if (tarea == null) {

            throw new IllegalArgumentException(
                    "No existe una tarea con ese ID"
            );
        }

        taskRepository.eliminar(id);
    }


    
    ///VALIDACIONES
    

    private void validarTarea(Task task) {

        if (task == null) {

            throw new IllegalArgumentException(
                    "La tarea no puede ser null"
            );
        }


        if (task.getTitulo() == null ||
                task.getTitulo().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El titulo de la tarea es obligatorio"
            );
        }


        if (task.getProgreso() < 0 ||
                task.getProgreso() > 100) {

            throw new IllegalArgumentException(
                    "El progreso debe estar entre 0 y 100"
            );
        }


        if (task.getPrioridad() == null) {

            throw new IllegalArgumentException(
                    "La prioridad es obligatoria"
            );
        }


        if (task.getEstado() == null) {

            throw new IllegalArgumentException(
                    "El estado es obligatorio"
            );
        }


        if (task.getIdUsuario() <= 0) {

            throw new IllegalArgumentException(
                    "El usuario de la tarea es obligatorio"
            );
        }


        if (task.getFechaCreacion() == null) {

            throw new IllegalArgumentException(
                    "La fecha de creacion es obligatoria"
            );
        }


        if (task.getFechaLimite() != null &&
                task.getFechaLimite()
                        .isBefore(task.getFechaCreacion())) {

            throw new IllegalArgumentException(
                    "La fecha limite no puede ser anterior " +
                            "a la fecha de creacion"
            );
        }
    }
}

