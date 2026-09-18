package edu.umg.programacion2.empleados.repository.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import edu.umg.programacion2.empleados.model.Empleado;
import edu.umg.programacion2.empleados.repository.EmpleadoRepository;

public class EmpleadoRepositoryMemoria implements EmpleadoRepository {

    private final Map<Long, Empleado> empleados = new LinkedHashMap<>();
    private long siguienteId = 1L;

    @Override
    public Empleado crear(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado no puede ser nulo");
        }

        empleado.setId(siguienteId++);
        empleados.put(empleado.getId(), empleado);
        return empleado;
    }

    @Override
    public Optional<Empleado> buscarPorId(Long id) {
        return Optional.ofNullable(empleados.get(id));
    }

    @Override
    public List<Empleado> listarTodos() {
        return new ArrayList<>(empleados.values());
    }

    @Override
    public boolean actualizar(Empleado empleado) {
        if (empleado == null || empleado.getId() == null
                || !empleados.containsKey(empleado.getId())) {
            return false;
        }

        empleados.put(empleado.getId(), empleado);
        return true;
    }

    @Override
    public boolean eliminarPorId(Long id) {
        return empleados.remove(id) != null;
    }
}