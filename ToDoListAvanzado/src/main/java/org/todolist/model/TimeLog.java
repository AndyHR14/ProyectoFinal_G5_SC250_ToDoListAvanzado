/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.model;

/**
 *
 * @author Alvm Tech
 */
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeLog {

    // Atributos
    private int idRegistro;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private int duracionMinutos;
    private int idTarea;

    // Constructor vacío
    public TimeLog() {
    }

    // Constructor con parámetros
    public TimeLog(int idRegistro, LocalDateTime inicio, LocalDateTime fin, int duracionMinutos, int idTarea) {
        this.idRegistro = idRegistro;
        this.inicio = inicio;
        this.fin = fin;
        this.duracionMinutos = duracionMinutos;
        this.idTarea = idTarea;
    }

    // Getters y Setters
    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }
    
    public void iniciarConteo() {

        if (inicio != null && fin == null) {
            throw new IllegalStateException(
                    "El conteo ya está iniciado."
            );
        }

        inicio = LocalDateTime.now();
        fin = null;
        duracionMinutos = 0;
    }
    
    public void finalizarConteo() {

        if (inicio == null) {
            throw new IllegalStateException(
                    "No se puede finalizar un conteo que no ha iniciado."
            );
        }

        if (fin != null) {
            throw new IllegalStateException(
                    "El conteo ya ha finalizado."
            );
        }

        fin = LocalDateTime.now();

        calcularDuracion();
    }
    
    public void calcularDuracion() {

        if (inicio == null || fin == null) {
            throw new IllegalStateException(
                    "Se necesitan fecha de inicio y fecha de fin para calcular la duración."
            );
        }

        long minutos = Duration.between(
                inicio,
                fin
        ).toMinutes();

        duracionMinutos = (int) minutos;
    }
    
    public boolean estaActivo() {

        return inicio != null && fin == null;
    }

    @Override
    public String toString() {
        return "TimeLog{" +
                "idRegistro=" + idRegistro +
                ", inicio=" + inicio +
                ", fin=" + fin +
                ", duracionMinutos=" + duracionMinutos +
                ", idTarea=" + idTarea +
                '}';
    }
}