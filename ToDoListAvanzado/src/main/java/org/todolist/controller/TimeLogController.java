/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.controller;
import org.todolist.model.TimeLog;
import org.todolist.service.TimeLogService;
import java.util.List;
/**
 *
 * @author Alvm Tech
 */
public class TimeLogController {

    private final TimeLogService timeLogService;

    public TimeLogController() {
        this.timeLogService = new TimeLogService();
    }


    ///---Crear registro de tiempo---///
    public void crearRegistro(TimeLog registro) {

        timeLogService.crearRegistro(registro);
    }


    ///---Obtener todos los registros---///
    public List<TimeLog> obtenerTodosLosRegistros() {

        return timeLogService.obtenerTodosLosRegistros();
    }


    ///---Buscar registro por ID---///
    public TimeLog buscarRegistroPorId(int id) {

        return timeLogService.buscarRegistroPorId(id);
    }
    
    
    public TimeLog buscarRegistroActivoPorTarea(int idTarea) {

        return timeLogService.buscarRegistroActivoPorTarea(idTarea);
    }



    ///---Actualizar registro---///
    public void actualizarRegistro(TimeLog registro) {

        timeLogService.actualizarRegistro(registro);
    }


    ///---Eliminar registro---///
    public void eliminarRegistro(int id) {

        timeLogService.eliminarRegistro(id);
    }
    
    ///---Iniciar conteo---///
    public TimeLog iniciarConteo(int idTarea) {

        return timeLogService.iniciarConteo(idTarea);
    }
    
    
    ///---Finalizar conteo---///
    public void finalizarConteo(int idRegistro) {

        timeLogService.finalizarConteo(idRegistro);
    }
}