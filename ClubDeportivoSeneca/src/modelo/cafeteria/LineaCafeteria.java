package modelo.cafeteria;

import java.io.Serializable;

public class LineaCafeteria implements Serializable {

    private ProductoCafeteria producto;
    private int cantidad;
    private double precioUnitario;

    public LineaCafeteria(ProductoCafeteria producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
    }

    public double subtotal() {
        return cantidad * precioUnitario;
    }

    public ProductoCafeteria getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    @Override
    public String toString() {
        return cantidad + " x " + producto.getNombre() + " = $" + subtotal();
    }
}
