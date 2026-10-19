package modelo.usuarios;

import java.util.ArrayList;
import java.util.List;

import modelo.admin.SolicitudCambioTurno;
import modelo.admin.SugerenciaArticulo;
import modelo.admin.TurnoEmpleado;
import modelo.tienda.CodigoDescuento;

public abstract class Empleado extends Usuario {

    private CodigoDescuento codigo;
    private final List<TurnoEmpleado> turnos = new ArrayList<>();

    protected Empleado(String login, String password, String nombre) {
        super(login, password, nombre);
    }

    public void solicitarTurno(SolicitudCambioTurno s) {

    }

    public void sugerirArticulo(SugerenciaArticulo s) {

    }

    public void compartirCodigo(Socio s) {
        if (codigo != null) {
            codigo.compartirCon(s);
        }
    }

    public void agregarTurno(TurnoEmpleado t) {
        turnos.add(t);
    }

    public CodigoDescuento getCodigo() {
        return codigo;
    }

    public void setCodigo(CodigoDescuento codigo) {
        this.codigo = codigo;
    }

    public List<TurnoEmpleado> getTurnos() {
        return turnos;
    }
}
