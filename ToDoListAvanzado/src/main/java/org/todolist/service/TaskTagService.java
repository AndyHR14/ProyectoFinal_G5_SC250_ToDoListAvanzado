package org.todolist.service;

import org.todolist.repository.TaskTagRepository;

import java.util.List;

public class TaskTagService {

    private final TaskTagRepository taskTagRepository;

    public TaskTagService() {
        this.taskTagRepository =
                new TaskTagRepository();
    }


    
    /// ASIGNAR ETIQUETA A UNA TAREA
    

    public void asignarEtiqueta(
            int idTarea,
            int idEtiqueta) {

        validarIds(idTarea, idEtiqueta);

        taskTagRepository.asignarEtiqueta(
                idTarea,
                idEtiqueta
        );
    }


    
    /// OBTENER ETIQUETAS DE UNA TAREA
    

    public List<Integer> obtenerEtiquetasDeTarea(
            int idTarea) {

        if (idTarea <= 0) {

            throw new IllegalArgumentException(
                    "El ID de la tarea debe ser mayor que 0"
            );
        }

        return taskTagRepository
                .obtenerEtiquetasDeTarea(idTarea);
    }


    
    /// ELIMINAR UNA ETIQUETA DE UNA TAREA
    

    public void eliminarEtiqueta(
            int idTarea,
            int idEtiqueta) {

        validarIds(idTarea, idEtiqueta);

        taskTagRepository.eliminarEtiqueta(
                idTarea,
                idEtiqueta
        );
    }


    
    /// ELIMINAR TODAS LAS ETIQUETAS DE UNA TAREA
    

    public void eliminarTodasLasEtiquetas(
            int idTarea) {

        if (idTarea <= 0) {

            throw new IllegalArgumentException(
                    "El ID de la tarea debe ser mayor que 0"
            );
        }

        taskTagRepository.eliminarTodasLasEtiquetas(
                idTarea
        );
    }


    
    /// VALIDACIONES
    

    private void validarIds(
            int idTarea,
            int idEtiqueta) {

        if (idTarea <= 0) {

            throw new IllegalArgumentException(
                    "El ID de la tarea debe ser mayor que 0"
            );
        }

        if (idEtiqueta <= 0) {

            throw new IllegalArgumentException(
                    "El ID de la etiqueta debe ser mayor que 0"
            );
        }
    }
}