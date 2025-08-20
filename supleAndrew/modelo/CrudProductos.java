package supleAndrew.modelo;

import java.util.Map;

public interface CrudProductos {
    // Mostrar todos
    public Map<Integer, Productos> seleccionarTodo();

    // Buscar por código
    public Productos buscarPorCodigo(String codigo);

    // Insertar
    public void insertar(Productos producto);

    // Actualizar
    public void actualizar(Productos producto);

    // Eliminar
    public void eliminar(int id);
}
