package modelo.comun;

import java.time.LocalDate;
import java.time.LocalTime;

public interface Reservable {
    boolean estaDisponible(LocalDate fecha, LocalTime inicio, LocalTime fin);

    boolean validarJugadores(int n);
}
