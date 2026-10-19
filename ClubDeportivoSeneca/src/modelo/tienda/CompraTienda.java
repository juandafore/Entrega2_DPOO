package modelo.tienda;

import java.util.ArrayList;
import java.util.List;

import modelo.usuarios.Usuario;

public class CompraTienda extends Compra {

    public static final double IVA = 0.19;

    private final List<LineaTienda> lineas = new ArrayList<>();
    private Usuario comprador;
    private CodigoDescuento codigoAplicado;

    public CompraTienda(Usuario comprador) {
        this.comprador = comprador;
    }

    @Override
    public double calcularImpuesto() {
        return calcularSubtotal() * IVA;
    }

    @Override
    protected double calcularSubtotal() {
        double s = 0;
        for (LineaTienda l : lineas) {
            s += l.subtotal();
        }
        return s;
    }

    @Override
    public String rubro() {
        return "TIENDA";
    }

    public LineaTienda agregarLinea(Articulo a, int n) {
        if (!a.hayExistencias(n)) {
            throw new IllegalStateException("Sin existencias para " + a.getNombre());
        }
        LineaTienda linea = new LineaTienda(a, n);
        lineas.add(linea);
        return linea;
    }

    public void aplicarCodigo(CodigoDescuento c) {
        int pct = c.porcentajePara(comprador);
        aplicarDescuento(calcularSubtotal() * pct / 100.0);
        this.codigoAplicado = c;
    }

    public void descontarInventario() {
        for (LineaTienda l : lineas) {
            l.getArticulo().descontar(l.getCantidad());
        }
    }

    public List<LineaTienda> getLineas() {
        return lineas;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public CodigoDescuento getCodigoAplicado() {
        return codigoAplicado;
    }
}
