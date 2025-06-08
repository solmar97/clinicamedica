package com.mycompany.clinicamedica.modelo;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.clinicamedica.conexion.ConexionBD;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MedicoModelo {

public List<Medico> obtenerMedicosPorEspecialidad(int especialidadId) {
    System.out.println("Buscando médicos para especialidad ID: " + especialidadId);
    List<Medico> lista = new ArrayList<>();

    String sql = "SELECT m.MedicoID, m.Matricula, m.DiaLaboral, m.HorarioLaboral, m.Usuario, m.Contrasenia, " +
                 "p.DNI, p.Nombre, p.Apellido, p.FechaNacimiento, p.Telefono, p.Genero, p.Email " +
                 "FROM Medico m " +
                 "JOIN Persona p ON m.DNI = p.DNI " +
                 "JOIN MedicoEspecialidad me ON m.MedicoID = me.MedicoID " +
                 "WHERE me.EspecialidadID = ?";

    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, especialidadId);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Medico medico = new Medico();

                medico.setMedicoID(rs.getInt("MedicoID"));
                medico.setDni(String.valueOf(rs.getInt("DNI")));
                medico.setNombre(rs.getString("Nombre"));
                medico.setApellido(rs.getString("Apellido"));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechaComoTexto = rs.getDate("FechaNacimiento").toLocalDate().format(formatter);
                medico.setFechaNacimiento(fechaComoTexto);

                medico.setTelefono(rs.getString("Telefono"));
                medico.setGenero(rs.getString("Genero"));
                medico.setEmail(rs.getString("Email"));

                medico.setMatricula(rs.getString("Matricula"));
                medico.setDiaLaboral(rs.getString("DiaLaboral"));
                medico.setHorarioLaboral(rs.getString("HorarioLaboral"));
                medico.setUsuario(rs.getString("Usuario"));
                medico.setContrasenia(rs.getString("Contrasenia"));

                // Cargar horarios de este médico
                medico.setHorarios(obtenerHorariosPorMedico(medico.getMedicoID()));

                Especialidad esp = new Especialidad();
                esp.setEspecialidadID(especialidadId);
                medico.setEspecialidad(esp);

                lista.add(medico);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    System.out.println("Médicos obtenidos para especialidad ID " + especialidadId + ":");
    for (Medico m : lista) {
    System.out.println(m.getDni() + " - " + m.getNombre());
}
    return lista;  
    
}

  private List<Horario> obtenerHorariosPorMedico(int medicoID) {
    List<Horario> horarios = new ArrayList<>();
    String sql = "SELECT Dia, HoraInicio, HoraFin FROM HorarioMedico WHERE MedicoID = ?";

    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, medicoID);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String dia = rs.getString("Dia");
                LocalTime horaInicio = LocalTime.parse(rs.getString("HoraInicio"));
                LocalTime horaFin = LocalTime.parse(rs.getString("HoraFin"));

    // Esta línea declara y crea el objeto horario
    Horario horario = new Horario(dia, horaInicio, horaFin);

    horarios.add(horario);
                System.out.println("Horario para médico " + medicoID + ": " + dia + " " + horaInicio + "-" + horaFin);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return horarios;
}

    public List<Especialidad> obtenerEspecialidades() {
        List<Especialidad> especialidades = new ArrayList<>();
        String sql = "SELECT EspecialidadID, Nombre, Descripcion FROM Especialidad";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Especialidad e = new Especialidad();
                e.setEspecialidadID(rs.getInt("EspecialidadID"));
                e.setNombre(rs.getString("Nombre"));
                e.setDescripcion(rs.getString("Descripcion"));
                especialidades.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especialidades;
    }

    public Medico obtenerMedicoPorDNI(int dni) {
        Medico medico = null;
        String sql = "SELECT m.MedicoID, m.Matricula, m.DiaLaboral, m.HorarioLaboral, m.Usuario, m.Contrasenia, " +
                     "p.Nombre, p.Apellido, p.FechaNacimiento, p.Telefono, p.Genero, p.Email " +
                     "FROM Medico m JOIN Persona p ON m.DNI = p.DNI WHERE m.DNI = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dni);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    medico = new Medico();
                    medico.setMedicoID(rs.getInt("MedicoID"));
                    medico.setDni(String.valueOf(dni));
                    medico.setNombre(rs.getString("Nombre"));
                    medico.setApellido(rs.getString("Apellido"));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String fechaComoTexto = rs.getDate("FechaNacimiento").toLocalDate().format(formatter);
                    medico.setFechaNacimiento(fechaComoTexto);
                    medico.setTelefono(rs.getString("Telefono"));
                    medico.setGenero(rs.getString("Genero"));
                    medico.setEmail(rs.getString("Email"));
                    medico.setMatricula(rs.getString("Matricula"));
                    medico.setDiaLaboral(rs.getString("DiaLaboral"));
                    medico.setHorarioLaboral(rs.getString("HorarioLaboral"));
                    medico.setUsuario(rs.getString("Usuario"));
                    medico.setContrasenia(rs.getString("Contrasenia"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medico;
    }

    public int obtenerMedicoIDPorDNI(String dni) {
        int id = -1;
        String sql = "SELECT MedicoID FROM Medico WHERE DNI = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(dni));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("MedicoID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    
 public boolean actualizarMedico(Medico medico) {
    boolean actualizado = false;

    String sqlPersona = "UPDATE Persona SET Nombre = ?, Apellido = ?, FechaNacimiento = ?, Telefono = ?, Genero = ?, Email = ? WHERE DNI = ?";
    String sqlMedico = "UPDATE Medico SET Matricula = ?, DiaLaboral = ?, HorarioLaboral = ?, Usuario = ?, Contrasenia = ? WHERE DNI = ?";

    try (Connection conn = ConexionBD.obtenerConexion()) {
        conn.setAutoCommit(false); // transacción

        try {
            // Actualizar Persona
            try (PreparedStatement stmtP = conn.prepareStatement(sqlPersona)) {
                stmtP.setString(1, medico.getNombre());
                stmtP.setString(2, medico.getApellido());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fecha = LocalDate.parse(medico.getFechaNacimiento(), formatter);
                stmtP.setDate(3, Date.valueOf(fecha));
                stmtP.setString(4, medico.getTelefono());
                stmtP.setString(5, medico.getGenero());
                stmtP.setString(6, medico.getEmail());
                stmtP.setInt(7, Integer.parseInt(medico.getDni()));
                stmtP.executeUpdate();
            }

            // Actualizar Medico
            try (PreparedStatement stmtM = conn.prepareStatement(sqlMedico)) {
                stmtM.setString(1, medico.getMatricula());
                stmtM.setString(2, medico.getDiaLaboral());
                stmtM.setString(3, medico.getHorarioLaboral());
                stmtM.setString(4, medico.getUsuario());
                stmtM.setString(5, medico.getContrasenia());
                stmtM.setInt(6, Integer.parseInt(medico.getDni()));
                stmtM.executeUpdate();
            }

            // Obtener MedicoID para actualizar horarios
            int medicoID = -1;
            String sqlObtenerID = "SELECT MedicoID FROM Medico WHERE DNI = ?";
            try (PreparedStatement stmtID = conn.prepareStatement(sqlObtenerID)) {
                stmtID.setInt(1, Integer.parseInt(medico.getDni()));
                try (ResultSet rs = stmtID.executeQuery()) {
                    if (rs.next()) {
                        medicoID = rs.getInt("MedicoID");
                    }
                }
            }

            if (medicoID != -1) {
                // Borrar horarios existentes
                String sqlDeleteHorarios = "DELETE FROM HorarioMedico WHERE MedicoID = ?";
                try (PreparedStatement stmtDelete = conn.prepareStatement(sqlDeleteHorarios)) {
                    stmtDelete.setInt(1, medicoID);
                    stmtDelete.executeUpdate();
                }

                // Insertar nuevos horarios
                String[] dias = medico.getDiaLaboral().split(",");
                String[] horas = medico.getHorarioLaboral().split("-");
                if (horas.length == 2) {
                    String horaInicio = horas[0].trim() + ":00";
                    String horaFin = horas[1].trim() + ":00";

                    String sqlInsertHorario = "INSERT INTO HorarioMedico (MedicoID, Dia, HoraInicio, HoraFin) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsertHorario)) {
                        for (String dia : dias) {
                            stmtInsert.setInt(1, medicoID);
                            stmtInsert.setString(2, dia.trim());
                            stmtInsert.setString(3, horaInicio);
                            stmtInsert.setString(4, horaFin);
                            stmtInsert.executeUpdate();
                        }
                    }
                }
            }

            conn.commit();
            actualizado = true;

        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return actualizado;
}

    public boolean eliminarMedico(String dni) {
        boolean eliminado = false;

        int medicoID = obtenerMedicoIDPorDNI(dni);
        if (medicoID == -1) return false;

        String sqlEliminarHorarios = "DELETE FROM HorarioMedico WHERE MedicoID = ?";
        String sqlEliminarEspecialidades = "DELETE FROM MedicoEspecialidad WHERE MedicoID = ?";
        String sqlMedico = "DELETE FROM Medico WHERE DNI = ?";
        String sqlPersona = "DELETE FROM Persona WHERE DNI = ?";

        try (Connection conn = ConexionBD.obtenerConexion()) {
            conn.setAutoCommit(false); // Inicia transacción

            try (PreparedStatement stmtH = conn.prepareStatement(sqlEliminarHorarios)) {
                stmtH.setInt(1, medicoID);
                stmtH.executeUpdate();
            }

            try (PreparedStatement stmtE = conn.prepareStatement(sqlEliminarEspecialidades)) {
                stmtE.setInt(1, medicoID);
                stmtE.executeUpdate();
            }

            try (PreparedStatement stmtM = conn.prepareStatement(sqlMedico);
                 PreparedStatement stmtP = conn.prepareStatement(sqlPersona)) {

                stmtM.setString(1, dni);
                stmtM.executeUpdate();

                stmtP.setString(1, dni);
                stmtP.executeUpdate();

                conn.commit();
                eliminado = true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eliminado;
    }

    public boolean agregarMedico(Medico nuevoMedico) {
    boolean agregado = false;

    String sqlInsertPersona = "INSERT INTO Persona (DNI, Nombre, Apellido, FechaNacimiento, Telefono, Genero, Email) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String sqlInsertMedico = "INSERT INTO Medico (Matricula, DNI, DiaLaboral, HorarioLaboral, Usuario, Contrasenia) VALUES (?, ?, ?, ?, ?, ?)";
    String sqlInsertMedicoEspecialidad = "INSERT INTO MedicoEspecialidad (MedicoID, EspecialidadID) VALUES (?, ?)";

    try (Connection conn = ConexionBD.obtenerConexion()) {
        conn.setAutoCommit(false);

        try (PreparedStatement stmtPersona = conn.prepareStatement(sqlInsertPersona);
             PreparedStatement stmtMedico = conn.prepareStatement(sqlInsertMedico, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtMedicoEspecialidad = conn.prepareStatement(sqlInsertMedicoEspecialidad)) {

            // Insert Persona
            stmtPersona.setInt(1, Integer.parseInt(nuevoMedico.getDni()));
            stmtPersona.setString(2, nuevoMedico.getNombre());
            stmtPersona.setString(3, nuevoMedico.getApellido());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha = LocalDate.parse(nuevoMedico.getFechaNacimiento(), formatter);
            stmtPersona.setDate(4, Date.valueOf(fecha));

            stmtPersona.setString(5, nuevoMedico.getTelefono());
            stmtPersona.setString(6, nuevoMedico.getGenero());
            stmtPersona.setString(7, nuevoMedico.getEmail());

            stmtPersona.executeUpdate();

            // Insert Medico
            stmtMedico.setString(1, nuevoMedico.getMatricula());
            stmtMedico.setInt(2, Integer.parseInt(nuevoMedico.getDni()));
            stmtMedico.setString(3, nuevoMedico.getDiaLaboral());
            stmtMedico.setString(4, nuevoMedico.getHorarioLaboral());
            stmtMedico.setString(5, nuevoMedico.getUsuario());
            stmtMedico.setString(6, nuevoMedico.getContrasenia());

            int filasInsertadas = stmtMedico.executeUpdate();

            if (filasInsertadas == 0) {
                throw new SQLException("Error al insertar el médico");
            }

            // Obtener el MedicoID generado
            int medicoID;
            try (ResultSet generatedKeys = stmtMedico.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    medicoID = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el MedicoID");
                }
            }

            // Insertar en MedicoEspecialidad
            int especialidadID = nuevoMedico.getEspecialidad().getEspecialidadID();
            stmtMedicoEspecialidad.setInt(1, medicoID);
            stmtMedicoEspecialidad.setInt(2, especialidadID);

            stmtMedicoEspecialidad.executeUpdate();
            
// Parsear días
String[] dias = nuevoMedico.getDiaLaboral().split(",");

// Parsear rango horario
String[] horas = nuevoMedico.getHorarioLaboral().split("-");
String horaInicio = horas[0].trim() + ":00"; // "17:00:00"
String horaFin = horas[1].trim() + ":00";    // "20:00:00"

// Preparar insert para horarios
String sqlInsertHorario = "INSERT INTO HorarioMedico (MedicoID, Dia, HoraInicio, HoraFin) VALUES (?, ?, ?, ?)";
try (PreparedStatement stmtHorario = conn.prepareStatement(sqlInsertHorario)) {
    for (String dia : dias) {
        stmtHorario.setInt(1, medicoID);
        stmtHorario.setString(2, dia.trim());
        stmtHorario.setString(3, horaInicio);
        stmtHorario.setString(4, horaFin);
        stmtHorario.executeUpdate();
    }
}
            conn.commit();
            agregado = true;

        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return agregado;
}

    public Especialidad obtenerEspecialidadPorNombre(String nombreEspecialidad) {
        Especialidad especialidad = null;
        String sql = "SELECT EspecialidadID, Nombre, Descripcion FROM Especialidad WHERE Nombre = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreEspecialidad);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    especialidad = new Especialidad();
                    especialidad.setEspecialidadID(rs.getInt("EspecialidadID"));
                    especialidad.setNombre(rs.getString("Nombre"));
                    especialidad.setDescripcion(rs.getString("Descripcion"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especialidad;
    }

  public List<Medico> obtenerTodosLosMedicos() {
    List<Medico> medicos = new ArrayList<>();

    String sql = "SELECT m.MedicoID, m.DNI, p.Nombre, p.Apellido, p.FechaNacimiento, p.Telefono, p.Genero, p.Email, " +
                 "m.Matricula, m.DiaLaboral, m.HorarioLaboral, m.Usuario, m.Contrasenia, " +
                 "GROUP_CONCAT(e.Nombre SEPARATOR ', ') AS Especialidades " +
                 "FROM Medico m " +
                 "JOIN Persona p ON m.DNI = p.DNI " +
                 "LEFT JOIN MedicoEspecialidad me ON m.MedicoID = me.MedicoID " +
                 "LEFT JOIN Especialidad e ON me.EspecialidadID = e.EspecialidadID " +
                 "GROUP BY m.MedicoID, m.DNI, p.Nombre, p.Apellido, p.FechaNacimiento, p.Telefono, p.Genero, p.Email, " +
                 "m.Matricula, m.DiaLaboral, m.HorarioLaboral, m.Usuario, m.Contrasenia";

    try (Connection conn = ConexionBD.obtenerConexion();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (rs.next()) {
            Medico medico = new Medico();
            medico.setMedicoID(rs.getInt("MedicoID"));
            medico.setDni(rs.getString("DNI"));
            medico.setNombre(rs.getString("Nombre"));
            medico.setApellido(rs.getString("Apellido"));

            // Formateo fecha a dd/MM/yyyy (si lo querés así)
            LocalDate fecha = rs.getDate("FechaNacimiento").toLocalDate();
            medico.setFechaNacimiento(fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            medico.setTelefono(rs.getString("Telefono"));
            medico.setGenero(rs.getString("Genero"));
            medico.setEmail(rs.getString("Email"));
            medico.setMatricula(rs.getString("Matricula"));
            medico.setDiaLaboral(rs.getString("DiaLaboral"));
            medico.setHorarioLaboral(rs.getString("HorarioLaboral"));
            medico.setUsuario(rs.getString("Usuario"));
            medico.setContrasenia(rs.getString("Contrasenia"));

            // Carga las especialidades concatenadas en un objeto Especialidad
            Especialidad especialidad = new Especialidad();
            especialidad.setNombre(rs.getString("Especialidades"));
            medico.setEspecialidad(especialidad);

            // Traer horarios usando el método privado 
            medico.setHorarios(obtenerHorariosPorMedico(medico.getMedicoID()));

             System.out.println("Cargando médico: " + medico.getDni() + " - " + medico.getNombre());
            medicos.add(medico);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return medicos;
}

}



