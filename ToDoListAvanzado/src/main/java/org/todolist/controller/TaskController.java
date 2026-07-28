package org.todolist.controller;

import org.todolist.model.Task;
import org.todolist.service.TaskService;

import java.util.List;

public class TaskController {

    private final TaskService taskService;

    public TaskController() {
        this.taskService = new TaskService();
    }


    public void crearTarea(Task task) {

        taskService.crearTarea(task);
    }


    public List<Task> obtenerTodasLasTareas() {

        return taskService.obtenerTodasLasTareas();
    }


    public Task buscarTareaPorId(int id) {

        return taskService.buscarTareaPorId(id);
    }


    public void actualizarTarea(Task task) {

        taskService.actualizarTarea(task);
    }


    public void eliminarTarea(int id) {

        taskService.eliminarTarea(id);
    }
}