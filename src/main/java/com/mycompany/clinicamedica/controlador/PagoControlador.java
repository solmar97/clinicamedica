package com.mycompany.clinicamedica.controlador;

import com.mycompany.clinicamedica.modelo.*;
import com.mycompany.clinicamedica.vista.PagoVista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagoControlador {
    private PagoVista vista;
    private PagoModelo modelo;
    private List<Turno> turnosMostrados = new ArrayList<>(); // lista paralela a la tabla

    public PagoControlador(PagoVista vista, PagoModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        cargarTurnosDelDia();

        vista.getBtnBuscar().addActionListener(this::buscarPorDni);
        vista.getBtnPagar().addActionListener(this::realizarPago);
        vista.getBtnCerrar().addActionListener(e -> vista.dispose());
    }

    private void cargarTurnosDelDia() {
        List<Turno> turnos = modelo.listarTurnosPorFecha(LocalDate.now());
        cargarEnTabla(turnos);
    }

    private void cargarEnTabla(List<Turno> turnos) {
        DefaultTableModel tabla = vista.getModeloTabla();
        tabla.setRowCount(0);
        turnosMostrados.clear();

        for (Turno t : turnos) {
            String nombreCompletoPaciente = t.getNombrePaciente() + " " + t.getApellidoPaciente();
            String obraSocial = t.getObraSocialPaciente(); // ya viene del modelo ahora
            String nombreCompletoMedico = t.getNombreMedico() + " " + t.getApellidoMedico();

            tabla.addRow(new Object[]{
                t.getDniPaciente(),
                nombreCompletoPaciente,
                t.getFechaProgramada(),
                t.getHoraProgramada(),
                obraSocial,
                nombreCompletoMedico
            });

            turnosMostrados.add(t); // guardar para acceso directo
        }
    }

    private void buscarPorDni(ActionEvent e) {
        String dniTexto = vista.getTxtDniBusqueda().getText().trim();
        if (dniTexto.isEmpty()) {
            cargarTurnosDelDia();
            return;
        }

        try {
            int dni = Integer.parseInt(dniTexto);
            List<Turno> turnosFiltrados = modelo.listarTurnosPorFecha(LocalDate.now()).stream()
                .filter(t -> t.getDniPaciente() == dni)
                .toList();

            cargarEnTabla(turnosFiltrados);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "DNI inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

private void realizarPago(ActionEvent e) {
    int fila = vista.getTablaTurnos().getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(vista, "Seleccioná un turno para pagar.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    DefaultTableModel tabla = vista.getModeloTabla();
    int dni = (int) tabla.getValueAt(fila, 0);
    String obraSocial = (String) tabla.getValueAt(fila, 4);

    boolean aplicaDescuento = !obraSocial.toLowerCase().contains("particular");
    double montoBase = 25000.00; // Reemplazable por monto según especialidad luego
    double montoFinal = aplicaDescuento ? montoBase * 0.75 : montoBase;

 String mensaje = "DNI: " + dni +
                 "\nObra social: " + obraSocial +
                 "\nValor base de la consulta: $25000.00" +
                 (aplicaDescuento ? "\nDescuento aplicado: 25%" : "") +
                 "\nMonto final a pagar: $" + montoFinal +
                 "\n\n¿Deseás confirmar el pago?";

    int opcion = JOptionPane.showOptionDialog(
        vista,
        mensaje,
        "Confirmar Pago",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        new Object[]{"Sí", "No"},
        "Sí"
    );

    if (opcion != JOptionPane.YES_OPTION) return;

    // Extraer IDs correctamente (de otra fuente si hace falta)
    List<Turno> turnos = modelo.listarTurnosPorFecha(LocalDate.now());
    Turno turnoSeleccionado = turnos.stream()
        .filter(t -> t.getDniPaciente() == dni)
        .findFirst()
        .orElse(null);

    if (turnoSeleccionado == null) {
        JOptionPane.showMessageDialog(vista, "No se pudo encontrar el turno correspondiente.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    Pago pago = new Pago();
    pago.setTurnoID(turnoSeleccionado.getTurnoID());
    pago.setPacienteID(turnoSeleccionado.getPacienteID());
    pago.setFechaPago(LocalDate.now());
    pago.setDescuentoAplicado(aplicaDescuento);
    pago.setMonto(montoFinal);

    boolean registrado = modelo.agregarPago(pago);
if (registrado) {
    JOptionPane.showMessageDialog(
        vista,
        "✅ El pago fue registrado exitosamente.\n" +
        "💡 El turno ahora está disponible para que el médico lo visualice en su perfil.",
        "Pago confirmado",
        JOptionPane.INFORMATION_MESSAGE
    );
    cargarTurnosDelDia(); // actualizar tabla
} else {
    JOptionPane.showMessageDialog(
        vista,
        "❌ Error al registrar el pago.\nIntente nuevamente.",
        "Error",
        JOptionPane.ERROR_MESSAGE
    );
}

}
}
