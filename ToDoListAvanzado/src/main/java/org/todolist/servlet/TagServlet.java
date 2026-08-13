package org.todolist.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.todolist.controller.TagController;
import org.todolist.model.Tag;

import java.io.IOException;
import java.util.List;

@WebServlet("/tags")
public class TagServlet extends HttpServlet {

    private final TagController tagController =
            new TagController();



    /// MOSTRAR ETIQUETAS / PREPARAR EDICIÓN


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

                int id =
                        Integer.parseInt(idTexto);

                Tag tag =
                        tagController.buscarTag(id);

                request.setAttribute(
                        "tagEditar",
                        tag
                );
            }
        }


        
        /// OBTENER TODAS LAS ETIQUETAS
        

        List<Tag> etiquetas =
                tagController.obtenerTags();

        request.setAttribute(
                "etiquetas",
                etiquetas
        );


        
        /// MOSTRAR JSP
        

        request.getRequestDispatcher(
                "/tags.jsp"
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


            Tag tag =
                    new Tag();

            tag.setNombre(nombre);


            tagController.crearTag(tag);
        }


        
        /// ACTUALIZAR
        

        else if ("actualizar".equals(accion)) {

            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            String nombre =
                    request.getParameter("nombre");


            Tag tag =
                    new Tag();

            tag.setIdEtiqueta(id);

            tag.setNombre(nombre);


            tagController.actualizarTag(tag);
        }


        
        /// ELIMINAR
        

        else if ("eliminar".equals(accion)) {

            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );


            tagController.eliminarTag(id);
        }


        
        /// VOLVER A LA LISTA
        

        response.sendRedirect(
                request.getContextPath() + "/tags"
        );
    }
}