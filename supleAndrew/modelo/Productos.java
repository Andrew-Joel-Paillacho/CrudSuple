package supleAndrew.modelo;

public class Productos {
    private int id;
    private String codigo;
    private String nombre;
    private double precio;
    private int stock;

    // Constructor vacío
    public Productos() {
    }

    public Productos(int id, String codigo, String nombre, double precio, int stock) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public Productos(String codigo, String nombre, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Los demás métodos permanecen igual...
    public int getId() {
        return id;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "id=" + id +
                "\n, codigo = " + codigo +
                "\n, nombre = " + nombre +
                "\n, precio = " + precio +
                "\n, stock = " + stock;
    }
}