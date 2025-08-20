package supleAndrew.modelo;

import supleAndrew.conexion.Conexion;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ServicioU
{
    private CrudUsuarios implementacion = new ImplUsuarios();
    Conexion conexion = new Conexion();

    public Map<Integer, UsuarioS> seleccionarTodo() {
        return implementacion.seleccionarTodo();
    }

    public void insertar(UsuarioS usuarios) {
        implementacion.insertar(usuarios);
    }

    public void actualizar(UsuarioS usuarios) {
        implementacion.actualizar(usuarios);
    }

    public void eliminar(int id) {
        if (JOptionPane.showConfirmDialog(null,
                "¿Estás seguro que deseas eliminar este usuario?",
                "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            implementacion.eliminar(id);
        }
    }

    public String obtenerPassword(String usuario) {
        String password = null;
        String sql = "SELECT pass FROM usuarios WHERE usuario = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario); // Cambiado de 2 a 1
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                password = rs.getString("pass");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al obtener contraseña: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return password;
    }
}
