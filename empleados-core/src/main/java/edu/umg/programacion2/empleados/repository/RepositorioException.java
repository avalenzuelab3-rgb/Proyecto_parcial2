package edu.umg.programacion2.empleados.repository;

public class RepositorioException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RepositorioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}