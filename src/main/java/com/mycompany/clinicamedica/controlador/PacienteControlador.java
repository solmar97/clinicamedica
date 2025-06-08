package com.mycompany.clinicamedica.controlador;

import com.mycompany.clinicamedica.conexion.ConexionBD;
import com.mycompany.clinicamedica.modelo.Paciente;
import com.mycompany.clinicamedica.modelo.PacienteModelo;
import com.mycompany.clinicamedica.vista.DetallePacienteVista;
import com.mycompany.clinicamedica.vista.PacienteVista;
import com.mycompany.clinicamedica.vista.SecretarioVista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PacienteControlador {

    private PacienteModelo modelo;
    private PacienteVista vista;

    public PacienteControlador() {
        try {
            Connection conexion = ConexionBD.obtenerConexion();
            this.modelo = new PacienteModelo(conexion);
            this.vista = new PacienteVista();

            inicializar();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos.");
        }
    }
    
    public PacienteControlador(PacienteVista vista, PacienteModelo modelo) {
    this.vista = vista;
    this.modelo = modelo;
    inicializar();
}

    private void inicializar() {
        cargarPacientes();

        // Buscar por DNI mientras escribe
        vista.getTxtBuscarDNI().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarPacientesPorDni();
            }
        });

        vista.getBtnRegistrar().addActionListener(e -> abrirFormularioRegistro());

        vista.getBtnVerDetalles().addActionListener(e -> {
            int filaSeleccionada = vista.getTablaPacientes().getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(vista, "Seleccione un paciente.");
                return;
            }
            abrirDetallePaciente(filaSeleccionada);
        });

        vista.getBtnAtras().addActionListener(e -> {
            vista.dispose();
            try {
                Connection conexion = ConexionBD.obtenerConexion();
                SecretarioVista sv = new SecretarioVista(conexion);
                SecretarioControlador sc = new SecretarioControlador(sv);
                sc.iniciarVista();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Error al volver a la vista de secretario.");
            }
        });

        vista.setVisible(true);
    }

    private void cargarPacientes() {
        List<Paciente> pacientes = modelo.obtenerTodos();
        DefaultTableModel tableModel = (DefaultTableModel) vista.getTablaPacientes().getModel();
        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Paciente p : pacientes) {
            tableModel.addRow(new Object[]{
                p.getDni(),
                p.getNombre(),
                p.getApellido(),
                p.getFechaNacimiento().format(formatter),
                p.getObraSocial(),
                p.getNumeroAfiliado(),
                p.getGenero(),
                p.getTelefono(),
                p.getEmail()
            });
        }
    }

private void filtrarPacientesPorDni() {
    String dniParcial = vista.getTxtBuscarDNI().getText().trim();
    DefaultTableModel tableModel = (DefaultTableModel) vista.getTablaPacientes().getModel();
    tableModel.setRowCount(0);

    if (dniParcial.isEmpty()) {
        cargarPacientes();
        return;
    }

    List<Paciente> pacientes = modelo.buscarPorDniParcial(dniParcial);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    for (Paciente p : pacientes) {
        tableModel.addRow(new Object[]{
            p.getDni(),
            p.getNombre(),
            p.getApellido(),
            p.getFechaNacimiento().format(formatter),
            p.getObraSocial(),
            p.getNumeroAfiliado(),
            p.getGenero(),
            p.getTelefono(),
            p.getEmail()
        });
    }
}

    private void abrirFormularioRegistro() {
        DetallePacienteVista detalleVista = new DetallePacienteVista("Nuevo Paciente");

        // Limpiar campos
        detalleVista.setDni("");
        detalleVista.setNombre("");
        detalleVista.setApellido("");
        detalleVista.setFechaNacimiento("");
        detalleVista.setObraSocial("");
        detalleVista.setNumeroAfiliado("");
        detalleVista.setGenero("");
        detalleVista.setTelefono("");
        detalleVista.setEmail("");

        detalleVista.getBtnGuardar().setText("Registrar Paciente");
        detalleVista.getBtnEliminar().setVisible(false);

        detalleVista.getBtnGuardar().addActionListener(ev -> {
             LocalDate fechaNacimiento = detalleVista.getFechaNacimientoComoLocalDate();
    if (fechaNacimiento == null) {
        // No continua si la es  fecha inválida
        return;
    }
            try {
                Paciente nuevoPaciente = new Paciente();
                nuevoPaciente.setDni(detalleVista.getDni());
                nuevoPaciente.setNombre(detalleVista.getNombre());
                nuevoPaciente.setApellido(detalleVista.getApellido());
                nuevoPaciente.setFechaNacimiento(detalleVista.getFechaNacimientoComoLocalDate());
                nuevoPaciente.setObraSocial(detalleVista.getObraSocial());
                nuevoPaciente.setNumeroAfiliado(detalleVista.getNumeroAfiliado());
                nuevoPaciente.setGenero(detalleVista.getGenero());
                nuevoPaciente.setTelefono(detalleVista.getTelefono());
                nuevoPaciente.setEmail(detalleVista.getEmail());

                boolean agregado = modelo.agregarPaciente(nuevoPaciente);
                if (agregado) {
                    detalleVista.mostrarMensaje("Paciente registrado correctamente.");
                    detalleVista.dispose();
                    cargarPacientes();
                } else {
                    detalleVista.mostrarMensaje("Error al registrar paciente (posible DNI duplicado).");
                }
            } catch (Exception ex) {
                detalleVista.mostrarMensaje("Error: " + ex.getMessage());
            }
        });

        detalleVista.getBtnCancelar().addActionListener(ev -> detalleVista.dispose());

        detalleVista.setVisible(true);
    }

