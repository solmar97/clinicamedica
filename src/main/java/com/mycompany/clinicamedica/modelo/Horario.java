package com.mycompany.clinicamedica.modelo;

import java.time.LocalTime;

public class Horario {
    private String dia; // ej: "Lunes", "Martes", etc.
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public Horario(String dia, LocalTime horaInicio, LocalTime horaFin) {
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    // Método que devuelve el horario en formato legible
    public String getHorario() {
        return dia + ": " + horaInicio.toString() + " - " + horaFin.toString();
    }
}
