package modelo.usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.cafeteria.VentaCafeteria;
import modelo.comun.Categoria;
import modelo.comun.Deporte;
import modelo.comun.Disciplina;
import modelo.comun.Nivel;
import modelo.deportesconjunto.EstadisticaJugador;
import modelo.deportesconjunto.Equipo;
import modelo.deportesconjunto.InscripcionEquipo;
import modelo.deportesconjunto.Suspension;
import modelo.deportesindividuales.InscripcionDisciplina;
import modelo.deportesindividuales.Instalacion;
import modelo.deportesindividuales.PartidoPractica;
import modelo.deportesindividuales.Reserva;
import modelo.tienda.CompraTienda;

public class Socio extends Usuario {

    private int edad;
    private int puntosFidelidad;
    private Entrenador entrenadorAsociado;

    private final List<InscripcionEquipo> inscripcionesEquipo = new ArrayList<>();
    private final List<InscripcionDisciplina> inscripcionesDisciplina = new ArrayList<>();
    private final List<Reserva> reservas = new ArrayList<>();
    private final List<EstadisticaJugador> estadisticas = new ArrayList<>();
    private final List<Suspension> suspensiones = new ArrayList<>();

    public Socio(String login, String password, String nombre, int edad) {
        super(login, password, nombre);
        this.edad = edad;
    }

    public InscripcionEquipo inscribirseEquipo(Equipo e, String posicion, int numeroCamiseta) {
        if (perteneceAEquipo(e.getDeporte())) {
            throw new IllegalStateException(nombre + " ya pertenece a un equipo de " + e.getDeporte());
        }
        InscripcionEquipo ins = e.inscribir(this, posicion, numeroCamiseta);
        inscripcionesEquipo.add(ins);
        return ins;
    }

    public boolean perteneceAEquipo(Deporte d) {
        for (InscripcionEquipo i : inscripcionesEquipo) {
            if (i.getEquipo().getDeporte() == d) {
                return true;
            }
        }
        return false;
    }

    public InscripcionDisciplina inscribirseDisciplina(Disciplina d, Nivel nivel) {
        InscripcionDisciplina ins = new InscripcionDisciplina(this, d, nivel);
        inscripcionesDisciplina.add(ins);
        return ins;
    }

    public InscripcionDisciplina inscripcionEn(Disciplina d) {
        for (InscripcionDisciplina i : inscripcionesDisciplina) {
            if (i.getDisciplina() == d) {
                return i;
            }
        }
        return null;
    }

    public void reservar(Instalacion i, Reserva r) {
        i.agregarReserva(r);
        reservas.add(r);
    }

    public PartidoPractica programarPractica(Socio rival, Disciplina d) {
        InscripcionDisciplina mia = inscripcionEn(d);
        InscripcionDisciplina suya = rival.inscripcionEn(d);
        if (mia == null || suya == null) {
            throw new IllegalStateException("Ambos socios deben estar inscritos en " + d);
        }
        PartidoPractica p = new PartidoPractica(d, LocalDate.now(), null);
        p.agregarParticipante(mia);
        p.agregarParticipante(suya);
        if (!p.puedeJugarse()) {
            throw new IllegalStateException("Niveles incompatibles para el partido de práctica");
        }
        return p;
    }

    public void comprarTienda(CompraTienda c) {
        c.calcularTotal();
        int pts = c.generarPuntos();
        comprar(c);
        agregarPuntos(pts);
    }

    public void comprarCafeteria(VentaCafeteria v) {
        if (!v.validarBebidaCaliente()) {
            throw new IllegalStateException("No se puede despachar bebida caliente en esta instalación");
        }
        v.calcularTotal();
        int pts = v.generarPuntos();
        comprar(v);
        agregarPuntos(pts);
    }

    public void agregarPuntos(int p) {
        puntosFidelidad += p;
    }

    public double redimirPuntos(int p) {
        if (p > puntosFidelidad) {
            throw new IllegalStateException("Puntos insuficientes (saldo: " + puntosFidelidad + ")");
        }
        puntosFidelidad -= p;
        return p;
    }

    public List<EstadisticaJugador> consultarEstadisticas() {
        return new ArrayList<>(estadisticas);
    }

    public List<Object> consultarHistorial() {
        List<Object> h = new ArrayList<>();
        h.addAll(compras);
        h.addAll(reservas);
        h.addAll(inscripcionesEquipo);
        h.addAll(inscripcionesDisciplina);
        return h;
    }

    public Categoria categoriaPorEdad() {
        return Categoria.porEdad(edad);
    }

    public boolean esEntrenador() {
        return entrenadorAsociado != null;
    }

    public void agregarEstadistica(EstadisticaJugador e) {
        estadisticas.add(e);
    }

    public void agregarSuspension(Suspension s) {
        suspensiones.add(s);
    }

    public boolean estaSuspendido() {
        for (Suspension s : suspensiones) {
            if (s.estaVigente()) {
                return true;
            }
        }
        return false;
    }

    public int getEdad() {
        return edad;
    }

    public int getPuntosFidelidad() {
        return puntosFidelidad;
    }

    public Entrenador getEntrenadorAsociado() {
        return entrenadorAsociado;
    }

    public void setEntrenadorAsociado(Entrenador entrenadorAsociado) {
        this.entrenadorAsociado = entrenadorAsociado;
    }

    public List<InscripcionEquipo> getInscripcionesEquipo() {
        return inscripcionesEquipo;
    }

    public List<InscripcionDisciplina> getInscripcionesDisciplina() {
        return inscripcionesDisciplina;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public List<Suspension> getSuspensiones() {
        return suspensiones;
    }

    @Override
    public String tipo() {
        return "Socio";
    }
}
