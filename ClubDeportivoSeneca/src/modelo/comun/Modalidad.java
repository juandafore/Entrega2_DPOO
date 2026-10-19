package modelo.comun;

public enum Modalidad {
    SENCILLOS(2),
    DOBLES(4);

    private final int numeroJugadores;

    Modalidad(int numeroJugadores) {
        this.numeroJugadores = numeroJugadores;
    }

    public int getNumeroJugadores() {
        return numeroJugadores;
    }
}
