package supleAndrew.modelo;

import supleAndrew.conexion.Conexion;

import java.sql.*;
import javax.swing.JOptionPane;

public class ImplFacturas implements CrudFacturas {
    private final String INSERT = "INSERT INTO facturas(id_cliente, fecha, subtotal, descuento, total) VALUES (?,?,?,?,?)";

    @Override
    public int insertar(Factura factura) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, factura.getIdCliente());
            pstmt.setTimestamp(2, new Timestamp(factura.getFecha().getTime()));
            pstmt.setDouble(3, factura.getSubtotal());
            pstmt.setDouble(4, factura.getDescuento());
            pstmt.setDouble(5, factura.getTotal());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                JOptionPane.showMessageDialog(null, "Error al insertar factura: ninguna fila afectada", "Error", JOptionPane.ERROR_MESSAGE);
                return -1;
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al obtener ID de la factura", "Error", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar factura: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    @Override
    public int obtenerUltimoId() {
        // Este método ya no es necesario, pero lo mantenemos por compatibilidad
        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()")) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener último ID: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }
}