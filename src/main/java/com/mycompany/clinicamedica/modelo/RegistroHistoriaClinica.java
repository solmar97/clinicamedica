package com.mycompany.clinicamedica.modelo;

import java.time.LocalDate;

public class RegistroHistoriaClinica {
    private LocalDate fecha;
    private String descripcion;
    private int medicoID;
    private String nombreMedico;
    private String apellidoMedico;
    private String especialidad;
    private boolean atendido; // Nuevo campo

    // Getters y Setters
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getMedicoID() { return medicoID; }
    public void setMedicoID(int medicoID) { this.medicoID = medicoID; }

    public String getNombreMedico() { return nombreMedico; }
    public void setNombreMedico(String nombreMedico) { this.nombreMedico = nombreMedico; }

    public String getApellidoMedico() { return apellidoMedico; }
    public void setApellidoMedico(String apellidoMedico) { this.apellidoMedico = apellidoMedico; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public boolean isAtendido() { return atendido; }
    public void setAtendido(boolean atendido) { this.atendido = atendido; }
    

}
