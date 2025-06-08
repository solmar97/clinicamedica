package com.mycompany.clinicamedica.controlador;

import com.mycompany.clinicamedica.modelo.PacienteModelo;
import java.time.format.DateTimeFormatter;
import com.mycompany.clinicamedica.modelo.Turno;
import com.mycompany.clinicamedica.modelo.TurnoModelo;
import com.mycompany.clinicamedica.vista.TurnoVista;
import com.mycompany.clinicamedica.vista.DetalleTurnoVista;
import com.mycompany.clinicamedica.vista.SecretarioVista;

import javax.swing.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TurnoControlador {
    private TurnoModelo modelo;
    private TurnoVista vista;
    private Connection conexion;
    private List<Turno> listaTurnosActual;
    private SecretarioVista secretarioVista;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TurnoControlador(Connection conexion, SecretarioVista secretarioVista) {
        this.conexion = conexion;
        this.secretarioVista = secretarioVista;
        this.modelo = new TurnoModelo(conexion);

        SwingUtilities.invokeLater(() -> {
            vista = new TurnoVista();
            initController();
            vista.setVisible(true);
            secretarioVista.setVisible(false); // Oculta el menú principal
        });
    }

    private void initController() {
        cargarTurnosTabla(modelo.listarTurnos());

        vista.agregarListenerBuscar(e -> aplicarFiltro());
        vista.agregarListenerNuevoTurno(e -> abrirFormularioNuevoTurno());
        vista.agregarListenerVerModificarTurno(e -> abrirDetalleModificarTurno());
        vista.agregarListenerCancelarTurno(e -> cancelarTurnoSeleccionado());

        vista.agregarListenerVolver(e -> {
            vista.dispose();
            secretarioVista.setVisible(true);
        });

        vista.getTablaTurnos().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = vista.getTablaTurnos().getSelectedRow();
                boolean haySeleccion = fila >= 0 && fila < listaTurnosActual.size();
                vista.habilitarBotonesTurnoSeleccionado(haySeleccion);
            }
        });

        vista.habilitarBotonesTurnoSeleccionado(false);
    }

    private void cargarTurnosTabla(List<Turno> turnos) {
        this.listaTurnosActual = turnos;
        var modeloTabla = vista.getModeloTabla();
        modeloTabla.setRowCount(0);

        for (Turno t : turnos) {
            String fecha = t.getFechaProgramada().format(FORMATO_FECHA);
            String hora = t.getHoraProgramada().toString();

            modeloTabla.addRow(new Object[]{
                fecha, hora,
                String.valueOf(t.getDniPaciente()),
                t.getNombrePaciente(),
                t.getApellidoPaciente(),
                t.getNombreMedico(),
                t.getApellidoMedico(),
                t.getEspecialidad() != null ? t.getEspecialidad() : ""
            });
        }

        vista.limpiarDetalles();
        vista.habilitarBotonesTurnoSeleccionado(false);
    }

    private void aplicarFiltro() {
        String filtroDniStr = vista.getFiltroDniPaciente().trim();
        String filtroApellido = vista.getFiltroApellidoMedico().trim();

        int dni = 0; 

        if (!filtroDniStr.isEmpty()) {
            try {
                dni = Integer.parseInt(filtroDniStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "DNI inválido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (dni == 0 && filtroApellido.isEmpty()) {
            cargarTurnosTabla(modelo.listarTurnos());
        } else {
            List<Turno> listaFiltrada = modelo.filtrarTurnosPorDniOPorApellido(dni, filtroApellido);
            cargarTurnosTabla(listaFiltrada);
        }
    }

    private Turno obtenerTurnoDesdeFila(int fila) {
        if (fila >= 0 && fila < listaTurnosActual.size()) {
            return listaTurnosActual.get(fila);
        }
        return null;
    }

    private void abrirFormularioNuevoTurno() {
        PacienteModelo pacienteModelo = new PacienteModelo(conexion);
        DetalleTurnoVista detalleVista = new DetalleTurnoVista(modelo, pacienteModelo);

        List<String> medicos = modelo.listarNombresMedicos();
        detalleVista.cargarMedicos(medicos);

        // Actualiza horas disponibles si cambia médico o fecha
        detalleVista.getComboMedicos().addActionListener(e -> actualizarHorasDisponibles(detalleVista));
        detalleVista.getDateChooser().getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                actualizarHorasDisponibles(detalleVista);
            }
        });

        detalleVista.getBtnGuardar().addActionListener(e -> {
            try {
                String fechaStr = detalleVista.getFecha();
                String horaStr = detalleVista.getHora();

                LocalDate fecha = LocalDate.parse(fechaStr, FORMATO_FECHA);
                LocalTime hora = LocalTime.parse(horaStr + ":00");

                String medicoSeleccionado = detalleVista.getMedicoSeleccionado();
                if (medicoSeleccionado == null || medicoSeleccionado.isEmpty()) {
                    JOptionPane.showMessageDialog(detalleVista, "Seleccioná un médico.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String dniPacienteStr = detalleVista.getDniPaciente();
                if (dniPacienteStr == null || dniPacienteStr.isEmpty()) {
                    JOptionPane.showMessageDialog(detalleVista, "Ingresá DNI del paciente.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int dniPaciente = Integer.parseInt(dniPacienteStr);
                int pacienteID = modelo.obtenerPacienteIDPorDNI(dniPaciente);
                if (pacienteID == -1) {
                    JOptionPane.showMessageDialog(detalleVista, "Paciente no encontrado con ese DNI.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Turno nuevoTurno = new Turno();
                nuevoTurno.setFechaProgramada(fecha);
                nuevoTurno.setHoraProgramada(hora);

                int idMedico = modelo.obtenerIdMedicoPorNombreCompleto(medicoSeleccionado);
                nuevoTurno.setMedicoID(idMedico);

                nuevoTurno.setPacienteID(pacienteID);

                boolean agregado = modelo.agregarTurno(nuevoTurno);
                if (agregado) {
                    JOptionPane.showMessageDialog(detalleVista, "Turno registrado correctamente.");
                    detalleVista.dispose();
                    aplicarFiltro();
                } else {
                    JOptionPane.showMessageDialog(detalleVista, "Error al registrar turno.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(detalleVista, "DNI inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(detalleVista, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        detalleVista.getBtnCerrar().addActionListener(ev -> detalleVista.dispose());

        detalleVista.setVisible(true);
    }

    private void actualizarHorasDisponibles(DetalleTurnoVista detalleVista) {
        try {
            String medicoSeleccionado = detalleVista.getMedicoSeleccionado();
            String fechaStr = detalleVista.getFecha();

            if (medicoSeleccionado == null || medicoSeleccionado.isEmpty() || fechaStr == null || fechaStr.isEmpty()) {
                return;
            }

            int medicoID = modelo.obtenerIdMedicoPorNombreCompleto(medicoSeleccionado);
            LocalDate fecha = LocalDate.parse(fechaStr, FORMATO_FECHA);

           List<LocalTime> horasDisponibles = modelo.obtenerHorasDisponibles(medicoID, fecha);

            detalleVista.cargarHoras(horasDisponibles);

        } catch (Exception ex) {
            System.err.println("Error actualizando horas disponibles: " + ex.getMessage());
        }
    }

 private void abrirDetalleModificarTurno() {
    int fila = vista.getTablaTurnos().getSelectedRow();
    Turno turno = obtenerTurnoDesdeFila(fila);
    if (turno != null) {
        PacienteModelo pacienteModelo = new PacienteModelo(conexion);

        DetalleTurnoVista detalleVista = new DetalleTurnoVista(turno, modelo, pacienteModelo);

        List<String> medicos = modelo.listarMedicosPorEspecialidad(turno.getEspecialidad());
        detalleVista.cargarMedicos(medicos);

        String medicoCompleto = turno.getNombreMedico() + " " + turno.getApellidoMedico();
        detalleVista.getComboMedicos().setSelectedItem(medicoCompleto);

        detalleVista.getBtnGuardar().addActionListener(e -> {
            try {
                String fechaStr = detalleVista.getFecha();
                String horaStr = detalleVista.getHora();

                LocalDate fecha = LocalDate.parse(fechaStr, FORMATO_FECHA);
                LocalTime hora = LocalTime.parse(horaStr + ":00");

                String medicoSeleccionado = detalleVista.getMedicoSeleccionado();
                if (medicoSeleccionado == null || medicoSeleccionado.isEmpty()) {
                    JOptionPane.showMessageDialog(detalleVista, "Seleccioná un médico.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String dniPacienteStr = detalleVista.getDniPaciente();
                if (dniPacienteStr == null || dniPacienteStr.isEmpty()) {
                    JOptionPane.showMessageDialog(detalleVista, "Ingresá DNI del paciente.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int dniPaciente = Integer.parseInt(dniPacienteStr);

                int pacienteID = modelo.obtenerPacienteIDPorDNI(dniPaciente);
                if (pacienteID == -1) {
                    JOptionPane.showMessageDialog(detalleVista, "Paciente no encontrado con ese DNI.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idMedico = modelo.obtenerIdMedicoPorNombreCompleto(medicoSeleccionado);

                // Modificar el turno existente con los datos nuevos
                turno.setFechaProgramada(fecha);
                turno.setHoraProgramada(hora);
                turno.setMedicoID(idMedico);
                turno.setPacienteID(pacienteID);

                boolean actualizado = modelo.actualizarTurno(turno);
                if (actualizado) {
                    JOptionPane.showMessageDialog(detalleVista, "Turno modificado correctamente.");
                    detalleVista.dispose();
                    aplicarFiltro();
                } else {
                    JOptionPane.showMessageDialog(detalleVista, "Error al modificar turno.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(detalleVista, "DNI inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(detalleVista, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        detalleVista.getBtnCerrar().addActionListener(ev -> detalleVista.dispose());

        detalleVista.setVisible(true);
    } else {
        JOptionPane.showMessageDialog(vista, "Seleccioná un turno para ver los detalles.");
    }
}


    private void cancelarTurnoSeleccionado() {
        int fila = vista.getTablaTurnos().getSelectedRow();
        Turno turno = obtenerTurnoDesdeFila(fila);
        if (turno != null) {
            int confirm = JOptionPane.showConfirmDialog(vista,
                    "¿Seguro que querés cancelar el turno del paciente " + turno.getNombrePaciente() + "?",
                    "Confirmar cancelación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean eliminado = modelo.eliminarTurno(turno.getTurnoID());
                if (eliminado) {
                    JOptionPane.showMessageDialog(vista, "Turno cancelado con éxito.");
                    aplicarFiltro();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al cancelar turno.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
  public List<LocalTime> obtenerHorasDisponiblesParaMedicoYFecha(int medicoId, LocalDate fecha) {
    return modelo.obtenerHorasDisponibles(medicoId, fecha);  
}


}
