package modelo.deportesconjunto;

import java.io.Serializable;

import modelo.competencia.Competencia;
import modelo.usuarios.Socio;

public class Suspension implements Serializable {

    private String motivo;
    private boolean cumplida;
    private Socio socio;
    private Partido partidoOrigen;
    private Competencia competencia;

    public Suspension(Socio socio, String motivo, Partido partidoOrigen, Competencia competencia) {
        this.socio = socio;
        this.motivo = motivo;
        this.partidoOrigen = partidoOrigen;
        this.competencia = competencia;
    }

    public boolean estaVigente() {
        return !cumplida;
    }

    public void cumplir() {
        this.cumplida = true;
    }

    public String getMotivo() {
        return motivo;
    }

    public Socio getSocio() {
        return socio;
    }

    public Partido getPartidoOrigen() {
        return partidoOrigen;
    }

    public Competencia getCompetencia() {
        return competencia;
    }
}
