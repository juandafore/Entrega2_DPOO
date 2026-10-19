package modelo.usuarios;

import java.util.List;

import modelo.admin.TurnoEmpleado;

public class Fisioterapeuta extends Empleado {

    public Fisioterapeuta(String login, String password, String nombre) {
        super(login, password, nombre);
    }

    public List<TurnoEmpleado> consultarTurnos() {
        return getTurnos();
    }

    @Override
    public String tipo() {
        return "Fisioterapeuta";
    }
}
