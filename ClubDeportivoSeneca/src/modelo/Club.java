package modelo;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import modelo.admin.HorarioAtencion;
import modelo.admin.ServicioReportes;
import modelo.admin.SolicitudCambioTurno;
import modelo.admin.SugerenciaArticulo;
import modelo.admin.TurnoEmpleado;
import modelo.cafeteria.ProductoCafeteria;
import modelo.cafeteria.Snack;
import modelo.comun.DiaSemana;
import modelo.comun.Disciplina;
import modelo.comun.TipoSugerencia;
import modelo.competencia.Competencia;
import modelo.competencia.InscripcionCompetencia;
import modelo.deportesconjunto.Equipo;
import modelo.deportesconjunto.PartidoInterno;
import modelo.entrenamiento.SesionEntrenamiento;
import modelo.deportesindividuales.ConfiguracionDisciplina;
import modelo.deportesindividuales.Instalacion;
import modelo.deportesindividuales.Reserva;
import modelo.tienda.Articulo;
import modelo.tienda.Compra;
import modelo.tienda.UbicacionInventario;
import modelo.usuarios.Administrador;
import modelo.usuarios.Empleado;
import modelo.usuarios.Socio;
import modelo.usuarios.Usuario;

public class Club implements Serializable {

    private String nombre = "Club Deportivo Séneca";

    private final List<Socio> socios = new ArrayList<>();
    private final List<Empleado> empleados = new ArrayList<>();
    private Administrador administrador;

    private final List<Equipo> equipos = new ArrayList<>();
    private final List<Competencia> competencias = new ArrayList<>();
    private final List<PartidoInterno> partidosInternos = new ArrayList<>();

    private final List<Instalacion> instalaciones = new ArrayList<>();
    private final List<ConfiguracionDisciplina> configuraciones = new ArrayList<>();

    private final List<Articulo> articulos = new ArrayList<>();
    private final List<UbicacionInventario> ubicaciones = new ArrayList<>();
    private final List<ProductoCafeteria> productosCafeteria = new ArrayList<>();

    private final List<HorarioAtencion> horarios = new ArrayList<>();
    private final List<TurnoEmpleado> turnos = new ArrayList<>();
    private final List<SugerenciaArticulo> sugerencias = new ArrayList<>();
    private final List<SolicitudCambioTurno> solicitudes = new ArrayList<>();
    private final List<SesionEntrenamiento> sesiones = new ArrayList<>();

    private ServicioReportes servicioReportes;

    public Usuario autenticar(String login, String password) {
        for (Usuario u : todosLosUsuarios()) {
            if (u.autenticar(login, password)) {
                return u;
            }
        }
        return null;
    }

    public List<Usuario> todosLosUsuarios() {
        List<Usuario> todos = new ArrayList<>();
        todos.addAll(socios);
        todos.addAll(empleados);
        if (administrador != null) {
            todos.add(administrador);
        }
        return todos;
    }

    public void agregarEquipo(Equipo e) {
        equipos.add(e);
    }

    public void eliminarEquipo(Equipo e) {
        e.setActivo(false);
        equipos.remove(e);
    }

    public void registrarSocio(Socio s) {
        socios.add(s);
    }

    public void eliminarSocio(Socio s) {
        s.setActivo(false);
        socios.remove(s);
    }

    public void registrarSugerencia(SugerenciaArticulo s) {
        if (!sugerencias.contains(s)) {
            sugerencias.add(s);
        }
    }

    public void incorporarSugerencia(SugerenciaArticulo s) {
        if (s.getTipo() == TipoSugerencia.ARTICULO) {
            articulos.add(new Articulo(s.getNombre(), "nuevo", 0.0, 0));
        } else if (s.getTipo() == TipoSugerencia.SNACK) {
            productosCafeteria.add(new Snack(s.getNombre(), 0.0, new String[0]));
        }
        if (!sugerencias.contains(s)) {
            sugerencias.add(s);
        }
    }

    public void registrarTurno(TurnoEmpleado t) {
        turnos.add(t);
    }

    public void registrarSolicitud(SolicitudCambioTurno s) {
        if (!solicitudes.contains(s)) {
            solicitudes.add(s);
        }
    }

