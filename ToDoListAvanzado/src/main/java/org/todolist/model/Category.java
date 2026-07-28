package org.todolist.model;

public class Category {

    //Atributos
    private int id;
    private String nombre;
    private String color;

    //constructor

    public Category() {}

    public Category(int id, String nombre, String color){

        this.id = id;
        this.nombre = nombre;
        this.color = color;
    }

    //getters

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getColor() {
        return color;
    }


    //setters

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
