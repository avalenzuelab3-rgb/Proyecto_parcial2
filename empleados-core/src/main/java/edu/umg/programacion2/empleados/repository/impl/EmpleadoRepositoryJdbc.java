package edu.umg.programacion2.empleados.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.umg.programacion2.empleados.config.ConexionBD;
import edu.umg.programacion2.empleados.model.Empleado;
import edu.umg.programacion2.empleados.repository.EmpleadoRepository;
import edu.umg.programacion2.empleados.repository.RepositorioException;

public class EmpleadoRepositoryJdbc implements EmpleadoRepository {

    private final ConexionBD conexionBD;

    public EmpleadoRepositoryJdbc(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public Empleado crear(Empleado empleado) {

        String sql = "INSERT INTO empleados "
                + "(nombre, departamento, salario, fecha_contratacion, "
                + "tipo_contrato, activo) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setString(1, empleado.getNombre());
            sentencia.setString(2, empleado.getDepartamento());
            sentencia.setDouble(3, empleado.getSalario());
            sentencia.setDate(
                    4,
                    Date.valueOf(empleado.getFechaContratacion())
            );
            sentencia.setString(5, empleado.getTipoContrato());
            sentencia.setBoolean(6, empleado.isActivo());

            sentencia.executeUpdate();

            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    empleado.setId(claves.getLong(1));
                }
            }

            return empleado;

        } catch (SQLException e) {
            throw new RepositorioException(
                    "No se pudo crear el empleado", e);
        }
    }

    @Override
    public Optional<Empleado> buscarPorId(Long id) {

        String sql = "SELECT id, nombre, departamento, salario, "
                + "fecha_contratacion, tipo_contrato, activo "
                + "FROM empleados WHERE id = ?";

        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setLong(1, id);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(convertirEmpleado(resultado));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RepositorioException(
                    "No se pudo buscar el empleado", e);
        }
    }

    @Override
    public List<Empleado> listarTodos() {

        String sql = "SELECT id, nombre, departamento, salario, "
                + "fecha_contratacion, tipo_contrato, activo "
                + "FROM empleados ORDER BY id";

        List<Empleado> empleados = new ArrayList<>();

        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                empleados.add(convertirEmpleado(resultado));
            }

            return empleados;

        } catch (SQLException e) {
            throw new RepositorioException(
                    "No se pudieron listar los empleados", e);
        }
    }

    @Override
    public boolean actualizar(Empleado empleado) {

        String sql = "UPDATE empleados "
                + "SET nombre = ?, departamento = ?, salario = ?, "
                + "fecha_contratacion = ?, tipo_contrato = ?, activo = ? "
                + "WHERE id = ?";

        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, empleado.getNombre());
            sentencia.setString(2, empleado.getDepartamento());
            sentencia.setDouble(3, empleado.getSalario());
            sentencia.setDate(
                    4,
                    Date.valueOf(empleado.getFechaContratacion())
            );
            sentencia.setString(5, empleado.getTipoContrato());
            sentencia.setBoolean(6, empleado.isActivo());
            sentencia.setLong(7, empleado.getId());

            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RepositorioException(
                    "No se pudo actualizar el empleado", e);
        }
    }

    @Override
    public boolean eliminarPorId(Long id) {

        String sql = "DELETE FROM empleados WHERE id = ?";

        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setLong(1, id);
            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RepositorioException(
                    "No se pudo eliminar el empleado", e);
        }
    }

    private Empleado convertirEmpleado(ResultSet resultado)
            throws SQLException {

        Empleado empleado = new Empleado();

        empleado.setId(resultado.getLong("id"));
        empleado.setNombre(resultado.getString("nombre"));
        empleado.setDepartamento(resultado.getString("departamento"));
        empleado.setSalario(resultado.getDouble("salario"));
        empleado.setFechaContratacion(
                resultado.getDate("fecha_contratacion").toLocalDate()
        );
        empleado.setTipoContrato(
                resultado.getString("tipo_contrato")
        );
        empleado.setActivo(resultado.getBoolean("activo"));

        return empleado;
    }
}