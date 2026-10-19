package modelo.deportesindividuales;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.comun.Disciplina;
import modelo.comun.Disputable;

public class PartidoPractica implements Disputable, Serializable {

    public static final int K_ELO = 24;

    private LocalDate fecha;
    private Disciplina disciplina;
    private int sets;
    private Reserva reserva;
    private final List<ParticipantePractica> participantes = new ArrayList<>();
    private boolean jugado;

    public PartidoPractica(Disciplina disciplina, LocalDate fecha, Reserva reserva) {
        this.disciplina = disciplina;
        this.fecha = fecha;
        this.reserva = reserva;
    }

    public ParticipantePractica agregarParticipante(InscripcionDisciplina inscripcion) {
        ParticipantePractica p = new ParticipantePractica(inscripcion);
        participantes.add(p);
        return p;
    }

    @Override
    public boolean puedeJugarse() {
        if (participantes.size() < 2 || participantes.size() > 4) {
            return false;
        }

        for (int i = 0; i < participantes.size(); i++) {
            for (int j = i + 1; j < participantes.size(); j++) {
                InscripcionDisciplina a = participantes.get(i).getInscripcion();
                InscripcionDisciplina b = participantes.get(j).getInscripcion();
                if (!a.puedeJugarCon(b)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void registrarMarcador(int local, int visitante) {
        this.sets = local + visitante;
        if (participantes.size() >= 2) {
            participantes.get(0).setGanador(local >= visitante);
            participantes.get(1).setGanador(visitante > local);
        }
        this.jugado = true;
    }

    public void actualizarELO() {
        for (ParticipantePractica p : participantes) {
            int delta = p.isGanador() ? K_ELO : -K_ELO;
            p.getInscripcion().actualizarELO(delta, this);
        }
    }

    public List<ParticipantePractica> getParticipantes() {
        return participantes;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public int getSets() {
        return sets;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public boolean isJugado() {
        return jugado;
    }
}
