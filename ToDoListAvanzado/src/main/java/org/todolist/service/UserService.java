/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.service;
import org.todolist.model.User;
import org.todolist.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
 
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;
 

/**
 *
 * @author Andy
 */
public class UserService {
     private final UserRepository userRepository;
 
    private static final Pattern CORREO_REGEX =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");
 
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User registrar(String nombre, String correo, String contrasenaPlano) throws SQLException {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (correo == null || !CORREO_REGEX.matcher(correo).matches()) {
            throw new IllegalArgumentException("El correo no es válido");
        }
        if (contrasenaPlano == null || contrasenaPlano.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }
        if (userRepository.buscarPorCorreo(correo).isPresent()) {
            throw new IllegalStateException("Ya existe un usuario con ese correo");
        }
 
        String hash = BCrypt.hashpw(contrasenaPlano, BCrypt.gensalt());
        User nuevoUsuario = new User(nombre, correo, hash);
        return userRepository.crear(nuevoUsuario);
    }
 
    public Optional<User> login(String correo, String contrasenaPlano) throws SQLException {
        Optional<User> encontrado = userRepository.buscarPorCorreo(correo);
 
        if (encontrado.isPresent() && BCrypt.checkpw(contrasenaPlano, encontrado.get().getContrasenaHash())) {
            return encontrado;
        }
        return Optional.empty();
    }
 
    public Optional<User> buscarPorId(int id) throws SQLException {
        return userRepository.buscarPorId(id);
    }
 
    public boolean actualizarDatos(User user) throws SQLException {
        return userRepository.actualizar(user);
    }
 
    public boolean eliminar(int id) throws SQLException {
        return userRepository.eliminar(id);
    }
}