package modelo.competencia;

import java.io.Serializable;

import modelo.comun.ReglaPuntaje;

public class PuntajeSinEmpate implements ReglaPuntaje, Serializable {

    private int victoria = 3;
    private int derrota = 0;

    @Override
    public int puntos(String resultado) {
        switch (resultado) {
            case "VICTORIA":
                return victoria;
            case "DERROTA":
                return derrota;
            default:
                return 0;
        }
    }
}
