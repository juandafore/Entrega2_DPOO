package main;

import modelo.tienda.Articulo;
import modelo.tienda.CodigoDescuento;
import modelo.tienda.CompraTienda;
import modelo.tienda.LineaTienda;
import modelo.tienda.UbicacionInventario;
import modelo.usuarios.Entrenador;
import modelo.usuarios.Socio;

public class Programa7 {

    public static void main(String[] args) {
        System.out.println("### PROGRAMA 7: Tienda y puntos de fidelidad ###\n");

        UbicacionInventario bodega = new UbicacionInventario("Bodega central");
        Articulo raqueta = new Articulo("Raqueta Pro", "Raquetas", 200000, 5);
        Articulo pelota = new Articulo("Pelota x3", "Pelotas", 20000, 100);
        bodega.trasladar(raqueta);
        bodega.trasladar(pelota);

        Socio juan = new Socio("juan", "x", "Juan", 25);
        Entrenador coach = new Entrenador("coach", "x", "Coach");
        CodigoDescuento codigo = new CodigoDescuento("COACH15", coach);
        coach.setCodigo(codigo);

        CompraTienda c1 = new CompraTienda(juan);
        c1.agregarLinea(raqueta, 2);
        c1.agregarLinea(pelota, 3);
        juan.comprarTienda(c1);
        c1.descontarInventario();
        recibo("Compra de Juan", c1);
        System.out.println("   Inventario raquetas restante: " + raqueta.getCantidadDisponible());
        System.out.println("   Puntos de Juan: " + juan.getPuntosFidelidad());

        try {
            CompraTienda cFalla = new CompraTienda(juan);
            cFalla.agregarLinea(raqueta, 10);
        } catch (RuntimeException ex) {
            System.out.println("\n[RECHAZO] " + ex.getMessage());
        }

        CompraTienda c2 = new CompraTienda(coach);
        c2.agregarLinea(raqueta, 1);
        c2.aplicarCodigo(codigo);
        coach.comprar(c2);
        recibo("\nCompra del empleado (código propio 15%)", c2);

        coach.compartirCodigo(juan);
        CompraTienda c3 = new CompraTienda(juan);
        c3.agregarLinea(raqueta, 1);
        c3.aplicarCodigo(codigo);
        juan.comprarTienda(c3);
        recibo("Compra de Juan con código compartido (8%)", c3);

        int saldo = juan.getPuntosFidelidad();
        int aRedimir = Math.min(saldo, 1000);
        double descuento = juan.redimirPuntos(aRedimir);
        System.out.println("\nJuan redime " + aRedimir + " puntos = $" + (long) descuento
                + " de descuento. Saldo restante: " + juan.getPuntosFidelidad());
    }

    private static void recibo(String titulo, CompraTienda c) {
        c.calcularTotal();
        System.out.println(titulo + ":");
        for (LineaTienda l : c.getLineas()) {
            System.out.println("   " + l);
        }
        System.out.println("   Subtotal $" + (long) c.getSubtotal()
                + " | IVA $" + (long) c.calcularImpuesto()
                + " | Descuento $" + (long) c.getDescuento()
                + " | TOTAL $" + (long) c.getTotal()
                + " | Puntos +" + c.getPuntosGenerados());
    }
}
