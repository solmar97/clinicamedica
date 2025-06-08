package com.mycompany.clinicamedica.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DetallePacienteVista extends JFrame {
    private JTextField txtDni, txtNombre, txtApellido, txtFechaNacimiento, txtTelefono, txtGenero, txtEmail, txtObraSocial, txtNumeroAfiliado;
    private JButton btnGuardar, btnEliminar, btnCancelar;

    public DetallePacienteVista() {
        setTitle("Detalle Paciente");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(11, 2, 5, 5)); 

        add(new JLabel("DNI:"));
        txtDni = new JTextField();
        txtDni.setEditable(true);  
        add(txtDni);
        
        txtDni.addKeyListener(new KeyAdapter() {
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c)) {
            e.consume(); // Ignora cualquier tecla que no sea dígito
        }
    }
});


        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        add(txtApellido);

        add(new JLabel("Fecha Nacimiento (dd/MM/yyyy):"));
        txtFechaNacimiento = new JTextField();
        add(txtFechaNacimiento);

        add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        add(txtTelefono);

        add(new JLabel("Género:"));
        txtGenero = new JTextField();
        add(txtGenero);

        add(new JLabel("Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("Obra Social:"));
        txtObraSocial = new JTextField();
        add(txtObraSocial);

        add(new JLabel("Número Afiliado:"));
        txtNumeroAfiliado = new JTextField();
        add(txtNumeroAfiliado);

        btnGuardar = new JButton("Guardar Cambios");
        btnEliminar = new JButton("Eliminar Paciente");
        btnCancelar = new JButton("Cancelar");

        add(btnGuardar);
        add(btnEliminar);
        add(new JLabel());
        add(btnCancelar);
    }
    
        public DetallePacienteVista(String tituloVentana) {
    this(); // Llama al constructor por defecto
    setTitle(tituloVentana); // Cambia el título de la ventana
}

    // Getters y setters para los campos

    public String getDni() { return txtDni.getText(); }
    public void setDni(String dni) { txtDni.setText(dni); }

    public String getNombre() { return txtNombre.getText(); }
    public void setNombre(String nombre) { txtNombre.setText(nombre); }

    public String getApellido() { return txtApellido.getText(); }
    public void setApellido(String apellido) { txtApellido.setText(apellido); }

    public String getFechaNacimiento() { return txtFechaNacimiento.getText(); }
    public void setFechaNacimiento(String fecha) { txtFechaNacimiento.setText(fecha); }

    public String getTelefono() { return txtTelefono.getText(); }
    public void setTelefono(String telefono) { txtTelefono.setText(telefono); }

    public String getGenero() { return txtGenero.getText(); }
    public void setGenero(String genero) { txtGenero.setText(genero); }

    public String getEmail() { return txtEmail.getText(); }
    public void setEmail(String email) { txtEmail.setText(email); }

    public String getObraSocial() { return txtObraSocial.getText(); }
    public void setObraSocial(String obraSocial) { txtObraSocial.setText(obraSocial); }

    public String getNumeroAfiliado() { return txtNumeroAfiliado.getText(); }
    public void setNumeroAfiliado(String numeroAfiliado) { txtNumeroAfiliado.setText(numeroAfiliado); }

    // Getters para botones

    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnCancelar() { return btnCancelar; }

public LocalDate getFechaNacimientoComoLocalDate() {
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(txtFechaNacimiento.getText(), formatter);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy.");
        return null;  // No lanzamos excepción, solo devolvemos null
    }
}

    public void mostrarMensaje(String mensaje) {
      JOptionPane.showMessageDialog(this, mensaje);
    }

}
