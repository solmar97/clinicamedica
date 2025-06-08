package com.mycompany.clinicamedica.vista;

import com.mycompany.clinicamedica.modelo.Especialidad;
import javax.swing.*;
import java.awt.*;

public class DetalleMedicoVista extends JFrame {
    private JTextField campoNombre, campoApellido, campoFechaNac, campoTelefono, campoGenero, campoEmail;
    private JTextField campoDNI, campoMatricula, campoDia, campoHorario, campoUsuario, campoContrasena;
    private JComboBox<Especialidad> campoEspecialidad;
    private JButton btnGuardarCambios, btnEliminar, btnCancelar;

    public DetalleMedicoVista() {
        setTitle("Detalle del Médico");
        setSize(400, 650);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(15, 2, 5, 5)); 

        add(new JLabel("DNI:")); campoDNI = new JTextField(); campoDNI.setEditable(true); add(campoDNI);
        add(new JLabel("Nombre:")); campoNombre = new JTextField(); add(campoNombre);
        add(new JLabel("Apellido:")); campoApellido = new JTextField(); add(campoApellido);
        add(new JLabel("Fecha Nac (DD-MM-YYYY):")); campoFechaNac = new JTextField(); add(campoFechaNac);
        add(new JLabel("Teléfono:")); campoTelefono = new JTextField(); add(campoTelefono);
        add(new JLabel("Género:")); campoGenero = new JTextField(); add(campoGenero);
        add(new JLabel("Email:")); campoEmail = new JTextField(); add(campoEmail);

        add(new JLabel("Especialidad:"));
        campoEspecialidad = new JComboBox<>();
        add(campoEspecialidad);

        add(new JLabel("Matrícula:")); campoMatricula = new JTextField(); add(campoMatricula);
        add(new JLabel("Día Laboral:")); campoDia = new JTextField(); add(campoDia);
        add(new JLabel("Horario Laboral:")); campoHorario = new JTextField(); add(campoHorario);
        add(new JLabel("Usuario:")); campoUsuario = new JTextField(); add(campoUsuario);
        add(new JLabel("Contraseña:")); campoContrasena = new JTextField(); add(campoContrasena);

        btnGuardarCambios = new JButton("Guardar Cambios");
        btnEliminar = new JButton("Eliminar Médico");
        btnCancelar = new JButton("Cancelar");

        add(btnGuardarCambios); add(btnEliminar); add(new JLabel()); add(btnCancelar);
    }

    public DetalleMedicoVista(String tituloVentana) {
    this(); // Llama al constructor por defecto
    setTitle(tituloVentana); // Cambia el título de la ventana
}
    // Getters para todos los campos y botones
    public JTextField getCampoDNI() { return campoDNI; }
    public JTextField getCampoNombre() { return campoNombre; }
    public JTextField getCampoApellido() { return campoApellido; }
    public JTextField getCampoFechaNac() { return campoFechaNac; }
    public JTextField getCampoTelefono() { return campoTelefono; }
    public JTextField getCampoGenero() { return campoGenero; }
    public JTextField getCampoEmail() { return campoEmail; }
    public JComboBox<Especialidad> getCampoEspecialidad() { return campoEspecialidad; }
    public JTextField getCampoMatricula() { return campoMatricula; }
    public JTextField getCampoDia() { return campoDia; }
    public JTextField getCampoHorario() { return campoHorario; }
    public JTextField getCampoUsuario() { return campoUsuario; }
    public JTextField getCampoContrasena() { return campoContrasena; }

    public JButton getBtnGuardarCambios() { return btnGuardarCambios; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnCancelar() { return btnCancelar; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
