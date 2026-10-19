package modelo.deportesindividuales;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import modelo.comun.Disciplina;
import modelo.comun.Reservable;

public abstract class Instalacion implements Reservable, Serializable {

    protected String nombre;
    protected boolean techada;
    protected boolean activa = true;
    protected Disciplina disciplina;
    protected final List<Reserva> reservas = new ArrayList<>();

    protected Instalacion(String nombre, boolean techada, Disciplina disciplina) {
        this.nombre = nombre;
        this.techada = techada;
        this.disciplina = disciplina;
    }

    @Override
    public boolean validarJugadores(int n) {
        return disciplina.admiteJugadores(n);
    }

    @Override
    public boolean estaDisponible(LocalDate fecha, LocalTime inicio, LocalTime fin) {
        if (!activa) {
            return false;
        }
        for (Reserva r : reservas) {
            if (r.chocaCon(fecha, inicio, fin)) {
                return false;
            }
        }
        return true;
    }

    public boolean restringeBebidaCaliente() {
        return false;
    }

    public void agregarReserva(Reserva r) {
        reservas.add(r);
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isTechada() {
        return techada;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    @Override
    public String toString() {
        return nombre + " (" + disciplina + (techada ? ", techada" : "") + ")";
    }
}
