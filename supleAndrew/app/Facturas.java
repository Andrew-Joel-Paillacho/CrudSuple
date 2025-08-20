package supleAndrew.app;

import supleAndrew.modelo.Productos;
import supleAndrew.modelo.ServicioFacturacion;
import supleAndrew.modelo.ServicioProductos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class Facturas extends JFrame{
    private JPanel panel1;
    private JTextField txtIdCliente;
    private JTextField txtCodigo;
    private JTextField txtCantidad;
    private JTextField txtDescuento;
    private JLabel txtSubtotal;
    private JLabel txtTotal;
    private JButton btSubtotal;
    private JButton guardarButton;
    private JButton btTotal;
    private JTable table1;
    private JTextField txtDia;
    private JTextField txtAnio;
    private JComboBox<String> txtMes;
    private JButton regresarAlMenuButton;

    private ServicioProductos servicioProductos;
    private ServicioFacturacion servicioFacturacion;
    private Productos producto;

    public Facturas() {
        setTitle("Panel de Facturación");
        setSize(960, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        setLocationRelativeTo(null);

        // Inicializar servicios
        servicioProductos = new ServicioProductos();
        servicioFacturacion = new ServicioFacturacion();
        producto = new Productos();

        txtMes.setModel(new DefaultComboBoxModel<>(new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"}));

        // Configurar tabla y cargar todos los detalles al iniciar
        configurarTabla();
        cargarTodosDetalles();

        // Action Listeners
        btSubtotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularSubtotal();
            }
        });

        btTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularTotal();
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarFactura();
            }
        });

        // Validar que solo se ingresen números en ciertos campos
        agregarValidacionesNumericas();


        regresarAlMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu();
                menu.setVisible(true);
                setVisible(false);
            }
        });
    }

    private void configurarTabla() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Factura", "Código", "Producto", "Cantidad", "Precio Unitario", "Total Línea"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table1.setModel(model);

        // Ajustar el ancho de las columnas
        table1.getColumnModel().getColumn(0).setPreferredWidth(80);
        table1.getColumnModel().getColumn(1).setPreferredWidth(80);
        table1.getColumnModel().getColumn(2).setPreferredWidth(200);
        table1.getColumnModel().getColumn(3).setPreferredWidth(60);
        table1.getColumnModel().getColumn(4).setPreferredWidth(100);
        table1.getColumnModel().getColumn(5).setPreferredWidth(100);
    }

    private void cargarTodosDetalles() {
        servicioFacturacion.cargarTodosDetallesEnTabla(table1);
    }

    private void agregarValidacionesNumericas() {
        txtIdCliente.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });

        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });

        txtDescuento.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == '.' || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });
    }

    private void calcularSubtotal() {
        try {
            int idCliente = Integer.parseInt(txtIdCliente.getText());
            String dia = txtDia.getText();
            String mes = (String) txtMes.getSelectedItem();
            String anio = txtAnio.getText();
            String codigo = txtCodigo.getText();
            String cantidad = txtCantidad.getText();
            String descuento = txtDescuento.getText();

            if (servicioFacturacion.calcularSubtotal(idCliente, dia, mes, anio, codigo, cantidad, descuento)) {
                txtSubtotal.setText(String.format("$%.2f", servicioFacturacion.getSubtotal()));
                // Limpiar el total cuando se recalcula el subtotal
                txtTotal.setText("$0.00");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de cliente debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularTotal() {
        if (servicioFacturacion.getSubtotal() == 0) {
            JOptionPane.showMessageDialog(this, "Primero debe calcular el subtotal", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        servicioFacturacion.calcularTotal();
        txtTotal.setText(String.format("$%.2f", servicioFacturacion.getTotal()));
    }

    private void guardarFactura() {
        try {
            if (servicioFacturacion.getTotal() == 0) {
                JOptionPane.showMessageDialog(this, "Primero debe calcular el total", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idCliente = Integer.parseInt(txtIdCliente.getText());
            String dia = txtDia.getText();
            String mes = (String) txtMes.getSelectedItem();
            String anio = txtAnio.getText();
            String cantidad = txtCantidad.getText();
            String descuento = txtDescuento.getText();

            if (servicioFacturacion.guardarFactura(idCliente, dia, mes, anio, cantidad, descuento)) {
                JOptionPane.showMessageDialog(this, "Factura guardada exitosamente");

                // Recargar todos los detalles en la tabla
                cargarTodosDetalles();

                // Limpiar campos
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de cliente debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtIdCliente.setText("");
        txtDia.setText("");
        txtAnio.setText("");
        txtCodigo.setText("");
        txtCantidad.setText("");
        txtDescuento.setText("");
        txtSubtotal.setText("$0.00");
        txtTotal.setText("$0.00");
        txtMes.setSelectedIndex(0);

        // Reiniciar el servicio para una nueva factura
        servicioFacturacion = new ServicioFacturacion();
        producto = new Productos();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Facturas().setVisible(true);
        });
    }
}