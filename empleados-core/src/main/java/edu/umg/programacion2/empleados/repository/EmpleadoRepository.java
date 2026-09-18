package edu.umg.programacion2.empleados.repository;

import java.util.List;
import java.util.Optional;

import edu.umg.programacion2.empleados.model.Empleado;

public interface EmpleadoRepository {

    Empleado crear(Empleado empleado);

    Optional<Empleado> buscarPorId(Long id);

    List<Empleado> listarTodos();

    boolean actualizar(Empleado empleado);

    boolean eliminarPorId(Long id);
}