package edu.umg.programacion2.empleados.model;

import java.time.LocalDate;

public class Empleado {

    private Long id;
    private String nombre;
    private String departamento;
    private double salario;
    private LocalDate fechaContratacion;
    private boolean activo;

    public Empleado() {
    }

    public Empleado(
            Long id,
            String nombre,
            String departamento,
            double salario,
            LocalDate fechaContratacion,
            boolean activo) {

        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.salario = salario;
        this.fechaContratacion = fechaContratacion;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(
            LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}