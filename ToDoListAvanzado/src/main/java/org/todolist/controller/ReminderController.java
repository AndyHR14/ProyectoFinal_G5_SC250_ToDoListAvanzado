/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.controller;

/**
 *
 * @author Alvm Tech
 */

import org.todolist.model.Reminder;
import org.todolist.service.ReminderService;

import java.util.List;

public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController() {

        this.reminderService = new ReminderService();
    }


    public void crearReminder(Reminder reminder) {

        reminderService.crearReminder(reminder);
    }


    public List<Reminder> obtenerTodosLosReminder() {

        return reminderService.obtenerTodosLosReminder();
    }


    public Reminder buscarReminderPorId(int id) {

        return reminderService.buscarReminderPorId(id);
    }


    public void actualizarReminder(Reminder reminder) {

        reminderService.actualizarReminder(reminder);
    }


    public void eliminarReminder(int id) {

        reminderService.eliminarReminder(id);
    }
    
    
    public void activarReminder(int idRecordatorio) {

        reminderService.activarReminder(
                idRecordatorio
        );
    }
    
    
    public void desactivarReminder(int idRecordatorio) {

        reminderService.desactivarReminder(
                idRecordatorio
        );
    }
    
    
    public List<Reminder> obtenerRecordatoriosActivos() {

        return reminderService.obtenerRecordatoriosActivos();
    }


}