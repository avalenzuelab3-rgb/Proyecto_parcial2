package edu.umg.programacion2.empleados.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import edu.umg.programacion2.empleados.model.Empleado;

public class EmpleadoTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private final String[] columnas = {
            "ID",
            "Nombre",
            "Departamento",
            "Salario",
            "Fecha de contratación",
            "Tipo de contrato",
            "Activo"
    };

    private List<Empleado> empleados = new ArrayList<>();

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = new ArrayList<>(empleados);
        fireTableDataChanged();
    }

    public Empleado getEmpleado(int fila) {
        return empleados.get(fila);
    }

    @Override
    public int getRowCount() {
        return empleados.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int columna) {
        return columnas[columna];
    }

    @Override
    public Object getValueAt(int fila, int columna) {

        Empleado empleado = empleados.get(fila);

        switch (columna) {
            case 0:
                return empleado.getId();

            case 1:
                return empleado.getNombre();

            case 2:
                return empleado.getDepartamento();

            case 3:
                return empleado.getSalario();

            case 4:
                return empleado.getFechaContratacion();

            case 5:
                return empleado.getTipoContrato();

            case 6:
                return empleado.isActivo() ? "Sí" : "No";

            default:
                return "";
                
                
                
        }
    }
}