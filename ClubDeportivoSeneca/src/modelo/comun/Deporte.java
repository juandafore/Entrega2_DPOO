package modelo.comun;

public enum Deporte {
    FUTBOL(7, true),
    BALONCESTO(5, false),
    VOLEIBOL(6, false);

    private final int minJugadores;
    private final boolean admiteEmpate;

    Deporte(int minJugadores, boolean admiteEmpate) {
        this.minJugadores = minJugadores;
        this.admiteEmpate = admiteEmpate;
    }

    public int getMinJugadores() {
        return minJugadores;
    }

    public boolean admiteEmpate() {
        return admiteEmpate;
    }
}
