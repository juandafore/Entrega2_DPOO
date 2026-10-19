package main;

import modelo.comun.Categoria;
import modelo.comun.Deporte;
import modelo.deportesconjunto.Equipo;
import modelo.deportesconjunto.InscripcionEquipo;
import modelo.usuarios.Socio;

public class Programa2 {

    public static void main(String[] args) {
        System.out.println("### PROGRAMA 2: Equipos e inscripciones ###\n");

        Equipo futbol = new Equipo("Leones FC", Deporte.FUTBOL, Categoria.MAYORES, 2);
        Equipo baloncesto = new Equipo("Tigres BC", Deporte.BALONCESTO, Categoria.MAYORES, 5);
        Equipo sub15 = new Equipo("Cachorros", Deporte.FUTBOL, Categoria.SUB15, 10);

        Socio juan = new Socio("juan", "x", "Juan", 20);
        Socio pedro = new Socio("pedro", "x", "Pedro", 22);
        Socio luis = new Socio("luis", "x", "Luis", 19);
        Socio nino = new Socio("nino", "x", "Niño Sub15", 14);

        try {
            juan.inscribirseEquipo(futbol, "Delantero", 10);
            System.out.println("   [OK]      Juan #10 a Leones FC");
        } catch (RuntimeException ex) {
            System.out.println("   [RECHAZO] Juan #10 a Leones FC -> " + ex.getMessage());
        }

        try {
            pedro.inscribirseEquipo(futbol, "Portero", 7);
            System.out.println("   [OK]      Pedro #7 a Leones FC");
        } catch (RuntimeException ex) {
            System.out.println("   [RECHAZO] Pedro #7 a Leones FC -> " + ex.getMessage());
        }

        try {
            luis.inscribirseEquipo(futbol, "Defensa", 9);
            System.out.println("   [OK]      Luis #9 a Leones FC");
        } catch (RuntimeException ex) {
            System.out.println("   [RECHAZO] Luis #9 a Leones FC (cupo lleno) -> " + ex.getMessage());
        }

        try {
            pedro.inscribirseEquipo(futbol, "Medio", 11);
            System.out.println("   [OK]      Pedro #11 a Leones FC");
        } catch (RuntimeException ex) {
            System.out.println("   [RECHAZO] Pedro #11 a Leones FC (segundo equipo mismo deporte) -> " + ex.getMessage());
        }

        try {
            luis.inscribirseEquipo(sub15, "Medio", 10);
            System.out.println("   [OK]      Luis #10 a Cachorros SUB15");
        } catch (RuntimeException ex) {
            System.out.println("   [RECHAZO] Luis #10 a Cachorros SUB15 (edad no corresponde) -> " + ex.getMessage());
        }

        try {
            nino.inscribirseEquipo(sub15, "Medio", 10);
            System.out.println("   [OK]      Niño #10 a Cachorros SUB15 (edad ok)");
        } catch (RuntimeException ex) {
            System.out.println("   [RECHAZO] Niño #10 a Cachorros SUB15 -> " + ex.getMessage());
        }

        try {
            juan.inscribirseEquipo(baloncesto, "Base", 4);
            System.out.println("   [OK]      Juan #4 a Tigres BC (otro deporte, válido)");
        } catch (RuntimeException ex) {
            System.out.println("   [RECHAZO] Juan a Tigres BC -> " + ex.getMessage());
        }

        try {
            pedro.inscribirseEquipo(baloncesto, "Alero", 4);
            System.out.println("   [OK]      Pedro #4 a Tigres BC");
        } catch (RuntimeException ex) {
            System.out.println("   [RECHAZO] Pedro #4 a Tigres BC (camiseta repetida) -> " + ex.getMessage());
        }

        System.out.println("\nNóminas finales:");
        imprimirNomina(futbol);
        imprimirNomina(baloncesto);
        imprimirNomina(sub15);
    }

    private static void imprimirNomina(Equipo e) {
        System.out.println("   " + e);
        for (InscripcionEquipo i : e.getInscripciones()) {
            System.out.println("      #" + i.getNumeroCamiseta() + " " + i.getSocio().getNombre()
                    + " (" + i.getPosicion() + ")");
        }
    }
}
