package com.mycompany.clinicamedica.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteModelo {

    private final Connection conexion;

    public PacienteModelo(Connection conexion) {
        this.conexion = conexion;
    }

    public List<Paciente> obtenerTodos() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT p.DNI, per.Nombre, per.Apellido, per.Telefono, per.Email, per.FechaNacimiento, per.Genero, p.ObraSocial, p.NumeroAfiliado " +
                     "FROM Paciente p JOIN Persona per ON p.DNI = per.DNI";
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pacientes.add(new Paciente(
                    rs.getString("DNI"),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getDate("FechaNacimiento").toLocalDate(),
                    rs.getString("Telefono"),
                    rs.getString("Genero"),
                    rs.getString("Email"),
                    rs.getString("ObraSocial"),
                    rs.getString("NumeroAfiliado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }

    public Paciente buscarPorDni(String dni) {
        String sql = "SELECT p.DNI, per.Nombre, per.Apellido, per.Telefono, per.Email, per.FechaNacimiento, per.Genero, p.ObraSocial, p.NumeroAfiliado " +
                     "FROM Paciente p JOIN Persona per ON p.DNI = per.DNI WHERE p.DNI = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Paciente(
                    rs.getString("DNI"),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getDate("FechaNacimiento").toLocalDate(),
                    rs.getString("Telefono"),
                    rs.getString("Genero"),
                    rs.getString("Email"),
                    rs.getString("ObraSocial"),
                    rs.getString("NumeroAfiliado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Paciente> buscarPorDniParcial(String dniParcial) {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT p.DNI, per.Nombre, per.Apellido, per.Telefono, per.Email, per.FechaNacimiento, per.Genero, p.ObraSocial, p.NumeroAfiliado " +
                     "FROM Paciente p JOIN Persona per ON p.DNI = per.DNI WHERE p.DNI LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + dniParcial + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pacientes.add(new Paciente(
                        rs.getString("DNI"),
                        rs.getString("Nombre"),
                        rs.getString("Apellido"),
                        rs.getDate("FechaNacimiento").toLocalDate(),
                        rs.getString("Telefono"),
                        rs.getString("Genero"),
                        rs.getString("Email"),
                        rs.getString("ObraSocial"),
                        rs.getString("NumeroAfiliado")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }

    public boolean agregarPaciente(Paciente paciente) {
        String sqlPersona = "INSERT INTO Persona (DNI, Nombre, Apellido, FechaNacimiento, Telefono, Genero, Email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlPaciente = "INSERT INTO Paciente (DNI, ObraSocial, NumeroAfiliado) VALUES (?, ?, ?)";

        try {
            conexion.setAutoCommit(false);

            try (PreparedStatement stmtPersona = conexion.prepareStatement(sqlPersona);
                 PreparedStatement stmtPaciente = conexion.prepareStatement(sqlPaciente)) {

                stmtPersona.setString(1, paciente.getDni());
                stmtPersona.setString(2, paciente.getNombre());
                stmtPersona.setString(3, paciente.getApellido());
                stmtPersona.setDate(4, Date.valueOf(paciente.getFechaNacimiento()));
                stmtPersona.setString(5, paciente.getTelefono());
                stmtPersona.setString(6, paciente.getGenero());
                stmtPersona.setString(7, paciente.getEmail());
                stmtPersona.executeUpdate();

                stmtPaciente.setString(1, paciente.getDni());
                stmtPaciente.setString(2, paciente.getObraSocial());
                stmtPaciente.setString(3, paciente.getNumeroAfiliado());
                stmtPaciente.executeUpdate();

                conexion.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean actualizarPaciente(Paciente paciente, String dniViejo) {
        String sqlPersona = "UPDATE Persona SET DNI = ?, Nombre = ?, Apellido = ?, FechaNacimiento = ?, Telefono = ?, Genero = ?, Email = ? WHERE DNI = ?";
        String sqlPaciente = "UPDATE Paciente SET DNI = ?, ObraSocial = ?, NumeroAfiliado = ? WHERE DNI = ?";
        try {
            conexion.setAutoCommit(false);

            try (PreparedStatement stmtPersona = conexion.prepareStatement(sqlPersona);
                 PreparedStatement stmtPaciente = conexion.prepareStatement(sqlPaciente)) {

                stmtPersona.setString(1, paciente.getDni());
                stmtPersona.setString(2, paciente.getNombre());
                stmtPersona.setString(3, paciente.getApellido());
                stmtPersona.setDate(4, Date.valueOf(paciente.getFechaNacimiento()));
                stmtPersona.setString(5, paciente.getTelefono());
                stmtPersona.setString(6, paciente.getGenero());
                stmtPersona.setString(7, paciente.getEmail());
                stmtPersona.setString(8, dniViejo);
                stmtPersona.executeUpdate();

                stmtPaciente.setString(1, paciente.getDni());
                stmtPaciente.setString(2, paciente.getObraSocial());
                stmtPaciente.setString(3, paciente.getNumeroAfiliado());
                stmtPaciente.setString(4, dniViejo);
                stmtPaciente.executeUpdate();

                conexion.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean eliminarPaciente(String dni) {
        String sqlPaciente = "DELETE FROM Paciente WHERE DNI = ?";
        String sqlPersona = "DELETE FROM Persona WHERE DNI = ?";
        try {
            conexion.setAutoCommit(false);

            try (PreparedStatement stmtPaciente = conexion.prepareStatement(sqlPaciente);
                 PreparedStatement stmtPersona = conexion.prepareStatement(sqlPersona)) {

                stmtPaciente.setString(1, dni);
                stmtPaciente.executeUpdate();

                stmtPersona.setString(1, dni);
                stmtPersona.executeUpdate();

                conexion.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

public Paciente obtenerPacientePorDNI(String dni) {
    Paciente paciente = null;
    String sql = "SELECT p.PacienteID, per.Nombre, per.Apellido, per.Telefono, " +
                 "p.DNI, p.ObraSocial, p.NumeroAfiliado " +
                 "FROM paciente p " +
                 "JOIN persona per ON p.DNI = per.DNI " +
                 "WHERE p.DNI = ?";

    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setString(1, dni);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                paciente = new Paciente();
                paciente.setId(rs.getInt("PacienteID"));
                paciente.setDni(rs.getString("DNI"));
                paciente.setNombre(rs.getString("Nombre"));
                paciente.setApellido(rs.getString("Apellido"));
                paciente.setTelefono(rs.getString("Telefono"));
                paciente.setObraSocial(rs.getString("ObraSocial"));
                paciente.setNumeroAfiliado(rs.getString("NumeroAfiliado"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return paciente;
}

}
