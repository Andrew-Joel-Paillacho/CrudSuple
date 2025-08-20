package supleAndrew.modelo;

import java.util.List;

public interface CrudDetalleFactura {
    void insertar(DetalleFactura detalle);
    void actualizarStockProducto(int idProducto, int cantidadVendida);
    List<DetalleFactura> obtenerTodosDetalles(); // Nuevo método para todos los detalles
    List<DetalleFactura> obtenerDetallesPorFactura(int idFactura); // Mantenemos este por si acaso
}