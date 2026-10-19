package modelo.competencia;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.deportesconjunto.Equipo;
import modelo.usuarios.Socio;

public class InscripcionCompetencia implements Serializable {

    private Equipo equipo;
    private Competencia competencia;
    private LocalDate fechaInscripcion;
    private final List<Socio> nomina = new ArrayList<>();

    public InscripcionCompetencia(Equipo equipo, Competencia competencia) {
        this.equipo = equipo;
        this.competencia = competencia;
        this.fechaInscripcion = LocalDate.now();
    }

    public void habilitar(Socio s) {
        if (!nomina.contains(s)) {
            nomina.add(s);
        }
    }

    public boolean nominaCompleta() {
        return nomina.size() >= competencia.getNominaMinima();
    }

    public boolean enNomina(Socio s) {
        return nomina.contains(s);
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public Competencia getCompetencia() {
        return competencia;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public List<Socio> getNomina() {
        return nomina;
    }
}
