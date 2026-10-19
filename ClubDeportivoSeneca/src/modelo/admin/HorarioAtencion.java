package modelo.admin;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import modelo.comun.DiaSemana;

public class HorarioAtencion implements Serializable {

    private DiaSemana dia;
    private LocalTime apertura;
    private LocalTime cierre;
    private int minEntrenadores;
    private int minFisioterapeutas;

    public HorarioAtencion(DiaSemana dia, LocalTime apertura, LocalTime cierre,
            int minEntrenadores, int minFisioterapeutas) {
        this.dia = dia;
        this.apertura = apertura;
        this.cierre = cierre;
        this.minEntrenadores = minEntrenadores;
        this.minFisioterapeutas = minFisioterapeutas;
    }

    public boolean cubreMinimos(List<TurnoEmpleado> turnos) {
        int entrenadores = 0;
        int fisios = 0;
        for (TurnoEmpleado t : turnos) {
            if (t.cubre(dia, apertura, cierre)) {
                String tipo = t.getEmpleado().tipo();
                if (tipo.equals("Entrenador")) {
                    entrenadores++;
                } else if (tipo.equals("Fisioterapeuta")) {
                    fisios++;
                }
            }
        }
        return entrenadores >= minEntrenadores && fisios >= minFisioterapeutas;
    }

    public DiaSemana getDia() {
        return dia;
    }

    public LocalTime getApertura() {
        return apertura;
    }

    public LocalTime getCierre() {
        return cierre;
    }

    public int getMinEntrenadores() {
        return minEntrenadores;
    }

    public int getMinFisioterapeutas() {
        return minFisioterapeutas;
    }
}
