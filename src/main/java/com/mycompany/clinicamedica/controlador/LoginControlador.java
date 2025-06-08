package com.mycompany.clinicamedica.controlador;

import com.mycompany.clinicamedica.conexion.ConexionBD;
import com.mycompany.clinicamedica.modelo.MedicoModelo;
import com.mycompany.clinicamedica.modelo.UsuarioModelo;
import com.mycompany.clinicamedica.vista.LoginVista;
import com.mycompany.clinicamedica.vista.MedicoVista;
import com.mycompany.clinicamedica.vista.SecretarioVista;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.*;

public class LoginControlador {
    private LoginVista vista;
    private UsuarioModelo modelo;

    public LoginControlador(LoginVista vista, UsuarioModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        this.vista.btnIniciarSesion.addActionListener(e -> iniciarSesion());
    }

private void iniciarSesion() {
    String usuario = vista.txtUsuario.getText();
    String contrasena = new String(vista.txtContrasena.getPassword());

    if (modelo.autenticarUsuario(usuario, contrasena)) {
        String rol = modelo.obtenerRolUsuario(usuario);
        String nombreCompleto = usuario; // valor por defecto si no se encuentra el nombre

        try {
            if ("Secretario".equalsIgnoreCase(rol)) {
                nombreCompleto = modelo.obtenerNombreCompletoSecretario(usuario);

                // Obtener SecretarioID y guardar en Sesion
                int secretarioID = modelo.obtenerSecretarioID(usuario);
                com.mycompany.clinicamedica.modelo.Sesion.iniciarSesion(usuario, secretarioID);

                JOptionPane.showMessageDialog(vista, "Bienvenido, " + nombreCompleto);
                vista.dispose();

                Connection conexion = ConexionBD.obtenerConexion();
                SecretarioVista secretarioVista = new SecretarioVista(conexion);
                new SecretarioControlador(secretarioVista);

            } else if ("Medico".equalsIgnoreCase(rol)) {
                nombreCompleto = modelo.obtenerNombreCompletoMedico(usuario);

                JOptionPane.showMessageDialog(vista, "Bienvenido, " + nombreCompleto);
                vista.dispose();

                MedicoVista medicoVista = new MedicoVista();
                MedicoModelo medicoModelo = new MedicoModelo();
                new MedicoControlador(medicoModelo, medicoVista);

            } else {
                JOptionPane.showMessageDialog(null, "Rol no soportado: " + rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage());
        }

    } else {
        JOptionPane.showMessageDialog(vista, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


}
