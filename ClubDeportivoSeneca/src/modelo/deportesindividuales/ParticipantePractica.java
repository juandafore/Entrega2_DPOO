package modelo.deportesindividuales;

import java.io.Serializable;

public class ParticipantePractica implements Serializable {

    private InscripcionDisciplina inscripcion;
    private int aces;
    private int games;
    private int erroresNoForzados;
    private double puntosTotales;
    private boolean ganador;

    public ParticipantePractica(InscripcionDisciplina inscripcion) {
        this.inscripcion = inscripcion;
    }

    public boolean esEntrenador() {
        return inscripcion.getSocio().esEntrenador();
    }

    public void registrarEstadisticas(int aces, int games, int erroresNoForzados, double puntosTotales) {
        this.aces = aces;
        this.games = games;
        this.erroresNoForzados = erroresNoForzados;
        this.puntosTotales = puntosTotales;
    }

    public InscripcionDisciplina getInscripcion() {
        return inscripcion;
    }

    public int getAces() {
        return aces;
    }

    public int getGames() {
        return games;
    }

    public int getErroresNoForzados() {
        return erroresNoForzados;
    }

    public double getPuntosTotales() {
        return puntosTotales;
    }

    public boolean isGanador() {
        return ganador;
    }

    public void setGanador(boolean ganador) {
        this.ganador = ganador;
    }
}
