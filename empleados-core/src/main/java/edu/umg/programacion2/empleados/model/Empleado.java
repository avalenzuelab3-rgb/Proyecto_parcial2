package edu.umg.programacion2.empleados.model;

import java.time.LocalDate;

public class Empleado {

    private Long id;
    private String nombre;
    private String departamento;
    private double salario;
    private LocalDate fechaContratacion;
    private String tipoContrato;
    private boolean activo;

    public Empleado() {
        this.tipoContrato = "Temporal";
    }

    // Constructor anterior para mantener compatibilidad
    public Empleado(
            Long id,
            String nombre,
            String departamento,
            double salario,
            LocalDate fechaContratacion,
            boolean activo) {

        this(
                id,
                nombre,
                departamento,
                salario,
                fechaContratacion,
                "Temporal",
                activo
        );
    }

    // Constructor actualizado
    public Empleado(
            Long id,
            String nombre,
            String departamento,
            double salario,
            LocalDate fechaContratacion,
            String tipoContrato,
            boolean activo) {

        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.salario = salario;
        this.fechaContratacion = fechaContratacion;
        this.tipoContrato = tipoContrato;
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

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}