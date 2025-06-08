package com.mycompany.clinicamedica.vista;

import java.text.SimpleDateFormat;
import com.mycompany.clinicamedica.modelo.Turno;
import com.mycompany.clinicamedica.modelo.TurnoModelo;
import com.mycompany.clinicamedica.modelo.PacienteModelo;
import com.mycompany.clinicamedica.modelo.Paciente; 
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DetalleTurnoVista extends JFrame {
    private TurnoModelo turnoModelo;
    private PacienteModelo pacienteModelo;  
    private JLabel lblPaciente, lblTelefono, lblObraSocial, lblAfiliado;
    private JTextField txtDniPaciente;
    private JButton btnBuscarPaciente;  
    private JComboBox<String> comboEspecialidades;  
    private JComboBox<String> comboMedicos;
    private JDateChooser dateChooser;
    private JComboBox<String> comboHora;
    private JButton btnGuardar, btnCerrar;
    private DiasLaboralesEvaluator evaluadorActual;

 // Constructor para turno existente (detalle)
public DetalleTurnoVista(Turno turno, TurnoModelo turnoModelo, PacienteModelo pacienteModelo) {
    this.turnoModelo = turnoModelo;
    this.pacienteModelo = pacienteModelo;
    setTitle("Detalle del Turno");
    setSize(400, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    initComponentes();

    lblPaciente.setText(turno.getNombrePaciente() + " " + turno.getApellidoPaciente());
    txtDniPaciente.setText(String.valueOf(turno.getDniPaciente()));
    txtDniPaciente.setEditable(false);

    lblTelefono.setText(turno.getTelefonoPaciente());
    lblObraSocial.setText(turno.getObraSocialPaciente());
    lblAfiliado.setText(turno.getNumeroAfiliadoPaciente());

    Date fechaDate = Date.from(turno.getFechaProgramada().atStartOfDay(ZoneId.systemDefault()).toInstant());
    dateChooser.setDate(fechaDate);

    // Cargar especialidad y médico
    String especialidad = turno.getEspecialidad();
    String nombreMedico = turno.getNombreMedicoCompleto();

    comboEspecialidades.setSelectedItem(especialidad);

    // Necesitamos esperar a que se carguen los médicos al cambiar la especialidad
    SwingUtilities.invokeLater(() -> {
        comboMedicos.setSelectedItem(nombreMedico);

        int medicoID = turnoModelo.obtenerIdMedicoPorNombreCompleto(nombreMedico);
        List<LocalTime> horasDisponibles = turnoModelo.obtenerHorasDisponibles(medicoID, turno.getFechaProgramada());

        cargarHoras(horasDisponibles);

        String horaTurno = turno.getHoraProgramada().toString().substring(0, 5);
        comboHora.setSelectedItem(horaTurno);
    });
}

    // Constructor para nuevo turno
    public DetalleTurnoVista(TurnoModelo turnoModelo, PacienteModelo pacienteModelo) {
        this.turnoModelo = turnoModelo;
        this.pacienteModelo = pacienteModelo;
        setTitle("Nuevo Turno");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponentes();

        lblPaciente.setText("");
        txtDniPaciente.setText("");
        txtDniPaciente.setEditable(true);

        lblTelefono.setText("");
        lblObraSocial.setText("");
        lblAfiliado.setText("");

        dateChooser.setDate(null);
    }

    private void initComponentes() {
        JPanel panel = new JPanel(new GridLayout(0, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // DNI paciente + botón buscar
        panel.add(new JLabel("DNI Paciente:"));
        txtDniPaciente = new JTextField();
        panel.add(txtDniPaciente);
        btnBuscarPaciente = new JButton("Buscar");
        panel.add(btnBuscarPaciente);

        // Labels para paciente
        lblPaciente = new JLabel("");
        panel.add(new JLabel("Paciente:"));
        panel.add(lblPaciente);
        panel.add(new JLabel(""));

        lblTelefono = new JLabel("");
        panel.add(new JLabel("Teléfono:"));
        panel.add(lblTelefono);
        panel.add(new JLabel(""));

        lblObraSocial = new JLabel("");
        panel.add(new JLabel("Obra Social:"));
        panel.add(lblObraSocial);
        panel.add(new JLabel(""));

        lblAfiliado = new JLabel("");
        panel.add(new JLabel("Afiliado N°:"));
        panel.add(lblAfiliado);
        panel.add(new JLabel(""));

        // Combo especialidades
        panel.add(new JLabel("Especialidad:"));
        comboEspecialidades = new JComboBox<>();
        panel.add(comboEspecialidades);
        panel.add(new JLabel(""));

        // Combo médicos
        panel.add(new JLabel("Médico:"));
        comboMedicos = new JComboBox<>();
        panel.add(comboMedicos);
        panel.add(new JLabel(""));

        // Fecha
        panel.add(new JLabel("Fecha:"));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        panel.add(dateChooser);
        panel.add(new JLabel(""));

        // Hora
        panel.add(new JLabel("Hora (HH:MM):"));
        comboHora = new JComboBox<>();
        panel.add(comboHora);
        panel.add(new JLabel(""));

        // Botones: Guardar, espacio vacío, Cancelar
        btnGuardar = new JButton("Guardar");
        btnCerrar = new JButton("Cancelar");

        panel.add(btnGuardar);

        JPanel espacio = new JPanel();
        espacio.setOpaque(false);
        espacio.setPreferredSize(new Dimension(20, 20));
        panel.add(espacio);

        panel.add(btnCerrar);

        add(panel);

        // Acción botón Buscar paciente
        btnBuscarPaciente.addActionListener(e -> {
            String dniTexto = txtDniPaciente.getText().trim();
            if (dniTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresá un DNI para buscar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Paciente paciente = pacienteModelo.obtenerPacientePorDNI(dniTexto);
            if (paciente != null) {
                lblPaciente.setText(paciente.getNombre() + " " + paciente.getApellido());
                lblTelefono.setText(paciente.getTelefono());
                lblObraSocial.setText(paciente.getObraSocial());
                lblAfiliado.setText(paciente.getNumeroAfiliado());
            } else {
                JOptionPane.showMessageDialog(this, "Paciente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                lblPaciente.setText("");
                lblTelefono.setText("");
                lblObraSocial.setText("");
                lblAfiliado.setText("");
            }
        });

        // Cargar especialidades en comboEspecialidades
        List<String> especialidades = turnoModelo.obtenerEspecialidades();
        comboEspecialidades.removeAllItems();
        for (String esp : especialidades) {
            comboEspecialidades.addItem(esp);
        }

        // Listener para filtrar médicos por especialidad
        comboEspecialidades.addActionListener(e -> {
            String especialidadSeleccionada = (String) comboEspecialidades.getSelectedItem();
            if (especialidadSeleccionada != null) {
                List<String> medicosFiltrados = turnoModelo.obtenerMedicosPorEspecialidad(especialidadSeleccionada);
                comboMedicos.removeAllItems();
                for (String medico : medicosFiltrados) {
                    comboMedicos.addItem(medico);
                }
            }
        });

        // Al iniciar, seleccionamos la primera especialidad para cargar los médicos
        if (comboEspecialidades.getItemCount() > 0) {
            comboEspecialidades.setSelectedIndex(0);
        }

        // Listener para actualizar días laborales al cambiar médico
        comboMedicos.addActionListener(e -> {
            String medico = (String) comboMedicos.getSelectedItem();
            if (medico != null) {
                int medicoID = turnoModelo.obtenerIdMedicoPorNombreCompleto(medico);
                Set<DayOfWeek> diasLaborales = new HashSet<>(turnoModelo.obtenerDiasLaboralesMedico(medicoID));

                if (evaluadorActual != null) {
                    dateChooser.getJCalendar().getDayChooser().removeDateEvaluator(evaluadorActual);
                }

                evaluadorActual = new DiasLaboralesEvaluator(diasLaborales);
                dateChooser.getJCalendar().getDayChooser().addDateEvaluator(evaluadorActual);

                dateChooser.repaint();
            }
        });
    }

    // Métodos para cargar médicos, horas, obtener datos, etc.
    public void cargarMedicos(List<String> medicos) {
        comboMedicos.removeAllItems();
        for (String medico : medicos) {
            comboMedicos.addItem(medico);
        }
    }

    public String getMedicoSeleccionado() {
        return (String) comboMedicos.getSelectedItem();
    }

    public Date getFechaDate() {
        return dateChooser.getDate();
    }

    public void setFechaDate(Date date) {
        dateChooser.setDate(date);
    }

    public String getHora() {
        return (String) comboHora.getSelectedItem();
    }

    public String getDniPaciente() {
        return txtDniPaciente.getText().trim();
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnCerrar() {
        return btnCerrar;
    }

    public JComboBox<String> getComboMedicos() {
        return comboMedicos;
    }

    public void cargarHoras(List<LocalTime> horasDisponibles) {
        comboHora.removeAllItems();
        for (LocalTime hora : horasDisponibles) {
            comboHora.addItem(hora.toString().substring(0, 5)); // "HH:mm"
        }
    }

    public JDateChooser getDateChooser() {
        return dateChooser;
    }

    public String getFecha() {
        Date fecha = dateChooser.getDate();
        if (fecha == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fecha);
    }
}
