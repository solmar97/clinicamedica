package com.mycompany.clinicamedica.modelo;

import java.util.List;

public class Medico {
    private Especialidad especialidad;  // Ahora es un objeto Especialidad
    private String nombre;
    private String apellido;
    private String dni;
    private String fechaNacimiento;
    private String telefono;
    private String genero;
    private String email;
    private String matricula;
    private String diaLaboral;
    private String horarioLaboral;
    private String usuario;
    private String contrasenia;
    private int medicoID;
    private List<Horario> horarios;

    public Medico() {
        // Constructor vacío
    }

    // Constructor completo para facilitar la creación
    public Medico(int medicoID, String dni, String nombre, String apellido, String fechaNacimiento, String telefono,
                  String genero, String email, String matricula, String diaLaboral, String horarioLaboral,
                  String usuario, String contrasenia, Especialidad especialidad, List<Horario> horarios) {
        this.medicoID = medicoID;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.genero = genero;
        this.email = email;
        this.matricula = matricula;
        this.diaLaboral = diaLaboral;
        this.horarioLaboral = horarioLaboral;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.especialidad = especialidad;
        this.horarios = horarios;
    }

    // Getters
    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getGenero() {
        return genero;
    }

    public String getEmail() {
        return email;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getDiaLaboral() {
        return diaLaboral;
    }

    public String getHorarioLaboral() {
        return horarioLaboral;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public int getMedicoID() {
        return medicoID;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    // Setters
    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setDiaLaboral(String diaLaboral) {
        this.diaLaboral = diaLaboral;
    }

    public void setHorarioLaboral(String horarioLaboral) {
        this.horarioLaboral = horarioLaboral;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setMedicoID(int medicoID) {
        this.medicoID = medicoID;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }
}
