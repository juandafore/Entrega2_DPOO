package modelo.deportesconjunto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import modelo.comun.Disputable;
import modelo.comun.EstadoPartido;

public abstract class Partido implements Disputable, Serializable {

    protected LocalDate fecha;
    protected LocalTime hora;
    protected EstadoPartido estado = EstadoPartido.PROGRAMADO;
    protected int marcadorLocal;
    protected int marcadorVisitante;
    protected Equipo local;
    protected Equipo visitante;
    protected final List<SetPartido> sets = new ArrayList<>();
    protected final List<EstadisticaJugador> estadisticas = new ArrayList<>();

    protected Partido(Equipo local, Equipo visitante, LocalDate fecha, LocalTime hora) {
        this.local = local;
        this.visitante = visitante;
        this.fecha = fecha;
        this.hora = hora;
    }

    @Override
    public boolean puedeJugarse() {
        int min = local.getDeporte().getMinJugadores();
        return local.cumpleMinimo(min) && visitante.cumpleMinimo(min);
    }

    @Override
    public void registrarMarcador(int local, int visitante) {
        if (!puedeJugarse()) {
            this.estado = EstadoPartido.NO_DISPUTABLE;
            throw new IllegalStateException("El partido no puede jugarse: un equipo no alcanza el mínimo");
        }
        this.marcadorLocal = local;
        this.marcadorVisitante = visitante;
        this.estado = EstadoPartido.JUGADO;
    }

    public SetPartido registrarSet(int puntosLocal, int puntosVisitante) {
        SetPartido s = new SetPartido(sets.size() + 1, puntosLocal, puntosVisitante);
        sets.add(s);
        if (s.ganadorSet() == 1) {
            marcadorLocal++;
        } else {
            marcadorVisitante++;
        }
        this.estado = EstadoPartido.JUGADO;
        return s;
    }

    public void agregarEstadistica(EstadisticaJugador e) {
        e.registrar(this);
        estadisticas.add(e);
    }

    public String resultado() {
        if (marcadorLocal > marcadorVisitante) {
            return "VICTORIA_LOCAL";
        }
        if (marcadorVisitante > marcadorLocal) {
            return "VICTORIA_VISITANTE";
        }
        return "EMPATE";
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public EstadoPartido getEstado() {
        return estado;
    }

    public int getMarcadorLocal() {
        return marcadorLocal;
    }

    public int getMarcadorVisitante() {
        return marcadorVisitante;
    }

    public Equipo getLocal() {
        return local;
    }

    public Equipo getVisitante() {
        return visitante;
    }

    public List<SetPartido> getSets() {
        return sets;
    }

    public List<EstadisticaJugador> getEstadisticas() {
        return estadisticas;
    }
}
