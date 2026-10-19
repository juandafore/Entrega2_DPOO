package main;

import java.time.LocalDate;
import java.time.LocalTime;

import modelo.comun.Categoria;
import modelo.comun.Deporte;
import modelo.comun.EstadoAsistencia;
import modelo.comun.TipoSesion;
import modelo.deportesconjunto.Equipo;
import modelo.entrenamiento.AsistenciaEntrenamiento;
import modelo.entrenamiento.SesionEntrenamiento;
import modelo.deportesindividuales.CanchaTenis;
import modelo.usuarios.Entrenador;
import modelo.usuarios.Socio;

public class Programa6 {

    public static void main(String[] args) {
        System.out.println("### PROGRAMA 6: Sesiones de entrenamiento y asistencia ###\n");

        Entrenador coach = new Entrenador("coach", "x", "Carlos Coach");
        Equipo equipo = new Equipo("Leones FC", Deporte.FUTBOL, Categoria.MAYORES, 18);
        for (int i = 1; i <= 5; i++) {
            new Socio("j" + i, "x", "Jugador " + i, 20).inscribirseEquipo(equipo, "Jugador", i);
        }

        SesionEntrenamiento sesion = new SesionEntrenamiento(coach, LocalDate.now(), LocalTime.of(16, 0),
                90, TipoSesion.GRUPAL, new CanchaTenis("Campo-1", false));
        coach.planearSesion(sesion);
        sesion.convocarEquipo(equipo);
        System.out.println("Sesión del " + sesion.getFecha() + " | convocados: " + sesion.getConvocados().size());

        Socio j1 = equipo.getInscripciones().get(0).getSocio();
        Socio j2 = equipo.getInscripciones().get(1).getSocio();
        Socio j3 = equipo.getInscripciones().get(2).getSocio();
        AsistenciaEntrenamiento a1 = sesion.registrarAsistencia(j1, EstadoAsistencia.ASISTIO);
        sesion.registrarAsistencia(j2, EstadoAsistencia.TARDE);
        sesion.registrarAsistencia(j3, EstadoAsistencia.FALTO);

        coach.evaluar(a1, 5, "Excelente desempeño");
        System.out.println(j1.getNombre() + " evaluado: nota=" + a1.getNota() + " (" + a1.getComentario() + ")");
        try {
            coach.evaluar(a1, 9, "fuera de rango");
        } catch (RuntimeException ex) {
            System.out.println("[RECHAZO] nota inválida -> " + ex.getMessage());
        }

        System.out.println("\nResumen: " + sesion.resumenAsistencia());
    }
}
