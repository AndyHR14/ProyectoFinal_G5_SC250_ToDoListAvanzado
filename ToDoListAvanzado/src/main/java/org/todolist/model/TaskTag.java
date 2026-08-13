package org.todolist.model;

public class TaskTag {

    private int idTarea;
    private int idEtiqueta;

    public TaskTag() {
    }

    public TaskTag(int idTarea, int idEtiqueta) {
        this.idTarea = idTarea;
        this.idEtiqueta = idEtiqueta;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public int getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(int idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

    @Override
    public String toString() {
        return "TaskTag{" +
                "idTarea=" + idTarea +
                ", idEtiqueta=" + idEtiqueta +
                '}';
    }
}