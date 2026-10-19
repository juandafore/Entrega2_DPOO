package modelo.cafeteria;

public class Bebida extends ProductoCafeteria {

    private String tipo;
    private boolean caliente;

    public Bebida(String nombre, double precio, String tipo, boolean caliente) {
        super(nombre, precio);
        this.tipo = tipo;
        this.caliente = caliente;
    }

    public boolean esCaliente() {
        return caliente;
    }

    public String getTipo() {
        return tipo;
    }
}
