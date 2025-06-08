package com.mycompany.clinicamedica.modelo;

import java.time.LocalDate;

public class Paciente {
    private int id;
    private String dni;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String genero;
    private String email;
    private String obraSocial;
    private String numeroAfiliado;

    // Constructor completo CON id
    public Paciente(int id, String dni, String nombre, String apellido, LocalDate fechaNacimiento,
                    String telefono, String genero, String email,
                    String obraSocial, String numeroAfiliado) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.genero = genero;
        this.email = email;
        this.obraSocial = obraSocial;
        this.numeroAfiliado = numeroAfiliado;
    }

    // Constructor completo SIN id
    public Paciente(String dni, String nombre, String apellido, LocalDate fechaNacimiento,
                    String telefono, String genero, String email,
                    String obraSocial, String numeroAfiliado) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.genero = genero;
        this.email = email;
        this.obraSocial = obraSocial;
        this.numeroAfiliado = numeroAfiliado;
    }

    // Constructor vacío
    public Paciente() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFechaNacimiento() {
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

    public String getObraSocial() {
        return obraSocial;
    }

    public String getNumeroAfiliado() {
        return numeroAfiliado;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
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

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public void setNumeroAfiliado(String numeroAfiliado) {
        this.numeroAfiliado = numeroAfiliado;
    }
}
