package modelo.usuarios;

import modelo.Club;
import modelo.admin.ServicioReportes;
import modelo.admin.SolicitudCambioTurno;
import modelo.admin.SugerenciaArticulo;
import modelo.admin.TurnoEmpleado;
import modelo.competencia.Competencia;
import modelo.competencia.InscripcionCompetencia;
import modelo.deportesconjunto.Equipo;
import modelo.tienda.Articulo;
import modelo.tienda.UbicacionInventario;

public class Administrador extends Usuario {

    private Club club;

    public Administrador(String login, String password, String nombre) {
        super(login, password, nombre);
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public void crearEquipo(Equipo e) {
        club.agregarEquipo(e);
    }

    public void darDeBajaEquipo(Equipo e) {
        club.eliminarEquipo(e);
    }

    public void registrarSocio(Socio s) {
        club.registrarSocio(s);
    }

    public void darDeBajaSocio(Socio s) {
        club.eliminarSocio(s);
    }

    public void reabastecer(Articulo a, int n) {
        a.reabastecer(n);
    }

    public void trasladar(Articulo a, UbicacionInventario u) {
        u.trasladar(a);
    }

    public void resolverSugerencia(SugerenciaArticulo s, boolean aprobar) {
        if (aprobar) {
            s.aprobar();
            club.incorporarSugerencia(s);
        } else {
            s.rechazar();
        }
    }

    public void definirTurno(Empleado e, TurnoEmpleado t) {
        e.agregarTurno(t);
        club.registrarTurno(t);
    }

    public boolean resolverTurno(SolicitudCambioTurno s, boolean aprobar) {
        return club.resolverCambioTurno(s, aprobar);
    }

    public InscripcionCompetencia inscribirEquipo(Equipo e, Competencia c) {
        return club.inscribirEnCompetencia(e, c);
    }

    public ServicioReportes reportes() {
        return club.getServicioReportes();
    }

    @Override
    public String tipo() {
        return "Administrador";
    }
}
