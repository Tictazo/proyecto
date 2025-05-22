import javax.swing.*;
import java.awt.event.*;
import java.util.regex.*;

public class RegistroFrame extends JFrame {
    public RegistroFrame() {
        setTitle("Registro de Administrador");
        setSize(350, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextField nombreField = new JTextField();
        JTextField correoField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JPasswordField confirmarField = new JPasswordField();
        JButton registrarBtn = new JButton("Registrar");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("Nombre completo:"));
        add(nombreField);
        add(new JLabel("Correo electrónico:"));
        add(correoField);
        add(new JLabel("Contraseña:"));
        add(passField);
        add(new JLabel("Confirmar contraseña:"));
        add(confirmarField);
        add(registrarBtn);

        registrarBtn.addActionListener(e -> {
            String nombre = nombreField.getText().trim();
            String correo = correoField.getText().trim();
            String pass = new String(passField.getPassword());
            String confirm = new String(confirmarField.getPassword());

            // Validaciones
            if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                JOptionPane.showMessageDialog(this, "Nombre inválido. Solo letras y espacios.");
                return;
            }

            if (!correo.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
                JOptionPane.showMessageDialog(this, "Correo inválido.");
                return;
            }

            if (pass.length() < 8 || !pass.matches(".*[a-zA-Z].*") || !pass.matches(".*\\d.*")) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 8 caracteres, una letra y un número.");
                return;
            }

            if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.");
                return;
            }

            for (Admin admin : AdminArchivoUtil.cargar()) {
                if (admin.getCorreo().equalsIgnoreCase(correo)) {
                    JOptionPane.showMessageDialog(this, "El correo ya está registrado.");
                    return;
                }
            }

            String passHash = HashUtil.sha256(pass);
            Admin nuevo = new Admin(correo, passHash);
            AdminArchivoUtil.guardar(nuevo);

            JOptionPane.showMessageDialog(this, "Administrador registrado exitosamente.");
            dispose(); // Cierra la ventana de registro
        });

        setVisible(true);
    }
}
