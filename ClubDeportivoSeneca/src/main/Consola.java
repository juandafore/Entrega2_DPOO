package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.Club;
import modelo.admin.SolicitudCambioTurno;
import modelo.admin.SugerenciaArticulo;
import modelo.admin.TurnoEmpleado;
import modelo.cafeteria.LineaCafeteria;
import modelo.cafeteria.ProductoCafeteria;
import modelo.cafeteria.VentaCafeteria;
import modelo.comun.Categoria;
import modelo.comun.Deporte;
import modelo.comun.DiaSemana;
import modelo.comun.Disciplina;
import modelo.comun.EstadoAsistencia;
import modelo.comun.EstadoSolicitud;
import modelo.comun.EstadoSugerencia;
import modelo.comun.Modalidad;
import modelo.comun.Nivel;
import modelo.comun.TipoSesion;
import modelo.comun.TipoSugerencia;
import modelo.competencia.Competencia;
import modelo.competencia.FilaPosicion;
import modelo.competencia.InscripcionCompetencia;
import modelo.deportesconjunto.Equipo;
import modelo.deportesconjunto.EstadisticaBaloncesto;
import modelo.deportesconjunto.EstadisticaFutbol;
import modelo.deportesconjunto.EstadisticaJugador;
import modelo.deportesconjunto.EstadisticaVoleibol;
import modelo.deportesconjunto.InscripcionEquipo;
import modelo.deportesconjunto.PartidoInterno;
import modelo.deportesconjunto.Suspension;
import modelo.deportesindividuales.InscripcionDisciplina;
import modelo.deportesindividuales.Instalacion;
import modelo.deportesindividuales.PartidoPractica;
import modelo.deportesindividuales.Reserva;
import modelo.entrenamiento.AsistenciaEntrenamiento;
import modelo.entrenamiento.SesionEntrenamiento;
import modelo.tienda.Articulo;
import modelo.tienda.CompraTienda;
import modelo.tienda.LineaTienda;
import modelo.tienda.UbicacionInventario;
import modelo.usuarios.Administrador;
import modelo.usuarios.Empleado;
import modelo.usuarios.Entrenador;
import modelo.usuarios.Fisioterapeuta;
import modelo.usuarios.Socio;
import modelo.usuarios.Usuario;
import persistencia.PersistenciaClub;

public class Consola {

    private static final String CARPETA_DATOS = "datos";
    private static Scanner sc = new Scanner(System.in);
    private static Club club;

    public static void main(String[] args) throws Exception {
        club = PersistenciaClub.cargar(CARPETA_DATOS);
        if (club == null) {
            club = DatosIniciales.crearClubDemo();
            System.out.println("(No había datos guardados; se crearon datos de prueba)");
        }
        System.out.println("=== Club Deportivo Séneca ===");
        System.out.println("Usuarios de prueba: admin/admin, carlos/carlos, fabiola/fabiola, juan/juan\n");

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Iniciar sesión");
            System.out.println("0. Salir");
            int op = leerEntero("Opción: ");
            if (op == 1) {
                iniciarSesion();
            } else if (op == 0) {
                salir = true;
            } else {
                System.out.println("Opción inválida.");
            }
        }

