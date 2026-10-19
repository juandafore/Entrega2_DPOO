package modelo.deportesindividuales;

import modelo.comun.Disciplina;

public class CanchaPadel extends Instalacion {

    public CanchaPadel(String nombre, boolean techada) {
        super(nombre, techada, Disciplina.PADEL);
    }

    @Override
    public boolean validarJugadores(int n) {
        return n == 4;
    }

    @Override
    public boolean restringeBebidaCaliente() {
        return techada;
    }
}
