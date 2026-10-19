package modelo.deportesindividuales;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import modelo.comun.Disciplina;
import modelo.comun.Nivel;
import modelo.usuarios.Socio;

public class InscripcionDisciplina implements Serializable {

    public static final int ELO_INICIAL = 1000;

    private Socio socio;
    private Disciplina disciplina;
    private Nivel nivel;
    private int puntajeELO = ELO_INICIAL;
    private final List<RankingELO> historial = new ArrayList<>();

    public InscripcionDisciplina(Socio socio, Disciplina disciplina, Nivel nivel) {
        this.socio = socio;
        this.disciplina = disciplina;
        this.nivel = nivel;
    }

    public void actualizarELO(int delta, PartidoPractica partido) {
        int antes = puntajeELO;
        puntajeELO += delta;
        historial.add(new RankingELO(antes, puntajeELO, partido));
    }

    public boolean esAdyacente(InscripcionDisciplina o) {
        return nivel.esAdyacente(o.nivel);
    }

    public boolean puedeJugarCon(InscripcionDisciplina o) {
        if (disciplina != o.disciplina) {
            return false;
        }
        return esAdyacente(o) || socio.esEntrenador() || o.socio.esEntrenador();
    }

    public Socio getSocio() {
        return socio;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public int getPuntajeELO() {
        return puntajeELO;
    }

    public List<RankingELO> getHistorial() {
        return historial;
    }
}
