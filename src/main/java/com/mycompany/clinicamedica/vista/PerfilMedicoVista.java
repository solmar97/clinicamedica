package com.mycompany.clinicamedica.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PerfilMedicoVista extends JFrame {
    private JTable tablaTurnos;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar;
    private JButton btnVerPaciente;
    private JButton btnHistoriaClinica;
    private JButton btnAtras;

    public PerfilMedicoVista() {
        setTitle("Perfil del Médico - Turnos del Día");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Cambié la columna "Atendido" por "Estado del turno"
        modeloTabla = new DefaultTableModel(
          new String[]{"TurnoID", "Nombre Paciente", "DNI", "Obra Social", "Fecha", "Hora", "Estado del turno"}, 0
        );

        tablaTurnos = new JTable(modeloTabla);
        add(new JScrollPane(tablaTurnos), BorderLayout.CENTER);

        // Ocultar la columna de TurnoID (índice 0)
        tablaTurnos.getColumnModel().getColumn(0).setMinWidth(0);
        tablaTurnos.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaTurnos.getColumnModel().getColumn(0).setWidth(0);

        // Panel con botones
        JPanel panelBotones = new JPanel();
        btnActualizar = new JButton("Actualizar");
        btnVerPaciente = new JButton("Ver Detalles del Paciente");
        btnHistoriaClinica = new JButton("Historia Clínica");
        btnAtras = new JButton("Atrás");

        panelBotones.add(btnActualizar);
        panelBotones.add(btnVerPaciente);
        panelBotones.add(btnHistoriaClinica);
        panelBotones.add(btnAtras);

        add(panelBotones, BorderLayout.SOUTH);
    }

    public JTable getTablaTurnos() {
        return tablaTurnos;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnVerPaciente() {
        return btnVerPaciente;
    }

    public JButton getBtnHistoriaClinica() {
        return btnHistoriaClinica;
    }

    public JButton getBtnAtras() {
        return btnAtras;
    }
}