        PersistenciaClub.guardar(club, CARPETA_DATOS);
        System.out.println("Datos guardados. ¡Hasta luego!");
    }

    private static void iniciarSesion() {
        String login = leerTexto("Login: ");
        String pass = leerTexto("Password: ");
        Usuario u = club.autenticar(login, pass);
        if (u == null) {
            System.out.println("Credenciales inválidas.");
            return;
        }
        System.out.println("Bienvenido, " + u.getNombre() + " (" + u.tipo() + ")");
        String tipo = u.tipo();
        if (tipo.equals("Administrador")) {
            menuAdministrador((Administrador) u);
        } else if (tipo.equals("Entrenador")) {
            menuEntrenador((Entrenador) u);
        } else if (tipo.equals("Fisioterapeuta")) {
            menuFisioterapeuta((Fisioterapeuta) u);
        } else if (tipo.equals("Socio")) {
            menuSocio((Socio) u);
        }
    }

    private static void menuSocio(Socio s) {
        boolean cerrar = false;
        while (!cerrar) {
            System.out.println("\n--- MENÚ SOCIO (" + s.getNombre() + ") ---");
            System.out.println("1. Inscribirme a un equipo");
            System.out.println("2. Inscribirme a una disciplina");
            System.out.println("3. Reservar instalación");
            System.out.println("4. Programar partido de práctica");
            System.out.println("5. Consultar mis estadísticas y ranking");
            System.out.println("6. Consultar resultados y tabla de posiciones");
            System.out.println("7. Comprar en la tienda");
            System.out.println("8. Comprar en la cafetería");
            System.out.println("9. Redimir puntos de fidelidad");
            System.out.println("10. Consultar mi historial");
            System.out.println("0. Cerrar sesión");
            int op = leerEntero("Opción: ");
            if (op == 1) {
                socioInscribirseEquipo(s);
            } else if (op == 2) {
                socioInscribirseDisciplina(s);
            } else if (op == 3) {
                socioReservar(s);
            } else if (op == 4) {
                socioProgramarPractica(s);
            } else if (op == 5) {
                socioConsultarEstadisticas(s);
            } else if (op == 6) {
                socioConsultarResultados();
            } else if (op == 7) {
                socioComprarTienda(s);
            } else if (op == 8) {
                socioComprarCafeteria(s);
            } else if (op == 9) {
                socioRedimirPuntos(s);
            } else if (op == 10) {
                socioHistorial(s);
            } else if (op == 0) {
                cerrar = true;
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

    private static void socioInscribirseEquipo(Socio s) {
        Equipo e = seleccionarEquipo();
        if (e == null) {
            return;
        }
        String pos = leerTexto("Posición: ");
        int num = leerEntero("Número de camiseta: ");
        try {
            s.inscribirseEquipo(e, pos, num);
            System.out.println("Inscripción exitosa en " + e.getNombre());
        } catch (RuntimeException ex) {
            System.out.println("No se pudo inscribir: " + ex.getMessage());
        }
    }

    private static void socioInscribirseDisciplina(Socio s) {
        Disciplina d = seleccionarDisciplina();
        Nivel n = seleccionarNivel();
        s.inscribirseDisciplina(d, n);
        System.out.println("Inscrito en " + d + " con nivel " + n);
    }

    private static void socioReservar(Socio s) {
        Instalacion inst = seleccionarInstalacion();
        if (inst == null) {
            return;
        }
        int jug = leerEntero("Número de jugadores: ");
        Modalidad m = seleccionarModalidad();
        Reserva r = new Reserva(s, inst, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), jug, m);
        boolean ok = club.reservar(s, inst, r);
        if (ok) {
            System.out.println("Reserva CONFIRMADA en " + inst.getNombre());
        } else {
            System.out.println("Reserva RECHAZADA (" + r.getEstado() + ")");
        }
    }

    private static void socioProgramarPractica(Socio s) {
        Disciplina d = seleccionarDisciplina();
        Socio rival = seleccionarSocio(s);
        if (rival == null) {
            return;
        }
        try {
            PartidoPractica p = s.programarPractica(rival, d);
            int gl = leerEntero("Sets ganados por " + s.getNombre() + ": ");
            int gv = leerEntero("Sets ganados por " + rival.getNombre() + ": ");
            p.registrarMarcador(gl, gv);
            p.actualizarELO();
            System.out.println("Partido jugado. ELO actualizado:");
            System.out.println("   " + s.getNombre() + ": " + s.inscripcionEn(d).getPuntajeELO());
            System.out.println("   " + rival.getNombre() + ": " + rival.inscripcionEn(d).getPuntajeELO());
        } catch (RuntimeException ex) {
            System.out.println("No se pudo programar: " + ex.getMessage());
        }
    }

    private static void socioConsultarEstadisticas(Socio s) {
        System.out.println("Ranking ELO por disciplina:");
        if (s.getInscripcionesDisciplina().isEmpty()) {
            System.out.println("   (sin disciplinas)");
        }
        for (InscripcionDisciplina i : s.getInscripcionesDisciplina()) {
            System.out.println("   " + i.getDisciplina() + ": nivel " + i.getNivel()
                    + ", ELO " + i.getPuntajeELO());
        }
        System.out.println("Estadísticas de partidos registradas: " + s.consultarEstadisticas().size());
    }

    private static void socioConsultarResultados() {
        System.out.println("Partidos internos:");
        if (club.getPartidosInternos().isEmpty()) {
            System.out.println("   (sin partidos)");
        }
        for (PartidoInterno p : club.getPartidosInternos()) {
            System.out.println("   " + p.getLocal().getNombre() + " " + p.getMarcadorLocal()
                    + "-" + p.getMarcadorVisitante() + " " + p.getVisitante().getNombre());
        }
        System.out.println("Tablas de posiciones:");
        for (Competencia c : club.getCompetencias()) {
            System.out.println("   " + c.getNombre() + ":");
            for (FilaPosicion f : c.getTabla().ordenar()) {
                System.out.println("      " + f.getInscripcion().getEquipo().getNombre()
                        + " Pts=" + f.getPuntos());
            }
        }
    }

    private static void socioComprarTienda(Socio s) {
        CompraTienda c = new CompraTienda(s);
        llenarCompraTienda(c);
        if (c.getLineas().isEmpty()) {
            System.out.println("Compra cancelada (sin artículos).");
            return;
        }
        s.comprarTienda(c);
        c.descontarInventario();
        reciboTienda(c);
    }

    private static void socioComprarCafeteria(Socio s) {
        if (club.getProductosCafeteria().isEmpty()) {
            System.out.println("No hay productos.");
            return;
        }
        VentaCafeteria v = new VentaCafeteria(s);
        boolean seguir = true;
        while (seguir) {
            System.out.println("\nProductos disponibles:");
            List<ProductoCafeteria> prods = club.getProductosCafeteria();
            for (int i = 0; i < prods.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + prods.get(i));
            }
            int idx = leerEntero("Producto (0 para terminar): ");
            if (idx == 0) {
                seguir = false;
            } else if (idx >= 1 && idx <= prods.size()) {
                ProductoCafeteria p = prods.get(idx - 1);
                String[] al = p.informarAlergenos();
                if (al.length > 0) {
                    System.out.print("  Alérgenos: ");
                    for (int k = 0; k < al.length; k++) {
                        System.out.print(al[k] + (k < al.length - 1 ? ", " : ""));
                    }
                    System.out.println();
                }
                int cant = leerEntero("Cantidad: ");
                v.agregarLinea(p, cant);
                System.out.println("Agregado.");
            } else {
                System.out.println("Opción inválida.");
            }
        }
        if (v.getLineas().isEmpty()) {
            System.out.println("Compra cancelada (sin productos).");
            return;
        }
        try {
            s.comprarCafeteria(v);
        } catch (RuntimeException ex) {
            System.out.println("No se pudo completar: " + ex.getMessage());
            return;
        }
        System.out.println("\n--- RECIBO CAFETERÍA ---");
        for (LineaCafeteria l : v.getLineas()) {
            System.out.println("  " + l);
        }
        System.out.println("  Subtotal $" + (long) v.getSubtotal()
                + " | Consumo $" + (long) v.calcularImpuesto()
                + " | Propina $" + (long) v.getPropina()
                + " | TOTAL $" + (long) v.getTotal()
                + " | Puntos +" + v.getPuntosGenerados());
    }

    private static void socioRedimirPuntos(Socio s) {
        System.out.println("Saldo de puntos: " + s.getPuntosFidelidad());
        int p = leerEntero("Puntos a redimir: ");
        try {
            double desc = s.redimirPuntos(p);
            System.out.println("Redimidos " + p + " puntos = $" + (long) desc + " de descuento.");
            System.out.println("Nuevo saldo: " + s.getPuntosFidelidad());
        } catch (RuntimeException ex) {
            System.out.println("No se pudo redimir: " + ex.getMessage());
        }
    }

    private static void socioHistorial(Socio s) {
        List<Object> h = s.consultarHistorial();
        System.out.println("Mi historial (" + h.size() + " elementos):");
        for (Object o : h) {
            System.out.println("   - " + o);
        }
    }

    private static void menuEntrenador(Entrenador e) {
        boolean cerrar = false;
        while (!cerrar) {
            System.out.println("\n--- MENÚ ENTRENADOR (" + e.getNombre() + ") ---");
            System.out.println("1. Planear sesión de entrenamiento");
            System.out.println("2. Registrar asistencia y evaluación de una sesión");
            System.out.println("3. Registrar resultado y estadísticas de un partido");
            System.out.println("4. Gestionar nómina de una competencia");
            System.out.println("5. Sugerir nuevo artículo o snack");
            System.out.println("6. Solicitar cambio de turno");
            System.out.println("7. Comprar en la tienda con mi descuento");
            System.out.println("8. Compartir mi código de descuento");
            System.out.println("0. Cerrar sesión");
            int op = leerEntero("Opción: ");
            if (op == 1) {
                entrenadorPlanearSesion(e);
            } else if (op == 2) {
                entrenadorRegistrarAsistencia(e);
            } else if (op == 3) {
                entrenadorRegistrarResultado(e);
            } else if (op == 4) {
                entrenadorGestionarNomina(e);
            } else if (op == 5) {
                empleadoSugerir(e);
            } else if (op == 6) {
                empleadoSolicitarTurno(e);
            } else if (op == 7) {
                empleadoComprarTienda(e);
            } else if (op == 8) {
                empleadoCompartirCodigo(e);
            } else if (op == 0) {
                cerrar = true;
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

    private static void entrenadorPlanearSesion(Entrenador e) {
        Equipo eq = seleccionarEquipo();
        if (eq == null) {
            return;
        }
        Instalacion inst = club.getInstalaciones().isEmpty() ? null : club.getInstalaciones().get(0);
        SesionEntrenamiento ses = new SesionEntrenamiento(e, LocalDate.now(), LocalTime.of(16, 0),
                90, TipoSesion.GRUPAL, inst);
        e.planearSesion(ses);
        ses.convocarEquipo(eq);
        club.agregarSesion(ses);
        System.out.println("Sesión creada para " + eq.getNombre() + ". Convocados: " + ses.getConvocados().size());
    }

    private static void entrenadorRegistrarAsistencia(Entrenador e) {
        List<SesionEntrenamiento> mias = new ArrayList<SesionEntrenamiento>();
        for (SesionEntrenamiento s : club.getSesiones()) {
            if (s.getResponsable() == e) {
                mias.add(s);
            }
        }
        if (mias.isEmpty()) {
            System.out.println("No tiene sesiones planeadas.");
            return;
        }
        for (int i = 0; i < mias.size(); i++) {
            System.out.println("  " + (i + 1) + ". Sesión " + mias.get(i).getFecha()
                    + " (convocados: " + mias.get(i).getConvocados().size() + ")");
        }
        int idx = leerEntero("Sesión: ");
        if (idx < 1 || idx > mias.size()) {
            System.out.println("Opción inválida.");
            return;
        }
        SesionEntrenamiento ses = mias.get(idx - 1);
        for (Socio s : ses.getConvocados()) {
            int est = leerEntero("Asistencia de " + s.getNombre() + " (1=Asistió, 2=Faltó, 3=Tarde): ");
            EstadoAsistencia ea = EstadoAsistencia.ASISTIO;
            if (est == 2) {
                ea = EstadoAsistencia.FALTO;
            } else if (est == 3) {
                ea = EstadoAsistencia.TARDE;
            }
            AsistenciaEntrenamiento a = ses.registrarAsistencia(s, ea);
            int nota = leerEntero("Nota 1-5 (0 para omitir): ");
            if (nota >= 1 && nota <= 5) {
                e.evaluar(a, nota, leerTexto("Comentario: "));
            }
        }
        System.out.println(ses.resumenAsistencia());
    }

    private static void entrenadorRegistrarResultado(Entrenador e) {
        System.out.println("Equipo LOCAL:");
        Equipo local = seleccionarEquipo();
        System.out.println("Equipo VISITANTE:");
        Equipo visita = seleccionarEquipo();
        if (local == null || visita == null || local == visita) {
            System.out.println("Selección inválida.");
            return;
        }
        PartidoInterno p = new PartidoInterno(local, visita, LocalDate.now(), LocalTime.NOON);
        int gl = leerEntero("Marcador " + local.getNombre() + ": ");
        int gv = leerEntero("Marcador " + visita.getNombre() + ": ");
        try {
            e.registrarResultado(p, gl, gv);
        } catch (RuntimeException ex) {
            System.out.println("No se pudo registrar: " + ex.getMessage());
            return;
        }
        club.agregarPartidoInterno(p);
        System.out.println("Resultado: " + gl + "-" + gv + " (" + p.resultado() + ")");

        boolean seguir = leerEntero("¿Registrar estadísticas de jugadores? (1=sí, 0=no): ") == 1;
        while (seguir) {
            Socio jugador = seleccionarSocioDeEquipo(local);
            if (jugador == null) {
                break;
            }
            EstadisticaJugador est = crearEstadistica(local.getDeporte(), jugador);
            e.registrarEstadistica(p, est);
            jugador.agregarEstadistica(est);
            if (est.generaSuspension()) {
                jugador.agregarSuspension(new Suspension(jugador, "Acumulación de tarjetas", p, null));
                System.out.println("   " + jugador.getNombre() + " queda SUSPENDIDO.");
            }
            seguir = leerEntero("¿Otra estadística? (1=sí, 0=no): ") == 1;
        }
    }

    private static EstadisticaJugador crearEstadistica(Deporte dep, Socio jugador) {
        if (dep == Deporte.FUTBOL) {
            int goles = leerEntero("Goles: ");
            int asist = leerEntero("Asistencias: ");
            int amar = leerEntero("Tarjetas amarillas: ");
            int rojas = leerEntero("Tarjetas rojas: ");
            return new EstadisticaFutbol(jugador, goles, asist, amar, rojas);
        } else if (dep == Deporte.BALONCESTO) {
            int pts = leerEntero("Puntos: ");
            int reb = leerEntero("Rebotes: ");
            int asist = leerEntero("Asistencias: ");
            return new EstadisticaBaloncesto(jugador, pts, reb, asist);
        } else {
            int aces = leerEntero("Aces: ");
            int bloq = leerEntero("Bloqueos: ");
            int pts = leerEntero("Puntos: ");
            return new EstadisticaVoleibol(jugador, aces, bloq, pts);
        }
    }

    private static void entrenadorGestionarNomina(Entrenador e) {
        Competencia c = seleccionarCompetencia();
        if (c == null) {
            return;
        }
        if (c.getInscripciones().isEmpty()) {
            System.out.println("Esa competencia no tiene equipos inscritos.");
            return;
        }
        for (int i = 0; i < c.getInscripciones().size(); i++) {
            System.out.println("  " + (i + 1) + ". " + c.getInscripciones().get(i).getEquipo().getNombre());
        }
        int idx = leerEntero("Inscripción: ");
        if (idx < 1 || idx > c.getInscripciones().size()) {
            System.out.println("Opción inválida.");
            return;
        }
        InscripcionCompetencia ins = c.getInscripciones().get(idx - 1);
        for (InscripcionEquipo ie : ins.getEquipo().getInscripciones()) {
            e.gestionarNomina(ins, ie.getSocio());
        }
        System.out.println("Nómina habilitada: " + ins.getNomina().size()
                + " jugadores. ¿Completa? " + ins.nominaCompleta());
    }

    private static void menuFisioterapeuta(Fisioterapeuta f) {
        boolean cerrar = false;
        while (!cerrar) {
            System.out.println("\n--- MENÚ FISIOTERAPEUTA (" + f.getNombre() + ") ---");
            System.out.println("1. Sugerir nuevo artículo o snack");
            System.out.println("2. Solicitar cambio de turno");
            System.out.println("3. Comprar en la tienda con mi descuento");
            System.out.println("4. Compartir mi código de descuento");
            System.out.println("0. Cerrar sesión");
            int op = leerEntero("Opción: ");
            if (op == 1) {
                empleadoSugerir(f);
            } else if (op == 2) {
                empleadoSolicitarTurno(f);
            } else if (op == 3) {
                empleadoComprarTienda(f);
            } else if (op == 4) {
                empleadoCompartirCodigo(f);
            } else if (op == 0) {
                cerrar = true;
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

    private static void empleadoSugerir(Empleado e) {
        String nombre = leerTexto("Nombre del artículo/snack: ");
        System.out.println("1. Artículo de tienda");
        System.out.println("2. Snack de cafetería");
        int t = leerEntero("Tipo: ");
        TipoSugerencia tipo = (t == 2) ? TipoSugerencia.SNACK : TipoSugerencia.ARTICULO;
        SugerenciaArticulo s = new SugerenciaArticulo(e, nombre, tipo);
        e.sugerirArticulo(s);
        club.registrarSugerencia(s);
        System.out.println("Sugerencia enviada (queda PENDIENTE de aprobación).");
    }

    private static void empleadoSolicitarTurno(Empleado e) {
        List<TurnoEmpleado> mios = new ArrayList<TurnoEmpleado>();
        for (TurnoEmpleado t : club.getTurnos()) {
            if (t.getEmpleado() == e) {
                mios.add(t);
            }
        }
        if (mios.isEmpty()) {
            System.out.println("No tiene turnos asignados.");
            return;
        }
        for (int i = 0; i < mios.size(); i++) {
            TurnoEmpleado t = mios.get(i);
            System.out.println("  " + (i + 1) + ". " + t.getDia() + " " + t.getHoraInicio() + "-" + t.getHoraFin());
        }
        int idx = leerEntero("Turno a cambiar: ");
        if (idx < 1 || idx > mios.size()) {
            System.out.println("Opción inválida.");
            return;
        }
        DiaSemana nuevoDia = seleccionarDia();
        int hi = leerEntero("Nueva hora inicio (0-23): ");
        int hf = leerEntero("Nueva hora fin (0-23): ");
        SolicitudCambioTurno sol = new SolicitudCambioTurno(e, mios.get(idx - 1), nuevoDia,
                LocalTime.of(hi, 0), LocalTime.of(hf, 0));
        e.solicitarTurno(sol);
        club.registrarSolicitud(sol);
        System.out.println("Solicitud enviada (queda PENDIENTE de aprobación del administrador).");
    }

    private static void empleadoComprarTienda(Empleado e) {
        CompraTienda c = new CompraTienda(e);
        llenarCompraTienda(c);
        if (c.getLineas().isEmpty()) {
            System.out.println("Compra cancelada (sin artículos).");
            return;
        }
        if (e.getCodigo() != null) {
            c.aplicarCodigo(e.getCodigo());
        }
        e.comprar(c);
        c.descontarInventario();
        reciboTienda(c);
    }

    private static void empleadoCompartirCodigo(Empleado e) {
        if (e.getCodigo() == null) {
            System.out.println("No tiene código de descuento.");
            return;
        }
        Socio s = seleccionarSocio(null);
        if (s == null) {
            return;
        }
        e.compartirCodigo(s);
        System.out.println("Código compartido con " + s.getNombre() + " (8% de descuento).");
    }

    private static void menuAdministrador(Administrador a) {
        boolean cerrar = false;
        while (!cerrar) {
            System.out.println("\n--- MENÚ ADMINISTRADOR (" + a.getNombre() + ") ---");
            System.out.println("1. Crear equipo");
            System.out.println("2. Dar de baja equipo");
            System.out.println("3. Registrar socio");
            System.out.println("4. Dar de baja socio");
            System.out.println("5. Consultar inventario");
            System.out.println("6. Mover artículo entre ubicaciones");
            System.out.println("7. Reabastecer la tienda");
            System.out.println("8. Aprobar sugerencias de artículos/snacks");
            System.out.println("9. Administrar turnos (resolver solicitudes)");
            System.out.println("10. Inscribir equipo en competencia");
            System.out.println("11. Consultar historial completo");
            System.out.println("12. Generar reporte de ventas");
            System.out.println("13. Generar reporte deportivo");
            System.out.println("0. Cerrar sesión");
            int op = leerEntero("Opción: ");
            if (op == 1) {
                adminCrearEquipo(a);
            } else if (op == 2) {
                adminDarBajaEquipo(a);
            } else if (op == 3) {
                adminRegistrarSocio(a);
            } else if (op == 4) {
                adminDarBajaSocio(a);
            } else if (op == 5) {
                System.out.println(a.reportes().estadoInventario());
            } else if (op == 6) {
                adminMoverArticulo(a);
            } else if (op == 7) {
                adminReabastecer(a);
            } else if (op == 8) {
                adminAprobarSugerencias(a);
            } else if (op == 9) {
                adminAdministrarTurnos(a);
            } else if (op == 10) {
                adminInscribirCompetencia(a);
            } else if (op == 11) {
                Object[] h = a.reportes().historialCompleto();
                System.out.println("Historial completo: " + h.length + " elementos registrados.");
            } else if (op == 12) {
                LocalDate hoy = LocalDate.now();
                System.out.println(a.reportes().reporteVentas(hoy, hoy));
            } else if (op == 13) {
                LocalDate hoy = LocalDate.now();
                System.out.println(a.reportes().reporteDeportivo(hoy, hoy));
            } else if (op == 0) {
                cerrar = true;
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

    private static void adminCrearEquipo(Administrador a) {
        String nombre = leerTexto("Nombre del equipo: ");
        Deporte dep = seleccionarDeporte();
        Categoria cat = seleccionarCategoria();
        int cupo = leerEntero("Cupo máximo: ");
        a.crearEquipo(new Equipo(nombre, dep, cat, cupo));
        System.out.println("Equipo creado. Total equipos: " + club.getEquipos().size());
    }

    private static void adminDarBajaEquipo(Administrador a) {
        Equipo e = seleccionarEquipo();
        if (e == null) {
            return;
        }
        a.darDeBajaEquipo(e);
        System.out.println("Equipo dado de baja. Total equipos: " + club.getEquipos().size());
    }

    private static void adminRegistrarSocio(Administrador a) {
        String login = leerTexto("Login: ");
        String pass = leerTexto("Password: ");
        String nombre = leerTexto("Nombre: ");
        int edad = leerEntero("Edad: ");
        a.registrarSocio(new Socio(login, pass, nombre, edad));
        System.out.println("Socio registrado. Total socios: " + club.getSocios().size());
    }

    private static void adminDarBajaSocio(Administrador a) {
        Socio s = seleccionarSocio(null);
        if (s == null) {
            return;
        }
        a.darDeBajaSocio(s);
        System.out.println("Socio dado de baja. Total socios: " + club.getSocios().size());
    }

    private static void adminMoverArticulo(Administrador a) {
        Articulo art = seleccionarArticulo();
        if (art == null) {
            return;
        }
        if (club.getUbicaciones().isEmpty()) {
            System.out.println("No hay ubicaciones.");
            return;
        }
        List<UbicacionInventario> ubis = club.getUbicaciones();
        for (int i = 0; i < ubis.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + ubis.get(i).getNombre());
        }
        int idx = leerEntero("Ubicación destino: ");
        if (idx < 1 || idx > ubis.size()) {
            System.out.println("Opción inválida.");
            return;
        }
        a.trasladar(art, ubis.get(idx - 1));
        System.out.println(art.getNombre() + " movido a " + art.getUbicacion());
    }

    private static void adminReabastecer(Administrador a) {
        Articulo art = seleccionarArticulo();
        if (art == null) {
            return;
        }
        int cant = leerEntero("Cantidad a agregar: ");
        a.reabastecer(art, cant);
        System.out.println("Nuevo stock: " + art);
    }

    private static void adminAprobarSugerencias(Administrador a) {
        List<SugerenciaArticulo> pend = new ArrayList<SugerenciaArticulo>();
        for (SugerenciaArticulo s : club.getSugerencias()) {
            if (s.getEstado() == EstadoSugerencia.PENDIENTE) {
                pend.add(s);
            }
        }
        if (pend.isEmpty()) {
            System.out.println("No hay sugerencias pendientes.");
            return;
        }
        for (int i = 0; i < pend.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + pend.get(i).getNombre() + " (" + pend.get(i).getTipo() + ")");
        }
        int idx = leerEntero("Sugerencia: ");
        if (idx < 1 || idx > pend.size()) {
            System.out.println("Opción inválida.");
            return;
        }
        boolean aprobar = leerEntero("1=Aprobar, 0=Rechazar: ") == 1;
        a.resolverSugerencia(pend.get(idx - 1), aprobar);
        System.out.println("Sugerencia " + pend.get(idx - 1).getEstado());
    }

    private static void adminAdministrarTurnos(Administrador a) {
        List<SolicitudCambioTurno> pend = new ArrayList<SolicitudCambioTurno>();
        for (SolicitudCambioTurno s : club.getSolicitudes()) {
            if (s.getEstado() == EstadoSolicitud.PENDIENTE) {
                pend.add(s);
            }
        }
        if (pend.isEmpty()) {
            System.out.println("No hay solicitudes pendientes.");
            return;
        }
        for (int i = 0; i < pend.size(); i++) {
            SolicitudCambioTurno s = pend.get(i);
            System.out.println("  " + (i + 1) + ". " + s.getSolicitante().getNombre()
                    + " -> " + s.getNuevoDia() + " " + s.getNuevaHoraInicio() + "-" + s.getNuevaHoraFin());
        }
        int idx = leerEntero("Solicitud: ");
        if (idx < 1 || idx > pend.size()) {
            System.out.println("Opción inválida.");
            return;
        }
        boolean aprobar = leerEntero("1=Aprobar, 0=Rechazar: ") == 1;
        boolean r = a.resolverTurno(pend.get(idx - 1), aprobar);
        System.out.println("Resultado: " + (r ? "APROBADA" : "RECHAZADA (no cumple mínimos o rechazada)"));
    }

    private static void adminInscribirCompetencia(Administrador a) {
        Equipo e = seleccionarEquipo();
        if (e == null) {
            return;
        }
        Competencia c = seleccionarCompetencia();
        if (c == null) {
            return;
        }
        try {
            a.inscribirEquipo(e, c);
            System.out.println(e.getNombre() + " inscrito en " + c.getNombre());
        } catch (RuntimeException ex) {
            System.out.println("No se pudo inscribir: " + ex.getMessage());
        }
    }

    private static void llenarCompraTienda(CompraTienda c) {
        if (club.getArticulos().isEmpty()) {
            System.out.println("No hay artículos.");
            return;
        }
        boolean seguir = true;
        while (seguir) {
            System.out.println("\nArtículos disponibles:");
            List<Articulo> arts = club.getArticulos();
            for (int i = 0; i < arts.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + arts.get(i));
            }
            int idx = leerEntero("Artículo (0 para terminar): ");
            if (idx == 0) {
                seguir = false;
            } else if (idx >= 1 && idx <= arts.size()) {
                int cant = leerEntero("Cantidad: ");
                try {
                    c.agregarLinea(arts.get(idx - 1), cant);
                    System.out.println("Agregado.");
                } catch (RuntimeException ex) {
                    System.out.println("No se pudo agregar: " + ex.getMessage());
                }
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

    private static void reciboTienda(CompraTienda c) {
        c.calcularTotal();
        System.out.println("\n--- RECIBO TIENDA ---");
        for (LineaTienda l : c.getLineas()) {
            System.out.println("  " + l);
        }
        System.out.println("  Subtotal $" + (long) c.getSubtotal()
                + " | IVA $" + (long) c.calcularImpuesto()
                + " | Descuento $" + (long) c.getDescuento()
                + " | TOTAL $" + (long) c.getTotal()
                + " | Puntos +" + c.getPuntosGenerados());
    }

    private static Equipo seleccionarEquipo() {
        if (club.getEquipos().isEmpty()) {
            System.out.println("No hay equipos.");
            return null;
        }
        List<Equipo> eqs = club.getEquipos();
        for (int i = 0; i < eqs.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + eqs.get(i));
        }
        int idx = leerEntero("Equipo: ");
        if (idx < 1 || idx > eqs.size()) {
            System.out.println("Opción inválida.");
            return null;
        }
        return eqs.get(idx - 1);
    }

    private static Socio seleccionarSocio(Socio excluir) {
        List<Socio> socios = new ArrayList<Socio>();
        for (Socio s : club.getSocios()) {
            if (s != excluir) {
                socios.add(s);
            }
        }
        if (socios.isEmpty()) {
            System.out.println("No hay socios disponibles.");
            return null;
        }
        for (int i = 0; i < socios.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + socios.get(i).getNombre());
        }
        int idx = leerEntero("Socio: ");
        if (idx < 1 || idx > socios.size()) {
            System.out.println("Opción inválida.");
            return null;
        }
        return socios.get(idx - 1);
    }

    private static Socio seleccionarSocioDeEquipo(Equipo e) {
        if (e.getInscripciones().isEmpty()) {
            System.out.println("El equipo no tiene jugadores.");
            return null;
        }
        List<InscripcionEquipo> ins = e.getInscripciones();
        for (int i = 0; i < ins.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + ins.get(i).getSocio().getNombre());
        }
        int idx = leerEntero("Jugador: ");
        if (idx < 1 || idx > ins.size()) {
            System.out.println("Opción inválida.");
            return null;
        }
        return ins.get(idx - 1).getSocio();
    }

    private static Instalacion seleccionarInstalacion() {
        if (club.getInstalaciones().isEmpty()) {
            System.out.println("No hay instalaciones.");
            return null;
        }
        List<Instalacion> insts = club.getInstalaciones();
        for (int i = 0; i < insts.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + insts.get(i));
        }
        int idx = leerEntero("Instalación: ");
        if (idx < 1 || idx > insts.size()) {
            System.out.println("Opción inválida.");
            return null;
        }
        return insts.get(idx - 1);
    }

    private static Articulo seleccionarArticulo() {
        if (club.getArticulos().isEmpty()) {
            System.out.println("No hay artículos.");
            return null;
        }
        List<Articulo> arts = club.getArticulos();
        for (int i = 0; i < arts.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + arts.get(i));
        }
        int idx = leerEntero("Artículo: ");
        if (idx < 1 || idx > arts.size()) {
            System.out.println("Opción inválida.");
            return null;
        }
        return arts.get(idx - 1);
    }

    private static Competencia seleccionarCompetencia() {
        if (club.getCompetencias().isEmpty()) {
            System.out.println("No hay competencias.");
            return null;
        }
        List<Competencia> comps = club.getCompetencias();
        for (int i = 0; i < comps.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + comps.get(i).getNombre());
        }
        int idx = leerEntero("Competencia: ");
        if (idx < 1 || idx > comps.size()) {
            System.out.println("Opción inválida.");
            return null;
        }
        return comps.get(idx - 1);
    }

    private static Deporte seleccionarDeporte() {
        Deporte[] vals = Deporte.values();
        for (int i = 0; i < vals.length; i++) {
            System.out.println("  " + (i + 1) + ". " + vals[i]);
        }
        int idx = leerEntero("Deporte: ");
        if (idx < 1 || idx > vals.length) {
            return vals[0];
        }
        return vals[idx - 1];
    }

    private static Categoria seleccionarCategoria() {
        Categoria[] vals = Categoria.values();
        for (int i = 0; i < vals.length; i++) {
            System.out.println("  " + (i + 1) + ". " + vals[i]);
        }
        int idx = leerEntero("Categoría: ");
        if (idx < 1 || idx > vals.length) {
            return vals[0];
        }
        return vals[idx - 1];
    }

    private static Disciplina seleccionarDisciplina() {
        Disciplina[] vals = Disciplina.values();
        for (int i = 0; i < vals.length; i++) {
            System.out.println("  " + (i + 1) + ". " + vals[i]);
        }
        int idx = leerEntero("Disciplina: ");
        if (idx < 1 || idx > vals.length) {
            return vals[0];
        }
        return vals[idx - 1];
    }

    private static Nivel seleccionarNivel() {
        Nivel[] vals = Nivel.values();
        for (int i = 0; i < vals.length; i++) {
            System.out.println("  " + (i + 1) + ". " + vals[i]);
        }
        int idx = leerEntero("Nivel: ");
        if (idx < 1 || idx > vals.length) {
            return vals[0];
        }
        return vals[idx - 1];
    }

    private static Modalidad seleccionarModalidad() {
        Modalidad[] vals = Modalidad.values();
        for (int i = 0; i < vals.length; i++) {
            System.out.println("  " + (i + 1) + ". " + vals[i]);
        }
        int idx = leerEntero("Modalidad: ");
        if (idx < 1 || idx > vals.length) {
            return vals[0];
        }
        return vals[idx - 1];
    }

    private static DiaSemana seleccionarDia() {
        DiaSemana[] vals = DiaSemana.values();
        for (int i = 0; i < vals.length; i++) {
            System.out.println("  " + (i + 1) + ". " + vals[i]);
        }
        int idx = leerEntero("Día: ");
        if (idx < 1 || idx > vals.length) {
            return vals[0];
        }
        return vals[idx - 1];
    }

    private static int leerEntero(String msg) {
        while (true) {
            System.out.print(msg);
            String linea = sc.nextLine();
            try {
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private static String leerTexto(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }
}
