package supleAndrew.app;

import supleAndrew.modelo.Clientes;
import supleAndrew.modelo.ServicioClientes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Map;

public class Cliente extends JFrame {
    private JPanel panel1;
    private JTextField txtNombre;
    private JTextField txtIdentificacion;
    private JTable table1;
    private JButton regresarAlMenuButton;
    private JButton buscarButton;
    private JButton agregarButton;
    private JScrollPane scrollPane;

    private ServicioClientes servicioClientes;
    private DefaultTableModel tableModel;

    public Cliente() {
        setTitle("Gestión de Clientes");
        setSize(600, 400);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        setLocationRelativeTo(null);

        // Inicializar el servicio
        servicioClientes = new ServicioClientes();

        // Configurar la tabla
        configurarTabla();

        // Cargar todos los clientes al iniciar
        cargarTodosClientes();

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
                buscarCliente();
            }
        });

        // Agregar ActionListener al botón agregar
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCliente();
            }
        });

        // Agregar KeyListener para buscar al presionar Enter en identificación
        txtIdentificacion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarCliente();
                }
            }
        });
    }

    private void configurarTabla() {
        // Configurar el modelo de la tabla
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable
            }
        };

        tableModel.addColumn("ID");
        tableModel.addColumn("Identificación");
        tableModel.addColumn("Nombre");

        table1.setModel(tableModel);

        // Ajustar el ancho de las columnas
        table1.getColumnModel().getColumn(0).setPreferredWidth(50);
        table1.getColumnModel().getColumn(1).setPreferredWidth(100);
        table1.getColumnModel().getColumn(2).setPreferredWidth(200);
    }

    private void cargarTodosClientes() {
        // Limpiar la tabla
        tableModel.setRowCount(0);

        // Obtener todos los clientes
        Map<Integer, Clientes> clientes = servicioClientes.obtenerTodosClientes();

        // Llenar la tabla
        for (Clientes cliente : clientes.values()) {
            tableModel.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getIdentificacion(),
                    cliente.getNombre()
            });
        }
    }

    private void buscarCliente() {
        String identificacion = txtIdentificacion.getText();
        Clientes cliente = servicioClientes.buscarPorIdentificacion(identificacion);

        if (cliente != null) {
            // Llenar el campo de nombre
            txtNombre.setText(cliente.getNombre());

            // Mostrar solo el cliente encontrado en la tabla
            tableModel.setRowCount(0); // Limpiar tabla
            tableModel.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getIdentificacion(),
                    cliente.getNombre()
            });
        } else {
            // Limpiar campos y mostrar todos los clientes
            txtNombre.setText("");
            cargarTodosClientes();
        }
    }

    private void agregarCliente() {
        String identificacion = txtIdentificacion.getText();
        String nombre = txtNombre.getText();

        // Validar campos vacíos
        if (identificacion.trim().isEmpty() || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        servicioClientes.agregarCliente(identificacion, nombre);

        // Limpiar campos después de agregar
        txtIdentificacion.setText("");
        txtNombre.setText("");

        // Actualizar la tabla con todos los clientes
        cargarTodosClientes();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Cliente().setVisible(true);
        });
    }
}