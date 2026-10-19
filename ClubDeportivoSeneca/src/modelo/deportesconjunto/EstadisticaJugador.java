package modelo.deportesconjunto;

import java.io.Serializable;

import modelo.usuarios.Socio;

public abstract class EstadisticaJugador implements Serializable {

    protected Socio socio;
    protected Partido partido;

    protected EstadisticaJugador(Socio socio) {
        this.socio = socio;
    }

    public void registrar(Partido partido) {
        this.partido = partido;
    }

    public int golesAnotados() {
        return 0;
    }

    public boolean generaSuspension() {
        return false;
    }

    public Socio getSocio() {
        return socio;
    }

    public Partido getPartido() {
        return partido;
    }
}
