package supleAndrew.modelo;

import supleAndrew.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ImplDetalleFactura implements CrudDetalleFactura {
    private final String INSERT = "INSERT INTO detalle_factura(id_factura, id_producto, cantidad, precio_unitario, total_linea) VALUES (?,?,?,?,?)";
    private final String UPDATE_STOCK = "UPDATE productos SET stock = stock - ? WHERE id = ?";
    private final String SELECT_ALL = "SELECT df.*, p.codigo as codigo_producto, p.nombre as nombre_producto, f.id as id_factura_completo " +
            "FROM detalle_factura df " +
            "INNER JOIN productos p ON df.id_producto = p.id " +
            "INNER JOIN facturas f ON df.id_factura = f.id " +
            "ORDER BY df.id_factura, df.id";

    @Override
    public void insertar(DetalleFactura detalle) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(INSERT)) {

            pstmt.setInt(1, detalle.getIdFactura());
            pstmt.setInt(2, detalle.getIdProducto());
            pstmt.setInt(3, detalle.getCantidad());
            pstmt.setDouble(4, detalle.getPrecioUnitario());
            pstmt.setDouble(5, detalle.getTotalLinea());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar detalle: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actualizarStockProducto(int idProducto, int cantidadVendida) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_STOCK)) {

            pstmt.setInt(1, cantidadVendida);
            pstmt.setInt(2, idProducto);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar stock: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<DetalleFactura> obtenerTodosDetalles() {
        List<DetalleFactura> detalles = new ArrayList<>();

        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL)) {

            while (rs.next()) {
                DetalleFactura detalle = new DetalleFactura();
                detalle.setId(rs.getInt("id"));
                detalle.setIdFactura(rs.getInt("id_factura_completo"));
                detalle.setIdProducto(rs.getInt("id_producto"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
                detalle.setTotalLinea(rs.getDouble("total_linea"));
                detalle.setCodigoProducto(rs.getString("codigo_producto"));
                detalle.setNombreProducto(rs.getString("nombre_producto"));

                detalles.add(detalle);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener detalles: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return detalles;
    }

    // Mantenemos este método por si acaso se necesita en el futuro
    public List<DetalleFactura> obtenerDetallesPorFactura(int idFactura) {
        List<DetalleFactura> detalles = new ArrayList<>();
        String SELECT_BY_FACTURA = "SELECT df.*, p.codigo as codigo_producto, p.nombre as nombre_producto " +
                "FROM detalle_factura df " +
                "INNER JOIN productos p ON df.id_producto = p.id " +
                "WHERE df.id_factura = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_FACTURA)) {

            pstmt.setInt(1, idFactura);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                DetalleFactura detalle = new DetalleFactura();
                detalle.setId(rs.getInt("id"));
                detalle.setIdFactura(rs.getInt("id_factura"));
                detalle.setIdProducto(rs.getInt("id_producto"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
                detalle.setTotalLinea(rs.getDouble("total_linea"));
                detalle.setCodigoProducto(rs.getString("codigo_producto"));
                detalle.setNombreProducto(rs.getString("nombre_producto"));

                detalles.add(detalle);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener detalles: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return detalles;
    }
}