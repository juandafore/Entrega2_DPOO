package modelo.tienda;

import java.io.Serializable;

public class Articulo implements Serializable {

    private String nombre;
    private String categoria;
    private double precio;
    private int cantidadDisponible;
    private UbicacionInventario ubicacion;

    public Articulo(String nombre, String categoria, double precio, int cantidadDisponible) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
    }

    public boolean hayExistencias(int n) {
        return cantidadDisponible >= n;
    }

    public void descontar(int n) {
        if (!hayExistencias(n)) {
            throw new IllegalStateException("No hay existencias suficientes de " + nombre
                    + " (disponible: " + cantidadDisponible + ", solicitado: " + n + ")");
        }
        cantidadDisponible -= n;
    }

    public void reabastecer(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("La cantidad a reabastecer no puede ser negativa");
        }
        cantidadDisponible += n;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public UbicacionInventario getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionInventario ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return nombre + " (" + categoria + ") $" + precio + " x" + cantidadDisponible;
    }
}
