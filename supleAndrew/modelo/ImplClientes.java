package supleAndrew.modelo;

import supleAndrew.conexion.Conexion;

import javax.swing.*;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImplClientes implements CrudClientes {
    private final String SELECT = "SELECT * FROM clientes";
    private final String SELECT_BY_IDENTIFICACION = "SELECT * FROM clientes WHERE identificacion = ?";
    private final String INSERT = "INSERT INTO clientes(identificacion, nombre) VALUES (?,?)";
    private final String UPDATE = "UPDATE clientes SET identificacion=?, nombre=? WHERE id = ?";
    private final String DELETE = "DELETE FROM clientes WHERE id = ?";

    private Connection conn = null;

    private Connection conectar() {
        Conexion conexion = new Conexion();
        conn = conexion.conectar();
        return conn;
    }

    @Override
    public Map<Integer, Clientes> seleccionarTodo() {
        Map<Integer, Clientes> map = new LinkedHashMap<>();
        try {
            Connection conn = this.conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT);
            while (rs.next()) {
                Clientes cliente = new Clientes(
                        rs.getInt("id"),
                        rs.getString("identificacion"),
                        rs.getString("nombre")
                );
                map.put(rs.getInt("id"), cliente);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

    @Override
    public Clientes buscarPorIdentificacion(String identificacion) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Clientes cliente = null;

        try {
            conn = this.conectar();
            stmt = conn.prepareStatement(SELECT_BY_IDENTIFICACION);
            stmt.setString(1, identificacion);
            rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Clientes(
                        rs.getInt("id"),
                        rs.getString("identificacion"),
                        rs.getString("nombre")
                );
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return cliente;
    }

    @Override
    public void insertar(Clientes cliente) {
        try {
            Connection conn = this.conectar();
            PreparedStatement pstmt = conn.prepareStatement(INSERT);
            pstmt.setString(1, cliente.getIdentificacion());
            pstmt.setString(2, cliente.getNombre());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente agregado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actualizar(Clientes cliente) {
        try {
            conn = this.conectar();
            PreparedStatement pstmt = conn.prepareStatement(UPDATE);
            pstmt.setString(1, cliente.getIdentificacion());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setInt(3, cliente.getId());
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