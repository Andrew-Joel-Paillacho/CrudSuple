package supleAndrew.modelo;

public interface CrudFacturas {
    int insertar(Factura factura); // Cambiado de void a int para retornar el ID
    int obtenerUltimoId();
}