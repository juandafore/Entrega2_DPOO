package modelo.competencia;

import java.io.Serializable;

public class FilaPosicion implements Serializable {

    private InscripcionCompetencia inscripcion;
    private int puntos;
    private int victorias;
    private int empates;
    private int derrotas;
    private int setsGanados;
    private int setsPerdidos;

    public FilaPosicion(InscripcionCompetencia inscripcion) {
        this.inscripcion = inscripcion;
    }

    public void sumar(String resultado, int puntosGanados) {
        this.puntos += puntosGanados;
        switch (resultado) {
            case "VICTORIA":
                victorias++;
                break;
            case "EMPATE":
                empates++;
                break;
            case "DERROTA":
                derrotas++;
                break;
            default:
                break;
        }
    }

    public void registrarSets(int ganados, int perdidos) {
        this.setsGanados += ganados;
        this.setsPerdidos += perdidos;
    }

    public int diferenciaSets() {
        return setsGanados - setsPerdidos;
    }

    public InscripcionCompetencia getInscripcion() {
        return inscripcion;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getVictorias() {
        return victorias;
    }

    public int getEmpates() {
        return empates;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getSetsGanados() {
        return setsGanados;
    }

    public int getSetsPerdidos() {
        return setsPerdidos;
    }
}
