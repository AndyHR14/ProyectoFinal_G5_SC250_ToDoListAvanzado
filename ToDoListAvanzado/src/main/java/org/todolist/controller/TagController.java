package org.todolist.controller;


import org.todolist.model.Tag;
import org.todolist.service.TagService;
import org.todolist.service.TaskService;

import java.util.List;

public class TagController {

    private final TagService tagService;

    public TagController(){this.tagService = new TagService();}

    public void crearTag(Tag tag) {
        tagService.crearTag(tag);
    }



    public List<Tag> obtenerTags(){
        return tagService.obtenerTodas();
    }



    public Tag buscarTag(int id){
        return tagService.buscarPorId(id);
    }



    public void actualizarTag(Tag tag){
        tagService.actualizar(tag);
    }



    public void eliminarTag(int id){
        tagService.eliminar(id);
    }

}
