/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clinicamedica.vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author HP
 */
public class PacienteVista extends JFrame {
    private JTable tablaPacientes;
    private JTextField txtBuscarDNI;
    private JButton btnBuscar, btnRegistrar, btnVerDetalles, btnAtras;

    public PacienteVista() {
        setTitle("Gestión de Pacientes");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout());
        panelBusqueda.add(new JLabel("Buscar por DNI:"));
        txtBuscarDNI = new JTextField(15);
        panelBusqueda.add(txtBuscarDNI);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);
        add(panelBusqueda, BorderLayout.NORTH);

        // Tabla
tablaPacientes = new JTable(new javax.swing.table.DefaultTableModel(
    new Object[][] {},
    new String[] { 
        "DNI", "Nombre", "Apellido", "Fecha de nacimiento", 
        "Obra Social", "Numero de afiliado", "Genero", "Teléfono", "Email" 
    }
) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
});

        JScrollPane scrollPane = new JScrollPane(tablaPacientes);
        add(scrollPane, BorderLayout.CENTER);


        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnRegistrar = new JButton("Registrar Paciente");
        btnVerDetalles = new JButton("Ver Detalles");
        btnAtras = new JButton("Atrás");
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnVerDetalles);
        panelBotones.add(btnAtras);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Getters públicos para que el controlador pueda acceder a los componentes
    public JTable getTablaPacientes() { return tablaPacientes; }
    public JTextField getTxtBuscarDNI() { return txtBuscarDNI; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnVerDetalles() { return btnVerDetalles; }
    public JButton getBtnAtras() { return btnAtras; }
}
