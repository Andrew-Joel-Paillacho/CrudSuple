package supleAndrew.modelo;

import javax.swing.*;

public class ServicioProductos {
    private CrudProductos implementacion = new ImplProductos();

    public Productos buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "El campo de código no puede estar vacío",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Productos producto = implementacion.buscarPorCodigo(codigo.trim());

        if (producto == null) {
            JOptionPane.showMessageDialog(null,
                    "Producto no encontrado con el código: " + codigo,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return producto;
    }
}