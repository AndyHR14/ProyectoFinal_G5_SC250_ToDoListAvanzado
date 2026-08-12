/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.todolist.repository;
 
import org.todolist.model.User;
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author Andy
 */
public class UserRepository {
    public User crear(User user) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, correo, contrasena) VALUES (?, ?, ?)";
 
        try (Connection con = DatabaseConnection.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
 
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getCorreo());
            ps.setString(3, user.getContrasenaHash());
            ps.executeUpdate();
 
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getInt(1));
                }
            }
        }
        return user;
    }
 
    public Optional<User> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
 
        try (Connection con = DatabaseConnection.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }
 
    public Optional<User> buscarPorCorreo(String correo) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE correo = ?";
 
        try (Connection con = DatabaseConnection.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }
 
    public List<User> listarTodos() throws SQLException {
        String sql = "SELECT * FROM usuarios";
        List<User> resultado = new ArrayList<>();
 
        try (Connection con = DatabaseConnection.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
 
            while (rs.next()) {
                resultado.add(mapear(rs));
            }
        }
        return resultado;
    }
 
    public boolean actualizar(User user) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, correo = ? WHERE id_usuario = ?";
 
        try (Connection con = DatabaseConnection.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getCorreo());
            ps.setInt(3, user.getId());
            return ps.executeUpdate() > 0;
        }
    }
 
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
 
        try (Connection con = DatabaseConnection.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
 
    private User mapear(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id_usuario"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getString("contrasena"),
                rs.getTimestamp("fecha_creacion").toLocalDateTime()
        );
    }
}