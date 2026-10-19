package main;

import java.time.LocalDate;
import java.time.LocalTime;

import modelo.comun.Categoria;
import modelo.comun.Deporte;
import modelo.competencia.ASCUN;
import modelo.competencia.Competencia;
import modelo.competencia.FilaPosicion;
import modelo.competencia.InscripcionCompetencia;
import modelo.competencia.PartidoCompetencia;
import modelo.competencia.TorneoCerros;
import modelo.deportesconjunto.Alineacion;
import modelo.deportesconjunto.EstadisticaFutbol;
import modelo.deportesconjunto.Equipo;
import modelo.deportesconjunto.InscripcionEquipo;
import modelo.usuarios.Socio;

public class Programa4 {

    public static void main(String[] args) {
        String cual = args.length > 0 ? args[0] : "TorneoCerros";
        System.out.println("### PROGRAMA 4: Competencias (" + cual + ") ###\n");

        Equipo equipo = new Equipo("Leones FC", Deporte.FUTBOL, Categoria.MAYORES, 18);
        for (int i = 1; i <= 8; i++) {
            new Socio("ln" + i, "x", "León " + i, 21).inscribirseEquipo(equipo, "Jugador", i);
        }

        if (cual.equalsIgnoreCase("ASCUN")) {
            demostrarAscun(equipo);
        } else {
            demostrarTorneoCerros(equipo);
        }
    }

    private static void demostrarTorneoCerros(Equipo equipo) {
        TorneoCerros torneo = new TorneoCerros("Cerros 2026", "2026", Deporte.FUTBOL, Categoria.MAYORES, 7);
        InscripcionCompetencia ins = torneo.inscribir(equipo);
        for (InscripcionEquipo ie : equipo.getInscripciones()) {
            ins.habilitar(ie.getSocio());
        }
        System.out.println("Inscripción válida. Nómina completa: " + ins.nominaCompleta());

        jugar(torneo, ins, "Universidad A", 3, 1);
        jugar(torneo, ins, "Universidad B", 2, 2);
        jugar(torneo, ins, "Universidad C", 0, 1);

        System.out.println("\nTabla de posiciones:");
        for (FilaPosicion f : torneo.getTabla().ordenar()) {
            System.out.println("   " + f.getInscripcion().getEquipo().getNombre()
                    + " | Pts=" + f.getPuntos()
                    + " V=" + f.getVictorias() + " E=" + f.getEmpates() + " D=" + f.getDerrotas()
                    + " difSets=" + f.diferenciaSets());
        }
        Socio gol = torneo.maximoGoleador();
        System.out.println("Máximo goleador: " + (gol != null ? gol.getNombre() : "N/A"));
    }

    private static void jugar(Competencia comp, InscripcionCompetencia ins, String rival, int gf, int gc) {
        PartidoCompetencia p = new PartidoCompetencia(ins, rival, "Estadio Séneca", LocalDate.now(), LocalTime.NOON);
        comp.agregarPartido(p);

        for (InscripcionEquipo ie : ins.getEquipo().getInscripciones()) {
            p.alinear(new Alineacion(ie, true, p));
        }
        p.registrarMarcador(gf, gc);

        Socio delantero = ins.getEquipo().getInscripciones().get(0).getSocio();
        p.agregarEstadistica(new EstadisticaFutbol(delantero, gf, 0, 0, 0));
        comp.actualizarTabla(p);
        System.out.println("   vs " + rival + ": " + gf + "-" + gc + " (" + p.resultado() + ")");
    }

    private static void demostrarAscun(Equipo equipo) {
        ASCUN ascun = new ASCUN("ASCUN 2026", "2026", Deporte.FUTBOL, Categoria.MAYORES, 7);
        InscripcionCompetencia ins = ascun.inscribir(equipo);
        for (InscripcionEquipo ie : equipo.getInscripciones()) {
            ins.habilitar(ie.getSocio());
        }
        ascun.crearGrupo("Grupo A").agregar(ins);
        System.out.println("Fase inicial: " + ascun.getFaseActual());
        jugar(ascun, ins, "Universidad X", 2, 0);
        ascun.avanzarFase();
        System.out.println("Tras fase de grupos: " + ascun.getFaseActual());
        jugar(ascun, ins, "Universidad Y (semifinal)", 1, 0);
        ascun.avanzarFase();
        System.out.println("Fase final: " + ascun.getFaseActual());
    }
}
