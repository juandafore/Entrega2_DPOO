package modelo.deportesconjunto;

import modelo.usuarios.Socio;

public class EstadisticaVoleibol extends EstadisticaJugador {

    private int aces;
    private int bloqueos;
    private int puntos;

    public EstadisticaVoleibol(Socio socio, int aces, int bloqueos, int puntos) {
        super(socio);
        this.aces = aces;
        this.bloqueos = bloqueos;
        this.puntos = puntos;
    }

    public int totalPuntos() {
        return aces + bloqueos + puntos;
    }

    public int getAces() {
        return aces;
    }

    public int getBloqueos() {
        return bloqueos;
    }

    public int getPuntos() {
        return puntos;
    }
}
