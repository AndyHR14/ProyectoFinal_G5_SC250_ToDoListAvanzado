package org.todolist.service;

import org.todolist.model.Tag;
import org.todolist.repository.TagRepository;

import java.util.List;

public class TagService {

    private final TagRepository tagRepository;

    public TagService() {
        this.tagRepository = new TagRepository();
    }



    ///---Crear tag ---///
    public void crearTag(Tag tag) {

        validarTag(tag);

        tagRepository.guardar(tag);
    }



    ///---Obtener todas las tags---///
    public List<Tag> obtenerTodas() {

        return tagRepository.obtenerTodas();
    }



    ///---Buscar una categoria por id---///
    public Tag buscarPorId(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0"
            );
        }

        return tagRepository.buscarPorId(id);
    }



    ///---Actualizar tag---///
    public void actualizar(Tag tag) {

        validarTag(tag);

        tagRepository.actualizar(tag);
    }



    ///---Eliminar tag---///
    public void eliminar(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0"
            );
        }

        Tag tag = tagRepository.buscarPorId(id);

        if (tag == null) {
            throw new IllegalArgumentException(
                    "No existe una etiqueta con ese ID"
            );
        }

        tagRepository.eliminar(id);
    }



    ///---Validaciones---///
    private void validarTag(Tag tag) {

        if (tag == null) {
            throw new IllegalArgumentException(
                    "La etiqueta no puede ser null"
            );
        }

        if (tag.getNombre() == null ||
                tag.getNombre().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El nombre de la etiqueta es obligatorio"
            );
        }

        if (tag.getNombre().length() > 50) {

            throw new IllegalArgumentException(
                    "El nombre de la etiqueta no puede " +
                            "superar los 50 caracteres"
            );
        }
    }
}