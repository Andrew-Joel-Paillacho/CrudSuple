package supleAndrew.app;

import supleAndrew.modelo.Productos;
import supleAndrew.modelo.ServicioProductos;

import javax.swing.*;
import java.awt.event.*;

public class Producto extends JFrame{
    private JPanel panel1;
    private JTextField txtCodigo;
    private JButton regresarAlMenuButton;
    private JButton buscarButton;
    private JScrollPane txtPrint; // Este es el JScrollPane
    private JTextArea textArea; // Este es el JTextArea

    private ServicioProductos servicioProductos;

    public Producto() {
        setTitle("Visualizador de Productos");
        setSize(560, 300);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        setLocationRelativeTo(null);

        // Inicializar el servicio
        servicioProductos = new ServicioProductos();

        // Crear el JTextArea para mostrar resultados
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // CORRECCIÓN: Aplicar el color de fondo al JTextArea, no al JScrollPane
        textArea.setBackground(panel1.getBackground());
        textArea.setBorder(BorderFactory.createEmptyBorder());

        // También quitar bordes del JScrollPane
        txtPrint.setBorder(BorderFactory.createEmptyBorder());
        txtPrint.setViewportBorder(BorderFactory.createEmptyBorder());
        txtPrint.setViewportView(textArea);

        // Listener para cambios de fondo del panel
        panel1.addPropertyChangeListener("background", e ->
                textArea.setBackground(panel1.getBackground())
        );

        regresarAlMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu();
                menu.setVisible(true);
                setVisible(false);
            }
        });

        // Agregar ActionListener al botón buscar
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });

        // Agregar KeyListener para buscar al presionar Enter
        txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarProducto();
                }
            }
        });
    }

    private void buscarProducto() {
        String codigo = txtCodigo.getText();
        Productos producto = servicioProductos.buscarPorCodigo(codigo);

        if (producto != null) {
            // Mostrar la información del producto en el textArea
            String info = "=== INFORMACIÓN DEL PRODUCTO ===\n" +
                    "ID: " + producto.getId() + "\n" +
                    "Código: " + producto.getCodigo() + "\n" +
                    "Nombre: " + producto.getNombre() + "\n" +
                    "Precio: $" + producto.getPrecio() + "\n" +
                    "Stock: " + producto.getStock() + " unidades\n" +
                    "=================================";
            textArea.setText(info);
        } else {
            textArea.setText(""); // Limpiar el área de texto si no se encuentra
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Producto().setVisible(true);
        });
    }
}