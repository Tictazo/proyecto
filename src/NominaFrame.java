import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class NominaFrame extends JFrame {

    private JTextField filtroField;
    private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;
    private List<Empleado> empleados; // lista completa cargada desde archivo

    public NominaFrame(String archivo) {
        setTitle("Nómina de Empleados");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        empleados = ArchivoUtil.cargar(archivo);

        JPanel panelFiltro = new JPanel(new BorderLayout());
        panelFiltro.add(new JLabel("Buscar por nombre o apellido: "), BorderLayout.WEST);

        filtroField = new JTextField();
        panelFiltro.add(filtroField, BorderLayout.CENTER);

        add(panelFiltro, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {
                "ID", "Nombre", "Edad", "Sexo", "Dirección", "Teléfono", "Puesto", "Departamento", "Horas", "Costo/Hora", "Salario"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // No editable
            }
        };
        tablaEmpleados = new JTable(modeloTabla);
        add(new JScrollPane(tablaEmpleados), BorderLayout.CENTER);

        cargarDatosEnTabla(empleados);

        filtroField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }
        });

        setVisible(true);
    }

    private void cargarDatosEnTabla(List<Empleado> lista) {
        modeloTabla.setRowCount(0); // limpiar tabla
        for (Empleado emp : lista) {
            Object[] fila = {
                    emp.getId(),
                    emp.getNombre(),
                    emp.getEdad(),
                    emp.getSexo(),
                    emp.getDireccion(),
                    emp.getTelefono(),
                    emp.getPuesto(),
                    emp.getDepartamento(),
                    emp.getHoras(),
                    emp.getCostoHora(),
                    emp.getSalario()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void filtrar() {
        String textoFiltro = filtroField.getText().toLowerCase().trim();
        if (textoFiltro.isEmpty()) {
            cargarDatosEnTabla(empleados);
        } else {
            List<Empleado> filtrados = empleados.stream()
                    .filter(emp -> emp.getNombre().toLowerCase().contains(textoFiltro))
                    .collect(Collectors.toList());
            cargarDatosEnTabla(filtrados);
        }
    }
}