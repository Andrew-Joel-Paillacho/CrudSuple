package supleAndrew.app;

import supleAndrew.modelo.ServicioU;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends  JFrame
{
    private JPanel panel1;
    private JTextField txtUsuario;
    private JPasswordField txtpassword;
    private JButton iniciarSesionButton;
    private JButton crearUsuarioButton;
    private JCheckBox cbMostrarPass;

    public Login() {
        setTitle("Inicio de sesion");
        setSize(400, 300);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        setLocationRelativeTo(null);
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
        cbMostrarPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbMostrarPass.isSelected()) {
                    txtpassword.setEchoChar((char) 0); // Mostrar texto
                } else {
                    txtpassword.setEchoChar('•'); // Ocultar con puntos
                }
            }
        });
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtpassword.getPassword());

        // Validar campos vacíos
        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Usuario y contraseña son obligatorios",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ServicioU servicio = new ServicioU();

            // Verificar credenciales
            if (validarCredenciales(servicio, usuario, password)) {

                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                Menu menu = new Menu();
                menu.setVisible(true);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario, contraseña o tipo de usuario incorrectos",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al iniciar sesión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean validarCredenciales(ServicioU servicio, String usuario, String password) {
        // Obtener contraseña real desde la base de datos
        String passwordReal = servicio.obtenerPassword(usuario);

        if (passwordReal == null) {
            return false; // Usuario no existe
        }

        return password.equals(passwordReal);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}
