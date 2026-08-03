/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.controller;
import org.todolist.model.User;
import org.todolist.service.UserService;
 
import java.sql.SQLException;
import java.util.Optional;
/**
 *
 * @author Andy
 */
public class UserController {
    
    private final UserService userService;
 
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    public User registrarUsuario(String nombre, String correo, String contrasena) {
        try {
            return userService.registrar(nombre, correo, contrasena);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error al registrar: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
            return null;
        }
    }
    
    public Optional<User> iniciarSesion(String correo, String contrasena) {
        try {
            return userService.login(correo, contrasena);
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
            return Optional.empty();
        }
    }
}
 
