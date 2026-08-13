package org.todolist.controller;

import org.todolist.service.TaskTagService;

import java.util.List;

public class TaskTagController {

    private final TaskTagService taskTagService;

    public TaskTagController() {

        this.taskTagService =
                new TaskTagService();
    }


    // ============================================================
    // ASIGNAR ETIQUETA
    // ============================================================

    public void asignarEtiqueta(
            int idTarea,
            int idEtiqueta) {

        taskTagService.asignarEtiqueta(
                idTarea,
                idEtiqueta
        );
    }


    // ============================================================
    // OBTENER ETIQUETAS DE UNA TAREA
    // ============================================================

    public List<Integer> obtenerEtiquetasDeTarea(
            int idTarea) {

        return taskTagService
                .obtenerEtiquetasDeTarea(idTarea);
    }


    // ============================================================
    // ELIMINAR UNA ETIQUETA
    // ============================================================

    public void eliminarEtiqueta(
            int idTarea,
            int idEtiqueta) {

        taskTagService.eliminarEtiqueta(
                idTarea,
                idEtiqueta
        );
    }


    // ============================================================
    // ELIMINAR TODAS LAS ETIQUETAS
    // ============================================================

    public void eliminarTodasLasEtiquetas(
            int idTarea) {

        taskTagService.eliminarTodasLasEtiquetas(
                idTarea
        );
    }
}