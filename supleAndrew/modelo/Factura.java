package supleAndrew.modelo;

import java.util.Date;

public class Factura {
    private int id;
    private int idCliente;
    private Date fecha;
    private double subtotal;
    private double descuento;
    private double total;

    public Factura() {}

    public Factura(int idCliente, Date fecha, double subtotal, double descuento, double total) {
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = total;
    }

    public Factura(int id, int idCliente, Date fecha, double subtotal, double descuento, double total) {
        this.id = id;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = total;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}