package com.mycompany.clinicamedica.controlador;

import com.mycompany.clinicamedica.modelo.Especialidad;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadControlador {
    private Connection conexion;

    public EspecialidadControlador(Connection conexion) {
        this.conexion = conexion;
    }

    public List<Especialidad> listarEspecialidades() {
        List<Especialidad> lista = new ArrayList<>();
        String sql = "SELECT * FROM especialidad";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Especialidad e = new Especialidad();
                e.setEspecialidadID(rs.getInt("EspecialidadID"));
                e.setNombre(rs.getString("Nombre"));
                e.setDescripcion(rs.getString("Descripcion"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public boolean agregarEspecialidad(Especialidad e) {
        String sql = "INSERT INTO especialidad (Nombre, Descripcion) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getDescripcion());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean modificarEspecialidad(Especialidad e) {
        String sql = "UPDATE especialidad SET Nombre = ?, Descripcion = ? WHERE EspecialidadID = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getDescripcion());
            ps.setInt(3, e.getEspecialidadID());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminarEspecialidad(int especialidadID) {
        String sql = "DELETE FROM especialidad WHERE EspecialidadID = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, especialidadID);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Traer médicos por especialidad
    public List<String[]> listarMedicosPorEspecialidad(int especialidadID) {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT m.MedicoID, m.Nombre FROM medico m JOIN medico_especialidad me ON m.MedicoID = me.MedicoID WHERE me.EspecialidadID = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, especialidadID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new String[]{String.valueOf(rs.getInt("MedicoID")), rs.getString("Nombre")});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
