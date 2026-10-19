package modelo.usuarios;

import modelo.competencia.InscripcionCompetencia;
import modelo.deportesconjunto.EstadisticaJugador;
import modelo.deportesconjunto.Partido;
import modelo.entrenamiento.AsistenciaEntrenamiento;
import modelo.entrenamiento.SesionEntrenamiento;

public class Entrenador extends Empleado {

    public Entrenador(String login, String password, String nombre) {
        super(login, password, nombre);
    }

    public void planearSesion(SesionEntrenamiento s) {

    }

    public void registrarAsistencia(AsistenciaEntrenamiento a) {

    }

    public void evaluar(AsistenciaEntrenamiento a, int nota, String comentario) {
        a.evaluar(nota, comentario);
    }

    public void registrarResultado(Partido p, int local, int visitante) {
        p.registrarMarcador(local, visitante);
    }

    public void registrarEstadistica(Partido p, EstadisticaJugador e) {
        p.agregarEstadistica(e);
    }

    public void gestionarNomina(InscripcionCompetencia i, Socio s) {
        i.habilitar(s);
    }

    @Override
    public String tipo() {
        return "Entrenador";
    }
}
