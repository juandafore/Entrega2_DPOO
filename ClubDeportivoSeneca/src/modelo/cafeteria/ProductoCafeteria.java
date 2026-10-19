package modelo.cafeteria;

import java.io.Serializable;

public abstract class ProductoCafeteria implements Serializable {

    protected String nombre;
    protected double precio;

    protected ProductoCafeteria(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean esCaliente() {
        return false;
    }

    public String[] informarAlergenos() {
        return new String[0];
    }

    @Override
    public String toString() {
        return nombre + " $" + precio;
    }
}
