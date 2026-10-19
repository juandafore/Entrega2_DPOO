package main;

import java.time.LocalDate;
import java.time.LocalTime;

import modelo.Club;
import modelo.comun.Disciplina;
import modelo.comun.Modalidad;
import modelo.comun.Nivel;
import modelo.deportesindividuales.CanchaTenis;
import modelo.deportesindividuales.ConfiguracionDisciplina;
import modelo.deportesindividuales.InscripcionDisciplina;
import modelo.deportesindividuales.PartidoPractica;
import modelo.deportesindividuales.Reserva;
import modelo.usuarios.Entrenador;
import modelo.usuarios.Socio;

public class Programa5 {

    public static void main(String[] args) {
        System.out.println("### PROGRAMA 5: Academias individuales, reservas y ranking ELO ###\n");

        Club club = new Club();
        CanchaTenis t1 = new CanchaTenis("Tenis-1", false);
        CanchaTenis t2 = new CanchaTenis("Tenis-2", false);
        club.agregarInstalacion(t1);
        club.agregarInstalacion(t2);
        club.agregarConfiguracion(new ConfiguracionDisciplina(Disciplina.TENIS, 1));

        Socio ana = registrar(club, "ana", "Ana", 25, Nivel.INTERMEDIO);
        Socio ben = registrar(club, "ben", "Ben", 27, Nivel.AVANZADO);
        Socio cris = registrar(club, "cris", "Cris", 19, Nivel.PRINCIPIANTE);

        System.out.println("-- Reservas --");
        reservar(club, ana, t1, 2);
        reservar(club, ben, t2, 3);
        reservar(club, ben, t2, 4);

        System.out.println("\n-- Partidos de práctica (niveles) --");
        practica(ana, ben, Disciplina.TENIS);
        practica(cris, ben, Disciplina.TENIS);

        Entrenador coach = new Entrenador("coach", "x", "Coach");
        ben.setEntrenadorAsociado(coach);
        System.out.println("(Ben ahora es también entrenador)");
        practica(cris, ben, Disciplina.TENIS);
    }

    private static Socio registrar(Club club, String login, String nombre, int edad, Nivel nivel) {
        Socio s = new Socio(login, "x", nombre, edad);
        s.inscribirseDisciplina(Disciplina.TENIS, nivel);
        club.registrarSocio(s);
        return s;
    }

    private static void reservar(Club club, Socio s, CanchaTenis cancha, int jugadores) {
        Reserva r = new Reserva(s, cancha, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0),
                jugadores, Modalidad.SENCILLOS);
        boolean ok = club.reservar(s, cancha, r);
        System.out.println("   " + s.getNombre() + " reserva " + cancha.getNombre() + " (" + jugadores
                + " jug) -> " + (ok ? "CONFIRMADA" : "RECHAZADA (" + r.getEstado() + ")"));
    }

    private static void practica(Socio a, Socio b, Disciplina d) {
        InscripcionDisciplina ia = a.inscripcionEn(d);
        InscripcionDisciplina ib = b.inscripcionEn(d);
        int eloAntesA = ia.getPuntajeELO();
        int eloAntesB = ib.getPuntajeELO();
        try {
            PartidoPractica p = a.programarPractica(b, d);
            p.registrarMarcador(2, 1);
            p.actualizarELO();
            System.out.println("   " + a.getNombre() + " vs " + b.getNombre() + " -> jugado. "
                    + "ELO " + a.getNombre() + ": " + eloAntesA + "->" + ia.getPuntajeELO()
                    + " | " + b.getNombre() + ": " + eloAntesB + "->" + ib.getPuntajeELO());
        } catch (RuntimeException ex) {
            System.out.println("   [RECHAZO] " + a.getNombre() + " vs " + b.getNombre() + " -> " + ex.getMessage());
        }
    }
}
