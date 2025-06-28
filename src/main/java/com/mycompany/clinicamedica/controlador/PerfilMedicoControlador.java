package com.mycompany.clinicamedica.controlador;

import com.mycompany.clinicamedica.conexion.ConexionBD;
import com.mycompany.clinicamedica.modelo.*;
import com.mycompany.clinicamedica.vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PerfilMedicoControlador {
    private PerfilMedicoVista vista;
    private TurnoModelo turnoModelo;
    private PacienteModelo pacienteModelo;
    private HistoriaClinicaModelo historiaClinicaModelo;

    private int medicoID;

    public PerfilMedicoControlador(PerfilMedicoVista vista, int medicoID) {
        this.vista = vista;
        this.medicoID = medicoID;

        try {
            Connection conexion = ConexionBD.obtenerConexion();

            this.turnoModelo = new TurnoModelo(conexion);
            this.pacienteModelo = new PacienteModelo(conexion);
            this.historiaClinicaModelo = new HistoriaClinicaModelo(conexion);

            cargarTurnosDelDia();

            vista.getBtnActualizar().addActionListener(e -> cargarTurnosDelDia());
            vista.getBtnVerPaciente().addActionListener(this::verDetallePaciente);
            vista.getBtnHistoriaClinica().addActionListener(this::abrirHistoriaClinica);
            vista.getBtnAtras().addActionListener(e -> vista.dispose());

            vista.setVisible(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void cargarTurnosDelDia() {
        List<Turno> turnos = turnoModelo.obtenerTurnosPagadosDelDiaPorMedico(medicoID);
        DefaultTableModel tabla = vista.getModeloTabla();
        tabla.setRowCount(0);

        int orden = 1;
        for (Turno t : turnos) {
            // Obtener el estado del turno (supongamos método isAtendido())
            String estadoAtencion = (t.isAtendido()) ? "Atendido" : "Pendiente";

            tabla.addRow(new Object[]{
                orden++,
                t.getNombrePaciente() + " " + t.getApellidoPaciente(),
                t.getDniPaciente(),
                t.getObraSocialPaciente(),
                t.getFechaProgramada(),
                t.getHoraProgramada(),
                estadoAtencion
            });
        }
    }

    private void verDetallePaciente(ActionEvent e) {
        int fila = vista.getTablaTurnos().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccioná un turno.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int dniInt = (int) vista.getTablaTurnos().getValueAt(fila, 2);
        String dni = String.valueOf(dniInt);

        Paciente paciente = pacienteModelo.obtenerPacientePorDNI(dni);

        if (paciente != null) {
            DetallePacienteVista detalleVista = new DetallePacienteVista();
            detalleVista.mostrarSoloLectura(paciente);
            detalleVista.getBtnCancelar().addActionListener(ev -> detalleVista.dispose());
            detalleVista.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(vista, "No se encontró el paciente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirHistoriaClinica(ActionEvent e) {
        int fila = vista.getTablaTurnos().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccioná un turno.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int dniInt = (int) vista.getTablaTurnos().getValueAt(fila, 2);
        String dni = String.valueOf(dniInt);

        Paciente paciente = pacienteModelo.obtenerPacientePorDNI(dni);

        if (paciente != null) {
            HistoriaClinicaVista hcVista = new HistoriaClinicaVista();
            new HistoriaClinicaControlador(hcVista, historiaClinicaModelo, paciente.getPacienteID(), medicoID);
        } else {
            JOptionPane.showMessageDialog(vista, "Paciente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
