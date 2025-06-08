package com.mycompany.clinicamedica.vista;

import com.toedter.calendar.IDateEvaluator;

import java.awt.*;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DiasLaboralesEvaluator implements IDateEvaluator {

    private final Set<Integer> diasLaborales;

    public DiasLaboralesEvaluator(Set<DayOfWeek> diasPermitidos) {
        diasLaborales = new HashSet<>();
        for (DayOfWeek d : diasPermitidos) {
            diasLaborales.add(d.getValue()); // 1 = Lunes, ..., 7 = Domingo
        }
    }

    @Override
    public boolean isSpecial(Date date) {
        return false;
    }

    @Override
    public String getSpecialTooltip() {
        return null;
    }

    @Override
    public Color getSpecialForegroundColor() {
        return null;
    }
    
    @Override
    public Color getSpecialBackroundColor() {
        return null;
    }

    public Color getSpecialBackgroundColor() {
        return null;
    }

    @Override
    public boolean isInvalid(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dia = cal.get(Calendar.DAY_OF_WEEK); // DOMINGO = 1, ..., SÁBADO = 7

        // Convertir a formato ISO (Lunes = 1, Domingo = 7)
        int diaIso = (dia == Calendar.SUNDAY) ? 7 : dia - 1;
        return !diasLaborales.contains(diaIso);
    }

    @Override
    public String getInvalidTooltip() {
        return "Día no laborable";
    }

    @Override
    public Color getInvalidForegroundColor() {
        return Color.LIGHT_GRAY;
    }

    @Override
    public Color getInvalidBackroundColor() {
        return Color.WHITE;
    }

    public Color getInvalidBackgroundColor() {
        return Color.WHITE;
    }
}
