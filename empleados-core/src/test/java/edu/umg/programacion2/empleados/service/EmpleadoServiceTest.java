package edu.umg.programacion2.empleados.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
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

    private Empleado crearEmpleadoValido(
            String nombre,
            String departamento) {

        return new Empleado(
                null,
                nombre,
                departamento,
                6500.00,
                LocalDate.of(2024, 3, 15),
                true);
    }

    @Test
    void debeCrearEmpleado() {
        Empleado creado = service.crear(
                crearEmpleadoValido(
                        "Ana López",
                        "Sistemas"));

        assertNotNull(creado.getId());
        assertEquals("Ana López", creado.getNombre());
        assertEquals("Sistemas",
                creado.getDepartamento());
        assertEquals(1, service.listarTodos().size());
    }

    @Test
    void debeBuscarEmpleadoPorId() {
        Empleado empleado = service.crear(
                crearEmpleadoValido(
                        "Carlos Pérez",
                        "Contabilidad"));

        Optional<Empleado> resultado =
                service.buscarPorId(empleado.getId());

        assertTrue(resultado.isPresent());
        assertEquals(
                "Carlos Pérez",
                resultado.get().getNombre());
    }

    @Test
    void debeActualizarEmpleado() {
        Empleado empleado = service.crear(
                crearEmpleadoValido(
                        "María Gómez",
                        "Ventas"));

        empleado.setDepartamento(
                "Recursos Humanos");
        empleado.setSalario(7000.00);

        boolean actualizado =
                service.actualizar(empleado);

        assertTrue(actualizado);

        Empleado encontrado = service
                .buscarPorId(empleado.getId())
                .orElseThrow();

        assertEquals(
                "Recursos Humanos",
                encontrado.getDepartamento());
        assertEquals(
                7000.00,
                encontrado.getSalario());
    }

    @Test
    void debeEliminarEmpleado() {
        Empleado empleado = service.crear(
                crearEmpleadoValido(
                        "Luis Morales",
                        "Soporte"));

        boolean eliminado =
                service.eliminarPorId(
                        empleado.getId());

        assertTrue(eliminado);
        assertTrue(service.listarTodos().isEmpty());
    }

    @Test
    void debeListarEmpleados() {
        service.crear(crearEmpleadoValido(
                "Ana", "Sistemas"));

        service.crear(crearEmpleadoValido(
                "Luis", "Ventas"));

        List<Empleado> empleados =
                service.listarTodos();

        assertEquals(2, empleados.size());
    }

    @Test
    void debeRechazarNombreVacio() {
        Empleado empleado = crearEmpleadoValido(
                " ", "Sistemas");

        assertThrows(
                IllegalArgumentException.class,
                () -> service.crear(empleado));
    }

    @Test
    void debeRechazarDepartamentoVacio() {
        Empleado empleado = crearEmpleadoValido(
                "Ana", " ");

        assertThrows(
                IllegalArgumentException.class,
                () -> service.crear(empleado));
    }

    @Test
    void debeRechazarSalarioCero() {
        Empleado empleado = crearEmpleadoValido(
                "Ana", "Sistemas");

        empleado.setSalario(0);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.crear(empleado));
    }

    @Test
    void debeRechazarFechaFutura() {
        Empleado empleado = crearEmpleadoValido(
                "Ana", "Sistemas");

        empleado.setFechaContratacion(
                LocalDate.now().plusDays(1));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.crear(empleado));
    }
}