package supleAndrew.modelo;

import javax.swing.*;
import java.util.Map;

public class ServicioClientes {
    private CrudClientes implementacion = new ImplClientes();

    public Clientes buscarPorIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "El campo de identificación no puede estar vacío",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Clientes cliente = implementacion.buscarPorIdentificacion(identificacion.trim());

        if (cliente == null) {
            JOptionPane.showMessageDialog(null,
                    "Cliente no encontrado con la identificación: " + identificacion,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return cliente;
    }

    public void agregarCliente(String identificacion, String nombre) {
        if (identificacion == null || identificacion.trim().isEmpty() ||
                nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Todos los campos son obligatorios",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Clientes cliente = new Clientes(identificacion.trim(), nombre.trim());
        implementacion.insertar(cliente);
    }

    public Map<Integer, Clientes> obtenerTodosClientes() {
        return implementacion.seleccionarTodo();
    }
}