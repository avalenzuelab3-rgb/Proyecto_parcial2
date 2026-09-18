package edu.umg.programacion2.empleados.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private final String url;
    private final String usuario;
    private final String contrasena;

    public ConexionBD(String url, String usuario, String contrasena) {
        this.url = url;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(url, usuario, contrasena);
    }
}