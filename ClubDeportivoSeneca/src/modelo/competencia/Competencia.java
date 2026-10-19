package modelo.competencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.comun.Categoria;
import modelo.comun.Deporte;
import modelo.comun.ReglaPuntaje;
import modelo.deportesconjunto.EstadisticaJugador;
import modelo.deportesconjunto.Equipo;
import modelo.usuarios.Socio;

public abstract class Competencia implements Serializable {

    protected String nombre;
    protected String entidadOrganizadora;
    protected String temporada;
    protected Deporte deporte;
    protected Categoria categoriaPermitida;
    protected int nominaMinima;
    protected ReglaPuntaje reglaPuntaje;
    protected final List<InscripcionCompetencia> inscripciones = new ArrayList<>();
    protected final List<PartidoCompetencia> partidos = new ArrayList<>();
    protected TablaPosiciones tabla;

    protected Competencia(String nombre, String entidadOrganizadora, String temporada,
            Deporte deporte, Categoria categoriaPermitida, int nominaMinima) {
        this.nombre = nombre;
        this.entidadOrganizadora = entidadOrganizadora;
        this.temporada = temporada;
        this.deporte = deporte;
        this.categoriaPermitida = categoriaPermitida;
        this.nominaMinima = nominaMinima;
        this.reglaPuntaje = deporte.admiteEmpate() ? new PuntajeFutbol() : new PuntajeSinEmpate();
        this.tabla = new TablaPosiciones(this);
    }

    public boolean validarRequisitos(Equipo e) {
        return e.getDeporte() == deporte
                && e.getCategoria() == categoriaPermitida
                && e.jugadoresHabilitados() >= nominaMinima;
    }

    public InscripcionCompetencia inscribir(Equipo e) {
        if (!validarRequisitos(e)) {
            throw new IllegalStateException("El equipo " + e.getNombre()
                    + " no cumple los requisitos de " + nombre);
        }
        InscripcionCompetencia ins = new InscripcionCompetencia(e, this);
        inscripciones.add(ins);
        return ins;
    }

    public void agregarPartido(PartidoCompetencia p) {
        partidos.add(p);
    }

    public void actualizarTabla(PartidoCompetencia p) {
        tabla.actualizar(p);
    }

    public Socio maximoGoleador() {
        HashMap<Socio, Integer> goles = new HashMap<Socio, Integer>();
        for (PartidoCompetencia p : partidos) {
            for (EstadisticaJugador est : p.getEstadisticas()) {
                if (est.golesAnotados() > 0) {
                    Socio s = est.getSocio();
                    int acumulado = 0;
                    if (goles.containsKey(s)) {
                        acumulado = goles.get(s);
                    }
                    goles.put(s, acumulado + est.golesAnotados());
                }
            }
        }
        Socio mejor = null;
        int max = -1;
        for (Socio s : goles.keySet()) {
            int g = goles.get(s);
            if (g > max) {
                max = g;
                mejor = s;
            }
        }
        return mejor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEntidadOrganizadora() {
        return entidadOrganizadora;
    }

    public String getTemporada() {
        return temporada;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public Categoria getCategoriaPermitida() {
        return categoriaPermitida;
    }

    public int getNominaMinima() {
        return nominaMinima;
    }

    public ReglaPuntaje getReglaPuntaje() {
        return reglaPuntaje;
    }

    public TablaPosiciones getTabla() {
        return tabla;
    }

    public List<InscripcionCompetencia> getInscripciones() {
        return inscripciones;
    }

    public List<PartidoCompetencia> getPartidos() {
        return partidos;
    }
}
