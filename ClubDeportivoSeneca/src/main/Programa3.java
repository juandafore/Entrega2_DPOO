package main;

import java.time.LocalDate;
import java.time.LocalTime;

import modelo.comun.Categoria;
import modelo.comun.Deporte;
import modelo.deportesconjunto.EstadisticaFutbol;
import modelo.deportesconjunto.Equipo;
import modelo.deportesconjunto.PartidoInterno;
import modelo.deportesconjunto.Suspension;
import modelo.usuarios.Socio;

public class Programa3 {

    public static void main(String[] args) {
        System.out.println("### PROGRAMA 3: Partidos internos, estadísticas y suspensiones ###\n");

        Equipo local = llenarFutbol("Leones FC", 7);
        Equipo visita = llenarFutbol("Tigres FC", 7);
        Equipo incompleto = llenarFutbol("Patos FC", 5);

        PartidoInterno p1 = new PartidoInterno(local, visita, LocalDate.now(), LocalTime.NOON);
        System.out.println("Partido " + local.getNombre() + " vs " + visita.getNombre()
                + " -> puedeJugarse = " + p1.puedeJugarse());
        p1.registrarMarcador(3, 1);
        System.out.println("   Resultado: " + p1.getMarcadorLocal() + "-" + p1.getMarcadorVisitante()
                + " (" + p1.resultado() + ")");

        Socio goleador = local.getInscripciones().get(0).getSocio();
        EstadisticaFutbol est = new EstadisticaFutbol(goleador, 2, 1, 2, 0);
        p1.agregarEstadistica(est);
        goleador.agregarEstadistica(est);
        System.out.println("\n" + goleador.getNombre() + ": goles=" + est.getGoles()
                + ", amarillas=" + est.getTarjetasAmarillas() + ", ¿suspensión? " + est.generaSuspension());
        if (est.generaSuspension()) {
            goleador.agregarSuspension(new Suspension(goleador, "2 amarillas", p1, null));
            System.out.println("   -> " + goleador.getNombre() + " queda suspendido. Habilitados en "
                    + local.getNombre() + ": " + local.jugadoresHabilitados());
        }

        PartidoInterno p2 = new PartidoInterno(visita, incompleto, LocalDate.now(), LocalTime.NOON);
        System.out.println("\nPartido " + visita.getNombre() + " vs " + incompleto.getNombre()
                + " -> puedeJugarse = " + p2.puedeJugarse());
        try {
            p2.registrarMarcador(1, 0);
        } catch (RuntimeException ex) {
            System.out.println("   [RECHAZO] " + ex.getMessage());
        }
    }

    private static Equipo llenarFutbol(String nombre, int jugadores) {
        Equipo e = new Equipo(nombre, Deporte.FUTBOL, Categoria.MAYORES, 18);
        for (int i = 1; i <= jugadores; i++) {
            Socio s = new Socio(nombre + i, "x", nombre + " Jugador " + i, 20);
            s.inscribirseEquipo(e, "Jugador", i);
        }
        return e;
    }
}
