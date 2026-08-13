package org.todolist.controller;

import org.todolist.model.Category;
import org.todolist.service.CategoryService;

import java.util.List;

public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController() {
        this.categoryService = new CategoryService();
    }


    public void crearCategoria(Category category) {

        categoryService.crearCategoria(category);
    }


    public List<Category> obtenerCategorias() {

        return categoryService.obtenerCategorias();
    }


    public Category buscarCategoriaPorId(int id) {

        return categoryService.buscarCategoriaId(id);
    }


    public void actualizarCategoria(Category category) {

        categoryService.actualizarCategoria(category);
    }


    public void eliminarCategoria(int id) {

        categoryService.eliminarCategoria(id);
    }
}