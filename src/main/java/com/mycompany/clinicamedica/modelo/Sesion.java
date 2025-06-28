package com.mycompany.clinicamedica.modelo;

public class Sesion {
    private static String usuarioActual;
    private static int secretarioIDActual = -1;
    private static int medicoIDActual = -1;

    public static void iniciarSesionSecretario(String usuario, int secretarioID) {
        usuarioActual = usuario;
        secretarioIDActual = secretarioID;
        medicoIDActual = -1;
    }

    public static void iniciarSesionMedico(String usuario, int medicoID) {
        usuarioActual = usuario;
        medicoIDActual = medicoID;
        secretarioIDActual = -1;
    }

    public static String getUsuarioActual() {
        return usuarioActual;
    }

    public static int getSecretarioIDActual() {
        return secretarioIDActual;
    }

    public static int getMedicoIDActual() {
        return medicoIDActual;
    }

    public static boolean esSesionMedico() {
        return medicoIDActual != -1;
    }

    public static boolean esSesionSecretario() {
        return secretarioIDActual != -1;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
        secretarioIDActual = -1;
        medicoIDActual = -1;
    }
}
