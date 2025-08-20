package supleAndrew.modelo;

import java.util.Map;

public interface CrudUsuarios
{
    // Mostrar todos
    public Map<Integer, UsuarioS> seleccionarTodo();

    // Mostrar Uno
    public UsuarioS buscar(int id);

    // Insertar
    public void insertar(UsuarioS usuarios);

    // Actualizar
    public void actualizar(UsuarioS usuarios);

    // Eliminar
    public void eliminar(int id);
}
