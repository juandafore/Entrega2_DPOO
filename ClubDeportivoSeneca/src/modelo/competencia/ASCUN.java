package modelo.competencia;

import java.util.ArrayList;
import java.util.List;

import modelo.comun.Categoria;
import modelo.comun.Deporte;
import modelo.comun.FaseAscun;

public class ASCUN extends Competencia {

    private FaseAscun faseActual = FaseAscun.GRUPOS;
    private final List<GrupoAscun> grupos = new ArrayList<>();

    public ASCUN(String nombre, String temporada, Deporte deporte, Categoria categoriaPermitida, int nominaMinima) {
        super(nombre, "ASCUN", temporada, deporte, categoriaPermitida, nominaMinima);
    }

    public void avanzarFase() {
        switch (faseActual) {
            case GRUPOS:
                faseActual = FaseAscun.ELIMINACION;
                break;
            case ELIMINACION:
                faseActual = FaseAscun.FINALIZADA;
                break;
            default:
                break;
        }
    }

    public GrupoAscun crearGrupo(String nombre) {
        GrupoAscun g = new GrupoAscun(nombre);
        grupos.add(g);
        return g;
    }

    public FaseAscun getFaseActual() {
        return faseActual;
    }

    public List<GrupoAscun> getGrupos() {
        return grupos;
    }
}
