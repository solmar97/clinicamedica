package com.mycompany.clinicamedica.modelo;

import java.time.LocalDate;

public class Pago {
    private int pagoID;
    private int turnoID;
    private int pacienteID;
    private double monto;
    private LocalDate fechaPago;
    private boolean descuentoAplicado;

    // Getters y setters
    public int getPagoID() { return pagoID; }
    public void setPagoID(int pagoID) { this.pagoID = pagoID; }

    public int getTurnoID() { return turnoID; }
    public void setTurnoID(int turnoID) { this.turnoID = turnoID; }

    public int getPacienteID() { return pacienteID; }
    public void setPacienteID(int pacienteID) { this.pacienteID = pacienteID; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }

    public boolean isDescuentoAplicado() { return descuentoAplicado; }
    public void setDescuentoAplicado(boolean descuentoAplicado) { this.descuentoAplicado = descuentoAplicado; }
}
