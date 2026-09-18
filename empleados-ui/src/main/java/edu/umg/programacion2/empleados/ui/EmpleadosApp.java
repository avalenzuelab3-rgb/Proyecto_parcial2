package edu.umg.programacion2.empleados.ui;

import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import edu.umg.programacion2.empleados.config.ConexionBD;
import edu.umg.programacion2.empleados.repository.EmpleadoRepository;
import edu.umg.programacion2.empleados.repository.impl.EmpleadoRepositoryJdbc;
import edu.umg.programacion2.empleados.service.EmpleadoService;

public class EmpleadosApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> iniciarAplicacion());
    }

    private static void iniciarAplicacion() {
        configurarApariencia();

        JPasswordField campoPassword = new JPasswordField();

        int respuesta = JOptionPane.showConfirmDialog(
            null,
            campoPassword,
            "Ingrese la contraseña de MySQL para el usuario root",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (respuesta != JOptionPane.OK_OPTION) {
            return;
        }

        String password = new String(campoPassword.getPassword());

        ConexionBD conexionBD = new ConexionBD(
            "jdbc:mysql://localhost:3306/empleados_db"
                + "?useSSL=false"
                + "&allowPublicKeyRetrieval=true"
                + "&serverTimezone=UTC",
            "root",
            password
        );

        EmpleadoRepository repositorio =
            new EmpleadoRepositoryJdbc(conexionBD);

        EmpleadoService servicio = new EmpleadoService(repositorio);

        EmpleadoFrame ventana = new EmpleadoFrame(servicio);
        ventana.setVisible(true);
    }

    private static void configurarApariencia() {
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            // Se mantiene la apariencia predeterminada de Java.
        }
    }
}