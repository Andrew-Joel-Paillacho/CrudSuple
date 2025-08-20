package supleAndrew.modelo;

public class DetalleFactura {
    private int id;
    private int idFactura;
    private int idProducto;
    private int cantidad;
    private double precioUnitario;
    private double totalLinea;
    private String codigoProducto; // Nuevo campo
    private String nombreProducto; // Nuevo campo

    public DetalleFactura() {}

    public DetalleFactura(int idFactura, int idProducto, int cantidad, double precioUnitario, double totalLinea) {
        this.idFactura = idFactura;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.totalLinea = totalLinea;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdFactura() { return idFactura; }
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    public double getTotalLinea() { return totalLinea; }
    public void setTotalLinea(double totalLinea) { this.totalLinea = totalLinea; }
    public String getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
}