package modelo.deportesindividuales;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RankingELO implements Serializable {

    private LocalDateTime fecha;
    private int puntajeAntes;
    private int puntajeDespues;
    private PartidoPractica partido;

    public RankingELO(int puntajeAntes, int puntajeDespues, PartidoPractica partido) {
        this.fecha = LocalDateTime.now();
        this.puntajeAntes = puntajeAntes;
        this.puntajeDespues = puntajeDespues;
        this.partido = partido;
    }

    public int variacion() {
        return puntajeDespues - puntajeAntes;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public int getPuntajeAntes() {
        return puntajeAntes;
    }

    public int getPuntajeDespues() {
        return puntajeDespues;
    }

    public PartidoPractica getPartido() {
        return partido;
    }
}
