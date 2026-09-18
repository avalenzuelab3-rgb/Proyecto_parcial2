package edu.umg.programacion2.empleados.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.umg.programacion2.empleados.model.Empleado;
import edu.umg.programacion2.empleados.repository.impl.EmpleadoRepositoryMemoria;

class EmpleadoServiceTest {

    private EmpleadoService service;

    @BeforeEach
    void configurar() {
        service = new EmpleadoService(
                new EmpleadoRepositoryMemoria());
    }

    @Test
    void debeCrearEmpleado() {
        Empleado empleado = new Empleado(
                null,
                "Ana López",
                "Programadora",
                6500.00,
                true);

        Empleado creado = service.crear(empleado);

        assertNotNull(creado.getId());
        assertEquals("Ana López", creado.getNombre());
        assertEquals(1, service.listarTodos().size());
    }

    @Test
    void debeBuscarEmpleadoPorId() {
        Empleado empleado = service.crear(new Empleado(
                null,
                "Carlos Pérez",
                "Contador",
                5500.00,
                true));

        Optional<Empleado> resultado =
                service.buscarPorId(empleado.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Carlos Pérez",
                resultado.get().getNombre());
    }

    @Test
    void debeActualizarEmpleado() {
        Empleado empleado = service.crear(new Empleado(
                null,
                "María Gómez",
                "Secretaria",
                4500.00,
                true));

        empleado.setPuesto("Administradora");
        empleado.setSalario(6000.00);

        boolean actualizado = service.actualizar(empleado);

        assertTrue(actualizado);
        assertEquals("Administradora",
                service.buscarPorId(empleado.getId())
                       .orElseThrow()
                       .getPuesto());
    }

    @Test
    void debeEliminarEmpleado() {
        Empleado empleado = service.crear(new Empleado(
                null,
                "Luis Morales",
                "Técnico",
                5000.00,
                true));

        boolean eliminado =
                service.eliminarPorId(empleado.getId());

        assertTrue(eliminado);
        assertTrue(service.listarTodos().isEmpty());
    }

    @Test
    void debeListarEmpleados() {
        service.crear(new Empleado(
                null, "Ana", "Programadora", 6500.00, true));

        service.crear(new Empleado(
                null, "Luis", "Técnico", 5000.00, true));

        List<Empleado> empleados = service.listarTodos();

        assertEquals(2, empleados.size());
    }

    @Test
    void debeRechazarNombreVacio() {
        Empleado empleado = new Empleado(
                null, " ", "Programadora", 6500.00, true);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.crear(empleado));
    }

    @Test
    void debeRechazarSalarioNegativo() {
        Empleado empleado = new Empleado(
                null, "Ana", "Programadora", -100.00, true);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.crear(empleado));
    }
}