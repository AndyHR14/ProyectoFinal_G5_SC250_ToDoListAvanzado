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

import java.time.Duration;
import java.util.List;

public class TimeLogService {

    private final TimeLogRepository timeLogRepository;

    public TimeLogService() {
        this.timeLogRepository = new TimeLogRepository();
    }

    // Crear registro de tiempo
    public void crearRegistro(TimeLog registro) {

        validarRegistro(registro);

        // Si ya existe la fecha de fin, calcula automáticamente la duración
        if (registro.getFin() != null) {

            long minutos = Duration.between(
                    registro.getInicio(),
                    registro.getFin()
            ).toMinutes();

            registro.setDuracionMinutos((int) minutos);
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

    // Actualizar registro
    public void actualizarRegistro(TimeLog registro) {

        validarRegistro(registro);

        if (registro.getFin() != null) {

            long minutos = Duration.between(
                    registro.getInicio(),
                    registro.getFin()
            ).toMinutes();

            registro.setDuracionMinutos((int) minutos);
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