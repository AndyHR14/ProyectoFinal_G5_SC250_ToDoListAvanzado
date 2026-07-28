package org.todolist.model;
import java.time.LocalDate;
import org.todolist.enums.Estado;
import org.todolist.enums.Prioridad;

public class Task {

    //Atributos
    private int id;
    private String titulo;
    private LocalDate fechaCreacion;
    private LocalDate fechaLimite;


    private Prioridad prioridad;
    private Estado estado;

    private int progreso;
    private int idUsuario;
    private Integer idCategoria;
    private String descripcion;

    //constructor
    public Task(){}

    public Task(Prioridad prioridad, int id, String titulo, LocalDate fechaCreacion, LocalDate fechaLimite, Estado estado, int progreso, int idUsuario, Integer idCategoria, String descripcion) {
        this.prioridad = prioridad;
        this.id = id;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.fechaLimite = fechaLimite;
        this.estado = estado;
        this.progreso = progreso;
        this.idUsuario = idUsuario;
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaLimite=" + fechaLimite +
                ", prioridad=" + prioridad +
                ", estado=" + estado +
                ", progreso=" + progreso +
                ", idUsuario=" + idUsuario +
                ", idCategoria=" + idCategoria +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}

