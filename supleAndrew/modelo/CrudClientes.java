package supleAndrew.modelo;

import java.util.List;
import java.util.Map;

public interface CrudClientes {
    // Mostrar todos
    public Map<Integer, Clientes> seleccionarTodo();

    // Buscar por identificación
    public Clientes buscarPorIdentificacion(String identificacion);

    // Insertar
    public void insertar(Clientes cliente);

    // Actualizar
    public void actualizar(Clientes cliente);

    // Eliminar
    public void eliminar(int id);
}