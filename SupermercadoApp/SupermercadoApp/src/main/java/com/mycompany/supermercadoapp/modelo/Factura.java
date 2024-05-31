package com.mycompany.supermercadoapp.modelo;

import java.util.ArrayList;

public class Factura {

    private ArrayList<Producto> productos;
    private double precioFinal;
    private double precioFinalIVA;
    private static final double IVA = 0.19;
    private static final double incremento = 0.05;
    
    public Factura() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        calcularPrecioFinal();
    }

    public void eliminarProducto(String codigo) {
        productos.removeIf(producto -> producto.getCodigo().equals(codigo));
        calcularPrecioFinal();
    }

    public Producto buscarProducto(String codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo().equals(codigo)) {
                return producto;
            }
        }
        return null;
    }

    public void modificarProducto(String codigo) {
        Producto producto = buscarProducto(codigo);
        if (producto != null) {
            producto.setPrecio(producto.getPrecio() * incremento + producto.getPrecio());
            calcularPrecioFinal();
        }
    }

    private void calcularPrecioFinal() {
        this.precioFinal = 0;
        this.precioFinalIVA = 0;
        for (Producto producto : productos) {
            this.precioFinal += producto.getTotal();
        }
        this.precioFinalIVA = this.precioFinal * IVA + this.precioFinal;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public double getPrecioFinalIVA() {
        return precioFinalIVA;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }
}
