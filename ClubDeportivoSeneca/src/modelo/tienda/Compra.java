package modelo.tienda;

import java.io.Serializable;
import java.time.LocalDateTime;

import modelo.comun.Gravable;

public abstract class Compra implements Gravable, Serializable {

    public static final double TASA_PUNTOS = 0.02;

    protected LocalDateTime fecha = LocalDateTime.now();
    protected double subtotal;
    protected double descuento;
    protected int puntosRedimidos;
    protected int puntosGenerados;
    protected double total;

    @Override
    public abstract double calcularImpuesto();

    protected abstract double calcularSubtotal();

    public abstract String rubro();

    protected double extras() {
        return 0.0;
    }

    public double calcularTotal() {
        subtotal = calcularSubtotal();
        total = subtotal + calcularImpuesto() + extras() - descuento - puntosRedimidos;
        if (total < 0) {
            total = 0;
        }
        return total;
    }

    public void aplicarDescuento(double d) {
        this.descuento = d;
    }

    public int generarPuntos() {
        puntosGenerados = (int) Math.floor(calcularTotal() * TASA_PUNTOS);
        return puntosGenerados;
    }

    public void redimirPuntos(int p) {
        this.puntosRedimidos = p;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public int getPuntosRedimidos() {
        return puntosRedimidos;
    }

    public int getPuntosGenerados() {
        return puntosGenerados;
    }

    public double getTotal() {
        return total;
    }
}
