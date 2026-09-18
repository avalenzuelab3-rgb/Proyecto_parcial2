package edu.umg.programacion2.empleados.service;

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

        if (empleado.getId() == null || empleado.getId() <= 0) {
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

        if (empleado.getPuesto() == null
                || empleado.getPuesto().isBlank()) {
            throw new IllegalArgumentException(
                    "El puesto es obligatorio");
        }

        if (empleado.getSalario() < 0) {
            throw new IllegalArgumentException(
                    "El salario no puede ser negativo");
        }
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "El id debe ser valido");
        }
    }
}