    public boolean resolverCambioTurno(SolicitudCambioTurno s, boolean aprobar) {
        if (!solicitudes.contains(s)) {
            solicitudes.add(s);
        }
        if (!aprobar) {
            s.rechazar();
            return false;
        }
        TurnoEmpleado t = s.getTurnoOriginal();
        DiaSemana oldDia = t.getDia();
        LocalTime oldInicio = t.getHoraInicio();
        LocalTime oldFin = t.getHoraFin();
        s.aprobar();
        boolean ok = true;
        for (HorarioAtencion h : horarios) {
            if (!h.cubreMinimos(turnos)) {
                ok = false;
                break;
            }
        }
        if (!ok) {
            t.setDia(oldDia);
            t.setHoraInicio(oldInicio);
            t.setHoraFin(oldFin);
            s.rechazar();
        }
        return ok;
    }

    public InscripcionCompetencia inscribirEnCompetencia(Equipo e, Competencia c) {
        return c.inscribir(e);
    }

    public ServicioReportes getServicioReportes() {
        if (servicioReportes == null) {
            servicioReportes = new ServicioReportes(this);
        }
        return servicioReportes;
    }

    private ConfiguracionDisciplina configDe(Disciplina d) {
        for (ConfiguracionDisciplina c : configuraciones) {
            if (c.getDisciplina() == d) {
                return c;
            }
        }
        return null;
    }

    private int contarInstalacionesEnUso(Disciplina d) {
        int enUso = 0;
        for (Instalacion i : instalaciones) {
            if (i.getDisciplina() == d) {
                boolean enUsoEsta = false;
                for (Reserva r : i.getReservas()) {
                    if (r.getEstado() == modelo.comun.EstadoReserva.CONFIRMADA) {
                        enUsoEsta = true;
                    }
                }
                if (enUsoEsta) {
                    enUso++;
                }
            }
        }
        return enUso;
    }

    public boolean reservar(Socio socio, Instalacion instalacion, Reserva reserva) {
        if (!instalacion.validarJugadores(reserva.getNumeroJugadores())) {
            reserva.rechazar();
            return false;
        }
        ConfiguracionDisciplina cfg = configDe(instalacion.getDisciplina());
        int enUso = contarInstalacionesEnUso(instalacion.getDisciplina());
        if (cfg != null && !cfg.hayCupo(enUso)) {
            reserva.rechazar();
            return false;
        }
        reserva.confirmar();
        socio.reservar(instalacion, reserva);
        return true;
    }

    public List<Compra> getTodasLasCompras() {
        List<Compra> todas = new ArrayList<>();
        for (Socio s : socios) {
            todas.addAll(s.getCompras());
        }
        for (Empleado e : empleados) {
            todas.addAll(e.getCompras());
        }
        if (administrador != null) {
            todas.addAll(administrador.getCompras());
        }
        return todas;
    }

    public void agregarEmpleado(Empleado e) {
        empleados.add(e);
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
        administrador.setClub(this);
    }

    public void agregarCompetencia(Competencia c) {
        competencias.add(c);
    }

    public void agregarPartidoInterno(PartidoInterno p) {
        partidosInternos.add(p);
    }

    public void agregarInstalacion(Instalacion i) {
        instalaciones.add(i);
    }

    public void agregarConfiguracion(ConfiguracionDisciplina c) {
        configuraciones.add(c);
    }

    public void agregarArticulo(Articulo a) {
        articulos.add(a);
    }

    public void agregarUbicacion(UbicacionInventario u) {
        ubicaciones.add(u);
    }

    public void agregarProductoCafeteria(ProductoCafeteria p) {
        productosCafeteria.add(p);
    }

    public void agregarHorario(HorarioAtencion h) {
        horarios.add(h);
    }

    public void agregarSesion(SesionEntrenamiento s) {
        sesiones.add(s);
    }

    public String getNombre() {
        return nombre;
    }

    public List<Socio> getSocios() {
        return socios;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public List<Competencia> getCompetencias() {
        return competencias;
    }

    public List<PartidoInterno> getPartidosInternos() {
        return partidosInternos;
    }

    public List<Instalacion> getInstalaciones() {
        return instalaciones;
    }

    public List<ConfiguracionDisciplina> getConfiguraciones() {
        return configuraciones;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public List<UbicacionInventario> getUbicaciones() {
        return ubicaciones;
    }

    public List<ProductoCafeteria> getProductosCafeteria() {
        return productosCafeteria;
    }

    public List<HorarioAtencion> getHorarios() {
        return horarios;
    }

    public List<TurnoEmpleado> getTurnos() {
        return turnos;
    }

    public List<SugerenciaArticulo> getSugerencias() {
        return sugerencias;
    }

    public List<SolicitudCambioTurno> getSolicitudes() {
        return solicitudes;
    }

    public List<SesionEntrenamiento> getSesiones() {
        return sesiones;
    }
}
