package supleAndrew.modelo;

import supleAndrew.conexion.Conexion;

import javax.swing.*;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImplUsuarios implements CrudUsuarios
{
    private final String SELECT = "SELECT * from usuarios";
    private final String SELECT_BY_ID = "SELECT * from usuarios where id_usuario = ?";
    private final String INSERT = "INSERT INTO usuarios( usuario, pass, activo) VALUES (?,?,?)";
    private final String UPDATE = "UPDATE usuarios SET usuario=?, pass=?, activo=? WHERE id_usuario = ?";
    private final String DELETE = "DELETE FROM usuarios WHERE id = ?";

    private Connection conn = null;

    private Connection conectar() {
        Conexion conexion = new Conexion();
        conn = conexion.conectar();
        return conn;
    }

    @Override
    public Map<Integer, UsuarioS> seleccionarTodo() {
        Map<Integer, UsuarioS> map = new LinkedHashMap<>();

        try {
            Connection conn = this.conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT);
            while (rs.next()) {
                UsuarioS usuarios = new UsuarioS(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("contraseNIA"),
                        rs.getInt("activo")
                );
                map.put(rs.getInt("id"), usuarios);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

    @Override
    public UsuarioS buscar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UsuarioS usuarios = null;

        try {
            conn = this.conectar();
            stmt = conn.prepareStatement(SELECT_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                usuarios = new UsuarioS(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("contraseNIA"),
                        rs.getInt("activo")
                );
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return usuarios;
    }

    @Override
    public void insertar(UsuarioS usuarios) {
        try {
            Connection conn = this.conectar();
            PreparedStatement pstmt = conn.prepareStatement(INSERT);
            pstmt.setString(1, usuarios.getUsuario());
            pstmt.setString(2, usuarios.getContraseNIA());
            pstmt.setInt(3, usuarios.getActivo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void actualizar(UsuarioS usuarios) {
        try {
            conn = this.conectar();
            PreparedStatement pstmt = conn.prepareStatement(UPDATE);
            pstmt.setString(1, usuarios.getUsuario());
            pstmt.setString(2, usuarios.getContraseNIA());
            pstmt.setInt(3, usuarios.getActivo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        try {
            conn = this.conectar();
            PreparedStatement pstmt = conn.prepareStatement(DELETE);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
