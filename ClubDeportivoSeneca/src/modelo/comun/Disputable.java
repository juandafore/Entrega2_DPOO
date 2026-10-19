package modelo.comun;

public interface Disputable {
    boolean puedeJugarse();

    void registrarMarcador(int local, int visitante);
}
