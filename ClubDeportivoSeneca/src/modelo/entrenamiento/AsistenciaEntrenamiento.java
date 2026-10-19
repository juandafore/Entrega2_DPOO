package modelo.entrenamiento;

import java.io.Serializable;

import modelo.comun.EstadoAsistencia;
import modelo.usuarios.Socio;

public class AsistenciaEntrenamiento implements Serializable {

    private Socio socio;
    private EstadoAsistencia estado;
    private Integer nota;
    private String comentario;

    public AsistenciaEntrenamiento(Socio socio) {
        this.socio = socio;
    }

    public void registrar(EstadoAsistencia estado) {
        this.estado = estado;
    }

    public void evaluar(int nota, String comentario) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("La nota debe estar entre 1 y 5 (recibida: " + nota + ")");
        }
        this.nota = nota;
        this.comentario = comentario;
    }

    public Socio getSocio() {
        return socio;
    }

    public EstadoAsistencia getEstado() {
        return estado;
    }

    public Integer getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }
}
