package com.mycompany.clinicamedica.modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagoModelo {
    private Connection conexion;

    public PagoModelo(Connection conexion) {
        this.conexion = conexion;
    }

public boolean agregarPago(Pago pago) {
    String sql = "INSERT INTO pago (TurnoID, PacienteID, Monto, FechaPago, DescuentoAplicado) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setInt(1, pago.getTurnoID());
        stmt.setInt(2, pago.getPacienteID());
        stmt.setDouble(3, pago.getMonto());
        stmt.setDate(4, Date.valueOf(pago.getFechaPago()));
        stmt.setBoolean(5, pago.isDescuentoAplicado());

        int filas = stmt.executeUpdate();

        if (filas > 0) {
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int pagoID = rs.getInt(1);

                // 🔗 Asociar el PagoID al turno
                String updateSql = "UPDATE turno SET PagoID = ? WHERE TurnoID = ?";
                try (PreparedStatement updateStmt = conexion.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, pagoID);
                    updateStmt.setInt(2, pago.getTurnoID());
                    updateStmt.executeUpdate();
                }

                return true;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    // Listar turnos programados para una fecha específica (solo turnos pendientes de pago)
public List<Turno> listarTurnosPorFecha(LocalDate fecha) {
    List<Turno> lista = new ArrayList<>();
    String sql = "SELECT "
               + "t.TurnoID, t.PacienteID, pe.DNI, pe.Nombre, pe.Apellido, p.ObraSocial, "
               + "t.FechaProgramada, t.HoraProgramada, pm.Nombre AS NombreMedico, pm.Apellido AS ApellidoMedico "
               + "FROM turno t "
               + "JOIN paciente p ON t.PacienteID = p.PacienteID "
               + "JOIN persona pe ON p.DNI = pe.DNI "
               + "JOIN medico m ON t.MedicoID = m.MedicoID "
               + "JOIN persona pm ON m.DNI = pm.DNI "
               + "LEFT JOIN pago pa ON t.TurnoID = pa.TurnoID "
               + "WHERE t.FechaProgramada = ? AND pa.PagoID IS NULL "
               + "ORDER BY t.HoraProgramada";

    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setDate(1, Date.valueOf(fecha));
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Turno turno = new Turno();
            turno.setTurnoID(rs.getInt("TurnoID"));
            turno.setPacienteID(rs.getInt("PacienteID"));
            turno.setDniPaciente(rs.getInt("DNI"));
            turno.setNombrePaciente(rs.getString("Nombre"));
            turno.setApellidoPaciente(rs.getString("Apellido"));
            turno.setObraSocialPaciente(rs.getString("ObraSocial"));
            turno.setFechaProgramada(rs.getDate("FechaProgramada").toLocalDate());
            turno.setHoraProgramada(rs.getTime("HoraProgramada").toLocalTime());
            turno.setNombreMedico(rs.getString("NombreMedico"));
            turno.setApellidoMedico(rs.getString("ApellidoMedico"));

            lista.add(turno);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}


    // Obtener obra social del paciente (para cálculo)
    public String obtenerObraSocialPorPacienteID(int pacienteID) {
        String sql = "SELECT ObraSocial FROM paciente WHERE PacienteID = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, pacienteID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("ObraSocial");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
