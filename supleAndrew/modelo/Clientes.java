package supleAndrew.modelo;

public class Clientes {
    private int id;
    private String identificacion;
    private String nombre;

    //constructor
    public Clientes(String identificacion, String nombre) {
        this.identificacion = identificacion;
        this.nombre = nombre;
    }

    public Clientes(int id, String identificacion, String nombre) {
        this.id = id;
        this.identificacion = identificacion;
        this.nombre = nombre;
    }

    //setter y getters
    public int getId() {
        return id;
    }
    public String getIdentificacion() {
        return identificacion;
    }
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Clientes{" +
                "id=" + id +
                ", identificacion='" + identificacion + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}