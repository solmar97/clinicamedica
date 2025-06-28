package com.mycompany.clinicamedica.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class TurnoModelo {
    private Connection conexion;

    public TurnoModelo(Connection conexion) {
        this.conexion = conexion;
    }

    public List<String> listarMedicosPorEspecialidad(String especialidad) {
        List<String> medicos = new ArrayList<>();
        String sql = "SELECT per.Nombre, per.Apellido " +
                     "FROM Medico m " +
                     "JOIN Persona per ON m.DNI = per.DNI " +
                     "JOIN MedicoEspecialidad me ON m.MedicoID = me.MedicoID " +
                     "JOIN Especialidad e ON me.EspecialidadID = e.EspecialidadID " +
                     "WHERE e.Nombre = ? " +
                     "ORDER BY per.Apellido, per.Nombre";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, especialidad);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nombreCompleto = rs.getString("Nombre") + " " + rs.getString("Apellido");
                    medicos.add(nombreCompleto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicos;
    }

public List<Turno> listarTurnos() {
    List<Turno> turnos = new ArrayList<>();
    String sql = "SELECT t.TurnoID, t.PacienteID, t.MedicoID, t.SecretarioID, t.PagoID, t.FechaProgramada, t.HoraProgramada, " +
                 "t.Atendido, " +
                 "perPaciente.DNI AS PacienteDNI, perPaciente.Nombre AS PacienteNombre, perPaciente.Apellido AS PacienteApellido, " +
                 "perPaciente.Telefono AS PacienteTelefono, p.ObraSocial, p.NumeroAfiliado, " +
                 "perMedico.Nombre AS MedicoNombre, perMedico.Apellido AS MedicoApellido, e.Nombre AS MedicoEspecialidad " +
                 "FROM Turno t " +
                 "JOIN Paciente p ON t.PacienteID = p.PacienteID " +
                 "JOIN Persona perPaciente ON p.DNI = perPaciente.DNI " +
                 "JOIN Medico m ON t.MedicoID = m.MedicoID " +
                 "JOIN Persona perMedico ON m.DNI = perMedico.DNI " +
                 "LEFT JOIN MedicoEspecialidad me ON m.MedicoID = me.MedicoID " +
                 "LEFT JOIN Especialidad e ON me.EspecialidadID = e.EspecialidadID " +
                 "ORDER BY t.FechaProgramada, t.HoraProgramada";

    try (PreparedStatement stmt = conexion.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Turno t = new Turno();
            t.setTurnoID(rs.getInt("TurnoID"));
            t.setPacienteID(rs.getInt("PacienteID"));
            t.setMedicoID(rs.getInt("MedicoID"));
            t.setSecretarioID(rs.getInt("SecretarioID"));
            int pagoId = rs.getInt("PagoID");
            if (rs.wasNull()) {
                t.setPagoID(null);
            } else {
                t.setPagoID(pagoId);
            }
            t.setFechaProgramada(rs.getDate("FechaProgramada").toLocalDate());
            t.setHoraProgramada(rs.getTime("HoraProgramada").toLocalTime());

            // Setear el estado atendido
            t.setAtendido(rs.getBoolean("Atendido"));

            t.setDniPaciente(rs.getInt("PacienteDNI"));
            t.setNombrePaciente(rs.getString("PacienteNombre"));
            t.setApellidoPaciente(rs.getString("PacienteApellido"));
            t.setTelefonoPaciente(rs.getString("PacienteTelefono"));
            t.setObraSocialPaciente(rs.getString("ObraSocial"));
            t.setNumeroAfiliadoPaciente(rs.getString("NumeroAfiliado"));

            t.setNombreMedico(rs.getString("MedicoNombre"));
            t.setApellidoMedico(rs.getString("MedicoApellido"));
            t.setEspecialidad(rs.getString("MedicoEspecialidad"));

            turnos.add(t);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return turnos;
}


    public List<String> listarNombresMedicos() {
        List<String> medicos = new ArrayList<>();
        String sql = "SELECT per.Nombre, per.Apellido FROM Medico m " +
                     "JOIN Persona per ON m.DNI = per.DNI " +
                     "ORDER BY per.Apellido, per.Nombre";

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String nombreCompleto = rs.getString("Nombre") + " " + rs.getString("Apellido");
                medicos.add(nombreCompleto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicos;
    }

    public int obtenerIdMedicoPorNombreCompleto(String nombreCompleto) {
        String[] partes = nombreCompleto.trim().split(" ");
        if (partes.length < 2) {
            return -1;
        }

        String apellido = partes[partes.length - 1];
        String nombre = String.join(" ", Arrays.copyOf(partes, partes.length - 1));

        String sql = "SELECT m.MedicoID FROM Medico m JOIN Persona per ON m.DNI = per.DNI " +
                     "WHERE per.Nombre = ? AND per.Apellido = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("MedicoID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int obtenerPacienteIDPorDNI(int dni) {
        String sql = "SELECT PacienteID FROM Paciente WHERE DNI = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, dni);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("PacienteID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Turno> filtrarTurnosPorDniOPorApellido(int dniPaciente, String apellidoMedico) {
        List<Turno> turnos = new ArrayList<>();
        String sql = "SELECT t.TurnoID, t.PacienteID, t.MedicoID, t.SecretarioID, t.PagoID, t.FechaProgramada, t.HoraProgramada, " +
                     "perPaciente.DNI AS PacienteDNI, perPaciente.Nombre AS PacienteNombre, perPaciente.Apellido AS PacienteApellido, " +
                     "perPaciente.Telefono AS PacienteTelefono, p.ObraSocial, p.NumeroAfiliado, " +
                     "perMedico.Nombre AS MedicoNombre, perMedico.Apellido AS MedicoApellido, e.Nombre AS MedicoEspecialidad " +
                     "FROM Turno t " +
                     "JOIN Paciente p ON t.PacienteID = p.PacienteID " +
                     "JOIN Persona perPaciente ON p.DNI = perPaciente.DNI " +
                     "JOIN Medico m ON t.MedicoID = m.MedicoID " +
                     "JOIN Persona perMedico ON m.DNI = perMedico.DNI " +
                     "LEFT JOIN MedicoEspecialidad me ON m.MedicoID = me.MedicoID " +
                     "LEFT JOIN Especialidad e ON me.EspecialidadID = e.EspecialidadID " +
                     "WHERE (? = 0 OR perPaciente.DNI = ?) " +
                     "AND (? = '' OR perMedico.Apellido LIKE ?) " +
                     "ORDER BY t.FechaProgramada, t.HoraProgramada";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, dniPaciente);
            stmt.setInt(2, dniPaciente);
            stmt.setString(3, apellidoMedico);
            stmt.setString(4, apellidoMedico + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Turno t = new Turno();
                    t.setTurnoID(rs.getInt("TurnoID"));
                    t.setPacienteID(rs.getInt("PacienteID"));
                    t.setMedicoID(rs.getInt("MedicoID"));
                    t.setSecretarioID(rs.getInt("SecretarioID"));
                    int pagoId = rs.getInt("PagoID");
                    if (rs.wasNull()) {
                        t.setPagoID(null);
                    } else {
                        t.setPagoID(pagoId);
                    }
                    t.setFechaProgramada(rs.getDate("FechaProgramada").toLocalDate());
                    t.setHoraProgramada(rs.getTime("HoraProgramada").toLocalTime());

                    t.setDniPaciente(rs.getInt("PacienteDNI"));
                    t.setNombrePaciente(rs.getString("PacienteNombre"));
                    t.setApellidoPaciente(rs.getString("PacienteApellido"));
                    t.setTelefonoPaciente(rs.getString("PacienteTelefono"));
                    t.setObraSocialPaciente(rs.getString("ObraSocial"));
                    t.setNumeroAfiliadoPaciente(rs.getString("NumeroAfiliado"));

                    t.setNombreMedico(rs.getString("MedicoNombre"));
                    t.setApellidoMedico(rs.getString("MedicoApellido"));
                    t.setEspecialidad(rs.getString("MedicoEspecialidad"));

                    turnos.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return turnos;
    }
    
public List<LocalTime> obtenerHorasDisponibles(int medicoId, LocalDate fecha) {
    List<LocalTime> horasDisponibles = new ArrayList<>();
    String sql = "SELECT HoraInicio, HoraFin FROM HorarioMedico WHERE MedicoID = ? AND Dia = ?";

    String diaSemana = obtenerDiaSemanaEnEspañol(fecha.getDayOfWeek());

    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, medicoId);
        ps.setString(2, diaSemana);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Time horaInicio = rs.getTime("HoraInicio");
                Time horaFin = rs.getTime("HoraFin");

                if (horaInicio != null && horaFin != null) {
                    LocalTime inicio = horaInicio.toLocalTime();
                    LocalTime fin = horaFin.toLocalTime();
                    while (!inicio.isAfter(fin.minusMinutes(30))) {
                        horasDisponibles.add(inicio);
                        inicio = inicio.plusMinutes(30);
                    }
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Filtrar horas ya reservadas para ese médico y fecha
    String sqlTurnos = "SELECT HoraProgramada FROM Turno WHERE MedicoID = ? AND FechaProgramada = ?";
    try (PreparedStatement psTurnos = conexion.prepareStatement(sqlTurnos)) {
        psTurnos.setInt(1, medicoId);
        psTurnos.setDate(2, java.sql.Date.valueOf(fecha));
        try (ResultSet rsTurnos = psTurnos.executeQuery()) {
            List<LocalTime> horasOcupadas = new ArrayList<>();
            while (rsTurnos.next()) {
                Time horaOcupada = rsTurnos.getTime("HoraProgramada");
                if (horaOcupada != null) {
                    horasOcupadas.add(horaOcupada.toLocalTime());
                }
            }
            horasDisponibles.removeAll(horasOcupadas);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return horasDisponibles;
}

private String obtenerDiaSemanaEnEspañol(DayOfWeek day) {
    switch (day) {
        case MONDAY: return "Lunes";
        case TUESDAY: return "Martes";
        case WEDNESDAY: return "Miércoles";
        case THURSDAY: return "Jueves";
        case FRIDAY: return "Viernes";
        case SATURDAY: return "Sábado";
        case SUNDAY: return "Domingo";
        default: return "";
    }
}


    public List<String> obtenerHorasDisponiblesComoTexto(int medicoId, LocalDate fecha) {
        List<LocalTime> horas = obtenerHorasDisponibles(medicoId, fecha);
        List<String> horasTexto = new ArrayList<>();
        for (LocalTime hora : horas) {
            horasTexto.add(hora.toString());
        }
        return horasTexto;
    }
    
    public boolean agregarTurno(Turno turno) {
    String sql = "INSERT INTO Turno (PacienteID, MedicoID, SecretarioID, PagoID, FechaProgramada, HoraProgramada) " +
                 "VALUES (?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, turno.getPacienteID());
        ps.setInt(2, turno.getMedicoID());

        // Tomar SecretarioID actual de sesión
        int secretarioID = Sesion.getSecretarioIDActual();
        if (secretarioID == -1) {
            throw new SQLException("No hay SecretarioID en sesión");
        }
        ps.setInt(3, secretarioID);

        if (turno.getPagoID() != null) {
            ps.setInt(4, turno.getPagoID());
        } else {
            ps.setNull(4, Types.INTEGER);
        }

        ps.setDate(5, Date.valueOf(turno.getFechaProgramada()));
        ps.setTime(6, Time.valueOf(turno.getHoraProgramada()));

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
    
public boolean actualizarTurno(Turno turno) {
    String sql = "UPDATE Turno SET FechaProgramada = ?, HoraProgramada = ?, MedicoID = ?, PacienteID = ? WHERE TurnoID = ?";
    
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setDate(1, Date.valueOf(turno.getFechaProgramada()));
        stmt.setTime(2, Time.valueOf(turno.getHoraProgramada()));
        stmt.setInt(3, turno.getMedicoID());   // acá uso getMedicoID()
        stmt.setInt(4, turno.getPacienteID()); // acá uso getPacienteID()
        stmt.setInt(5, turno.getTurnoID());    // acá uso getTurnoID()

        int filas = stmt.executeUpdate();
        return filas > 0;
        
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}



    public boolean eliminarTurno(int turnoId) {
    String sql = "DELETE FROM Turno WHERE TurnoID = ?";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, turnoId);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    public List<String> obtenerEspecialidades() {
    List<String> especialidades = new ArrayList<>();
    String sql = "SELECT Nombre FROM Especialidad ORDER BY Nombre";
    try (PreparedStatement ps = conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            especialidades.add(rs.getString("Nombre"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return especialidades;
}

    public List<String> obtenerMedicosPorEspecialidad(String especialidad) {
    return listarMedicosPorEspecialidad(especialidad);
}

    
public Set<DayOfWeek> obtenerDiasLaboralesMedico(int medicoId) {
    Set<DayOfWeek> dias = new HashSet<>();
    String sql = "SELECT DISTINCT Dia FROM HorarioMedico WHERE MedicoID = ?";  // Cambié DiaSemana por Dia
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, medicoId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String diaTexto = rs.getString("Dia"); // Cambié DiaSemana por Dia
            
            // Como en la BD está en español (ej: "Lunes"), hay que mapearlo a DayOfWeek
            try {
                DayOfWeek dia = mapearDiaEspañolADayOfWeek(diaTexto);
                dias.add(dia);
            } catch (IllegalArgumentException e) {
                System.err.println("Día inválido en la base de datos: " + diaTexto);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return dias;
}

// Método para mapear días en español a DayOfWeek de Java
private DayOfWeek mapearDiaEspañolADayOfWeek(String diaEsp) {
    switch (diaEsp.toLowerCase()) {
        case "lunes": return DayOfWeek.MONDAY;
        case "martes": return DayOfWeek.TUESDAY;
        case "miércoles": return DayOfWeek.WEDNESDAY;
        case "miercoles": return DayOfWeek.WEDNESDAY;  // por si no usan tilde
        case "jueves": return DayOfWeek.THURSDAY;
        case "viernes": return DayOfWeek.FRIDAY;
        case "sábado": return DayOfWeek.SATURDAY;
        case "sabado": return DayOfWeek.SATURDAY;      // por si no usan tilde
        case "domingo": return DayOfWeek.SUNDAY;
        default:
            throw new IllegalArgumentException("Día desconocido: " + diaEsp);
    }
}

public List<Turno> obtenerTurnosPagadosDelDiaPorMedico(int medicoID) {
    List<Turno> turnos = new ArrayList<>();
    String sql = "SELECT t.TurnoID, t.PacienteID, t.MedicoID, t.SecretarioID, t.PagoID, t.FechaProgramada, t.HoraProgramada, " +
                 "t.Atendido, " +  // <-- agregá este campo en el SELECT
                 "perPaciente.DNI AS PacienteDNI, perPaciente.Nombre AS PacienteNombre, perPaciente.Apellido AS PacienteApellido, " +
                 "perPaciente.Telefono AS PacienteTelefono, p.ObraSocial, p.NumeroAfiliado, " +
                 "perMedico.Nombre AS MedicoNombre, perMedico.Apellido AS MedicoApellido, e.Nombre AS MedicoEspecialidad " +
                 "FROM Turno t " +
                 "JOIN Paciente p ON t.PacienteID = p.PacienteID " +
                 "JOIN Persona perPaciente ON p.DNI = perPaciente.DNI " +
                 "JOIN Medico m ON t.MedicoID = m.MedicoID " +
                 "JOIN Persona perMedico ON m.DNI = perMedico.DNI " +
                 "LEFT JOIN MedicoEspecialidad me ON m.MedicoID = me.MedicoID " +
                 "LEFT JOIN Especialidad e ON me.EspecialidadID = e.EspecialidadID " +
                 "WHERE t.MedicoID = ? AND t.FechaProgramada = CURDATE() AND t.PagoID IS NOT NULL " +
                 "ORDER BY t.HoraProgramada";

    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, medicoID);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Turno t = new Turno();
                t.setTurnoID(rs.getInt("TurnoID"));
                t.setPacienteID(rs.getInt("PacienteID"));
                t.setMedicoID(rs.getInt("MedicoID"));
                t.setSecretarioID(rs.getInt("SecretarioID"));
                int pagoId = rs.getInt("PagoID");
                if (rs.wasNull()) {
                    t.setPagoID(null);
                } else {
                    t.setPagoID(pagoId);
                }
                t.setFechaProgramada(rs.getDate("FechaProgramada").toLocalDate());
                t.setHoraProgramada(rs.getTime("HoraProgramada").toLocalTime());

                t.setAtendido(rs.getBoolean("Atendido"));  // <<<<<<<<<<<<<<<<<<<<

                t.setDniPaciente(rs.getInt("PacienteDNI"));
                t.setNombrePaciente(rs.getString("PacienteNombre"));
                t.setApellidoPaciente(rs.getString("PacienteApellido"));
                t.setTelefonoPaciente(rs.getString("PacienteTelefono"));
                t.setObraSocialPaciente(rs.getString("ObraSocial"));
                t.setNumeroAfiliadoPaciente(rs.getString("NumeroAfiliado"));

                t.setNombreMedico(rs.getString("MedicoNombre"));
                t.setApellidoMedico(rs.getString("MedicoApellido"));
                t.setEspecialidad(rs.getString("MedicoEspecialidad"));

                turnos.add(t);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return turnos;
}


public boolean marcarTurnoComoAtendido(int turnoID) {
    String sql = "UPDATE Turno SET Atendido = TRUE WHERE TurnoID = ?";
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, turnoID);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    
}
