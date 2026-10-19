package modelo.deportesconjunto;

import java.io.Serializable;

public class SetPartido implements Serializable {

    private int numero;
    private int puntosLocal;
    private int puntosVisitante;

    public SetPartido(int numero, int puntosLocal, int puntosVisitante) {
        this.numero = numero;
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
    }

    public int ganadorSet() {
        return puntosLocal >= puntosVisitante ? 1 : 2;
    }

    public int getNumero() {
        return numero;
    }

    public int getPuntosLocal() {
        return puntosLocal;
    }

    public int getPuntosVisitante() {
        return puntosVisitante;
    }
}
