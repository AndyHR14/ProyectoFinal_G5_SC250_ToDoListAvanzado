/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.service;

/**
 *
 * @author Alvm Tech
 */

import org.todolist.model.SubTask;
import org.todolist.repository.SubTaskRepository;

import java.util.List;

public class SubTaskService {

    private final SubTaskRepository subTaskRepository;

    public SubTaskService() {
        this.subTaskRepository = new SubTaskRepository();
    }


    ///---Crear SubTarea---///
    public void crearSubTask(SubTask subTask) {

        validarSubTask(subTask);

        subTaskRepository.guardar(subTask);
    }


    ///---Obtener todas las SubTareas---///
    public List<SubTask> obtenerTodasLasSubTasks() {

        return subTaskRepository.obtenerTodas();
    }
    
    public List<SubTask> obtenerSubTasksPorTarea(int idTarea) {

        if (idTarea <= 0) {

            throw new IllegalArgumentException(
                    "El ID de la tarea debe ser mayor que 0"
            );
        }

        return subTaskRepository.obtenerPorTarea(idTarea);
    }



    ///---Buscar SubTarea por ID---///
    public SubTask buscarSubTaskPorId(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0"
            );
        }

        return subTaskRepository.buscarPorId(id);
    }


    ///---Actualizar SubTarea---///
    public void actualizarSubTask(SubTask subTask) {

        validarSubTask(subTask);

        subTaskRepository.actualizar(subTask);
    }


    ///---Eliminar SubTarea---///
    public void eliminarSubTask(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0"
            );
        }

        SubTask subTask = subTaskRepository.buscarPorId(id);

        if (subTask == null) {
            throw new IllegalArgumentException(
                    "No existe una subtarea con ese ID"
            );
        }

        subTaskRepository.eliminar(id);
    }
    
    public void marcarComoCompletada(int idSubtarea) {

        if (idSubtarea <= 0) {

            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0"
            );
        }

        SubTask subTask =
                subTaskRepository.buscarPorId(idSubtarea);

        if (subTask == null) {

            throw new IllegalArgumentException(
                    "No existe una subtarea con ese ID"
            );
        }

        if (subTask.isCompletada()) {

            return;
        }

        subTaskRepository.actualizarEstado(
                idSubtarea,
                true
        );
    }
    
    public void marcarComoPendiente(int idSubtarea) {

        if (idSubtarea <= 0) {

            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0"
            );
        }

        SubTask subTask =
                subTaskRepository.buscarPorId(idSubtarea);

        if (subTask == null) {

            throw new IllegalArgumentException(
                    "No existe una subtarea con ese ID"
            );
        }

        if (!subTask.isCompletada()) {

            return;
        }

        subTaskRepository.actualizarEstado(
                idSubtarea,
                false
        );
    }


    ///---Validaciones---///
    private void validarSubTask(SubTask subTask) {

        if (subTask == null) {
            throw new IllegalArgumentException(
                    "La subtarea no puede ser null"
            );
        }

        if (subTask.getDescripcion() == null ||
                subTask.getDescripcion().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "La descripción es obligatoria"
            );
        }

        if (subTask.getIdTarea() <= 0) {

            throw new IllegalArgumentException(
                    "Debe asociarse a una tarea válida"
            );
        }
    }
}