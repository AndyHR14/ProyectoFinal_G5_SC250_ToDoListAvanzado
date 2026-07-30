/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.model;

/**
 *
 * @author Alvm Tech
 */
public class SubTask {

     // Atributos
    private int idSubtarea;
    private String descripcion;
    private boolean completada;
    private int idTarea;

    // Constructor vacío
    public SubTask() {
    }

    // Constructor con parámetros
    public SubTask(int idSubtarea, String descripcion, boolean completada, int idTarea) {
        this.idSubtarea = idSubtarea;
        this.descripcion = descripcion;
        this.completada = completada;
        this.idTarea = idTarea;
    }
    
    // Getters y Setters
    public int getIdSubtarea() {
        return idSubtarea;
    }

    public void setIdSubtarea(int idSubtarea) {
        this.idSubtarea = idSubtarea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "idSubtarea=" + idSubtarea +
                ", descripcion='" + descripcion + '\'' +
                ", completada=" + completada +
                ", idTarea=" + idTarea +
                '}';
    }
}