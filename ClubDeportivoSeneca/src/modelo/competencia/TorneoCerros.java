package modelo.competencia;

import modelo.comun.Categoria;
import modelo.comun.Deporte;

public class TorneoCerros extends Competencia {

    private String formatoLiga = "TODOS_CONTRA_TODOS";

    public TorneoCerros(String nombre, String temporada, Deporte deporte, Categoria categoriaPermitida, int nominaMinima) {
        super(nombre, "Torneo de los Cerros", temporada, deporte, categoriaPermitida, nominaMinima);
    }

    public String getFormatoLiga() {
        return formatoLiga;
    }

    public void setFormatoLiga(String formatoLiga) {
        this.formatoLiga = formatoLiga;
    }
}
