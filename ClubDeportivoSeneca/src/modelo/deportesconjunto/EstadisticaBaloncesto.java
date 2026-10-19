package modelo.deportesconjunto;

import modelo.usuarios.Socio;

public class EstadisticaBaloncesto extends EstadisticaJugador {

    private int puntos;
    private int rebotes;
    private int asistencias;

    public EstadisticaBaloncesto(Socio socio, int puntos, int rebotes, int asistencias) {
        super(socio);
        this.puntos = puntos;
        this.rebotes = rebotes;
        this.asistencias = asistencias;
    }

    public double promedioPuntos() {
        return puntos;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getRebotes() {
        return rebotes;
    }

    public int getAsistencias() {
        return asistencias;
    }
}
