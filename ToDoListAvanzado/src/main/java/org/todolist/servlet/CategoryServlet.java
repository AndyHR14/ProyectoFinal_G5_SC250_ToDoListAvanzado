package org.todolist.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.todolist.controller.CategoryController;
import org.todolist.model.Category;

import java.io.IOException;
import java.util.List;

@WebServlet("/categorias")
public class CategoryServlet extends HttpServlet {

    private final CategoryController categoryController =
            new CategoryController();



    /// MOSTRAR CATEGORIAS / PREPARAR EDICION


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion =
                request.getParameter("accion");


        
        /// EDITAR
        

        if ("editar".equals(accion)) {

            String idTexto =
                    request.getParameter("id");

            if (idTexto != null && !idTexto.isBlank()) {

                try {

                    int id =
                            Integer.parseInt(idTexto);

                    Category categoria =
                            categoryController.buscarCategoriaPorId(id);

                    if (categoria != null) {

                        request.setAttribute(
                                "categoriaEditar",
                                categoria
                        );

                    }

                } catch (NumberFormatException e) {

                    System.err.println(
                            "ID de categoría inválido: " + idTexto
                    );

                }
            }
        }


        
        /// OBTENER TODAS LAS CATEGORIAS
        

        List<Category> categorias =
                categoryController.obtenerCategorias();

        request.setAttribute(
                "categorias",
                categorias
        );


        
        /// MOSTRAR JSP
        

        request.getRequestDispatcher(
                "/categorias.jsp"
        ).forward(
                request,
                response
        );
    }



    /// CREAR / ACTUALIZAR / ELIMINAR


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion =
                request.getParameter("accion");


        
        /// CREAR
        

        if ("crear".equals(accion)) {

            String nombre =
                    request.getParameter("nombre");

            String color =
                    request.getParameter("color");


            Category category =
                    new Category();

            category.setNombre(nombre);

            category.setColor(color);


            categoryController.crearCategoria(category);
        }


        
        /// ACTUALIZAR
        

        else if ("actualizar".equals(accion)) {

            String idTexto =
                    request.getParameter("id");

            if (idTexto != null && !idTexto.isBlank()) {

                try {

                    int id =
                            Integer.parseInt(idTexto);

                    String nombre =
                            request.getParameter("nombre");

                    String color =
                            request.getParameter("color");


                    Category category =
                            new Category();

                    category.setId(id);

                    category.setNombre(nombre);

                    category.setColor(color);


                    categoryController.actualizarCategoria(
                            category
                    );

                } catch (NumberFormatException e) {

                    System.err.println(
                            "ID de categoría inválido: " + idTexto
                    );
                }
            }
        }


        
        /// ELIMINAR
        

        else if ("eliminar".equals(accion)) {

            String idTexto =
                    request.getParameter("id");

            if (idTexto != null && !idTexto.isBlank()) {

                try {

                    int id =
                            Integer.parseInt(idTexto);

                    categoryController.eliminarCategoria(id);

                } catch (NumberFormatException e) {

                    System.err.println(
                            "ID de categoría inválido: " + idTexto
                    );
                }
            }
        }


        
        /// VOLVER A LA LISTA
        

        response.sendRedirect(
                request.getContextPath() + "/categorias"
        );
    }
}