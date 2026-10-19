package modelo.competencia;

import java.io.Serializable;

import modelo.comun.ReglaPuntaje;

public class PuntajeFutbol implements ReglaPuntaje, Serializable {

    private int victoria = 3;
    private int empate = 1;
    private int derrota = 0;

    @Override
    public int puntos(String resultado) {
        switch (resultado) {
            case "VICTORIA":
                return victoria;
            case "EMPATE":
                return empate;
            case "DERROTA":
                return derrota;
            default:
                return 0;
        }
    }
}
