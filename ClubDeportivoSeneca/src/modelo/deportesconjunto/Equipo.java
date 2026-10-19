package modelo.deportesconjunto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import modelo.comun.Categoria;
import modelo.comun.Deporte;
import modelo.usuarios.Socio;

public class Equipo implements Serializable {

    private String nombre;
    private Deporte deporte;
    private Categoria categoria;
    private int cupoMaximo;
    private boolean activo = true;
    private final List<InscripcionEquipo> inscripciones = new ArrayList<>();

    public Equipo(String nombre, Deporte deporte, Categoria categoria, int cupoMaximo) {
        this.nombre = nombre;
        this.deporte = deporte;
        this.categoria = categoria;
        this.cupoMaximo = cupoMaximo;
    }

    public boolean cupoDisponible() {
        return inscripciones.size() < cupoMaximo;
    }

    public boolean camisetaDisponible(int n) {
        for (InscripcionEquipo i : inscripciones) {
            if (i.getNumeroCamiseta() == n) {
                return false;
            }
        }
        return true;
    }

    public InscripcionEquipo inscribir(Socio socio, String posicion, int numeroCamiseta) {
        if (!activo) {
            throw new IllegalStateException("El equipo " + nombre + " está dado de baja");
        }
        if (!cupoDisponible()) {
            throw new IllegalStateException("El equipo " + nombre + " no tiene cupo disponible");
        }
        if (!camisetaDisponible(numeroCamiseta)) {
            throw new IllegalStateException("La camiseta " + numeroCamiseta + " ya está asignada en " + nombre);
        }
        if (!categoria.corresponde(socio.getEdad())) {
            throw new IllegalStateException("La edad de " + socio.getNombre()
                    + " no corresponde a la categoría " + categoria);
        }
        InscripcionEquipo ins = new InscripcionEquipo(socio, this, posicion, numeroCamiseta);
        inscripciones.add(ins);
        return ins;
    }

    public int jugadoresHabilitados() {
        int n = 0;
        for (InscripcionEquipo i : inscripciones) {
            if (!i.getSocio().estaSuspendido()) {
                n++;
            }
        }
        return n;
    }

    public boolean cumpleMinimo(int min) {
        return jugadoresHabilitados() >= min;
    }

    public String getNombre() {
        return nombre;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<InscripcionEquipo> getInscripciones() {
        return inscripciones;
    }

    @Override
    public String toString() {
        return nombre + " [" + deporte + " " + categoria + "] (" + inscripciones.size() + "/" + cupoMaximo + ")";
    }
}
