package modelo.deportesindividuales;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import modelo.comun.EstadoReserva;
import modelo.comun.Modalidad;
import modelo.usuarios.Socio;

public class Reserva implements Serializable {

    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int numeroJugadores;
    private Modalidad modalidad;
    private EstadoReserva estado = EstadoReserva.PENDIENTE;
    private Socio socio;
    private Instalacion instalacion;

    public Reserva(Socio socio, Instalacion instalacion, LocalDate fecha, LocalTime horaInicio,
            LocalTime horaFin, int numeroJugadores, Modalidad modalidad) {
        this.socio = socio;
        this.instalacion = instalacion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.numeroJugadores = numeroJugadores;
        this.modalidad = modalidad;
    }

    public void confirmar() {
        this.estado = EstadoReserva.CONFIRMADA;
    }

    public void cancelar() {
        this.estado = EstadoReserva.CANCELADA;
    }

    public void rechazar() {
        this.estado = EstadoReserva.RECHAZADA;
    }

    public boolean validarCupoSimultaneo(int enUso, int maxSimultaneas) {
        return enUso < maxSimultaneas;
    }

    public boolean permiteBebidaCaliente() {
        return !instalacion.restringeBebidaCaliente();
    }

    public boolean chocaCon(LocalDate f, LocalTime ini, LocalTime fin) {
        if (estado != EstadoReserva.CONFIRMADA || !fecha.equals(f)) {
            return false;
        }
        return horaInicio.isBefore(fin) && ini.isBefore(horaFin);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public int getNumeroJugadores() {
        return numeroJugadores;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public Socio getSocio() {
        return socio;
    }

    public Instalacion getInstalacion() {
        return instalacion;
    }
}
