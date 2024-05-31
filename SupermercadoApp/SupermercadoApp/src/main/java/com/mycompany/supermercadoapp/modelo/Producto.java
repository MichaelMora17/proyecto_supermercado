package com.mycompany.supermercadoapp.modelo;

public class Producto {

    private String codigo;
    private String nombre;
    private int cantidad;
    private double precio;
    private double total;

    public Producto(String codigo, String nombre, int cantidad, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = cantidad * precio;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        calcularTotal();
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
        calcularTotal();
    }

    public double getTotal() {
        return total;
    }

    private void calcularTotal() {
        this.total = this.cantidad * this.precio;
    }

    @Override
    public String toString() {
        return codigo + " " + nombre + " " + cantidad + " " + precio + " " + total;
    }
}