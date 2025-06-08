package com.mycompany.clinicamedica.vista;

import javax.swing.*;

public class LoginVista extends JFrame {
    public JTextField txtUsuario = new JTextField();
    public JPasswordField txtContrasena = new JPasswordField();
    public JButton btnIniciarSesion = new JButton("Iniciar Sesión");

    public LoginVista() {
        setTitle("Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblUsuario = new JLabel("Usuario:");
        JLabel lblContrasena = new JLabel("Contraseña:");

        lblUsuario.setBounds(30, 30, 80, 25);
        txtUsuario.setBounds(120, 30, 130, 25);

        lblContrasena.setBounds(30, 70, 80, 25);
        txtContrasena.setBounds(120, 70, 130, 25);

        btnIniciarSesion.setBounds(90, 120, 120, 30);

        add(lblUsuario);
        add(txtUsuario);
        add(lblContrasena);
        add(txtContrasena);
        add(btnIniciarSesion);

        // Esta línea activa el botón con Enter
        getRootPane().setDefaultButton(btnIniciarSesion);
    }
}
