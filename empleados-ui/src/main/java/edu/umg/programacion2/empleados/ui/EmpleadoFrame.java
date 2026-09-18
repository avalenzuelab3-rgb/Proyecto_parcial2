package edu.umg.programacion2.empleados.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import edu.umg.programacion2.empleados.model.Empleado;
import edu.umg.programacion2.empleados.service.EmpleadoService;

public class EmpleadoFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color FONDO = new Color(241, 245, 249);
    private static final Color BLANCO = Color.WHITE;
    private static final Color TEXTO = new Color(15, 23, 42);
    private static final Color TEXTO_SECUNDARIO =
        new Color(100, 116, 139);
    private static final Color BORDE = new Color(203, 213, 225);

    private static final Color AZUL = new Color(37, 99, 235);
    private static final Color AZUL_OSCURO = new Color(30, 64, 175);
    private static final Color VERDE = new Color(22, 163, 74);
    private static final Color ROJO = new Color(220, 38, 38);
    private static final Color GRIS = new Color(71, 85, 105);

    private final EmpleadoService servicio;
    private final EmpleadoTableModel modeloTabla =
        new EmpleadoTableModel();

    private final JTextField campoId = crearCampoTexto();
    private final JTextField campoNombre = crearCampoTexto();
    private final JTextField campoDepartamento = crearCampoTexto();
    private final JTextField campoSalario = crearCampoTexto();
    private final JTextField campoFecha = crearCampoTexto();

    private final JCheckBox campoActivo =
        new JCheckBox("Empleado activo", true);

    private final JTable tabla = new JTable(modeloTabla);

    private final JLabel etiquetaEstado =
        new JLabel("Sistema listo");

    public EmpleadoFrame(EmpleadoService servicio) {
        this.servicio = servicio;

        configurarVentana();
        crearInterfaz();
        configurarEventos();
        cargarEmpleados();
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestión de Empleados");
        setSize(1180, 720);
        setMinimumSize(new Dimension(1000, 620));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(FONDO);
    }

    private void crearInterfaz() {
        setLayout(new BorderLayout());

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
        add(crearBarraEstado(), BorderLayout.SOUTH);
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new PanelDegradado();
        encabezado.setLayout(new BorderLayout());
        encabezado.setBorder(
            BorderFactory.createEmptyBorder(22, 30, 22, 30)
        );

        JLabel titulo = new JLabel("Gestión de empleados");
        titulo.setForeground(BLANCO);
        titulo.setFont(
            new Font("Segoe UI", Font.BOLD, 27)
        );

        JLabel subtitulo = new JLabel(
            "Administra la información del personal desde un solo lugar"
        );
        subtitulo.setForeground(new Color(219, 234, 254));
        subtitulo.setFont(
            new Font("Segoe UI", Font.PLAIN, 14)
        );

        JPanel panelTitulos = new JPanel(
            new BorderLayout(0, 5)
        );
        panelTitulos.setOpaque(false);
        panelTitulos.add(titulo, BorderLayout.NORTH);
        panelTitulos.add(subtitulo, BorderLayout.SOUTH);

        JLabel conexion = new JLabel("●  MYSQL CONECTADO");
        conexion.setOpaque(true);
        conexion.setBackground(new Color(30, 64, 175));
        conexion.setForeground(new Color(187, 247, 208));
        conexion.setFont(
            new Font("Segoe UI", Font.BOLD, 12)
        );
        conexion.setBorder(
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        );

        encabezado.add(panelTitulos, BorderLayout.WEST);
        encabezado.add(conexion, BorderLayout.EAST);

        return encabezado;
    }

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(
            new BorderLayout(20, 20)
        );

        contenido.setBackground(FONDO);
        contenido.setBorder(
            BorderFactory.createEmptyBorder(22, 25, 20, 25)
        );

        contenido.add(
            crearTarjetaFormulario(),
            BorderLayout.WEST
        );

        contenido.add(
            crearTarjetaTabla(),
            BorderLayout.CENTER
        );

        return contenido;
    }

    private JPanel crearTarjetaFormulario() {
        PanelRedondeado tarjeta =
            new PanelRedondeado(BLANCO, 22);

        tarjeta.setLayout(new BorderLayout(0, 18));
        tarjeta.setPreferredSize(new Dimension(355, 520));
        tarjeta.setBorder(
            BorderFactory.createEmptyBorder(22, 22, 22, 22)
        );

        JLabel titulo = new JLabel("Datos del empleado");
        titulo.setFont(
            new Font("Segoe UI", Font.BOLD, 20)
        );
        titulo.setForeground(TEXTO);

        JLabel descripcion = new JLabel(
            "Complete los campos requeridos."
        );
        descripcion.setFont(
            new Font("Segoe UI", Font.PLAIN, 12)
        );
        descripcion.setForeground(TEXTO_SECUNDARIO);

        JPanel encabezadoFormulario = new JPanel(
            new BorderLayout(0, 4)
        );
        encabezadoFormulario.setOpaque(false);
        encabezadoFormulario.add(
            titulo,
            BorderLayout.NORTH
        );
        encabezadoFormulario.add(
            descripcion,
            BorderLayout.SOUTH
        );

        JPanel formulario = new JPanel(
            new GridBagLayout()
        );
        formulario.setOpaque(false);

        campoId.setEditable(false);
        campoId.setBackground(new Color(241, 245, 249));

        campoFecha.setToolTipText(
            "Utilice el formato AAAA-MM-DD"
        );

        int fila = 0;

        agregarCampo(
            formulario,
            fila++,
            "ID",
            campoId
        );

        agregarCampo(
            formulario,
            fila++,
            "Nombre completo",
            campoNombre
        );

        agregarCampo(
            formulario,
            fila++,
            "Departamento",
            campoDepartamento
        );

        agregarCampo(
            formulario,
            fila++,
            "Salario",
            campoSalario
        );

        agregarCampo(
            formulario,
            fila++,
            "Fecha de contratación",
            campoFecha
        );

        campoActivo.setFont(
            new Font("Segoe UI", Font.PLAIN, 14)
        );
        campoActivo.setForeground(TEXTO);
        campoActivo.setBackground(BLANCO);
        campoActivo.setFocusPainted(false);
        campoActivo.setCursor(
            Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR
            )
        );

        GridBagConstraints gbcActivo =
            new GridBagConstraints();

        gbcActivo.gridx = 0;
        gbcActivo.gridy = fila * 2;
        gbcActivo.gridwidth = 2;
        gbcActivo.weightx = 1;
        gbcActivo.fill =
            GridBagConstraints.HORIZONTAL;
        gbcActivo.anchor =
            GridBagConstraints.WEST;
        gbcActivo.insets =
            new Insets(8, 0, 8, 0);

        formulario.add(campoActivo, gbcActivo);

        tarjeta.add(
            encabezadoFormulario,
            BorderLayout.NORTH
        );

        tarjeta.add(
            formulario,
            BorderLayout.CENTER
        );

        tarjeta.add(
            crearPanelBotones(),
            BorderLayout.SOUTH
        );

        return tarjeta;
    }

    private void agregarCampo(
        JPanel panel,
        int fila,
        String texto,
        JTextField campo
    ) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(
            new Font("Segoe UI", Font.BOLD, 13)
        );
        etiqueta.setForeground(TEXTO);

        GridBagConstraints gbcEtiqueta =
            new GridBagConstraints();

        gbcEtiqueta.gridx = 0;
        gbcEtiqueta.gridy = fila * 2;
        gbcEtiqueta.gridwidth = 2;
        gbcEtiqueta.anchor =
            GridBagConstraints.WEST;
        gbcEtiqueta.insets =
            new Insets(4, 0, 5, 0);

        panel.add(etiqueta, gbcEtiqueta);

        GridBagConstraints gbcCampo =
            new GridBagConstraints();

        gbcCampo.gridx = 0;
        gbcCampo.gridy = fila * 2 + 1;
        gbcCampo.gridwidth = 2;
        gbcCampo.weightx = 1;
        gbcCampo.fill =
            GridBagConstraints.HORIZONTAL;
        gbcCampo.insets =
            new Insets(0, 0, 9, 0);

        panel.add(campo, gbcCampo);
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(
            new GridBagLayout()
        );
        panel.setOpaque(false);

        BotonModerno botonGuardar =
            new BotonModerno(
                "Guardar",
                VERDE
            );

        BotonModerno botonActualizar =
            new BotonModerno(
                "Actualizar",
                AZUL
            );

        BotonModerno botonEliminar =
            new BotonModerno(
                "Eliminar",
                ROJO
            );

        BotonModerno botonLimpiar =
            new BotonModerno(
                "Limpiar",
                GRIS
            );

        botonGuardar.addActionListener(
            e -> guardarEmpleado()
        );

        botonActualizar.addActionListener(
            e -> actualizarEmpleado()
        );

        botonEliminar.addActionListener(
            e -> eliminarEmpleado()
        );

        botonLimpiar.addActionListener(
            e -> limpiarFormulario()
        );

        GridBagConstraints gbc =
            new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(4, 4, 4, 4);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(botonGuardar, gbc);

        gbc.gridx = 1;
        panel.add(botonActualizar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(botonEliminar, gbc);

        gbc.gridx = 1;
        panel.add(botonLimpiar, gbc);

        return panel;
    }

    private JPanel crearTarjetaTabla() {
        PanelRedondeado tarjeta =
            new PanelRedondeado(BLANCO, 22);

        tarjeta.setLayout(new BorderLayout(0, 16));
        tarjeta.setBorder(
            BorderFactory.createEmptyBorder(22, 22, 22, 22)
        );

        JLabel titulo = new JLabel(
            "Empleados registrados"
        );
        titulo.setFont(
            new Font("Segoe UI", Font.BOLD, 20)
        );
        titulo.setForeground(TEXTO);

        JLabel ayuda = new JLabel(
            "Selecciona una fila para editar o eliminar"
        );
        ayuda.setFont(
            new Font("Segoe UI", Font.PLAIN, 12)
        );
        ayuda.setForeground(TEXTO_SECUNDARIO);

        JPanel textos = new JPanel(
            new BorderLayout(0, 3)
        );
        textos.setOpaque(false);
        textos.add(titulo, BorderLayout.NORTH);
        textos.add(ayuda, BorderLayout.SOUTH);

        BotonModerno botonRecargar =
            new BotonModerno(
                "Actualizar lista",
                AZUL
            );

        botonRecargar.setPreferredSize(
            new Dimension(145, 38)
        );

        botonRecargar.addActionListener(
            e -> cargarEmpleados()
        );

        JPanel encabezado = new JPanel(
            new BorderLayout()
        );
        encabezado.setOpaque(false);
        encabezado.add(textos, BorderLayout.WEST);
        encabezado.add(
            botonRecargar,
            BorderLayout.EAST
        );

        configurarTabla();

        JScrollPane desplazamiento =
            new JScrollPane(tabla);

        desplazamiento.setBorder(
            BorderFactory.createLineBorder(BORDE)
        );

        desplazamiento
            .getViewport()
            .setBackground(BLANCO);

        tarjeta.add(encabezado, BorderLayout.NORTH);
        tarjeta.add(
            desplazamiento,
            BorderLayout.CENTER
        );

        return tarjeta;
    }

    private void configurarTabla() {
        tabla.setFont(
            new Font("Segoe UI", Font.PLAIN, 13)
        );
        tabla.setForeground(TEXTO);
        tabla.setBackground(BLANCO);
        tabla.setRowHeight(40);
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(
            new Color(226, 232, 240)
        );
        tabla.setSelectionBackground(
            new Color(219, 234, 254)
        );
        tabla.setSelectionForeground(TEXTO);
        tabla.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );
        tabla.setAutoCreateRowSorter(true);
        tabla.setFillsViewportHeight(true);

        JTableHeader encabezado =
            tabla.getTableHeader();

        encabezado.setPreferredSize(
            new Dimension(0, 44)
        );
        encabezado.setReorderingAllowed(false);

        DefaultTableCellRenderer renderEncabezado =
            new DefaultTableCellRenderer() {

                private static final long serialVersionUID = 1L;

                @Override
                public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column
                ) {
                    JLabel etiqueta = (JLabel)
                        super.getTableCellRendererComponent(
                            table,
                            value,
                            false,
                            false,
                            row,
                            column
                        );

                    etiqueta.setOpaque(true);
                    etiqueta.setBackground(
                        new Color(30, 41, 59)
                    );
                    etiqueta.setForeground(Color.WHITE);
                    etiqueta.setFont(
                        new Font(
                            "Segoe UI",
                            Font.BOLD,
                            13
                        )
                    );
                    etiqueta.setHorizontalAlignment(
                        SwingConstants.LEFT
                    );
                    etiqueta.setBorder(
                        BorderFactory.createEmptyBorder(
                            0,
                            11,
                            0,
                            8
                        )
                    );

                    return etiqueta;
                }
            };

        encabezado.setDefaultRenderer(
            renderEncabezado
        );

        tabla.getColumnModel()
            .getColumn(0)
            .setCellRenderer(
                crearRenderCelda(
                    SwingConstants.CENTER
                )
            );

        tabla.getColumnModel()
            .getColumn(1)
            .setCellRenderer(
                crearRenderCelda(
                    SwingConstants.LEFT
                )
            );

        tabla.getColumnModel()
            .getColumn(2)
            .setCellRenderer(
                crearRenderCelda(
                    SwingConstants.LEFT
                )
            );

        tabla.getColumnModel()
            .getColumn(3)
            .setCellRenderer(
                crearRenderCelda(
                    SwingConstants.CENTER
                )
            );

        tabla.getColumnModel()
            .getColumn(4)
            .setCellRenderer(
                crearRenderCelda(
                    SwingConstants.CENTER
                )
            );

        tabla.getColumnModel()
            .getColumn(5)
            .setCellRenderer(
                crearRenderCelda(
                    SwingConstants.CENTER
                )
            );

        tabla.getColumnModel()
            .getColumn(0)
            .setPreferredWidth(45);

        tabla.getColumnModel()
            .getColumn(1)
            .setPreferredWidth(165);

        tabla.getColumnModel()
            .getColumn(2)
            .setPreferredWidth(145);

        tabla.getColumnModel()
            .getColumn(3)
            .setPreferredWidth(90);

        tabla.getColumnModel()
            .getColumn(4)
            .setPreferredWidth(155);

        tabla.getColumnModel()
            .getColumn(5)
            .setPreferredWidth(70);
    }

    private DefaultTableCellRenderer crearRenderCelda(
        int alineacion
    ) {
        return new DefaultTableCellRenderer() {

            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
            ) {
                JLabel etiqueta = (JLabel)
                    super.getTableCellRendererComponent(
                        table,
                        value,
                        isSelected,
                        hasFocus,
                        row,
                        column
                    );

                etiqueta.setOpaque(true);
                etiqueta.setHorizontalAlignment(alineacion);
                etiqueta.setBorder(
                    BorderFactory.createEmptyBorder(
                        0,
                        10,
                        0,
                        10
                    )
                );

                if (isSelected) {
                    etiqueta.setBackground(
                        new Color(219, 234, 254)
                    );
                    etiqueta.setForeground(TEXTO);
                } else {
                    etiqueta.setBackground(
                        row % 2 == 0
                            ? Color.WHITE
                            : new Color(248, 250, 252)
                    );
                    etiqueta.setForeground(TEXTO);
                }

                return etiqueta;
            }
        };
    }

    private JPanel crearBarraEstado() {
        JPanel barra = new JPanel(
            new FlowLayout(FlowLayout.LEFT)
        );
        barra.setBackground(
            new Color(226, 232, 240)
        );
        barra.setBorder(
            BorderFactory.createEmptyBorder(
                7,
                25,
                7,
                25
            )
        );

        etiquetaEstado.setFont(
            new Font("Segoe UI", Font.PLAIN, 12)
        );
        etiquetaEstado.setForeground(GRIS);

        barra.add(etiquetaEstado);

        return barra;
    }

    private static JTextField crearCampoTexto() {
        JTextField campo = new JTextField();

        campo.setFont(
            new Font("Segoe UI", Font.PLAIN, 14)
        );
        campo.setForeground(TEXTO);
        campo.setBackground(Color.WHITE);
        campo.setCaretColor(AZUL);
        campo.setPreferredSize(
            new Dimension(250, 39)
        );

        campo.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(
                    7,
                    11,
                    7,
                    11
                )
            )
        );

        return campo;
    }

    private void configurarEventos() {
        tabla.getSelectionModel()
            .addListSelectionListener(evento -> {

                if (!evento.getValueIsAdjusting()
                    && tabla.getSelectedRow() >= 0) {

                    int filaModelo =
                        tabla.convertRowIndexToModel(
                            tabla.getSelectedRow()
                        );

                    Empleado empleado =
                        modeloTabla.getEmpleado(
                            filaModelo
                        );

                    mostrarEmpleado(empleado);
                }
            });
    }

    private void cargarEmpleados() {
        try {
            modeloTabla.setEmpleados(
                servicio.listarTodos()
            );

            etiquetaEstado.setText(
                "●  "
                    + modeloTabla.getRowCount()
                    + " empleado(s) registrado(s)"
            );

        } catch (RuntimeException e) {
            mostrarError(
                "No se pudieron cargar los empleados.",
                e
            );
        }
    }

    private void guardarEmpleado() {
        try {
            Empleado empleado = leerFormulario();
            empleado.setId(null);

            servicio.crear(empleado);

            mostrarMensaje(
                "Empleado guardado correctamente."
            );

            limpiarFormulario();
            cargarEmpleados();

        } catch (IllegalArgumentException e) {
            mostrarAdvertencia(e.getMessage());

        } catch (RuntimeException e) {
            mostrarError(
                "No se pudo guardar el empleado.",
                e
            );
        }
    }

    private void actualizarEmpleado() {
        if (campoId.getText().isBlank()) {
            mostrarAdvertencia(
                "Seleccione un empleado de la tabla."
            );
            return;
        }

        try {
            Empleado empleado = leerFormulario();

            empleado.setId(
                Long.valueOf(campoId.getText())
            );

            servicio.actualizar(empleado);

            mostrarMensaje(
                "Empleado actualizado correctamente."
            );

            limpiarFormulario();
            cargarEmpleados();

        } catch (IllegalArgumentException e) {
            mostrarAdvertencia(e.getMessage());

        } catch (RuntimeException e) {
            mostrarError(
                "No se pudo actualizar el empleado.",
                e
            );
        }
    }

    private void eliminarEmpleado() {
        if (campoId.getText().isBlank()) {
            mostrarAdvertencia(
                "Seleccione un empleado de la tabla."
            );
            return;
        }

        int respuesta =
            JOptionPane.showConfirmDialog(
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
            Long id = Long.valueOf(
                campoId.getText()
            );

            servicio.eliminarPorId(id);

            mostrarMensaje(
                "Empleado eliminado correctamente."
            );

            limpiarFormulario();
            cargarEmpleados();

        } catch (RuntimeException e) {
            mostrarError(
                "No se pudo eliminar el empleado.",
                e
            );
        }
    }

    private Empleado leerFormulario() {
        String nombre =
            campoNombre.getText().trim();

        String departamento =
            campoDepartamento.getText().trim();

        String textoSalario =
            campoSalario.getText().trim();

        String textoFecha =
            campoFecha.getText().trim();

        if (nombre.isEmpty()) {
            throw new IllegalArgumentException(
                "El nombre es obligatorio."
            );
        }

        if (departamento.isEmpty()) {
            throw new IllegalArgumentException(
                "El departamento es obligatorio."
            );
        }

        if (textoSalario.isEmpty()) {
            throw new IllegalArgumentException(
                "El salario es obligatorio."
            );
        }

        if (textoFecha.isEmpty()) {
            throw new IllegalArgumentException(
                "La fecha de contratación es obligatoria."
            );
        }

        double salario;

        try {
            salario = Double.parseDouble(
                textoSalario
            );
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
        empleado.setActivo(
            campoActivo.isSelected()
        );

        return empleado;
    }

    private void mostrarEmpleado(
        Empleado empleado
    ) {
        campoId.setText(
            String.valueOf(empleado.getId())
        );

        campoNombre.setText(
            empleado.getNombre()
        );

        campoDepartamento.setText(
            empleado.getDepartamento()
        );

        campoSalario.setText(
            String.valueOf(empleado.getSalario())
        );

        campoFecha.setText(
            String.valueOf(
                empleado.getFechaContratacion()
            )
        );

        campoActivo.setSelected(
            empleado.isActivo()
        );

        etiquetaEstado.setText(
            "●  Empleado seleccionado: "
                + empleado.getNombre()
        );
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

        etiquetaEstado.setText(
            "●  Formulario listo"
        );
    }

    private void mostrarMensaje(
        String mensaje
    ) {
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Operación exitosa",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void mostrarAdvertencia(
        String mensaje
    ) {
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Datos incorrectos",
            JOptionPane.WARNING_MESSAGE
        );
    }

    private void mostrarError(
        String mensaje,
        RuntimeException error
    ) {
        String detalle = error.getMessage();

        if (detalle == null || detalle.isBlank()) {
            detalle =
                "Revise la conexión con MySQL.";
        }

        JOptionPane.showMessageDialog(
            this,
            mensaje + "\n" + detalle,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    private static class PanelDegradado
        extends JPanel {

        private static final long serialVersionUID = 1L;

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 =
                (Graphics2D) g.create();

            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            GradientPaint degradado =
                new GradientPaint(
                    0,
                    0,
                    new Color(30, 64, 175),
                    getWidth(),
                    0,
                    new Color(37, 99, 235)
                );

            g2.setPaint(degradado);
            g2.fillRect(
                0,
                0,
                getWidth(),
                getHeight()
            );

            g2.dispose();
        }
    }

    private static class PanelRedondeado
        extends JPanel {

        private static final long serialVersionUID = 1L;

        private final Color color;
        private final int radio;

        public PanelRedondeado(
            Color color,
            int radio
        ) {
            this.color = color;
            this.radio = radio;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 =
                (Graphics2D) g.create();

            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(
                new Color(203, 213, 225, 90)
            );

            g2.fillRoundRect(
                3,
                4,
                getWidth() - 5,
                getHeight() - 5,
                radio,
                radio
            );

            g2.setColor(color);

            g2.fillRoundRect(
                0,
                0,
                getWidth() - 5,
                getHeight() - 6,
                radio,
                radio
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    private static class BotonModerno
        extends JButton {

        private static final long serialVersionUID = 1L;

        private final Color colorNormal;
        private Color colorActual;

        public BotonModerno(
            String texto,
            Color color
        ) {
            super(texto);

            this.colorNormal = color;
            this.colorActual = color;

            setFont(
                new Font(
                    "Segoe UI",
                    Font.BOLD,
                    13
                )
            );

            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);

            setCursor(
                Cursor.getPredefinedCursor(
                    Cursor.HAND_CURSOR
                )
            );

            setPreferredSize(
                new Dimension(130, 39)
            );

            setBorder(
                BorderFactory.createEmptyBorder(
                    9,
                    14,
                    9,
                    14
                )
            );

            addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                        MouseEvent e
                    ) {
                        colorActual =
                            colorNormal.darker();

                        repaint();
                    }

                    @Override
                    public void mouseExited(
                        MouseEvent e
                    ) {
                        colorActual =
                            colorNormal;

                        repaint();
                    }
                }
            );
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 =
                (Graphics2D) g.create();

            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(colorActual);

            g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                12,
                12
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }
}