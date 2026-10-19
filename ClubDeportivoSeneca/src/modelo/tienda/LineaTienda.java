package modelo.tienda;

import java.io.Serializable;

public class LineaTienda implements Serializable {

    private Articulo articulo;
    private int cantidad;
    private double precioUnitario;

    public LineaTienda(Articulo articulo, int cantidad) {
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.precioUnitario = articulo.getPrecio();
    }

    public double subtotal() {
        return cantidad * precioUnitario;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    @Override
    public String toString() {
        return cantidad + " x " + articulo.getNombre() + " = $" + subtotal();
    }
}
