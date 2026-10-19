package modelo.comun;

public enum Nivel {
    PRINCIPIANTE,
    INTERMEDIO,
    AVANZADO;

    public boolean esAdyacente(Nivel otro) {
        return Math.abs(this.ordinal() - otro.ordinal()) <= 1;
    }
}
