package com.mycompany.clinicamedica.vista;

import com.mycompany.clinicamedica.controlador.EspecialidadControlador;
import com.mycompany.clinicamedica.modelo.Especialidad;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class EspecialidadVista extends JFrame {
    private EspecialidadControlador controlador;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private SecretarioVista menuPrincipal; // referencia al menú principal

    public EspecialidadVista(Connection conexion, SecretarioVista menuPrincipal) {
        System.out.println("Creando ventana EspecialidadVista"); 
        this.controlador = new EspecialidadControlador(conexion);
        this.menuPrincipal = menuPrincipal; // guardás la referencia al menú principal

        setTitle("Gestión de Especialidades");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Descripción"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        cargarEspecialidades();

        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnAtras = new JButton("Atrás");

        JPanel botones = new JPanel();
        botones.add(btnAgregar);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        botones.add(btnAtras);

        add(scroll, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        // Acción agregar
        btnAgregar.addActionListener(e -> {
            JTextField nombre = new JTextField();
            JTextField descripcion = new JTextField();
            Object[] campos = {
                "Nombre:", nombre,
                "Descripción:", descripcion
            };
            int opcion = JOptionPane.showConfirmDialog(this, campos, "Nueva Especialidad", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                Especialidad nueva = new Especialidad();
                nueva.setNombre(nombre.getText());
                nueva.setDescripcion(descripcion.getText());
                if (controlador.agregarEspecialidad(nueva)) {
                    cargarEspecialidades();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar");
                }
            }
        });

        // Acción modificar
        btnModificar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
                String nombreActual = tabla.getValueAt(fila, 1).toString();
                String descActual = tabla.getValueAt(fila, 2).toString();

                JTextField nombre = new JTextField(nombreActual);
                JTextField descripcion = new JTextField(descActual);
                Object[] campos = {
                    "Nombre:", nombre,
                    "Descripción:", descripcion
                };
                int opcion = JOptionPane.showConfirmDialog(this, campos, "Modificar Especialidad", JOptionPane.OK_CANCEL_OPTION);
                if (opcion == JOptionPane.OK_OPTION) {
                    Especialidad modif = new Especialidad();
                    modif.setEspecialidadID(id);
                    modif.setNombre(nombre.getText());
                    modif.setDescripcion(descripcion.getText());
                    if (controlador.modificarEspecialidad(modif)) {
                        cargarEspecialidades();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al modificar");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná una fila para modificar");
            }
        });

        // Acción eliminar
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
                int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar especialidad seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (controlador.eliminarEspecialidad(id)) {
                        cargarEspecialidades();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar (puede estar en uso)");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná una fila para eliminar");
            }
        });

        // Botón Atrás para volver al menú principal
        btnAtras.addActionListener(e -> {
            if (menuPrincipal != null) {
                menuPrincipal.mostrar(); // usamos el método mostrar() para restaurar la ventana
            }
            this.dispose();
        });
    }

    private void cargarEspecialidades() {
        modeloTabla.setRowCount(0); // Limpia la tabla
        List<Especialidad> lista = controlador.listarEspecialidades();
        for (Especialidad e : lista) {
            modeloTabla.addRow(new Object[]{e.getEspecialidadID(), e.getNombre(), e.getDescripcion()});
        }
    }
}
