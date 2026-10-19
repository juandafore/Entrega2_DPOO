package modelo.admin;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import modelo.comun.Aprobable;
import modelo.comun.DiaSemana;
import modelo.comun.EstadoSolicitud;
import modelo.usuarios.Empleado;

public class SolicitudCambioTurno implements Aprobable, Serializable {

    private Empleado solicitante;
    private TurnoEmpleado turnoOriginal;
    private DiaSemana nuevoDia;
    private LocalTime nuevaHoraInicio;
    private LocalTime nuevaHoraFin;
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

    public SolicitudCambioTurno(Empleado solicitante, TurnoEmpleado turnoOriginal,
            DiaSemana nuevoDia, LocalTime nuevaHoraInicio, LocalTime nuevaHoraFin) {
        this.solicitante = solicitante;
        this.turnoOriginal = turnoOriginal;
        this.nuevoDia = nuevoDia;
        this.nuevaHoraInicio = nuevaHoraInicio;
        this.nuevaHoraFin = nuevaHoraFin;
    }

    @Override
    public void aprobar() {
        turnoOriginal.setDia(nuevoDia);
        turnoOriginal.setHoraInicio(nuevaHoraInicio);
        turnoOriginal.setHoraFin(nuevaHoraFin);
        estado = EstadoSolicitud.APROBADA;
    }

    @Override
    public void rechazar() {
        estado = EstadoSolicitud.RECHAZADA;
    }

    public boolean cumpleMinimos(HorarioAtencion h, List<TurnoEmpleado> turnosTrasCambio) {
        return h.cubreMinimos(turnosTrasCambio);
    }

    public Empleado getSolicitante() {
        return solicitante;
    }

    public TurnoEmpleado getTurnoOriginal() {
        return turnoOriginal;
    }

    public DiaSemana getNuevoDia() {
        return nuevoDia;
    }

    public LocalTime getNuevaHoraInicio() {
        return nuevaHoraInicio;
    }

    public LocalTime getNuevaHoraFin() {
        return nuevaHoraFin;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }
}
