package com.mycompany.clinicamedica.controlador;

import com.mycompany.clinicamedica.conexion.ConexionBD;
import com.mycompany.clinicamedica.modelo.Especialidad;
import com.mycompany.clinicamedica.modelo.Horario;
import com.mycompany.clinicamedica.modelo.Medico;
import com.mycompany.clinicamedica.modelo.MedicoModelo;
import com.mycompany.clinicamedica.vista.DetalleMedicoVista;
import com.mycompany.clinicamedica.vista.MedicoVista;
import com.mycompany.clinicamedica.vista.SecretarioVista;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MedicoControlador {
    private MedicoModelo modelo;
    private MedicoVista vista;

    public MedicoControlador(MedicoModelo modelo, MedicoVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        cargarDatosIniciales();

        vista.getEspecialidadCombo().addActionListener(e -> filtrarMedicos());
        vista.getFiltroApellidoField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                filtrarMedicos();
            }
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

        vista.getBtnRegistrar().addActionListener(e -> abrirFormularioRegistro());

        vista.getBtnVerDetalles().addActionListener(e -> {
            int filaSeleccionada = vista.getTablaHorarios().getSelectedRow();
            if (filaSeleccionada >= 0) {
                int dni = Integer.parseInt(vista.getTablaModel().getValueAt(filaSeleccionada, 3).toString());
                Medico medico = modelo.obtenerMedicoPorDNI(dni);
                if (medico != null) {
                    abrirDetalleMedico(medico);
                } else {
                    JOptionPane.showMessageDialog(vista, "No se encontró el médico con DNI " + dni);
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Seleccione un médico.");
            }
        });

        vista.setVisible(true);
    }

    private void cargarDatosIniciales() {
        cargarEspecialidades();
        filtrarMedicos();
    }

    private void cargarEspecialidades() {
        vista.getEspecialidadCombo().removeAllItems();
        // Opción para "Todas las especialidades"
        Especialidad especialidadTodas = new Especialidad();
        especialidadTodas.setEspecialidadID(-1); 
        especialidadTodas.setNombre("Todas");    // Texto que se muestra en el combo
        vista.getEspecialidadCombo().addItem(especialidadTodas);

        List<Especialidad> especialidades = modelo.obtenerEspecialidades();
        for (Especialidad esp : especialidades) {
            vista.getEspecialidadCombo().addItem(esp);
        }

        vista.getEspecialidadCombo().setSelectedIndex(0);
    }

    private void filtrarMedicos() {
        Especialidad especialidadSeleccionada = (Especialidad) vista.getEspecialidadCombo().getSelectedItem();
        String filtroApellido = vista.getFiltroApellidoField().getText().trim().toLowerCase();

        List<Medico> medicos;

        if (especialidadSeleccionada == null || especialidadSeleccionada.getEspecialidadID() == -1) {
            medicos = modelo.obtenerTodosLosMedicos();
        } else {
            int especialidadID = especialidadSeleccionada.getEspecialidadID();
            medicos = modelo.obtenerMedicosPorEspecialidad(especialidadID);
        }

        DefaultTableModel tabla = vista.getTablaModel();
        tabla.setRowCount(0);  // Limpia la tabla

        for (Medico m : medicos) {
            String apellido = m.getApellido();
            if (apellido != null && apellido.toLowerCase().contains(filtroApellido)) {
                for (Horario h : m.getHorarios()) {
                    tabla.addRow(new Object[]{
                        m.getEspecialidad() != null ? m.getEspecialidad().getNombre() : "",  
                        m.getNombre(),
                        m.getApellido(),
                        m.getDni(),
                        h.getDia(),
                        h.getHoraInicio() + " - " + h.getHoraFin()
                    });
                }
            }
        }
    }

    private void abrirFormularioRegistro() {
        DetalleMedicoVista detalleVista = new DetalleMedicoVista("Nuevo Médico");

        // Limpiar campos
        detalleVista.getCampoDNI().setText("");
        detalleVista.getCampoNombre().setText("");
        detalleVista.getCampoApellido().setText("");
        detalleVista.getCampoFechaNac().setText("");
        detalleVista.getCampoTelefono().setText("");
        detalleVista.getCampoGenero().setText("");
        detalleVista.getCampoEmail().setText("");
        detalleVista.getCampoMatricula().setText("");
        detalleVista.getCampoDia().setText("");
        detalleVista.getCampoHorario().setText("");
        detalleVista.getCampoUsuario().setText("");
        detalleVista.getCampoContrasena().setText("");
        detalleVista.getCampoEspecialidad().setSelectedItem(null);

        detalleVista.getBtnGuardarCambios().setText("Registrar Médico");
        detalleVista.getBtnEliminar().setVisible(false);

        // Cargar especialidades en el combo
        detalleVista.getCampoEspecialidad().removeAllItems();
        List<Especialidad> especialidades = modelo.obtenerEspecialidades();
        for (Especialidad esp : especialidades) {
            detalleVista.getCampoEspecialidad().addItem(esp);
        }

        detalleVista.getBtnGuardarCambios().addActionListener(ev -> {
            try {
                Medico nuevoMedico = new Medico();
                nuevoMedico.setDni(detalleVista.getCampoDNI().getText());
                nuevoMedico.setNombre(detalleVista.getCampoNombre().getText());
                nuevoMedico.setApellido(detalleVista.getCampoApellido().getText());
                nuevoMedico.setFechaNacimiento(detalleVista.getCampoFechaNac().getText());
                nuevoMedico.setTelefono(detalleVista.getCampoTelefono().getText());
                nuevoMedico.setGenero(detalleVista.getCampoGenero().getText());
                nuevoMedico.setEmail(detalleVista.getCampoEmail().getText());
                nuevoMedico.setMatricula(detalleVista.getCampoMatricula().getText());
                nuevoMedico.setDiaLaboral(detalleVista.getCampoDia().getText());
                nuevoMedico.setHorarioLaboral(detalleVista.getCampoHorario().getText());
                nuevoMedico.setUsuario(detalleVista.getCampoUsuario().getText());
                nuevoMedico.setContrasenia(detalleVista.getCampoContrasena().getText());

                Especialidad espSeleccionada = (Especialidad) detalleVista.getCampoEspecialidad().getSelectedItem();
                nuevoMedico.setEspecialidad(espSeleccionada);

                boolean agregado = modelo.agregarMedico(nuevoMedico);
                if (agregado) {
                    detalleVista.mostrarMensaje("Médico registrado correctamente.");
                    detalleVista.dispose();
                    filtrarMedicos();
                } else {
                    detalleVista.mostrarMensaje("Error: DNI duplicado o datos inválidos.");
                }
            } catch (NumberFormatException ex) {
                detalleVista.mostrarMensaje("DNI debe ser un número válido.");
            }
        });

        detalleVista.getBtnCancelar().addActionListener(ev -> detalleVista.dispose());

        detalleVista.setVisible(true);
    }

    private void abrirDetalleMedico(Medico medico) {
        DetalleMedicoVista detalleVista = new DetalleMedicoVista();

        detalleVista.getCampoDNI().setText(String.valueOf(medico.getDni()));
        detalleVista.getCampoNombre().setText(medico.getNombre());
        detalleVista.getCampoApellido().setText(medico.getApellido());
        detalleVista.getCampoFechaNac().setText(medico.getFechaNacimiento());
        detalleVista.getCampoTelefono().setText(medico.getTelefono());
        detalleVista.getCampoGenero().setText(medico.getGenero());
        detalleVista.getCampoEmail().setText(medico.getEmail());
        detalleVista.getCampoMatricula().setText(medico.getMatricula());
        detalleVista.getCampoDia().setText(medico.getDiaLaboral());
        detalleVista.getCampoHorario().setText(medico.getHorarioLaboral());
        detalleVista.getCampoUsuario().setText(medico.getUsuario());
        detalleVista.getCampoContrasena().setText(medico.getContrasenia());

        detalleVista.getCampoEspecialidad().removeAllItems();
        List<Especialidad> especialidades = modelo.obtenerEspecialidades();
        for (Especialidad esp : especialidades) {
            detalleVista.getCampoEspecialidad().addItem(esp);  // agregamos el objeto completo
        }

        detalleVista.getCampoEspecialidad().setSelectedItem(medico.getEspecialidad());

        detalleVista.getBtnGuardarCambios().addActionListener(e -> {
            medico.setNombre(detalleVista.getCampoNombre().getText());
            medico.setApellido(detalleVista.getCampoApellido().getText());
            medico.setFechaNacimiento(detalleVista.getCampoFechaNac().getText());
            medico.setTelefono(detalleVista.getCampoTelefono().getText());
            medico.setGenero(detalleVista.getCampoGenero().getText());
            medico.setEmail(detalleVista.getCampoEmail().getText());
            medico.setMatricula(detalleVista.getCampoMatricula().getText());
            medico.setDiaLaboral(detalleVista.getCampoDia().getText());
            medico.setHorarioLaboral(detalleVista.getCampoHorario().getText());
            medico.setUsuario(detalleVista.getCampoUsuario().getText());
            medico.setContrasenia(detalleVista.getCampoContrasena().getText());

            Especialidad espSeleccionada = (Especialidad) detalleVista.getCampoEspecialidad().getSelectedItem();
            medico.setEspecialidad(espSeleccionada);

            boolean actualizado = modelo.actualizarMedico(medico);
            if (actualizado) {
                detalleVista.mostrarMensaje("Médico actualizado correctamente.");
                detalleVista.dispose();
                filtrarMedicos();
            } else {
                detalleVista.mostrarMensaje("Error al actualizar médico.");
            }
        });

        detalleVista.getBtnEliminar().addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(detalleVista, "¿Seguro que desea eliminar este médico?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = modelo.eliminarMedico(medico.getDni());
                if (eliminado) {
                    detalleVista.mostrarMensaje("Médico eliminado correctamente.");
                    detalleVista.dispose();
                    filtrarMedicos();
                } else {
                    detalleVista.mostrarMensaje("Error al eliminar médico.");
                }
            }
        });

        detalleVista.getBtnCancelar().addActionListener(e -> detalleVista.dispose());

        detalleVista.setVisible(true);
    }
}
