package com.mycompany.clinicamedica.modelo;

public class Sesion {
    private static String usuarioActual;
    private static int secretarioIDActual = -1;

    public static void iniciarSesion(String usuario, int secretarioID) {
        usuarioActual = usuario;
        secretarioIDActual = secretarioID;
    }

    public static String getUsuarioActual() {
        return usuarioActual;
    }

    public static int getSecretarioIDActual() {
        return secretarioIDActual;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
        secretarioIDActual = -1;
    }
}
