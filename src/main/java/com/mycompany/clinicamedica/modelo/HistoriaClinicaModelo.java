package com.mycompany.clinicamedica.modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistoriaClinicaModelo {
    private Connection conexion;

    public HistoriaClinicaModelo(Connection conexion) {
        this.conexion = conexion;
    }

    public List<LocalDate> obtenerFechasPorPaciente(int pacienteID) {
        List<LocalDate> fechas = new ArrayList<>();
        String sql = "SELECT Fecha FROM HistoriaClinica WHERE PacienteID = ? ORDER BY Fecha DESC";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, pacienteID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                fechas.add(rs.getDate("Fecha").toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fechas;
    }

    public RegistroHistoriaClinica obtenerRegistroPorPacienteYFecha(int pacienteID, LocalDate fecha) {
        String sql = """
            SELECT hc.Descripcion, hc.MedicoID, hc.Atendido,
                   p.Nombre AS NombreMedico, p.Apellido AS ApellidoMedico,
                   e.Nombre AS Especialidad
            FROM HistoriaClinica hc
            JOIN Medico m ON hc.MedicoID = m.MedicoID
            JOIN Persona p ON m.DNI = p.DNI
            LEFT JOIN Especialidad e ON e.MedicoID = m.MedicoID
            WHERE hc.PacienteID = ? AND hc.Fecha = ?
            LIMIT 1
        """;

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, pacienteID);
            stmt.setDate(2, Date.valueOf(fecha));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                RegistroHistoriaClinica registro = new RegistroHistoriaClinica();
                registro.setFecha(fecha);
                registro.setDescripcion(rs.getString("Descripcion"));
                registro.setMedicoID(rs.getInt("MedicoID"));
                registro.setNombreMedico(rs.getString("NombreMedico"));
                registro.setApellidoMedico(rs.getString("ApellidoMedico"));
                registro.setEspecialidad(rs.getString("Especialidad"));
                registro.setAtendido(rs.getBoolean("Atendido"));
                return registro;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean guardarHistoria(int pacienteID, int medicoID, LocalDate fecha, String descripcion) {
        try {
            String sqlSelect = "SELECT HistoriaClinicaID, MedicoID FROM HistoriaClinica WHERE PacienteID = ? AND Fecha = ?";
            try (PreparedStatement stmtSelect = conexion.prepareStatement(sqlSelect)) {
                stmtSelect.setInt(1, pacienteID);
                stmtSelect.setDate(2, Date.valueOf(fecha));
                ResultSet rs = stmtSelect.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("HistoriaClinicaID");
                    int medicoIDActual = rs.getInt("MedicoID");
                    if (medicoID != medicoIDActual) {
                        return false;
                    }
                    String sqlUpdate = "UPDATE HistoriaClinica SET Descripcion = ?, Atendido = TRUE WHERE HistoriaClinicaID = ?";
                    try (PreparedStatement stmtUpdate = conexion.prepareStatement(sqlUpdate)) {
                        stmtUpdate.setString(1, descripcion);
                        stmtUpdate.setInt(2, id);
                        boolean ok = stmtUpdate.executeUpdate() > 0;
                        if (ok) marcarTurnoComoAtendido(pacienteID, medicoID, fecha);
                        return ok;
                    }
                } else {
                    String sqlMax = "SELECT IFNULL(MAX(HistoriaClinicaID), 0) AS maxID FROM HistoriaClinica";
                    int nuevoID = 0;
                    try (Statement stmtMax = conexion.createStatement()) {
                        ResultSet rsMax = stmtMax.executeQuery(sqlMax);
                        if (rsMax.next()) {
                            nuevoID = rsMax.getInt("maxID") + 1;
                        }
                    }
                    String sqlInsert = "INSERT INTO HistoriaClinica (HistoriaClinicaID, PacienteID, MedicoID, Fecha, Descripcion, Atendido) VALUES (?, ?, ?, ?, ?, TRUE)";
                    try (PreparedStatement stmtInsert = conexion.prepareStatement(sqlInsert)) {
                        stmtInsert.setInt(1, nuevoID);
                        stmtInsert.setInt(2, pacienteID);
                        stmtInsert.setInt(3, medicoID);
                        stmtInsert.setDate(4, Date.valueOf(fecha));
                        stmtInsert.setString(5, descripcion);
                        boolean ok = stmtInsert.executeUpdate() > 0;
                        if (ok) marcarTurnoComoAtendido(pacienteID, medicoID, fecha);
                        return ok;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean marcarComoAtendido(int pacienteID, LocalDate fecha, int medicoID) {
        String sql = "UPDATE HistoriaClinica SET Atendido = TRUE WHERE PacienteID = ? AND Fecha = ? AND MedicoID = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, pacienteID);
            stmt.setDate(2, Date.valueOf(fecha));
            stmt.setInt(3, medicoID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔁 NUEVO: método privado para marcar turno como atendido también
    private void marcarTurnoComoAtendido(int pacienteID, int medicoID, LocalDate fecha) {
        String sql = "UPDATE Turno SET Atendido = TRUE WHERE PacienteID = ? AND MedicoID = ? AND FechaProgramada = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, pacienteID);
            stmt.setInt(2, medicoID);
            stmt.setDate(3, Date.valueOf(fecha));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
