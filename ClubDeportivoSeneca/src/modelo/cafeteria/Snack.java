package modelo.cafeteria;

import java.util.Arrays;

public class Snack extends ProductoCafeteria {

    private String[] alergenos;

    public Snack(String nombre, double precio, String[] alergenos) {
        super(nombre, precio);
        this.alergenos = alergenos != null ? alergenos : new String[0];
    }

    public String[] informarAlergenos() {
        return alergenos;
    }

    @Override
    public String toString() {
        return super.toString() + " [alérgenos: " + Arrays.toString(alergenos) + "]";
    }
}
