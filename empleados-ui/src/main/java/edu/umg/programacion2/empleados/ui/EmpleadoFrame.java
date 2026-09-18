package edu.umg.programacion2.empleados.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import edu.umg.programacion2.empleados.model.Empleado;
import edu.umg.programacion2.empleados.service.EmpleadoService;

public class EmpleadoFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final EmpleadoService servicio;
    private final EmpleadoTableModel modeloTabla = new EmpleadoTableModel();

    private final JTextField campoId = new JTextField();
    private final JTextField campoNombre = new JTextField();
    private final JTextField campoDepartamento = new JTextField();
    private final JTextField campoSalario = new JTextField();
    private final JTextField campoFecha = new JTextField();
    private final JCheckBox campoActivo = new JCheckBox("Empleado activo", true);

    private final JTable tabla = new JTable(modeloTabla);

    public EmpleadoFrame(EmpleadoService servicio) {
        this.servicio = servicio;

        configurarVentana();
        crearInterfaz();
        configurarEventos();
        cargarEmpleados();
    }

    private void configurarVentana() {
        setTitle("Gestión de empleados");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void crearInterfaz() {
        campoId.setEditable(false);
        campoFecha.setToolTipText("Formato: AAAA-MM-DD");

        JPanel formulario = new JPanel(new GridLayout(6, 2, 8, 8));
        formulario.setBorder(BorderFactory.createTitledBorder("Datos del empleado"));

        formulario.add(new JLabel("ID:"));
        formulario.add(campoId);

        formulario.add(new JLabel("Nombre:"));
        formulario.add(campoNombre);

        formulario.add(new JLabel("Departamento:"));
        formulario.add(campoDepartamento);

        formulario.add(new JLabel("Salario:"));
        formulario.add(campoSalario);

        formulario.add(new JLabel("Fecha de contratación (AAAA-MM-DD):"));
        formulario.add(campoFecha);

        formulario.add(new JLabel("Estado:"));
        formulario.add(campoActivo);

        JButton botonNuevo = new JButton("Nuevo");
        JButton botonGuardar = new JButton("Guardar");
        JButton botonActualizar = new JButton("Actualizar");
        JButton botonEliminar = new JButton("Eliminar");
        JButton botonRecargar = new JButton("Recargar");

        botonNuevo.setActionCommand("NUEVO");
        botonGuardar.setActionCommand("GUARDAR");
        botonActualizar.setActionCommand("ACTUALIZAR");
        botonEliminar.setActionCommand("ELIMINAR");
        botonRecargar.setActionCommand("RECARGAR");

        botonNuevo.addActionListener(e -> limpiarFormulario());
        botonGuardar.addActionListener(e -> guardarEmpleado());
        botonActualizar.addActionListener(e -> actualizarEmpleado());
        botonEliminar.addActionListener(e -> eliminarEmpleado());
        botonRecargar.addActionListener(e -> cargarEmpleados());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botones.add(botonNuevo);
        botones.add(botonGuardar);
        botones.add(botonActualizar);
        botones.add(botonEliminar);
        botones.add(botonRecargar);

        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setAutoCreateRowSorter(true);

        JPanel panelSuperior = new JPanel(new BorderLayout(8, 8));
        panelSuperior.add(formulario, BorderLayout.CENTER);
        panelSuperior.add(botones, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void configurarEventos() {
        tabla.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                int filaModelo = tabla.convertRowIndexToModel(tabla.getSelectedRow());
                mostrarEmpleado(modeloTabla.getEmpleado(filaModelo));
            }
        });
    }

    private void cargarEmpleados() {
        try {
            modeloTabla.setEmpleados(servicio.listarTodos());
        } catch (RuntimeException e) {
            mostrarError("No se pudieron cargar los empleados.", e);
        }
    }

    private void guardarEmpleado() {
        try {
            Empleado empleado = leerFormulario();
            empleado.setId(null);

            servicio.crear(empleado);

            JOptionPane.showMessageDialog(
                this,
                "Empleado guardado correctamente.",
                "Operación exitosa",
                JOptionPane.INFORMATION_MESSAGE
            );

            limpiarFormulario();
            cargarEmpleados();

        } catch (IllegalArgumentException e) {
            mostrarAdvertencia(e.getMessage());
        } catch (RuntimeException e) {
            mostrarError("No se pudo guardar el empleado.", e);
        }
    }

    private void actualizarEmpleado() {
        if (campoId.getText().isBlank()) {
            mostrarAdvertencia("Seleccione un empleado de la tabla.");
            return;
        }

        try {
            Empleado empleado = leerFormulario();
            empleado.setId(Long.valueOf(campoId.getText()));

            servicio.actualizar(empleado);

            JOptionPane.showMessageDialog(
                this,
                "Empleado actualizado correctamente.",
                "Operación exitosa",
                JOptionPane.INFORMATION_MESSAGE
            );

            limpiarFormulario();
            cargarEmpleados();

        } catch (IllegalArgumentException e) {
            mostrarAdvertencia(e.getMessage());
        } catch (RuntimeException e) {
            mostrarError("No se pudo actualizar el empleado.", e);
        }
    }

    private void eliminarEmpleado() {
        if (campoId.getText().isBlank()) {
            mostrarAdvertencia("Seleccione un empleado de la tabla.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar este empleado?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Long id = Long.valueOf(campoId.getText());
            servicio.eliminarPorId(id);

            JOptionPane.showMessageDialog(
                this,
                "Empleado eliminado correctamente.",
                "Operación exitosa",
                JOptionPane.INFORMATION_MESSAGE
            );

            limpiarFormulario();
            cargarEmpleados();

        } catch (RuntimeException e) {
            mostrarError("No se pudo eliminar el empleado.", e);
        }
    }

    private Empleado leerFormulario() {
        String nombre = campoNombre.getText().trim();
        String departamento = campoDepartamento.getText().trim();
        String textoSalario = campoSalario.getText().trim();
        String textoFecha = campoFecha.getText().trim();

        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (departamento.isEmpty()) {
            throw new IllegalArgumentException("El departamento es obligatorio.");
        }

        if (textoSalario.isEmpty()) {
            throw new IllegalArgumentException("El salario es obligatorio.");
        }

        if (textoFecha.isEmpty()) {
            throw new IllegalArgumentException(
                "La fecha de contratación es obligatoria."
            );
        }

        double salario;

        try {
            salario = Double.parseDouble(textoSalario);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "El salario debe ser un número válido."
            );
        }

        if (salario <= 0) {
            throw new IllegalArgumentException(
                "El salario debe ser mayor que cero."
            );
        }

        LocalDate fecha;

        try {
            fecha = LocalDate.parse(textoFecha);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "La fecha debe tener el formato AAAA-MM-DD."
            );
        }

        Empleado empleado = new Empleado();
        empleado.setNombre(nombre);
        empleado.setDepartamento(departamento);
        empleado.setSalario(salario);
        empleado.setFechaContratacion(fecha);
        empleado.setActivo(campoActivo.isSelected());

        return empleado;
    }

    private void mostrarEmpleado(Empleado empleado) {
        campoId.setText(String.valueOf(empleado.getId()));
        campoNombre.setText(empleado.getNombre());
        campoDepartamento.setText(empleado.getDepartamento());
        campoSalario.setText(String.valueOf(empleado.getSalario()));
        campoFecha.setText(String.valueOf(empleado.getFechaContratacion()));
        campoActivo.setSelected(empleado.isActivo());
    }

    private void limpiarFormulario() {
        campoId.setText("");
        campoNombre.setText("");
        campoDepartamento.setText("");
        campoSalario.setText("");
        campoFecha.setText("");
        campoActivo.setSelected(true);
        tabla.clearSelection();
        campoNombre.requestFocus();
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Datos incorrectos",
            JOptionPane.WARNING_MESSAGE
        );
    }

    private void mostrarError(String mensaje, RuntimeException e) {
        JOptionPane.showMessageDialog(
            this,
            mensaje + "\n" + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}