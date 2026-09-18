package edu.umg.programacion2.empleados.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import edu.umg.programacion2.empleados.model.Empleado;
import edu.umg.programacion2.empleados.repository.EmpleadoRepository;

public class EmpleadoService {

    private final EmpleadoRepository repository;

    public EmpleadoService(EmpleadoRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException(
                    "El repositorio no puede ser nulo");
        }

        this.repository = repository;
    }

    public Empleado crear(Empleado empleado) {
        validar(empleado);
        return repository.crear(empleado);
    }

    public Optional<Empleado> buscarPorId(Long id) {
        validarId(id);
        return repository.buscarPorId(id);
    }

    public List<Empleado> listarTodos() {
        return repository.listarTodos();
    }

    public boolean actualizar(Empleado empleado) {
        validar(empleado);

        if (empleado.getId() == null
                || empleado.getId() <= 0) {
            throw new IllegalArgumentException(
                    "El empleado debe tener un id valido");
        }

        return repository.actualizar(empleado);
    }

    public boolean eliminarPorId(Long id) {
        validarId(id);
        return repository.eliminarPorId(id);
    }

    private void validar(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException(
                    "El empleado no puede ser nulo");
        }

        if (empleado.getNombre() == null
                || empleado.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio");
        }

        if (empleado.getDepartamento() == null
                || empleado.getDepartamento().isBlank()) {
            throw new IllegalArgumentException(
                    "El departamento es obligatorio");
        }

        if (empleado.getSalario() <= 0) {
            throw new IllegalArgumentException(
                    "El salario debe ser mayor a cero");
        }

        if (empleado.getFechaContratacion() == null) {
            throw new IllegalArgumentException(
                    "La fecha de contratacion es obligatoria");
        }

        if (empleado.getFechaContratacion()
                .isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha de contratacion no puede ser futura");
        }
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "El id debe ser valido");
        }
    }
}