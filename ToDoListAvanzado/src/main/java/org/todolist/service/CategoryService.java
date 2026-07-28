package org.todolist.service;

import org.todolist.model.Category;
import  org.todolist.repository.CategoryRepository;

import java.util.List;

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(){
        this.categoryRepository = new CategoryRepository();
    }



    ///---Crear categoria---///
    public void crearCategoria(Category category){

        validarCategory(category);

        categoryRepository.guardar(category);
    }



    ///---Obtener todas las categorias---///
    public List<Category> obtenerCategorias(){

        return categoryRepository.obtenerTodas();
    }



    ///---Buscar una categoria---///
    public  Category buscarCategoriaId(int id){

        if(id <= 0){
            throw new IllegalArgumentException(
                    "El Id debe ser mayor que 0"
            );
        }

        return categoryRepository.buscarPorId(id);
    }



    ///---Actualizar categoria---///
    public void actualizarCategoria(Category category){

        validarCategory(category);

        categoryRepository.actualizar(category);
    }



    ///---Eliminar categoria---///
    public void eliminarCategoria(int id){

        if(id <= 0){
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que 0"
            );
        }

        Category category = categoryRepository.buscarPorId(id);

        if(category == null){
            throw new IllegalArgumentException(
                    "No existe una categoria con ese ID"
            );
        }

        categoryRepository.eliminar(id);
    }




    private void validarCategory(Category category) {

        if (category == null) {
            throw new IllegalArgumentException(
                    "La categoria no puede ser null."
            );
        }

        if (category.getNombre() == null ||
                category.getNombre().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El nombre de la categoria es obligatorio."
            );
        }

        if (category.getNombre().length() > 50) {

            throw new IllegalArgumentException(
                    "El nombre de la categoria no puede " +
                            "superar los 50 caracteres."
            );
        }

        if (category.getColor() != null &&
                category.getColor().length() > 20) {

            throw new IllegalArgumentException(
                    "El color no puede superar los 20 caracteres."
            );
        }
    }


}
