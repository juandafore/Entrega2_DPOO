package modelo.competencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TablaPosiciones implements Serializable {

    private Competencia competencia;
    private final List<FilaPosicion> filas = new ArrayList<>();

    public TablaPosiciones(Competencia competencia) {
        this.competencia = competencia;
    }

    private FilaPosicion filaDe(InscripcionCompetencia ins) {
        for (FilaPosicion f : filas) {
            if (f.getInscripcion() == ins) {
                return f;
            }
        }
        FilaPosicion nueva = new FilaPosicion(ins);
        filas.add(nueva);
        return nueva;
    }

    public void actualizar(PartidoCompetencia p) {
        InscripcionCompetencia ins = p.getInscripcion();
        if (ins == null) {
            return;
        }
        FilaPosicion fila = filaDe(ins);
        String resultado;
        if (p.getMarcadorLocal() > p.getMarcadorVisitante()) {
            resultado = "VICTORIA";
        } else if (p.getMarcadorLocal() < p.getMarcadorVisitante()) {
            resultado = "DERROTA";
        } else {
            resultado = "EMPATE";
        }
        int pts = competencia.getReglaPuntaje().puntos(resultado);
        fila.sumar(resultado, pts);
        fila.registrarSets(p.getMarcadorLocal(), p.getMarcadorVisitante());
    }

    public List<FilaPosicion> ordenar() {
        List<FilaPosicion> copia = new ArrayList<>(filas);
        for (int i = 0; i < copia.size() - 1; i++) {
            int mejor = i;
            for (int j = i + 1; j < copia.size(); j++) {
                if (esMejor(copia.get(j), copia.get(mejor))) {
                    mejor = j;
                }
            }
            FilaPosicion tmp = copia.get(i);
            copia.set(i, copia.get(mejor));
            copia.set(mejor, tmp);
        }
        return copia;
    }

    private boolean esMejor(FilaPosicion a, FilaPosicion b) {
        if (a.getPuntos() != b.getPuntos()) {
            return a.getPuntos() > b.getPuntos();
        }
        return a.diferenciaSets() > b.diferenciaSets();
    }

    public List<FilaPosicion> getFilas() {
        return filas;
    }

    public Competencia getCompetencia() {
        return competencia;
    }
}
