package modelo.deportesindividuales;

import modelo.comun.Disciplina;

public class MesaTenis extends Instalacion {

    public MesaTenis(String nombre, boolean techada) {
        super(nombre, techada, Disciplina.TENIS_MESA);
    }

    @Override
    public boolean validarJugadores(int n) {
        return n == 2 || n == 4;
    }

    @Override
    public boolean restringeBebidaCaliente() {
        return techada;
    }
}
