/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.service;

/**
 *
 * @author Alvm Tech
 */

import org.todolist.model.Reminder;
import org.todolist.repository.ReminderRepository;

import java.util.List;

public class ReminderService {

    private final ReminderRepository reminderRepository;

    public ReminderService() {

        this.reminderRepository = new ReminderRepository();
    }


    ///---Crear Recordatorio---///
    public void crearReminder(Reminder reminder) {

        validarReminder(reminder);

        reminderRepository.guardar(reminder);
    }


    ///---Obtener todos los Recordatorios---///
    public List<Reminder> obtenerTodosLosReminder() {

        return reminderRepository.obtenerTodos();
    }


    ///---Buscar Recordatorio por ID---///
    public Reminder buscarReminderPorId(int id) {

        if (id <= 0) {

            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0"
            );
        }

        return reminderRepository.buscarPorId(id);
    }


    ///---Actualizar Recordatorio---///
    public void actualizarReminder(Reminder reminder) {

        validarReminder(reminder);

        reminderRepository.actualizar(reminder);
    }


    ///---Eliminar Recordatorio---///
    public void eliminarReminder(int id) {

        if (id <= 0) {

            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0"
            );
        }

        Reminder reminder =
                reminderRepository.buscarPorId(id);

        if (reminder == null) {

            throw new IllegalArgumentException(
                    "No existe un recordatorio con ese ID"
            );
        }

        reminderRepository.eliminar(id);
    }


    ///---Validaciones---///
    private void validarReminder(Reminder reminder) {

        if (reminder == null) {

            throw new IllegalArgumentException(
                    "El recordatorio no puede ser null"
            );
        }

        if (reminder.getFechaHora() == null) {

            throw new IllegalArgumentException(
                    "La fecha y hora son obligatorias"
            );
        }

        if (reminder.getIdTarea() <= 0) {

            throw new IllegalArgumentException(
                    "Debe asociarse a una tarea válida"
            );
        }
    }

}