package modelo.deportesindividuales;

import modelo.comun.Disciplina;

public class CanchaTenis extends Instalacion {

    public CanchaTenis(String nombre, boolean techada) {
        super(nombre, techada, Disciplina.TENIS);
    }

    @Override
    public boolean validarJugadores(int n) {
        return n == 2 || n == 4;
    }
}
