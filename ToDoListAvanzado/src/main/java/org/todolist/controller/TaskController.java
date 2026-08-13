package org.todolist.controller;

import org.todolist.model.Task;
import org.todolist.service.TaskService;

import java.util.List;

public class TaskController {

    private final TaskService taskService;


    public TaskController() {

        this.taskService =
                new TaskService();
    }


    // ============================================================
    // CREAR TAREA
    // ============================================================

    public void crearTarea(Task task) {

        taskService.crearTarea(task);
    }


    // ============================================================
    // OBTENER TODAS LAS TAREAS
    // ============================================================

    public List<Task> obtenerTodasLasTareas() {

        return taskService.obtenerTodasLasTareas();
    }


    // ============================================================
    // OBTENER TAREAS POR USUARIO
    // ============================================================

    public List<Task> obtenerTareasPorUsuario(
            int idUsuario) {

        return taskService.obtenerTareasPorUsuario(
                idUsuario
        );
    }


    // ============================================================
    // BUSCAR TAREA POR ID
    // ============================================================

    public Task buscarTareaPorId(int id) {

        return taskService.buscarTareaPorId(id);
    }


    // ============================================================
    // ACTUALIZAR TAREA
    // ============================================================

    public void actualizarTarea(Task task) {

        taskService.actualizarTarea(task);
    }


    // ============================================================
    // ELIMINAR TAREA
    // ============================================================

    public void eliminarTarea(int id) {

        taskService.eliminarTarea(id);
    }
}

