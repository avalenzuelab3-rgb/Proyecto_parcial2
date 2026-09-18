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
        SwingUtilities.invokeLater(
            EmpleadosApp::iniciarAplicacion
        );
    }

    private static void iniciarAplicacion() {
        configurarApariencia();

        /*
         * Primero busca la contraseña en la variable de entorno.
         * En tu computadora abrirá directamente porque ya la configuraste.
         */
        String password = System.getenv("MYSQL_PASSWORD");

        /*
         * Si se ejecuta en otra computadora y la variable no existe,
         * solicitará la contraseña correspondiente a esa instalación.
         */
        if (password == null || password.isBlank()) {
            JPasswordField campoPassword =
                new JPasswordField();

            int respuesta =
                JOptionPane.showConfirmDialog(
                    null,
                    campoPassword,
                    "Ingrese la contraseña de MySQL para el usuario root",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
                );

            if (respuesta != JOptionPane.OK_OPTION) {
                return;
            }

            password =
                new String(campoPassword.getPassword());

            if (password.isBlank()) {
                JOptionPane.showMessageDialog(
                    null,
                    "Debe ingresar la contraseña de MySQL.",
                    "Contraseña requerida",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }

        iniciarSistema(password);
    }

    private static void iniciarSistema(
        String password
    ) {
        try {
            ConexionBD conexionBD =
                new ConexionBD(
                    "jdbc:mysql://localhost:3306/empleados_db"
                        + "?useSSL=false"
                        + "&allowPublicKeyRetrieval=true"
                        + "&serverTimezone=UTC"
                        + "&characterEncoding=UTF-8",
                    "root",
                    password
                );

            EmpleadoRepository repositorio =
                new EmpleadoRepositoryJdbc(conexionBD);

            EmpleadoService servicio =
                new EmpleadoService(repositorio);

            EmpleadoFrame ventana =
                new EmpleadoFrame(servicio);

            ventana.setVisible(true);

        } catch (RuntimeException e) {
            String detalle = e.getMessage();

            if (detalle == null || detalle.isBlank()) {
                detalle =
                    "No fue posible establecer la conexión.";
            }

            JOptionPane.showMessageDialog(
                null,
                "No se pudo iniciar la aplicación.\n\n"
                    + "Compruebe lo siguiente:\n"
                    + "• MySQL Server está encendido.\n"
                    + "• La base de datos empleados_db existe.\n"
                    + "• La contraseña es correcta.\n\n"
                    + "Detalle: " + detalle,
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private static void configurarApariencia() {
        try {
            for (UIManager.LookAndFeelInfo apariencia
                    : UIManager.getInstalledLookAndFeels()) {

                if ("Nimbus".equals(
                        apariencia.getName())) {

                    UIManager.setLookAndFeel(
                        apariencia.getClassName()
                    );

                    break;
                }
            }
        } catch (Exception e) {
            // Se utilizará la apariencia predeterminada de Java.
        }
    }
}