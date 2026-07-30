/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.controller;

/**
 *
 * @author Alvm Tech
 */
package org.todolist.controller;

import org.todolist.model.SubTask;
import org.todolist.service.SubTaskService;

import java.util.List;

public class SubTaskController {

    private final SubTaskService subTaskService;

    public SubTaskController() {
        this.subTaskService = new SubTaskService();
    }


    public void crearSubTask(SubTask subTask) {

        subTaskService.crearSubTask(subTask);
    }


    public List<SubTask> obtenerTodasLasSubTasks() {

        return subTaskService.obtenerTodasLasSubTasks();
    }


    public SubTask buscarSubTaskPorId(int id) {

        return subTaskService.buscarSubTaskPorId(id);
    }


    public void actualizarSubTask(SubTask subTask) {

        subTaskService.actualizarSubTask(subTask);
    }


    public void eliminarSubTask(int id) {

        subTaskService.eliminarSubTask(id);
    }
}