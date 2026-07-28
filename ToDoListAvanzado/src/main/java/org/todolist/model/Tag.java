package org.todolist.model;

public class Tag {

    //Atributos

    private int idEtiqueta;
    private String nombre;

    //constructor

    public Tag() {
    }

    public Tag(int idEtiqueta, String nombre) {
        this.idEtiqueta = idEtiqueta;
        this.nombre = nombre;
    }

    public int getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(int idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "idEtiqueta=" + idEtiqueta +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
