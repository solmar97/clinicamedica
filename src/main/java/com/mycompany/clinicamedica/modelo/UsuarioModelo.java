package com.mycompany.clinicamedica.modelo;

import com.mycompany.clinicamedica.conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioModelo {

    public boolean autenticarUsuario(String usuario, String contrasena) {
        // Busca en secretario
        if (autenticarEnTabla("secretario", usuario, contrasena)) {
            return true;
        }
        // Busca en medico
        if (autenticarEnTabla("medico", usuario, contrasena)) {
            return true;
        }
        return false;
    }

    private boolean autenticarEnTabla(String tabla, String usuario, String contrasena) {
        String sql = "SELECT Contrasenia FROM " + tabla + " WHERE Usuario = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String passBD = rs.getString("Contrasenia");
                return contrasena.equals(passBD); // comparar texto plano (para pruebas)
            }

        } catch (SQLException e) {
            System.out.println("Error al autenticar en " + tabla + ": " + e.getMessage());
        }
        return false;
    }

    public String obtenerRolUsuario(String usuario) {
        if (existeUsuarioEnTabla("secretario", usuario)) {
            return "Secretario";
        } else if (existeUsuarioEnTabla("medico", usuario)) {
            return "Medico";
        } else {
            return "Desconocido";
        }
    }

    private boolean existeUsuarioEnTabla(String tabla, String usuario) {
        String sql = "SELECT 1 FROM " + tabla + " WHERE Usuario = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error al buscar usuario en " + tabla + ": " + e.getMessage());
            return false;
        }
    }

public String obtenerNombreCompletoSecretario(String usuario) {
    String sql = "SELECT p.Nombre, p.Apellido " +
                 "FROM persona p " +
                 "JOIN secretario s ON p.DNI = s.DNI " +
                 "WHERE s.Usuario = ?";
    try (Connection conn = ConexionBD.getConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, usuario);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("Nombre") + " " + rs.getString("Apellido");
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener nombre completo del secretario: " + e.getMessage());
    }
    return usuario; // Por si no encuentra, devuelve usuario
}

public String obtenerNombreCompletoMedico(String usuario) {
    String sql = "SELECT p.Nombre, p.Apellido " +
                 "FROM persona p " +
                 "JOIN medico m ON p.DNI = m.DNI " +
                 "WHERE m.Usuario = ?";
    try (Connection conn = ConexionBD.getConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, usuario);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("Nombre") + " " + rs.getString("Apellido");
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener nombre completo del medico: " + e.getMessage());
    }
    return usuario; // Por si no encuentra, devuelve usuario
}

public int obtenerSecretarioID(String usuario) {
    String sql = "SELECT SecretarioID FROM secretario WHERE Usuario = ?";
    try (Connection conn = ConexionBD.getConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, usuario);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("SecretarioID");
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener SecretarioID: " + e.getMessage());
    }
    return -1; // No encontrado
}

public int obtenerMedicoID(String usuario) {
    String sql = "SELECT MedicoID FROM medico WHERE Usuario = ?";
    try (Connection conn = ConexionBD.getConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, usuario);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("MedicoID");
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener MedicoID: " + e.getMessage());
    }
    return -1; // No encontrado
}


}
