/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andy
 */
public class User {
    private Integer id;          
    private String nombre;
    private String correo;
    private String contrasenaHash; 
    private LocalDateTime fechaCreacion;

    private List<Task> tareas; 

    public User(String nombre, String correo, String contrasenaHash) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
        this.tareas = new ArrayList<>();
    }

    public User(int id, String nombre, String correo,
                String contrasenaHash, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
        this.fechaCreacion = fechaCreacion;
        this.tareas = new ArrayList<>();
    }

    public void agregarTarea(Task tarea) {
        tareas.add(tarea);
    }

    public boolean eliminarTareaPorId(int idTarea) {
        return tareas.removeIf(t -> t.getId() == idTarea);
    }

    public Integer getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasenaHash() { return contrasenaHash; }
    public void setContrasenaHash(String contrasenaHash) { this.contrasenaHash = contrasenaHash; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }

    public List<Task> getTareas() { return tareas; }

    @Override
    public String toString() {
        return "User{id=" + id + ", nombre='" + nombre + "', correo='" + correo + "'}";
    }
}