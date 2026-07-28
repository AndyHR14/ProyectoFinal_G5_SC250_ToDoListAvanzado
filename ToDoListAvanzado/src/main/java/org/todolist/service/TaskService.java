package org.todolist.service;

import org.todolist.model.Task;
import org.todolist.repository.TaskRepository;

import java.util.List;

public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService() {
        this.taskRepository = new TaskRepository();
    }


    // Crear tarea
    public void crearTarea(Task task) {

        validarTarea(task);

        taskRepository.guardar(task);
    }


    // Obtener todas las tareas
    public List<Task> obtenerTodasLasTareas() {

        return taskRepository.obtenerTodas();
    }


    // Buscar una tarea
    public Task buscarTareaPorId(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0."
            );
        }

        return taskRepository.buscarPorId(id);
    }


    // Actualizar tarea
    public void actualizarTarea(Task task) {

        validarTarea(task);

        taskRepository.actualizar(task);
    }


    // Eliminar tarea
    public void eliminarTarea(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0."
            );
        }

        Task tarea = taskRepository.buscarPorId(id);

        if (tarea == null) {
            throw new IllegalArgumentException(
                    "No existe una tarea con ese ID."
            );
        }

        taskRepository.eliminar(id);
    }


    // Validaciones
    private void validarTarea(Task task) {

        if (task == null) {
            throw new IllegalArgumentException(
                    "La tarea no puede ser null."
            );
        }

        if (task.getTitulo() == null ||
                task.getTitulo().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El titulo de la tarea es obligatorio."
            );
        }

        if (task.getProgreso() < 0 ||
                task.getProgreso() > 100) {

            throw new IllegalArgumentException(
                    "El progreso debe estar entre 0 y 100."
            );
        }

        if (task.getPrioridad() == null) {

            throw new IllegalArgumentException(
                    "La prioridad es obligatoria."
            );
        }

        if (task.getEstado() == null) {

            throw new IllegalArgumentException(
                    "El estado es obligatorio."
            );
        }

        if (task.getFechaLimite() != null &&
                task.getFechaCreacion() != null &&
                task.getFechaLimite()
                        .isBefore(task.getFechaCreacion())) {

            throw new IllegalArgumentException(
                    "La fecha lImite no puede ser anterior " +
                            "a la fecha de creaciOn."
            );
        }
    }
}