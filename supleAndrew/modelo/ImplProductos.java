package supleAndrew.modelo;

import supleAndrew.conexion.Conexion;

import javax.swing.*;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImplProductos implements CrudProductos {
    private final String SELECT = "SELECT * FROM productos";
    private final String SELECT_BY_CODIGO = "SELECT * FROM productos WHERE codigo = ?";
    private final String INSERT = "INSERT INTO productos(codigo, nombre, precio, stock) VALUES (?,?,?,?)";
    private final String UPDATE = "UPDATE productos SET codigo=?, nombre=?, precio=?, stock=? WHERE id = ?";
    private final String DELETE = "DELETE FROM productos WHERE id = ?";

    private Connection conn = null;

    private Connection conectar() {
        Conexion conexion = new Conexion();
        conn = conexion.conectar();
        return conn;
    }

    @Override
    public Map<Integer, Productos> seleccionarTodo() {
        Map<Integer, Productos> map = new LinkedHashMap<>();
        try {
            Connection conn = this.conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT);
            while (rs.next()) {
                Productos producto = new Productos(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                );
                map.put(rs.getInt("id"), producto);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

    @Override
    public Productos buscarPorCodigo(String codigo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Productos producto = null;

        try {
            conn = this.conectar();
            stmt = conn.prepareStatement(SELECT_BY_CODIGO);
            stmt.setString(1, codigo);
            rs = stmt.executeQuery();

            if (rs.next()) {
                producto = new Productos(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
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
        return producto;
    }

    @Override
    public void insertar(Productos producto) {
        try {
            Connection conn = this.conectar();
            PreparedStatement pstmt = conn.prepareStatement(INSERT);
            pstmt.setString(1, producto.getCodigo());
            pstmt.setString(2, producto.getNombre());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void actualizar(Productos producto) {
        try {
            conn = this.conectar();
            PreparedStatement pstmt = conn.prepareStatement(UPDATE);
            pstmt.setString(1, producto.getCodigo());
            pstmt.setString(2, producto.getNombre());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            pstmt.setInt(5, producto.getId());
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