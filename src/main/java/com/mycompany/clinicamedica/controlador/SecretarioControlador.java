/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clinicamedica.controlador;

import com.mycompany.clinicamedica.modelo.MedicoModelo;
import com.mycompany.clinicamedica.modelo.UsuarioModelo;
import com.mycompany.clinicamedica.vista.EspecialidadVista;
import com.mycompany.clinicamedica.vista.LoginVista;
import com.mycompany.clinicamedica.vista.MedicoVista;
import com.mycompany.clinicamedica.vista.SecretarioVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mycompany.clinicamedica.conexion.ConexionBD;
import com.mycompany.clinicamedica.modelo.PacienteModelo;
import com.mycompany.clinicamedica.vista.PacienteVista;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class SecretarioControlador {
    private SecretarioVista vista;

    public SecretarioControlador(SecretarioVista vista) {
        this.vista = vista;
        this.vista.setVisible(true);
        this.vista.getBtnCerrarSesion().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
                LoginVista loginVista = new LoginVista();
        UsuarioModelo modelo = new UsuarioModelo();
        new LoginControlador(loginVista, modelo);
            }
        });

        this.vista.getBtnTurnos().addActionListener(e -> System.out.println("Abrir gestión de turnos"));
        this.vista.getBtnPacientes().addActionListener(e -> System.out.println("Abrir gestión de pacientes"));
        this.vista.getBtnPagos().addActionListener(e -> System.out.println("Abrir gestión de pagos"));
        this.vista.getBtnMedicos().addActionListener(e -> System.out.println("Abrir gestión de médicos"));
        
        this.vista.getBtnMedicos().addActionListener(e -> {
            System.out.println("Abriendo MedicoVista con controlador");
            vista.dispose();  // cierra SecretarioVista
            MedicoVista mv = new MedicoVista();  // crea la vista de Médicos
            MedicoModelo mm = new MedicoModelo(); // crea el modelo de Médicos
            new MedicoControlador(mm, mv); // crea el controlador de Médicos
            // El controlador de Medico hace visible la vista
        });
        
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


this.vista.getBtnPacientes().addActionListener(e -> {
    try {
        PacienteVista pacienteVista = new PacienteVista();
        PacienteModelo modelo = new PacienteModelo(ConexionBD.obtenerConexion());
        new PacienteControlador(pacienteVista, modelo);
        pacienteVista.setVisible(true);
    } catch (SQLException ex) {
        ex.printStackTrace();
        

// Mostrar mensaje de error al usuario
        JOptionPane.showMessageDialog(
            vista,
            "Error al conectar con la base de datos. Por favor, intente más tarde.",
            "Error de conexión",
            JOptionPane.ERROR_MESSAGE
        );
    }
});

this.vista.getBtnTurnos().addActionListener(e -> {
    System.out.println("Abrir gestión de turnos");
    try {
        Connection conexion = ConexionBD.obtenerConexion();
        new TurnoControlador(conexion, this.vista); // pasamos la vista actual
        // No necesitás crear otra instancia ni llamar a dispose() acá
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

    }

    public void iniciarVista() {
        vista.setVisible(true);
    }
     
}