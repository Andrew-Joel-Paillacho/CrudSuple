package supleAndrew.modelo;

import javax.swing.JOptionPane;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

public class ServicioFacturacion {
    private CrudFacturas crudFacturas = new ImplFacturas();
    private CrudDetalleFactura crudDetalle = new ImplDetalleFactura();
    private ServicioProductos servicioProductos = new ServicioProductos();

    private double subtotal = 0;
    private double total = 0;
    private int idFactura = -1;
    private Productos productoActual = null;

    public boolean calcularSubtotal(int idCliente, String dia, String mes, String anio,
                                    String codigo, String cantidadStr, String descuentoStr) {
        // Validar campos vacíos
        if (idCliente <= 0 || dia.isEmpty() || mes.isEmpty() || anio.isEmpty() ||
                codigo.isEmpty() || cantidadStr.isEmpty() || descuentoStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que cantidad y descuento sean números válidos
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            double descuento = Double.parseDouble(descuentoStr);

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (descuento < 0) {
                JOptionPane.showMessageDialog(null, "El descuento no puede ser negativo", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cantidad y descuento deben ser números válidos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Obtener información del producto
        productoActual = servicioProductos.buscarPorCodigo(codigo);
        if (productoActual == null) {
            return false;
        }

        // Verificar stock
        int cantidad = Integer.parseInt(cantidadStr);
        if (cantidad > productoActual.getStock()) {
            JOptionPane.showMessageDialog(null, "Stock insuficiente. Stock disponible: " + productoActual.getStock(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Calcular subtotal
        double descuento = Double.parseDouble(descuentoStr);
        double precioTotal = productoActual.getPrecio() * cantidad;
        subtotal = precioTotal - descuento;

        // Validar que el subtotal no sea negativo
        if (subtotal < 0) {
            JOptionPane.showMessageDialog(null, "El descuento no puede ser mayor al precio total", "Error", JOptionPane.ERROR_MESSAGE);
            subtotal = 0;
            return false;
        }

        return true;
    }

    public void calcularTotal() {
        double impuesto = subtotal * 0.15;
        total = subtotal + impuesto;
    }

    public boolean guardarFactura(int idCliente, String dia, String mes, String anio,
                                  String cantidadStr, String descuentoStr) {
        try {
            // Validar campos numéricos
            int cantidad = Integer.parseInt(cantidadStr);
            double descuento = Double.parseDouble(descuentoStr);

            if (cantidad <= 0 || descuento < 0) {
                JOptionPane.showMessageDialog(null, "Datos inválidos en cantidad o descuento", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Crear fecha usando Calendar
            Date fecha = crearFecha(dia, mes, anio);
            if (fecha == null) return false;

            // Crear y guardar factura - ahora insertar() retorna el ID
            Factura factura = new Factura(idCliente, fecha, subtotal, descuento, total);
            idFactura = crudFacturas.insertar(factura);

            if (idFactura == -1) {
                JOptionPane.showMessageDialog(null, "Error al guardar la factura", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Guardar detalle
            double totalLinea = productoActual.getPrecio() * cantidad;

            DetalleFactura detalle = new DetalleFactura(
                    idFactura,
                    productoActual.getId(),
                    cantidad,
                    productoActual.getPrecio(),
                    totalLinea
            );

            crudDetalle.insertar(detalle);

            // Actualizar stock
            crudDetalle.actualizarStockProducto(productoActual.getId(), cantidad);

            JOptionPane.showMessageDialog(null, "Factura guardada exitosamente con ID: " + idFactura);
            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error en formato de números: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar factura: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public List<DetalleFactura> obtenerDetallesFactura(int idFactura) {
        return ((ImplDetalleFactura) crudDetalle).obtenerDetallesPorFactura(idFactura);
    }

    private Date crearFecha(String dia, String mes, String anio) {
        try {
            int day = Integer.parseInt(dia);
            int month = Integer.parseInt(mes) - 1; // Meses en Calendar van de 0 a 11
            int year = Integer.parseInt(anio);

            // Validar rangos de fecha
            if (day < 1 || day > 31 || month < 0 || month > 11 || year < 1900) {
                JOptionPane.showMessageDialog(null, "Fecha inválida", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Usar Calendar para crear la fecha (evitando el constructor deprecado de Date)
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            return calendar.getTime();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use números.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Método para cargar detalles en la tabla
    public void cargarDetallesEnTabla(int idFactura, javax.swing.JTable table) {
        List<DetalleFactura> detalles = obtenerDetallesFactura(idFactura);

        // Crear modelo de tabla
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Código", "Producto", "Cantidad", "Precio Unitario", "Total Línea"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Llenar la tabla con los detalles
        for (DetalleFactura detalle : detalles) {
            model.addRow(new Object[]{
                    detalle.getCodigoProducto(),
                    detalle.getNombreProducto(),
                    detalle.getCantidad(),
                    String.format("$%.2f", detalle.getPrecioUnitario()),
                    String.format("$%.2f", detalle.getTotalLinea())
            });
        }

        table.setModel(model);

        // Ajustar el ancho de las columnas
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(60);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
    }

    // Método para obtener TODOS los detalles
    public List<DetalleFactura> obtenerTodosDetalles() {
        return ((ImplDetalleFactura) crudDetalle).obtenerTodosDetalles();
    }

    // Método para cargar TODOS los detalles en la tabla
    public void cargarTodosDetallesEnTabla(javax.swing.JTable table) {
        List<DetalleFactura> detalles = obtenerTodosDetalles();

        // Crear modelo de tabla
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Factura", "Código", "Producto", "Cantidad", "Precio Unitario", "Total Línea"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Llenar la tabla con todos los detalles
        for (DetalleFactura detalle : detalles) {
            model.addRow(new Object[]{
                    detalle.getIdFactura(),
                    detalle.getCodigoProducto(),
                    detalle.getNombreProducto(),
                    detalle.getCantidad(),
                    String.format("$%.2f", detalle.getPrecioUnitario()),
                    String.format("$%.2f", detalle.getTotalLinea())
            });
        }

        table.setModel(model);

        // Ajustar el ancho de las columnas
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
    }

    // Getters
    public double getSubtotal() { return subtotal; }
    public double getTotal() { return total; }
    public int getIdFactura() { return idFactura; }

    // Setter para idFactura (útil para cuando se carga una factura existente)
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }
}