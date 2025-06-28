package com.mycompany.clinicamedica.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PagoVista extends JFrame {
    private JTable tablaTurnos;
    private DefaultTableModel modeloTabla;
    private JTextField txtDniBusqueda;
    private JButton btnBuscar, btnPagar, btnCerrar;

    public PagoVista() {
        setTitle("Gestión de Pagos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponentes();
    }

    private void initComponentes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Panel superior para búsqueda por DNI
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar DNI Paciente:"));
        txtDniBusqueda = new JTextField(10);
        panelBusqueda.add(txtDniBusqueda);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);
        panel.add(panelBusqueda, BorderLayout.NORTH);

        // Tabla de turnos
       modeloTabla = new DefaultTableModel(new String[]{
    "DNI",
    "Paciente",
    "Fecha",
    "Hora",
    "Obra Social",
    "Médico"
}, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};
        tablaTurnos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaTurnos);
        panel.add(scroll, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel();
        btnPagar = new JButton("Realizar Pago");
        btnCerrar = new JButton("Cerrar");
        panelBotones.add(btnPagar);
        panelBotones.add(btnCerrar);

        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);
    }

    // Getters para controlador
    public JTable getTablaTurnos() { return tablaTurnos; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTextField getTxtDniBusqueda() { return txtDniBusqueda; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnPagar() { return btnPagar; }
    public JButton getBtnCerrar() { return btnCerrar; }
}

