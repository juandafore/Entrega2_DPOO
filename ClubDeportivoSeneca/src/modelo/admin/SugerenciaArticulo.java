package modelo.admin;

import java.io.Serializable;

import modelo.comun.Aprobable;
import modelo.comun.EstadoSugerencia;
import modelo.comun.TipoSugerencia;
import modelo.usuarios.Empleado;

public class SugerenciaArticulo implements Aprobable, Serializable {

    private String nombre;
    private TipoSugerencia tipo;
    private EstadoSugerencia estado = EstadoSugerencia.PENDIENTE;
    private Empleado autor;

    public SugerenciaArticulo(Empleado autor, String nombre, TipoSugerencia tipo) {
        this.autor = autor;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    @Override
    public void aprobar() {
        estado = EstadoSugerencia.APROBADA;
    }

    @Override
    public void rechazar() {
        estado = EstadoSugerencia.RECHAZADA;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoSugerencia getTipo() {
        return tipo;
    }

    public EstadoSugerencia getEstado() {
        return estado;
    }

    public Empleado getAutor() {
        return autor;
    }
}
