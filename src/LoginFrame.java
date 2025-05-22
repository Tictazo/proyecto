import javax.swing.*;
import java.awt.event.*;
import java.util.regex.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Login Admin");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Iniciar Sesión");
        JButton registerBtn = new JButton("Registrarse");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("Correo electrónico:"));
        add(userField);
        add(new JLabel("Contraseña:"));
        add(passField);
        add(loginBtn);
        add(registerBtn);

        loginBtn.addActionListener(e -> {
            String email = userField.getText().trim();
            String password = new String(passField.getPassword());

            // Validación de correo
            if (!Pattern.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$", email)) {
                JOptionPane.showMessageDialog(this, "Correo inválido");
                return;
            }

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La contraseña no debe estar vacía");
                return;
            }

            String passwordHash = HashUtil.sha256(password);
            boolean encontrado = false;

            for (Admin admin : AdminArchivoUtil.cargar()) {
                if (admin.getCorreo().equals(email) && admin.getPasswordHash().equals(passwordHash)) {
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                dispose();
                new MainFrame();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos. Intente nuevamente.");
            }
        });

        registerBtn.addActionListener(e -> new RegistroFrame());

        setVisible(true);
    }
}
