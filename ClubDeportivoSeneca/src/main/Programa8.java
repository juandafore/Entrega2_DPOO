package main;

import java.util.Arrays;

import modelo.cafeteria.Bebida;
import modelo.cafeteria.LineaCafeteria;
import modelo.cafeteria.Snack;
import modelo.cafeteria.VentaCafeteria;
import modelo.deportesindividuales.CanchaPadel;
import modelo.deportesindividuales.CanchaTenis;
import modelo.usuarios.Socio;

public class Programa8 {

    public static void main(String[] args) {
        System.out.println("### PROGRAMA 8: Cafetería y restricciones de seguridad ###\n");

        Bebida cafe = new Bebida("Café americano", 4000, "Café", true);
        Bebida jugo = new Bebida("Jugo de naranja", 5000, "Jugo", false);
        Snack mani = new Snack("Mix de maní", 6000, new String[] {"maní", "gluten"});

        Socio ana = new Socio("ana", "x", "Ana", 24);

        System.out.println("Alérgenos de " + mani.getNombre() + ": " + Arrays.toString(mani.informarAlergenos()) + "\n");

        VentaCafeteria v1 = new VentaCafeteria(ana);
        v1.agregarLinea(jugo, 1);
        v1.agregarLinea(mani, 2);
        ana.comprarCafeteria(v1);
        recibo("Venta normal", v1);
        System.out.println("   Puntos de Ana: " + ana.getPuntosFidelidad());

        VentaCafeteria v2 = new VentaCafeteria(ana);
        v2.agregarLinea(jugo, 2);
        v2.modificarPropina(2000);
        ana.comprarCafeteria(v2);
        recibo("Venta con propina modificada ($2000)", v2);

        VentaCafeteria v3 = new VentaCafeteria(ana);
        v3.agregarLinea(cafe, 1);
        v3.setInstalacionDelSocio(new CanchaPadel("Padel-1", true));
        try {
            ana.comprarCafeteria(v3);
        } catch (RuntimeException ex) {
            System.out.println("\n[RECHAZO] café en cancha de pádel techada -> " + ex.getMessage());
        }

        VentaCafeteria v4 = new VentaCafeteria(ana);
        v4.agregarLinea(cafe, 1);
        v4.setInstalacionDelSocio(new CanchaTenis("Tenis-1", false));
        ana.comprarCafeteria(v4);
        recibo("Café al aire libre (permitido)", v4);
    }

    private static void recibo(String titulo, VentaCafeteria v) {
        v.calcularTotal();
        System.out.println(titulo + ":");
        for (LineaCafeteria l : v.getLineas()) {
            System.out.println("   " + l);
        }
        System.out.println("   Subtotal $" + (long) v.getSubtotal()
                + " | Consumo $" + (long) v.calcularImpuesto()
                + " | Propina $" + (long) v.getPropina()
                + " | TOTAL $" + (long) v.getTotal());
    }
}
