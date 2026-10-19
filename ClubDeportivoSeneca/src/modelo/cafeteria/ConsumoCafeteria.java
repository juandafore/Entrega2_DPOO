package modelo.cafeteria;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modelo.deportesindividuales.Instalacion;
import modelo.usuarios.Socio;

public class ConsumoCafeteria implements Serializable {

    private LocalDateTime inicio;
    private LocalDateTime fin;
    private Socio socio;
    private final List<Bebida> bebidas = new ArrayList<>();

    public ConsumoCafeteria(Socio socio) {
        this.socio = socio;
        this.inicio = LocalDateTime.now();
    }

    public void agregarBebida(Bebida b) {
        bebidas.add(b);
    }

    public boolean estaActivo() {
        return fin == null;
    }

    public void cerrar() {
        this.fin = LocalDateTime.now();
    }

    public boolean tieneBebidaCaliente() {
        for (Bebida b : bebidas) {
            if (b.esCaliente()) {
                return true;
            }
        }
        return false;
    }

    public boolean permiteIngreso(Instalacion i) {
        return !(tieneBebidaCaliente() && i.restringeBebidaCaliente());
    }

    public Socio getSocio() {
        return socio;
    }

    public List<Bebida> getBebidas() {
        return bebidas;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }
}
