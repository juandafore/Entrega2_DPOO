package modelo.tienda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UbicacionInventario implements Serializable {

    private String nombre;
    private final List<Articulo> articulos = new ArrayList<>();

    public UbicacionInventario(String nombre) {
        this.nombre = nombre;
    }

    public void trasladar(Articulo a) {
        UbicacionInventario anterior = a.getUbicacion();
        if (anterior != null) {
            anterior.articulos.remove(a);
        }
        a.setUbicacion(this);
        if (!articulos.contains(a)) {
            articulos.add(a);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
