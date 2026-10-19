package modelo.comun;

public enum Disciplina {
    TENIS(new int[] {2, 4}),
    PADEL(new int[] {4}),
    TENIS_MESA(new int[] {2, 4});

    private final int[] jugadoresAdmitidos;

    Disciplina(int[] jugadoresAdmitidos) {
        this.jugadoresAdmitidos = jugadoresAdmitidos;
    }

    public boolean admiteJugadores(int n) {
        for (int permitido : jugadoresAdmitidos) {
            if (permitido == n) {
                return true;
            }
        }
        return false;
    }
}
