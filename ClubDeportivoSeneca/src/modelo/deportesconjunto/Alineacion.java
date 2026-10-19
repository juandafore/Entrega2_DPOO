package modelo.deportesconjunto;

import java.io.Serializable;

public class Alineacion implements Serializable {

    private InscripcionEquipo inscripcion;
    private boolean titular;
    private Partido partido;

    public Alineacion(InscripcionEquipo inscripcion, boolean titular, Partido partido) {
        this.inscripcion = inscripcion;
        this.titular = titular;
        this.partido = partido;
    }

    public boolean esElegible() {
        return !inscripcion.getSocio().estaSuspendido();
    }

    public InscripcionEquipo getInscripcion() {
        return inscripcion;
    }

    public boolean isTitular() {
        return titular;
    }

    public Partido getPartido() {
        return partido;
    }
}