private void abrirDetallePaciente(int fila) {
    DefaultTableModel modeloTabla = (DefaultTableModel) vista.getTablaPacientes().getModel();

    String dniOriginal = (String) modeloTabla.getValueAt(fila, 0);  // Guardamos el DNI original
    Paciente p = modelo.buscarPorDni(dniOriginal);
    if (p == null) {
        JOptionPane.showMessageDialog(vista, "Paciente no encontrado.");
        return;
    }

    DetallePacienteVista detalleVista = new DetallePacienteVista();

    detalleVista.setDni(p.getDni());
    detalleVista.setNombre(p.getNombre());
    detalleVista.setApellido(p.getApellido());
    detalleVista.setFechaNacimiento(p.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    detalleVista.setObraSocial(p.getObraSocial());
    detalleVista.setNumeroAfiliado(p.getNumeroAfiliado());
    detalleVista.setGenero(p.getGenero());
    detalleVista.setTelefono(p.getTelefono());
    detalleVista.setEmail(p.getEmail());

    detalleVista.getBtnGuardar().setText("Guardar Cambios");
    detalleVista.getBtnEliminar().setVisible(true);

    detalleVista.getBtnGuardar().addActionListener(ev -> {

        LocalDate fechaNacimiento = detalleVista.getFechaNacimientoComoLocalDate();
        if (fechaNacimiento == null) {
            return; 
        }

        try {
            p.setDni(detalleVista.getDni());  
            p.setNombre(detalleVista.getNombre());
            p.setApellido(detalleVista.getApellido());
            p.setFechaNacimiento(fechaNacimiento);
            p.setObraSocial(detalleVista.getObraSocial());
            p.setNumeroAfiliado(detalleVista.getNumeroAfiliado());
            p.setGenero(detalleVista.getGenero());
            p.setTelefono(detalleVista.getTelefono());
            p.setEmail(detalleVista.getEmail());

            boolean actualizado = modelo.actualizarPaciente(p, dniOriginal);  
            if (actualizado) {
                detalleVista.mostrarMensaje("Paciente actualizado correctamente.");
                detalleVista.dispose();
                cargarPacientes();
            } else {
                detalleVista.mostrarMensaje("Error al actualizar paciente.");
            }
        } catch (Exception ex) {
            detalleVista.mostrarMensaje("Error: " + ex.getMessage());
        }
    });

    detalleVista.getBtnEliminar().addActionListener(ev -> {
        int confirm = JOptionPane.showConfirmDialog(detalleVista, "¿Seguro que desea eliminar este paciente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean eliminado = modelo.eliminarPaciente(p.getDni());
            if (eliminado) {
                detalleVista.mostrarMensaje("Paciente eliminado correctamente.");
                detalleVista.dispose();
                cargarPacientes();
            } else {
                detalleVista.mostrarMensaje("Error al eliminar paciente.");
            }
        }
    });

    detalleVista.getBtnCancelar().addActionListener(ev -> detalleVista.dispose());

    detalleVista.setVisible(true);
}

}
