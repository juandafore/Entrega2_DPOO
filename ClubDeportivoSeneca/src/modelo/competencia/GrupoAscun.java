package modelo.competencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GrupoAscun implements Serializable {

    private String nombre;
    private final List<InscripcionCompetencia> equipos = new ArrayList<>();

    public GrupoAscun(String nombre) {
        this.nombre = nombre;
    }

    public void agregar(InscripcionCompetencia ins) {
        equipos.add(ins);
    }

    public List<InscripcionCompetencia> clasificados() {
        List<InscripcionCompetencia> orden = new ArrayList<>(equipos);

        return orden.size() <= 2 ? orden : orden.subList(0, 2);
    }

    public List<InscripcionCompetencia> desempateDirecto() {
        return new ArrayList<>(equipos);
    }

    public String getNombre() {
        return nombre;
    }

    public List<InscripcionCompetencia> getEquipos() {
        return equipos;
    }
}
