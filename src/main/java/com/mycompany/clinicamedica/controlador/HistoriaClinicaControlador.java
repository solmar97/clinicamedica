package com.mycompany.clinicamedica.controlador;

import com.mycompany.clinicamedica.modelo.*;
import com.mycompany.clinicamedica.vista.*;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoriaClinicaControlador {
    private HistoriaClinicaVista vista;
    private HistoriaClinicaModelo modelo;
    private int pacienteID;
    private int medicoID;

    public HistoriaClinicaControlador(HistoriaClinicaVista vista, HistoriaClinicaModelo modelo, int pacienteID, int medicoID) {
        this.vista = vista;
        this.modelo = modelo;
        this.pacienteID = pacienteID;
        this.medicoID = medicoID;

        cargarFechas();
        configurarEventos();

        vista.setVisible(true);
    }

    private void cargarFechas() {
        List<LocalDate> fechas = modelo.obtenerFechasPorPaciente(pacienteID);
        DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (LocalDate fecha : fechas) {
            comboModel.addElement(fecha.format(formatter));
        }

        // Agregar fecha de hoy al inicio (si no está)
        String hoyStr = LocalDate.now().format(formatter);
        if (comboModel.getSize() == 0 || !comboModel.getElementAt(0).equals(hoyStr)) {
            comboModel.insertElementAt(hoyStr, 0);
        }

        vista.getComboFechas().setModel(comboModel);
        cargarHistoriaPorFecha(hoyStr);
    }

    private void cargarHistoriaPorFecha(String fechaStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha = LocalDate.parse(fechaStr, formatter);

            RegistroHistoriaClinica registro = modelo.obtenerRegistroPorPacienteYFecha(pacienteID, fecha);
            if (registro == null) {
                vista.getAreaDescripcion().setText("");
                vista.getAreaDescripcion().setEditable(true);
                vista.getLblInfo().setText("Nueva entrada. Solo visible para vos hasta que guardes.");
                vista.getChkAtendido().setSelected(false);
            } else {
                vista.getAreaDescripcion().setText(registro.getDescripcion());
                boolean puedeEditar = registro.getMedicoID() == medicoID;
                vista.getAreaDescripcion().setEditable(puedeEditar);

                String nombreCompletoMedico = registro.getNombreMedico() + " " + registro.getApellidoMedico();
                String textoInfo = "Médico: " + nombreCompletoMedico +
                        " (" + (registro.getEspecialidad() != null ? registro.getEspecialidad() : "Sin especialidad") + ")";
                if (!puedeEditar) {
                    textoInfo += " - Solo lectura";
                }

                vista.getLblInfo().setText(textoInfo);
                vista.getChkAtendido().setSelected(registro.isAtendido());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar historia clínica para la fecha: " + fechaStr);
        }
    }

    private void configurarEventos() {
        vista.getComboFechas().addActionListener(e -> {
            String fechaSeleccionada = (String) vista.getComboFechas().getSelectedItem();
            if (fechaSeleccionada != null) {
                cargarHistoriaPorFecha(fechaSeleccionada);
            }
        });

        vista.getBtnGuardar().addActionListener(e -> {
            String fechaStr = (String) vista.getComboFechas().getSelectedItem();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha = LocalDate.parse(fechaStr, formatter);
            String descripcion = vista.getAreaDescripcion().getText();

            boolean ok = modelo.guardarHistoria(pacienteID, medicoID, fecha, descripcion);
            if (ok) {
                JOptionPane.showMessageDialog(vista, "Historia clínica guardada correctamente.");
                cargarFechas(); // recargar para actualizar estado atendido
            } else {
                JOptionPane.showMessageDialog(vista, "No está permitido modificar la historia clínica de otro médico.");
            }
        });

        vista.getBtnCerrar().addActionListener(e -> vista.dispose());
    }
}
