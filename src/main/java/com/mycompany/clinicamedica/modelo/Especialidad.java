package com.mycompany.clinicamedica.modelo;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.clinicamedica.conexion.ConexionBD;
import java.sql.*;

public class Especialidad {
    private int especialidadID;
    private String nombre;
    private String descripcion;

    public int getEspecialidadID() {
        return especialidadID;
    }

    public void setEspecialidadID(int especialidadID) {
        this.especialidadID = especialidadID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

@Override
public String toString() {
    return this.getNombre();
}

@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Especialidad that = (Especialidad) obj;
    return this.especialidadID == that.especialidadID;
}

@Override
public int hashCode() {
    return Integer.hashCode(especialidadID);
}


    // Método para obtener todas las especialidades como lista de objetos Especialidad
    public static List<Especialidad> obtenerTodasLasEspecialidades() {
        List<Especialidad> lista = new ArrayList<>();

        String sql = "SELECT especialidadID, nombre, descripcion FROM Especialidad";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Especialidad esp = new Especialidad();
                esp.setEspecialidadID(rs.getInt("especialidadID"));
                esp.setNombre(rs.getString("nombre"));
                esp.setDescripcion(rs.getString("descripcion"));
                lista.add(esp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
