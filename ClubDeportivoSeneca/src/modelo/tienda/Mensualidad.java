package modelo.tienda;

import modelo.usuarios.Usuario;

public class Mensualidad extends Compra {

    private Usuario socio;
    private String periodo;
    private double valor;

    public Mensualidad(Usuario socio, String periodo, double valor) {
        this.socio = socio;
        this.periodo = periodo;
        this.valor = valor;
    }

    @Override
    public double calcularImpuesto() {
        return 0.0;
    }

    @Override
    protected double calcularSubtotal() {
        return valor;
    }

    @Override
    public String rubro() {
        return "MENSUALIDAD";
    }

    public Usuario getSocio() {
        return socio;
    }

    public String getPeriodo() {
        return periodo;
    }

    public double getValor() {
        return valor;
    }
}
