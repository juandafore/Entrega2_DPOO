package modelo.tienda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import modelo.usuarios.Empleado;
import modelo.usuarios.Socio;
import modelo.usuarios.Usuario;

public class CodigoDescuento implements Serializable {

    private String codigo;
    private int porcentajePropio = 15;
    private int porcentajeCompartido = 8;
    private Empleado dueno;
    private final List<Socio> compartidoCon = new ArrayList<>();

    public CodigoDescuento(String codigo, Empleado dueno) {
        this.codigo = codigo;
        this.dueno = dueno;
    }

    public int porcentajePara(Usuario u) {
        if (u == dueno) {
            return porcentajePropio;
        }
        if (compartidoCon.contains(u)) {
            return porcentajeCompartido;
        }
        return 0;
    }

    public void compartirCon(Socio s) {
        if (!compartidoCon.contains(s)) {
            compartidoCon.add(s);
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public Empleado getDueno() {
        return dueno;
    }

    public List<Socio> getCompartidoCon() {
        return compartidoCon;
    }
}
