package com.mycompany.clinicamedica.controlador;

import com.mycompany.clinicamedica.modelo.MedicoModelo;
import com.mycompany.clinicamedica.modelo.UsuarioModelo;
import com.mycompany.clinicamedica.modelo.PacienteModelo;
import com.mycompany.clinicamedica.modelo.PagoModelo;
import com.mycompany.clinicamedica.vista.EspecialidadVista;
import com.mycompany.clinicamedica.vista.LoginVista;
import com.mycompany.clinicamedica.vista.MedicoVista;
import com.mycompany.clinicamedica.vista.PacienteVista;
import com.mycompany.clinicamedica.vista.PagoVista;
import com.mycompany.clinicamedica.vista.SecretarioVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mycompany.clinicamedica.conexion.ConexionBD;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class SecretarioControlador {
    private SecretarioVista vista;

    public SecretarioControlador(SecretarioVista vista) {
        this.vista = vista;
        this.vista.setVisible(true);

        // Botón Cerrar Sesión
        this.vista.getBtnCerrarSesion().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
                LoginVista loginVista = new LoginVista();
                UsuarioModelo modelo = new UsuarioModelo();
                new LoginControlador(loginVista, modelo);
            }
        });

        // Botón Turnos
        this.vista.getBtnTurnos().addActionListener(e -> {
            System.out.println("Abrir gestión de turnos");
            try {
                Connection conexion = ConexionBD.obtenerConexion();
                new TurnoControlador(conexion, this.vista);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                    vista,
                    "Error al conectar con la base de datos. Por favor, intente más tarde.",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Botón Pacientes
        this.vista.getBtnPacientes().addActionListener(e -> {
            try {
                PacienteVista pacienteVista = new PacienteVista();
                PacienteModelo modelo = new PacienteModelo(ConexionBD.obtenerConexion());
                new PacienteControlador(pacienteVista, modelo);
                pacienteVista.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                    vista,
                    "Error al conectar con la base de datos. Por favor, intente más tarde.",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Botón Pagos (ACA SE AGREGA EL CODIGO NUEVO)
        this.vista.getBtnPagos().addActionListener(e -> {
            try {
                Connection conexion = ConexionBD.obtenerConexion();
                PagoVista pagoVista = new PagoVista();
                PagoModelo pagoModelo = new PagoModelo(conexion);
                PagoControlador pagoControlador = new PagoControlador(pagoVista, pagoModelo);
                pagoVista.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                    vista,
                    "Error al conectar con la base de datos. Por favor, intente más tarde.",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Botón Médicos
        this.vista.getBtnMedicos().addActionListener(e -> {
            System.out.println("Abriendo MedicoVista con controlador");
            vista.dispose();
            MedicoVista mv = new MedicoVista();
            MedicoModelo mm = new MedicoModelo();
            new MedicoControlador(mm, mv);
        });

        // Botón Especialidades
        this.vista.getBtnEspecialidad().addActionListener(e -> {
            System.out.println("Abriendo EspecialidadVista con controlador");
            vista.setVisible(false);
            try {
                Connection conexion = ConexionBD.obtenerConexion();
                EspecialidadVista especialidadVista = new EspecialidadVista(conexion, vista);
                especialidadVista.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void iniciarVista() {
        vista.setVisible(true);
    }
}
