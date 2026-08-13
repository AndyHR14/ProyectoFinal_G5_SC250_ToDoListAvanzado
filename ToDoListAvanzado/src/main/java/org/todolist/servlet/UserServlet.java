
package org.todolist.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.todolist.controller.UserController;
import org.todolist.model.User;
import org.todolist.repository.UserRepository;
import org.todolist.service.UserService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/usuario")
public class UserServlet extends HttpServlet {

    private UserController userController;


    
   /// INICIALIZAR
    

    @Override
    public void init() throws ServletException {

        UserRepository userRepository =
                new UserRepository();

        UserService userService =
                new UserService(userRepository);

        userController =
                new UserController(userService);
    }


    
   /// GET
    

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion =
                request.getParameter("accion");


        
       /// MOSTRAR LOGIN
        

        if ("login".equals(accion)
                || accion == null) {

            request.getRequestDispatcher(
                    "/login.jsp"
            ).forward(
                    request,
                    response
            );

            return;
        }


        
       /// MOSTRAR REGISTRO
        

        if ("registro".equals(accion)) {

            request.getRequestDispatcher(
                    "/registro.jsp"
            ).forward(
                    request,
                    response
            );

            return;
        }


        
       /// LOGOUT
        

        if ("logout".equals(accion)) {

            HttpSession session =
                    request.getSession(false);

            if (session != null) {

                session.invalidate();
            }

            response.sendRedirect(
                    request.getContextPath()
                            + "/usuario?accion=login"
            );
        }
    }


    
   /// POST
    

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion =
                request.getParameter("accion");


        
       /// REGISTRAR USUARIO
        

        if ("registrar".equals(accion)) {

            String nombre =
                    request.getParameter("nombre");

            String correo =
                    request.getParameter("correo");

            String contrasena =
                    request.getParameter("contrasena");


            User usuario =
                    userController.registrarUsuario(
                            nombre,
                            correo,
                            contrasena
                    );


            if (usuario != null) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/usuario?accion=login"
                );

            } else {

                request.setAttribute(
                        "error",
                        "No se pudo registrar el usuario. " +
                                "Verifique los datos."
                );

                request.getRequestDispatcher(
                        "/registro.jsp"
                ).forward(
                        request,
                        response
                );
            }

            return;
        }


        
       /// INICIAR SESION
        

        if ("login".equals(accion)) {

            String correo =
                    request.getParameter("correo");

            String contrasena =
                    request.getParameter("contrasena");


            Optional<User> resultado =
                    userController.iniciarSesion(
                            correo,
                            contrasena
                    );


            if (resultado.isPresent()) {

                User usuario =
                        resultado.get();



               /// CREAR SESION


                HttpSession session =
                        request.getSession(true);


                session.setAttribute(
                        "usuario",
                        usuario
                );



               /// IR A TAREAS


                response.sendRedirect(
                        request.getContextPath()
                                + "/tareas"
                );

            } else {

                request.setAttribute(
                        "error",
                        "Correo o contraseña incorrectos."
                );

                request.getRequestDispatcher(
                        "/login.jsp"
                ).forward(
                        request,
                        response
                );
            }
        }
    }
}
