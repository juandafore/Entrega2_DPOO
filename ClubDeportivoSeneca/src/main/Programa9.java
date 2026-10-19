package main;

import java.time.LocalDate;
import java.time.LocalTime;

import modelo.Club;
import modelo.admin.HorarioAtencion;
import modelo.admin.SolicitudCambioTurno;
import modelo.admin.SugerenciaArticulo;
import modelo.admin.TurnoEmpleado;
import modelo.cafeteria.Bebida;
import modelo.cafeteria.VentaCafeteria;
import modelo.comun.Categoria;
import modelo.comun.Deporte;
import modelo.comun.DiaSemana;
import modelo.comun.TipoSesion;
import modelo.comun.TipoSugerencia;
import modelo.deportesconjunto.Equipo;
import modelo.deportesconjunto.PartidoInterno;
import modelo.entrenamiento.SesionEntrenamiento;
import modelo.deportesindividuales.CanchaTenis;
import modelo.tienda.Articulo;
import modelo.tienda.CompraTienda;
import modelo.tienda.Mensualidad;
import modelo.tienda.UbicacionInventario;
import modelo.usuarios.Administrador;
import modelo.usuarios.Entrenador;
import modelo.usuarios.Fisioterapeuta;
import modelo.usuarios.Socio;

public class Programa9 {

    public static void main(String[] args) {
        System.out.println("### PROGRAMA 9: Administrador (turnos, inventario y reportes) ###\n");

        Club club = new Club();
        Administrador admin = new Administrador("admin", "x", "Ana Admin");
        club.setAdministrador(admin);

        Entrenador e1 = new Entrenador("e1", "x", "Entrenador 1");
        Entrenador e2 = new Entrenador("e2", "x", "Entrenador 2");
        Fisioterapeuta f1 = new Fisioterapeuta("f1", "x", "Fisio 1");
        club.agregarEmpleado(e1);
        club.agregarEmpleado(e2);
        club.agregarEmpleado(f1);

        club.agregarHorario(new HorarioAtencion(DiaSemana.LUNES, LocalTime.of(8, 0), LocalTime.of(18, 0), 2, 1));
        TurnoEmpleado t1 = new TurnoEmpleado(e1, DiaSemana.LUNES, LocalTime.of(8, 0), LocalTime.of(18, 0));
        TurnoEmpleado t2 = new TurnoEmpleado(e2, DiaSemana.LUNES, LocalTime.of(8, 0), LocalTime.of(18, 0));
        TurnoEmpleado t3 = new TurnoEmpleado(f1, DiaSemana.LUNES, LocalTime.of(8, 0), LocalTime.of(18, 0));
        admin.definirTurno(e1, t1);
        admin.definirTurno(e2, t2);
        admin.definirTurno(f1, t3);

        System.out.println("-- Turnos --");
        SolicitudCambioTurno ok = new SolicitudCambioTurno(e2, t2, DiaSemana.LUNES,
                LocalTime.of(9, 0), LocalTime.of(17, 0));
        System.out.println("   Cambio e2 a lunes 9-17 (mantiene mínimos): aprobado="
                + admin.resolverTurno(ok, true));
        SolicitudCambioTurno malo = new SolicitudCambioTurno(e2, t2, DiaSemana.MARTES,
                LocalTime.of(8, 0), LocalTime.of(18, 0));
        System.out.println("   Cambio e2 a martes (deja lunes con 1 entrenador): aprobado="
                + admin.resolverTurno(malo, true));

        System.out.println("\n-- Equipos y jugadores --");
        Equipo eq = new Equipo("Leones FC", Deporte.FUTBOL, Categoria.MAYORES, 18);
        admin.crearEquipo(eq);
        Socio juan = new Socio("juan", "x", "Juan", 20);
        admin.registrarSocio(juan);
        System.out.println("   Equipos: " + club.getEquipos().size() + " | Socios: " + club.getSocios().size());
        admin.darDeBajaSocio(juan);
        System.out.println("   Tras baja de Juan -> Socios: " + club.getSocios().size());

        System.out.println("\n-- Inventario --");
        UbicacionInventario b1 = new UbicacionInventario("Bodega 1");
        UbicacionInventario b2 = new UbicacionInventario("Bodega 2");
        Articulo balon = new Articulo("Balón fútbol", "Balones", 80000, 10);
        club.agregarArticulo(balon);
        b1.trasladar(balon);
        admin.trasladar(balon, b2);
        admin.reabastecer(balon, 5);
        System.out.println("   " + balon + " | ubicación: " + balon.getUbicacion());

        SugerenciaArticulo sug = new SugerenciaArticulo(e1, "Termo Séneca", TipoSugerencia.ARTICULO);
        admin.resolverSugerencia(sug, true);
        System.out.println("   Sugerencia '" + sug.getNombre() + "' -> " + sug.getEstado()
                + " | artículos ahora: " + club.getArticulos().size());

        Socio comprador = new Socio("compra", "x", "Comprador", 30);
        club.registrarSocio(comprador);
        CompraTienda ct = new CompraTienda(comprador);
        ct.agregarLinea(balon, 1);
        comprador.comprarTienda(ct);
        VentaCafeteria vc = new VentaCafeteria(comprador);
        vc.agregarLinea(new Bebida("Agua", 3000, "Agua", false), 2);
        comprador.comprarCafeteria(vc);
        comprador.comprar(new Mensualidad(comprador, "2026-10", 50000));

        SesionEntrenamiento ses = new SesionEntrenamiento(e1, LocalDate.now(), LocalTime.of(10, 0),
                60, TipoSesion.GRUPAL, new CanchaTenis("Campo", false));
        club.agregarSesion(ses);
        Equipo eq2 = new Equipo("Pumas FC", Deporte.FUTBOL, Categoria.MAYORES, 18);
        for (int i = 1; i <= 7; i++) {
            new Socio("p" + i, "x", "P" + i, 20).inscribirseEquipo(eq, "J", i);
            new Socio("q" + i, "x", "Q" + i, 20).inscribirseEquipo(eq2, "J", i);
        }
        PartidoInterno pi = new PartidoInterno(eq, eq2, LocalDate.now(), LocalTime.NOON);
        pi.registrarMarcador(2, 2);
        club.agregarPartidoInterno(pi);

        System.out.println("\n-- Reportes --");
        LocalDate hoy = LocalDate.now();
        System.out.println(admin.reportes().reporteVentas(hoy, hoy));
        System.out.println();
        System.out.println(admin.reportes().reporteDeportivo(hoy, hoy));
        System.out.println(admin.reportes().estadoInventario());

        System.out.println("-- Restricción --");
        System.out.println("   El Administrador NO hereda de Socio: no expone inscribirseEquipo() ni reservar().");
    }
}
