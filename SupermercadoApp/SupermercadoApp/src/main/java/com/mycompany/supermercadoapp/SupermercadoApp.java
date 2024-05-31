package com.mycompany.supermercadoapp;

import com.mycompany.supermercadoapp.modelo.Factura;
import com.mycompany.supermercadoapp.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupermercadoApp extends JFrame {

    private JTextField codigoTextField;
    private JTextField nombreTextField;
    private JTextField cantidadTextField;
    private JTextField precioTextField;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton buscarButton;
    private JTable productosTable;
    private JLabel precioFinalLabel;
    private DefaultTableModel tableModel;

    private Factura factura;

    public SupermercadoApp() {
        factura = new Factura();
        initComponents();
    }

    private void initComponents() {
        setTitle("Supermercado App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel superior para ingresar datos
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 4, 5, 5));

        inputPanel.add(new JLabel("Código:"));
        codigoTextField = new JTextField();
        inputPanel.add(codigoTextField);

        inputPanel.add(new JLabel("Nombre:"));
        nombreTextField = new JTextField();
        inputPanel.add(nombreTextField);

        inputPanel.add(new JLabel("Cantidad:"));
        cantidadTextField = new JTextField();
        inputPanel.add(cantidadTextField);

        inputPanel.add(new JLabel("Precio:"));
        precioTextField = new JTextField();
        inputPanel.add(precioTextField);

        agregarButton = new JButton("Agregar Producto");
        modificarButton = new JButton("Modificar Producto");
        eliminarButton = new JButton("Eliminar Producto");
        buscarButton = new JButton("Buscar Producto");

        inputPanel.add(agregarButton);
        inputPanel.add(modificarButton);
        inputPanel.add(eliminarButton);
        inputPanel.add(buscarButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Tabla para mostrar productos
        tableModel = new DefaultTableModel(new Object[]{"Código", "Nombre", "Cantidad", "Precio", "Total"}, 0);
        productosTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productosTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Etiqueta para mostrar el precio final
        precioFinalLabel = new JLabel("Precio final: 0.0 Precio final con IVA: 0.0");
        JPanel footerPanel = new JPanel();
        footerPanel.add(precioFinalLabel);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Añadir panel principal al frame
        setContentPane(mainPanel);

        // Acción del botón Agregar
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = codigoTextField.getText();
                String nombre = nombreTextField.getText();
                int cantidad = Integer.parseInt(cantidadTextField.getText());
                double precio = Double.parseDouble(precioTextField.getText());
                Producto producto = new Producto(codigo, nombre, cantidad, precio);
                Producto checkProducto = factura.buscarProducto(producto.getCodigo());
                if (checkProducto == null) {
                    factura.agregarProducto(producto);
                } else {
                    JOptionPane.showMessageDialog(null, "Ya existe un producto con ese codigo");
                }
                actualizarTabla();
                vaciarCampos();
            }
        });

        // Acción del botón Modificar
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = codigoTextField.getText();
                Producto producto = factura.buscarProducto(codigo);
                if (producto != null) {
                    int result = JOptionPane.showConfirmDialog(null, "Producto encontrado: " + producto + "\n¿Desea modificar este producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        factura.modificarProducto(codigo);
                        actualizarTabla();
                        JOptionPane.showMessageDialog(null, "Producto modificado exitosamente.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                }
                vaciarCampos();
            }
        });

        // Acción del botón Eliminar
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = codigoTextField.getText();
                factura.eliminarProducto(codigo);
                actualizarTabla();
                vaciarCampos();
            }

        });

        // Acción del botón Buscar
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = codigoTextField.getText();
                Producto producto = factura.buscarProducto(codigo);
                if (producto != null) {
                    JOptionPane.showMessageDialog(null, "Producto encontrado: " + producto);
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                }
                vaciarCampos();
            }
        });
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);
        for (Producto producto : factura.getProductos()) {
            tableModel.addRow(new Object[]{producto.getCodigo(), producto.getNombre(), producto.getCantidad(), producto.getPrecio(), producto.getTotal()});
        }
        precioFinalLabel.setText("Precio final: " + factura.getPrecioFinal() + " Precio final con IVA: " + factura.getPrecioFinalIVA());
    }

    private void vaciarCampos() {
        codigoTextField.setText("");
        nombreTextField.setText("");
        cantidadTextField.setText("");
        precioTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SupermercadoApp().setVisible(true);
            }
        });
    }
}
