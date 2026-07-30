/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.model;

/**
 *
 * @author Alvm Tech
 */
import java.time.LocalDateTime;

public class Reminder {

    // Atributos
    private int idRecordatorio;
    private LocalDateTime fechaHora;
    private String mensaje;
    private boolean activo;
    private int idTarea;
    
    // Constructor vacío
    public Reminder() {
    }

    // Constructor con parámetros
    public Reminder(int idRecordatorio, LocalDateTime fechaHora, String mensaje, boolean activo, int idTarea) {
        this.idRecordatorio = idRecordatorio;
        this.fechaHora = fechaHora;
        this.mensaje = mensaje;
        this.activo = activo;
        this.idTarea = idTarea;
    }

    // Getters y Setters
    public int getIdRecordatorio() {
        return idRecordatorio;
    }

    public void setIdRecordatorio(int idRecordatorio) {
        this.idRecordatorio = idRecordatorio;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "idRecordatorio=" + idRecordatorio +
                ", fechaHora=" + fechaHora +
                ", mensaje='" + mensaje + '\'' +
                ", activo=" + activo +
                ", idTarea=" + idTarea +
                '}';
    }
}