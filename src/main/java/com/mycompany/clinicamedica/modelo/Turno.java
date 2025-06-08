package com.mycompany.clinicamedica.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Turno {
    private int turnoID;
    private int pacienteID;
    private int medicoID;
    private int secretarioID;
    private Integer pagoID; // Puede ser null
    private LocalDate fechaProgramada;
    private LocalTime horaProgramada;

    private int dniPaciente;  
    private String nombrePaciente;
    private String apellidoPaciente;
    private String telefonoPaciente;
    private String obraSocialPaciente;
    private String numeroAfiliadoPaciente;

    private String nombreMedico;
    private String apellidoMedico;
    private String especialidad;

    public Turno() {}

    // Getters y setters
    public int getTurnoID() {
        return turnoID;
    }

    public void setTurnoID(int turnoID) {
        this.turnoID = turnoID;
    }

    public int getPacienteID() {
        return pacienteID;
    }

    public void setPacienteID(int pacienteID) {
        this.pacienteID = pacienteID;
    }

    public int getMedicoID() {
        return medicoID;
    }

    public void setMedicoID(int medicoID) {
        this.medicoID = medicoID;
    }

    public int getSecretarioID() {
        return secretarioID;
    }

    public void setSecretarioID(int secretarioID) {
        this.secretarioID = secretarioID;
    }

    public Integer getPagoID() {
        return pagoID;
    }

    public void setPagoID(Integer pagoID) {
        this.pagoID = pagoID;
    }

    public LocalDate getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public LocalTime getHoraProgramada() {
        return horaProgramada;
    }

    public void setHoraProgramada(LocalTime horaProgramada) {
        this.horaProgramada = horaProgramada;
    }

    public int getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(int dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public String getTelefonoPaciente() {
        return telefonoPaciente;
    }

    public void setTelefonoPaciente(String telefonoPaciente) {
        this.telefonoPaciente = telefonoPaciente;
    }

    public String getObraSocialPaciente() {
        return obraSocialPaciente;
    }

    public void setObraSocialPaciente(String obraSocialPaciente) {
        this.obraSocialPaciente = obraSocialPaciente;
    }

    public String getNumeroAfiliadoPaciente() {
        return numeroAfiliadoPaciente;
    }

    public void setNumeroAfiliadoPaciente(String numeroAfiliadoPaciente) {
        this.numeroAfiliadoPaciente = numeroAfiliadoPaciente;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getApellidoMedico() {
        return apellidoMedico;
    }

    public void setApellidoMedico(String apellidoMedico) {
        this.apellidoMedico = apellidoMedico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }


   public String getNombreMedicoCompleto() {
    return this.nombreMedico + " " + this.apellidoMedico;
}


}
