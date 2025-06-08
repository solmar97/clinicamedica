package com.mycompany.clinicamedica.vista;

import com.mycompany.clinicamedica.modelo.MedicoModelo;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SecretarioVista extends JFrame {
    private JButton btnTurnos;
    private JButton btnPacientes;
    private JButton btnPagos;
    private JButton btnMedicos;
    private JButton btnEspecialidad;
    private JButton btnCerrarSesion;

    private Connection conexion;

    // Quito la instancia de EspecialidadVista de la vista

    public SecretarioVista(Connection conexion) {
        this.conexion = conexion;

        setTitle("Sistema Médico / Secretario");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crear botones
        btnTurnos = new JButton("Turnos");
        btnPacientes = new JButton("Pacientes");
        btnPagos = new JButton("Pagos");
        btnMedicos = new JButton("Médicos");
        btnEspecialidad = new JButton("Especialidades");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        // Crear panel y estilo
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font fuenteBoton = new Font("Arial", Font.PLAIN, 14);
        Dimension tamBoton = new Dimension(160, 40);

        for (JButton boton : List.of(btnTurnos, btnPacientes, btnPagos, btnMedicos, btnEspecialidad, btnCerrarSesion)) {
            boton.setFont(fuenteBoton);
            boton.setPreferredSize(tamBoton);
            panel.add(boton);
        }

        add(panel);

        // **Importante:** NO agrego listener aquí para btnEspecialidad
        // La lógica queda en el controlador para seguir MVC
    }

    // Método para mostrar la ventana desde otras vistas
    public void mostrar() {
        this.setVisible(true);
        this.setEnabled(true);
        this.toFront();
        this.requestFocus();
    }

    // Getters (opcional)
    public JButton getBtnTurnos() { return btnTurnos; }
    public JButton getBtnPacientes() { return btnPacientes; }
    public JButton getBtnPagos() { return btnPagos; }
    public JButton getBtnMedicos() { return btnMedicos; }
    public JButton getBtnEspecialidad() { return btnEspecialidad; }
    public JButton getBtnCerrarSesion() { return btnCerrarSesion; }
}
