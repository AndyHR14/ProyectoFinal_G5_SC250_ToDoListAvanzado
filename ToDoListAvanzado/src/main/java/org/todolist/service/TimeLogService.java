/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.service;

/**
 *
 * @author Alvm Tech
 */

import org.todolist.model.TimeLog;
import org.todolist.repository.TimeLogRepository;

import java.util.List;

public class TimeLogService {

    private final TimeLogRepository timeLogRepository;

    public TimeLogService() {
        this.timeLogRepository = new TimeLogRepository();
    }

    // Crear registro de tiempo
    public void crearRegistro(TimeLog registro) {

        validarRegistro(registro);

        if (registro.getFin() != null) {
            registro.calcularDuracion();
        }

        timeLogRepository.guardar(registro);
    }

    // Obtener todos los registros
    public List<TimeLog> obtenerTodosLosRegistros() {

        return timeLogRepository.obtenerTodos();
    }

    // Buscar registro por ID
    public TimeLog buscarRegistroPorId(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0"
            );
        }

        return timeLogRepository.buscarPorId(id);
    }
    
    public TimeLog buscarRegistroActivoPorTarea(int idTarea) {

        if (idTarea <= 0) {

            throw new IllegalArgumentException(
                    "El ID de la tarea debe ser mayor que 0"
            );
        }

        return timeLogRepository.buscarRegistroActivoPorTarea(idTarea);
    }


    // Actualizar registro
    public void actualizarRegistro(TimeLog registro) {

        validarRegistro(registro);

        if (registro.getFin() != null) {
            registro.calcularDuracion();
        }

        timeLogRepository.actualizar(registro);
    }

    // Eliminar registro
    public void eliminarRegistro(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0"
            );
        }

        TimeLog registro = timeLogRepository.buscarPorId(id);

        if (registro == null) {
            throw new IllegalArgumentException(
                    "No existe un registro con ese ID"
            );
        }

        timeLogRepository.eliminar(id);
    }
    
    //Iniciar Conteo
    public TimeLog iniciarConteo(int idTarea) {

        if (idTarea <= 0) {
            throw new IllegalArgumentException(
                    "Debe asociarse a una tarea válida"
            );
        }

        TimeLog registroActivo =
                timeLogRepository.buscarRegistroActivoPorTarea(
                        idTarea
                );

        if (registroActivo != null) {

            throw new IllegalStateException(
                    "La tarea ya tiene un conteo activo."
            );
        }

        TimeLog registro = new TimeLog();

        registro.setIdTarea(idTarea);

        registro.iniciarConteo();

        timeLogRepository.guardar(registro);

        return registro;
    }


    
    //Finalizar Conteo
    public void finalizarConteo(int idRegistro) {

        if (idRegistro <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0"
            );
        }

        TimeLog registro =
                timeLogRepository.buscarPorId(idRegistro);

        if (registro == null) {
            throw new IllegalArgumentException(
                    "No existe un registro con ese ID"
            );
        }

        registro.finalizarConteo();

        timeLogRepository.actualizar(registro);
    }

    // Validaciones
    private void validarRegistro(TimeLog registro) {

        if (registro == null) {
            throw new IllegalArgumentException(
                    "El registro no puede ser null"
            );
        }

        if (registro.getInicio() == null) {
            throw new IllegalArgumentException(
                    "La fecha de inicio es obligatoria"
            );
        }

        if (registro.getIdTarea() <= 0) {
            throw new IllegalArgumentException(
                    "Debe asociarse a una tarea válida"
            );
        }

        if (registro.getFin() != null &&
                registro.getFin().isBefore(registro.getInicio())) {

            throw new IllegalArgumentException(
                    "La fecha de fin no puede ser anterior a la fecha de inicio"
            );
        }
    }
}