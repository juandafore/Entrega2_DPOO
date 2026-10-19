package modelo.entrenamiento;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import modelo.comun.EstadoAsistencia;
import modelo.comun.TipoSesion;
import modelo.deportesconjunto.Equipo;
import modelo.deportesconjunto.InscripcionEquipo;
import modelo.deportesindividuales.Instalacion;
import modelo.usuarios.Entrenador;
import modelo.usuarios.Socio;

public class SesionEntrenamiento implements Serializable {

    private LocalDate fecha;
    private LocalTime hora;
    private int duracionMinutos;
    private TipoSesion tipo;
    private Entrenador responsable;
    private Instalacion instalacion;
    private final List<Socio> convocados = new ArrayList<>();
    private final List<AsistenciaEntrenamiento> asistencias = new ArrayList<>();

    public SesionEntrenamiento(Entrenador responsable, LocalDate fecha, LocalTime hora,
            int duracionMinutos, TipoSesion tipo, Instalacion instalacion) {
        this.responsable = responsable;
        this.fecha = fecha;
        this.hora = hora;
        this.duracionMinutos = duracionMinutos;
        this.tipo = tipo;
        this.instalacion = instalacion;
    }

    public void convocar(Socio s) {
        if (!convocados.contains(s)) {
            convocados.add(s);
        }
    }

    public void convocarEquipo(Equipo e) {
        for (InscripcionEquipo i : e.getInscripciones()) {
            convocar(i.getSocio());
        }
    }

    public AsistenciaEntrenamiento registrarAsistencia(Socio s, EstadoAsistencia estado) {
        if (!convocados.contains(s)) {
            throw new IllegalArgumentException(s.getNombre() + " no fue convocado a esta sesión");
        }
        AsistenciaEntrenamiento a = new AsistenciaEntrenamiento(s);
        a.registrar(estado);
        asistencias.add(a);
        return a;
    }

    public String resumenAsistencia() {
        int asistio = 0, falto = 0, tarde = 0;
        for (AsistenciaEntrenamiento a : asistencias) {
            if (a.getEstado() == EstadoAsistencia.ASISTIO) {
                asistio++;
            } else if (a.getEstado() == EstadoAsistencia.FALTO) {
                falto++;
            } else if (a.getEstado() == EstadoAsistencia.TARDE) {
                tarde++;
            }
        }
        return "Convocados: " + convocados.size() + " | Asistió: " + asistio
                + " | Faltó: " + falto + " | Tarde: " + tarde;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public TipoSesion getTipo() {
        return tipo;
    }

    public Entrenador getResponsable() {
        return responsable;
    }

    public Instalacion getInstalacion() {
        return instalacion;
    }

    public List<Socio> getConvocados() {
        return convocados;
    }

    public List<AsistenciaEntrenamiento> getAsistencias() {
        return asistencias;
    }
}
