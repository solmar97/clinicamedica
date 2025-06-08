package com.mycompany.clinicamedica.conexion;

import com.mycompany.clinicamedica.conexion.ConexionBD;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConexion {
    public static void main(String[] args) {
        try (Connection con = ConexionBD.obtenerConexion()) {
            if (con != null) {
                System.out.println("¡Conexión exitosa a la base de datos!");
            } else {
                System.out.println("No se pudo establecer conexión.");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos:");
            e.printStackTrace();
        }
    }
}
