import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class MainFrame extends JFrame {
    ListaEmpleados empleados = new ListaEmpleados();
    HistorialEliminaciones historial = new HistorialEliminaciones();
    private ColaEdiciones cola = new ColaEdiciones();
    String archivo = "nomina.txt";

    JPanel panelContenido;

    public MainFrame() {
        setTitle("Sistema de Gestión de Nómina");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Cargar datos desde archivo
        for (Empleado empCargado : ArchivoUtil.cargar(archivo)) {
            empleados.agregar(empCargado);
        }

        // Panel lateral con botones (menú)
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBackground(new Color(220, 220, 220));
        panelMenu.setPreferredSize(new Dimension(200, getHeight()));

        JButton btnRegistrar = new JButton("Registrar nuevo empleado");
        JButton btnMostrar = new JButton("Mostrar nómina");
        JButton btnActualizar = new JButton("Actualizar empleado");
        JButton btnEliminar = new JButton("Eliminar empleado");
        JButton btnConsultar = new JButton("Consultar empleado");
        JButton btnHistorial = new JButton("Ver historial de eliminaciones");
        JButton btnCola = new JButton("Ver cola de ediciones");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        panelMenu.add(btnRegistrar);
        panelMenu.add(btnMostrar);
        panelMenu.add(btnActualizar);
        panelMenu.add(btnEliminar);
        panelMenu.add(btnConsultar);
        panelMenu.add(btnHistorial);
        panelMenu.add(btnCola);
        panelMenu.add(btnCerrarSesion);

        // Panel principal (donde se muestran los formularios)
        panelContenido = new JPanel();
        panelContenido.setLayout(new BorderLayout());

        // Cerrar sesión
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        btnRegistrar.addActionListener(e -> mostrarRegistrarEmpleado());
        btnMostrar.addActionListener(e -> new NominaFrame(archivo));
        btnActualizar.addActionListener(e -> mostrarActualizarEmpleado());
        btnEliminar.addActionListener(e -> eliminarEmpleado());
        btnHistorial.addActionListener(e -> mostrarHistorialEliminaciones());
        btnCola.addActionListener(e -> mostrarColaEdiciones());
        btnConsultar.addActionListener(e -> mostrarConsultaEmpleado());

        add(panelMenu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);

        setVisible(true);
    }

    // Mostrar formulario para registrar nuevo empleado
    private void mostrarActualizarEmpleado() {
        panelContenido.removeAll();

        JPanel panelBuscar = new JPanel(new BorderLayout());
        JTextField idField = new JTextField();
        JButton buscarBtn = new JButton("Buscar");

        panelBuscar.add(new JLabel("Ingrese ID del empleado a actualizar: "), BorderLayout.WEST);
        panelBuscar.add(idField, BorderLayout.CENTER);
        panelBuscar.add(buscarBtn, BorderLayout.EAST);

        panelContenido.add(panelBuscar, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(11, 2, 10, 10));

        JTextField nombreField = new JTextField();
        JTextField edadField = new JTextField();
        JTextField sexoField = new JTextField();
        JTextField direccionField = new JTextField();
        JTextField telefonoField = new JTextField();
        JTextField puestoField = new JTextField();
        JTextField departamentoField = new JTextField();
        JTextField horasField = new JTextField();
        JTextField costoField = new JTextField();
        JButton actualizarBtn = new JButton("Actualizar");

        panelForm.add(new JLabel("Nombre:")); panelForm.add(nombreField);
        panelForm.add(new JLabel("Edad:")); panelForm.add(edadField);
        panelForm.add(new JLabel("Sexo:")); panelForm.add(sexoField);
        panelForm.add(new JLabel("Dirección:")); panelForm.add(direccionField);
        panelForm.add(new JLabel("Teléfono:")); panelForm.add(telefonoField);
        panelForm.add(new JLabel("Puesto:")); panelForm.add(puestoField);
        panelForm.add(new JLabel("Departamento:")); panelForm.add(departamentoField);
        panelForm.add(new JLabel("Horas trabajadas:")); panelForm.add(horasField);
        panelForm.add(new JLabel("Costo por hora:")); panelForm.add(costoField);
        panelForm.add(new JLabel()); panelForm.add(actualizarBtn);

        panelForm.setVisible(false);
        panelContenido.add(panelForm, BorderLayout.CENTER);

        buscarBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            Empleado emp = empleados.buscar(id);
            if (emp == null) {
                JOptionPane.showMessageDialog(this, "Empleado no encontrado.");
                panelForm.setVisible(false);
            } else {
                nombreField.setText(emp.getNombre());
                edadField.setText(String.valueOf(emp.getEdad()));
                sexoField.setText(emp.getSexo());
                direccionField.setText(emp.getDireccion());
                telefonoField.setText(emp.getTelefono());
                puestoField.setText(emp.getPuesto());
                departamentoField.setText(emp.getDepartamento());
                horasField.setText(String.valueOf(emp.getHoras()));
                costoField.setText(String.valueOf(emp.getCostoHora()));
                panelForm.setVisible(true);
            }
            panelContenido.revalidate();
            panelContenido.repaint();
        });

        actualizarBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            Empleado emp = empleados.buscar(id);

            if (emp != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Desea actualizar este registro?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;

                try {
                    emp.setNombre(nombreField.getText().trim());
                    emp.setEdad(Integer.parseInt(edadField.getText().trim()));
                    emp.setSexo(sexoField.getText().trim());
                    emp.setDireccion(direccionField.getText().trim());
                    emp.setTelefono(telefonoField.getText().trim());
                    emp.setPuesto(puestoField.getText().trim());
                    emp.setDepartamento(departamentoField.getText().trim());
                    emp.setHoras(Double.parseDouble(horasField.getText().trim()));
                    emp.setCostoHora(Double.parseDouble(costoField.getText().trim()));

                    ArchivoUtil.guardar(empleados.obtenerTodos(), archivo);
                    cola.encolar(emp.getId());

                    JOptionPane.showMessageDialog(this, "Empleado actualizado correctamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
                }
            }
        });

        panelContenido.revalidate();
        panelContenido.repaint();
    }


    // Mostrar lista de empleados
    private void mostrarNomina() {
        panelContenido.removeAll();

        JPanel panel = new JPanel(new BorderLayout());
        JTextField filtroField = new JTextField();
        JTextArea area = new JTextArea();
        area.setEditable(false);

        JButton btnFiltrar = new JButton("Filtrar por nombre");

        btnFiltrar.addActionListener(e -> {
            String filtro = filtroField.getText().toLowerCase();
            StringBuilder sb = new StringBuilder();
            for (Empleado emp : empleados.obtenerTodos()) {
                if (emp.getNombre().toLowerCase().contains(filtro)) {
                    sb.append(emp).append("\n");
                }
            }
            area.setText(sb.toString());
        });

        StringBuilder sb = new StringBuilder();
        for (Empleado emp : empleados.obtenerTodos()) {
            sb.append(emp).append("\n");
        }
        area.setText(sb.toString());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Buscar por nombre: "), BorderLayout.WEST);
        topPanel.add(filtroField, BorderLayout.CENTER);
        topPanel.add(btnFiltrar, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);

        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private void eliminarEmpleado() {
        String id = JOptionPane.showInputDialog(this, "Ingrese el ID del empleado a eliminar:");
        if (id == null || id.trim().isEmpty()) {
            return;  // Si cancela o no escribe nada, salir
        }
        id = id.trim();

        Empleado empEncontrado = empleados.buscar(id);
        if (empEncontrado == null) {
            JOptionPane.showMessageDialog(this, "Empleado con ID " + id + " no encontrado.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea eliminar al empleado con ID " + id + "?\nEsta acción no se puede deshacer.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Agregar a pila historial
            historial.agregar(empEncontrado);

            // Eliminar de lista ligada
            empleados.eliminar(id);

            // Guardar lista actualizada en archivo
            ArchivoUtil.guardar(empleados.obtenerTodos(), archivo);

            JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente y guardado en el historial.");
        }
    }

    private void mostrarHistorialEliminaciones() {
        Stack<Empleado> eliminados = historial.obtenerHistorial();
        if (eliminados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay eliminaciones registradas.");
            return;
        }

        StringBuilder sb = new StringBuilder("Historial de empleados eliminados:\n\n");
        for (Empleado emp : eliminados) {
            sb.append(emp).append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString());
    }
    private void mostrarColaEdiciones() {
        panelContenido.removeAll();

        java.util.Queue<String> colaEdiciones = cola.obtenerCola();
        if (colaEdiciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay ediciones en cola.");
            return;
        }

        JTextArea area = new JTextArea();
        area.setEditable(false);

        StringBuilder sb = new StringBuilder("Cola de empleados actualizados:\n\n");
        for (String id : colaEdiciones) {
            Empleado emp = empleados.buscar(id);
            if (emp != null) {
                sb.append(emp).append("\n");
            }
        }

        area.setText(sb.toString());
        panelContenido.add(new JScrollPane(area), BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private void mostrarConsultaEmpleado() {
        panelContenido.removeAll();

        JPanel panel = new JPanel(new BorderLayout());
        JTextField idField = new JTextField();
        JButton buscarBtn = new JButton("Consultar");

        JTextArea resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(new JLabel("Ingrese ID del empleado: "), BorderLayout.WEST);
        panelSuperior.add(idField, BorderLayout.CENTER);
        panelSuperior.add(buscarBtn, BorderLayout.EAST);

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        buscarBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            Empleado emp = empleados.buscar(id);
            if (emp != null) {
                resultadoArea.setText("Empleado encontrado:\n\n" + emp.toString());
            } else {
                resultadoArea.setText("Empleado no encontrado.");
            }
        });

        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }
    private void mostrarRegistrarEmpleado() {
        panelContenido.removeAll();

        // Formulario para registrar un empleado nuevo
        JPanel panelForm = new JPanel(new GridLayout(11, 2, 10, 10));

        JTextField idField = new JTextField();
        JTextField nombreField = new JTextField();
        JTextField edadField = new JTextField();
        JTextField sexoField = new JTextField();
        JTextField direccionField = new JTextField();
        JTextField telefonoField = new JTextField();
        JTextField puestoField = new JTextField();
        JTextField departamentoField = new JTextField();
        JTextField horasField = new JTextField();
        JTextField costoField = new JTextField();

        JButton guardarBtn = new JButton("Guardar");

        panelForm.add(new JLabel("ID:")); panelForm.add(idField);
        panelForm.add(new JLabel("Nombre:")); panelForm.add(nombreField);
        panelForm.add(new JLabel("Edad:")); panelForm.add(edadField);
        panelForm.add(new JLabel("Sexo:")); panelForm.add(sexoField);
        panelForm.add(new JLabel("Dirección:")); panelForm.add(direccionField);
        panelForm.add(new JLabel("Teléfono:")); panelForm.add(telefonoField);
        panelForm.add(new JLabel("Puesto:")); panelForm.add(puestoField);
        panelForm.add(new JLabel("Departamento:")); panelForm.add(departamentoField);
        panelForm.add(new JLabel("Horas trabajadas:")); panelForm.add(horasField);
        panelForm.add(new JLabel("Costo por hora:")); panelForm.add(costoField);
        panelForm.add(new JLabel()); panelForm.add(guardarBtn);

        panelContenido.add(panelForm, BorderLayout.CENTER);

        guardarBtn.addActionListener(ev -> {
            try {
                String id = idField.getText().trim();
                if (empleados.buscar(id) != null) {
                    JOptionPane.showMessageDialog(this, "El ID ya existe.");
                    return;
                }
                String nombre = nombreField.getText().trim();
                int edad = Integer.parseInt(edadField.getText().trim());
                String sexo = sexoField.getText().trim();
                String direccion = direccionField.getText().trim();
                String telefono = telefonoField.getText().trim();
                String puesto = puestoField.getText().trim();
                String departamento = departamentoField.getText().trim();
                double horas = Double.parseDouble(horasField.getText().trim());
                double costoHora = Double.parseDouble(costoField.getText().trim());

                Empleado nuevo = new Empleado(id, nombre, edad, sexo, direccion, telefono, puesto, departamento, horas, costoHora);
                empleados.agregar(nuevo);
                ArchivoUtil.guardar(empleados.obtenerTodos(), archivo);

                JOptionPane.showMessageDialog(this, "Empleado registrado correctamente.");
                mostrarNomina();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        panelContenido.revalidate();
        panelContenido.repaint();
    }

}