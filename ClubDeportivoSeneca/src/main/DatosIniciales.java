package main;

import java.time.LocalTime;

import modelo.Club;
import modelo.admin.HorarioAtencion;
import modelo.admin.TurnoEmpleado;
import modelo.cafeteria.Bebida;
import modelo.cafeteria.Snack;
import modelo.comun.Categoria;
import modelo.comun.Deporte;
import modelo.comun.DiaSemana;
import modelo.comun.Disciplina;
import modelo.competencia.TorneoCerros;
import modelo.deportesconjunto.Equipo;
import modelo.deportesindividuales.CanchaPadel;
import modelo.deportesindividuales.CanchaTenis;
import modelo.deportesindividuales.ConfiguracionDisciplina;
import modelo.deportesindividuales.MesaTenis;
import modelo.tienda.Articulo;
import modelo.tienda.CodigoDescuento;
import modelo.tienda.UbicacionInventario;
import modelo.usuarios.Administrador;
import modelo.usuarios.Entrenador;
import modelo.usuarios.Fisioterapeuta;
import modelo.usuarios.Socio;

public class DatosIniciales {

    public static Club crearClubDemo() {
        Club club = new Club();

        Administrador admin = new Administrador("admin", "admin", "Ana Admin");
        club.setAdministrador(admin);

        Entrenador carlos = new Entrenador("carlos", "carlos", "Carlos Coach");
        Entrenador diego = new Entrenador("diego", "diego", "Diego Coach");
        Fisioterapeuta fabiola = new Fisioterapeuta("fabiola", "fabiola", "Fabiola Fisio");
        club.agregarEmpleado(carlos);
        club.agregarEmpleado(diego);
        club.agregarEmpleado(fabiola);

        carlos.setCodigo(new CodigoDescuento("CARLOS15", carlos));
        diego.setCodigo(new CodigoDescuento("DIEGO15", diego));
        fabiola.setCodigo(new CodigoDescuento("FABIOLA15", fabiola));

        Socio juan = new Socio("juan", "juan", "Juan Pérez", 20);
        Socio maria = new Socio("maria", "maria", "María Gómez", 19);
        Socio pedro = new Socio("pedro", "pedro", "Pedro Ruiz", 22);
        club.registrarSocio(juan);
        club.registrarSocio(maria);
        club.registrarSocio(pedro);

        Equipo leones = new Equipo("Leones FC", Deporte.FUTBOL, Categoria.MAYORES, 18);
        Equipo tigres = new Equipo("Tigres BC", Deporte.BALONCESTO, Categoria.MAYORES, 12);
        club.agregarEquipo(leones);
        club.agregarEquipo(tigres);

        club.agregarCompetencia(new TorneoCerros("Torneo de los Cerros 2026", "2026",
                Deporte.FUTBOL, Categoria.MAYORES, 7));

        club.agregarInstalacion(new CanchaTenis("Tenis-1", false));
        club.agregarInstalacion(new CanchaPadel("Padel-1", true));
        club.agregarInstalacion(new MesaTenis("Mesa-1", true));
        club.agregarConfiguracion(new ConfiguracionDisciplina(Disciplina.TENIS, 2));
        club.agregarConfiguracion(new ConfiguracionDisciplina(Disciplina.PADEL, 1));
        club.agregarConfiguracion(new ConfiguracionDisciplina(Disciplina.TENIS_MESA, 1));

        UbicacionInventario bodega = new UbicacionInventario("Bodega central");
        club.agregarUbicacion(bodega);
        Articulo raqueta = new Articulo("Raqueta Pro", "Raquetas", 200000, 10);
        Articulo balon = new Articulo("Balón fútbol", "Balones", 80000, 20);
        Articulo camiseta = new Articulo("Camiseta oficial", "Uniformes", 50000, 30);
        club.agregarArticulo(raqueta);
        club.agregarArticulo(balon);
        club.agregarArticulo(camiseta);
        bodega.trasladar(raqueta);
        bodega.trasladar(balon);
        bodega.trasladar(camiseta);

        club.agregarProductoCafeteria(new Bebida("Café americano", 4000, "Café", true));
        club.agregarProductoCafeteria(new Bebida("Agua", 3000, "Agua", false));
        club.agregarProductoCafeteria(new Snack("Mix de maní", 6000, new String[] {"maní", "gluten"}));

        club.agregarHorario(new HorarioAtencion(DiaSemana.LUNES, LocalTime.of(8, 0), LocalTime.of(18, 0), 2, 1));
        club.registrarTurno(new TurnoEmpleado(carlos, DiaSemana.LUNES, LocalTime.of(8, 0), LocalTime.of(18, 0)));
        club.registrarTurno(new TurnoEmpleado(diego, DiaSemana.LUNES, LocalTime.of(8, 0), LocalTime.of(18, 0)));
        club.registrarTurno(new TurnoEmpleado(fabiola, DiaSemana.LUNES, LocalTime.of(8, 0), LocalTime.of(18, 0)));

        return club;
    }
}
