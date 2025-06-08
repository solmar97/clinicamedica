package com.mycompany.clinicamedica.vista;

import com.mycompany.clinicamedica.modelo.Especialidad;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MedicoVista extends JFrame {
    private JComboBox<Especialidad> especialidadCombo;
    private JTextField filtroApellidoField;
    private JTable tablaHorarios;
    private DefaultTableModel tablaModel;
    private JButton btnRegistrar;
    private JButton btnVerDetalles;
    private JButton btnAtras;

    public MedicoVista() {
        setTitle("Sistema Médico / Médico");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new GridLayout(2, 2));
        panelTop.add(new JLabel("Especialidad:"));
        especialidadCombo = new JComboBox<>();
        panelTop.add(especialidadCombo);

        panelTop.add(new JLabel("Filtrar por Apellido:"));
        filtroApellidoField = new JTextField();
        panelTop.add(filtroApellidoField);

        add(panelTop, BorderLayout.NORTH);

        tablaModel = new DefaultTableModel(new String[]{"Especialidad", "Nombre", "Apellido", "DNI", "Día", "Horario"}, 0);
        tablaHorarios = new JTable(tablaModel);
        add(new JScrollPane(tablaHorarios), BorderLayout.CENTER);

        JPanel panelBottom = new JPanel();
        btnRegistrar = new JButton("Registrar Médico");
        btnVerDetalles = new JButton("Ver Detalles Médico");
        btnAtras = new JButton("Atrás");
        panelBottom.add(btnRegistrar);
        panelBottom.add(btnVerDetalles);
        panelBottom.add(btnAtras);

        add(panelBottom, BorderLayout.SOUTH);
    }

    // Getters para el controlador
    public JComboBox<Especialidad> getEspecialidadCombo() {
        return especialidadCombo;
    }

    public JTextField getFiltroApellidoField() {
        return filtroApellidoField;
    }

    public JTable getTablaHorarios() {
        return tablaHorarios;
    }

    public DefaultTableModel getTablaModel() {
        return tablaModel;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnVerDetalles() {
        return btnVerDetalles;
    }

    public JButton getBtnAtras() {
        return btnAtras;
    }
}
