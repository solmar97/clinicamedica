package com.mycompany.clinicamedica.vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

public class TurnoVista extends JFrame {
    private JTable tablaTurnos;
    private DefaultTableModel modeloTabla;
    private JTextField txtFiltroDni;
    private JTextField txtFiltroApellido;
    private JButton btnBuscar;
    private JButton btnNuevoTurno;
    private JButton btnVerModificarTurno;
    private JButton btnCancelarTurno;
    private JButton btnVolver;

    public TurnoVista() {
        setTitle("Gestión de Turnos");
        setSize(950, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // Panel superior con filtros
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.add(new JLabel("DNI Paciente:"));
        txtFiltroDni = new JTextField(10);
        panelFiltro.add(txtFiltroDni);

        panelFiltro.add(new JLabel("Apellido Médico:"));
        txtFiltroApellido = new JTextField(10);
        panelFiltro.add(txtFiltroApellido);

        btnBuscar = new JButton("Buscar");
        panelFiltro.add(btnBuscar);

        add(panelFiltro, BorderLayout.NORTH);

        // Tabla con columna "Estado del turno"
        String[] columnas = {
            "Fecha", "Hora", "DNI Paciente", "Nombre Paciente", "Apellido Paciente",
            "Nombre Médico", "Apellido Médico", "Especialidad", "Estado del turno"
        };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaTurnos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaTurnos);
        add(scrollTabla, BorderLayout.CENTER);

        // Panel de botones abajo
        JPanel panelBotones = new JPanel();
        btnNuevoTurno = new JButton("Nuevo Turno");
        btnVerModificarTurno = new JButton("Detalle Turno");
        btnCancelarTurno = new JButton("Cancelar Turno");
        btnVolver = new JButton("Atrás");

        panelBotones.add(btnNuevoTurno);
        panelBotones.add(btnVerModificarTurno);
        panelBotones.add(btnCancelarTurno);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);
    }

    public JTable getTablaTurnos() {
        return tablaTurnos;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public String getFiltroDniPaciente() {
        return txtFiltroDni.getText().trim();
    }

    public String getFiltroApellidoMedico() {
        return txtFiltroApellido.getText().trim();
    }

    public void agregarListenerBuscar(ActionListener listener) {
        btnBuscar.addActionListener(listener);
    }

    public void agregarListenerNuevoTurno(ActionListener listener) {
        btnNuevoTurno.addActionListener(listener);
    }

    public void agregarListenerVerModificarTurno(ActionListener listener) {
        btnVerModificarTurno.addActionListener(listener);
    }

    public void agregarListenerCancelarTurno(ActionListener listener) {
        btnCancelarTurno.addActionListener(listener);
    }

    public void agregarListenerVolver(ActionListener listener) {
        btnVolver.addActionListener(listener);
    }

    public void limpiarDetalles() {
        // Ya no hay detalles, así que no hace nada
    }

    public void habilitarBotonesTurnoSeleccionado(boolean habilitar) {
        btnVerModificarTurno.setEnabled(habilitar);
        btnCancelarTurno.setEnabled(habilitar);
    }
}
