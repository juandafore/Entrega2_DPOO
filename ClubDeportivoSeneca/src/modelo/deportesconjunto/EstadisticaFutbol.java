package modelo.deportesconjunto;

import modelo.usuarios.Socio;

public class EstadisticaFutbol extends EstadisticaJugador {

    private int goles;
    private int asistencias;
    private int tarjetasAmarillas;
    private int tarjetasRojas;

    public EstadisticaFutbol(Socio socio, int goles, int asistencias, int tarjetasAmarillas, int tarjetasRojas) {
        super(socio);
        this.goles = goles;
        this.asistencias = asistencias;
        this.tarjetasAmarillas = tarjetasAmarillas;
        this.tarjetasRojas = tarjetasRojas;
    }

    public int tarjetasAcumuladas() {
        return tarjetasAmarillas + tarjetasRojas;
    }

    @Override
    public boolean generaSuspension() {
        return tarjetasAmarillas >= 2 || tarjetasRojas >= 1;
    }

    @Override
    public int golesAnotados() {
        return goles;
    }

    public int getGoles() {
        return goles;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public int getTarjetasAmarillas() {
        return tarjetasAmarillas;
    }

    public int getTarjetasRojas() {
        return tarjetasRojas;
    }
}
