package com.mycompany.clinicamedica.vista;

import javax.swing.*;
import java.awt.*;

public class HistoriaClinicaVista extends JFrame {
    private JComboBox<String> comboFechas;
    private JTextArea areaDescripcion;
    private JLabel lblInfo;
    private JCheckBox chkAtendido;
    private JButton btnGuardar, btnCerrar;

    public HistoriaClinicaVista() {
        setTitle("Historia Clínica del Paciente");
        setSize(550, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        comboFechas = new JComboBox<>();
        areaDescripcion = new JTextArea(10, 40);
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setWrapStyleWord(true);

        lblInfo = new JLabel(" ");
        chkAtendido = new JCheckBox("Paciente atendido");
        chkAtendido.setEnabled(false); // Solo visualización

        btnGuardar = new JButton("Guardar");
        btnCerrar = new JButton("Cerrar");

        JPanel panelNorte = new JPanel(new BorderLayout(5, 5));
        panelNorte.add(new JLabel("Fecha:"), BorderLayout.WEST);
        panelNorte.add(comboFechas, BorderLayout.CENTER);

        JPanel panelCentro = new JPanel(new BorderLayout(5, 5));
        panelCentro.setBorder(BorderFactory.createTitledBorder("Detalle de la Historia Clínica"));
        panelCentro.add(new JScrollPane(areaDescripcion), BorderLayout.CENTER);
        panelCentro.add(lblInfo, BorderLayout.SOUTH);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSur.add(chkAtendido);
        panelSur.add(btnGuardar);
        panelSur.add(btnCerrar);

        add(panelNorte, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }

    // Getters
    public JComboBox<String> getComboFechas() {
        return comboFechas;
    }

    public JTextArea getAreaDescripcion() {
        return areaDescripcion;
    }

    public JLabel getLblInfo() {
        return lblInfo;
    }

    public JCheckBox getChkAtendido() {
        return chkAtendido;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnCerrar() {
        return btnCerrar;
    }
}
