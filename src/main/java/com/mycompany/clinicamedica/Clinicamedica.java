package com.mycompany.clinicamedica;

import com.mycompany.clinicamedica.controlador.LoginControlador;
import com.mycompany.clinicamedica.modelo.UsuarioModelo;
import com.mycompany.clinicamedica.vista.LoginVista;

public class Clinicamedica {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginVista vista = new LoginVista();
            UsuarioModelo modelo = new UsuarioModelo();
            new LoginControlador(vista, modelo); // El controlador configura la vista
            vista.setVisible(true); // Mostrar ventana de login
        });
    }
}
