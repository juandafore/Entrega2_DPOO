package modelo.admin;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Club;
import modelo.deportesconjunto.PartidoInterno;
import modelo.competencia.Competencia;
import modelo.competencia.PartidoCompetencia;
import modelo.entrenamiento.SesionEntrenamiento;
import modelo.tienda.Articulo;
import modelo.tienda.Compra;

public class ServicioReportes implements Serializable {

    private Club club;

    public ServicioReportes(Club club) {
        this.club = club;
    }

    private static boolean enRango(LocalDate fecha, LocalDate desde, LocalDate hasta) {
        return !fecha.isBefore(desde) && !fecha.isAfter(hasta);
    }

    public String reporteVentas(LocalDate desde, LocalDate hasta) {
        double tienda = 0, cafeteria = 0, mensualidades = 0;
        for (Compra c : club.getTodasLasCompras()) {
            LocalDate f = c.getFecha().toLocalDate();
            if (enRango(f, desde, hasta)) {
                double total = c.calcularTotal();
                if (c.rubro().equals("TIENDA")) {
                    tienda += total;
                } else if (c.rubro().equals("CAFETERIA")) {
                    cafeteria += total;
                } else if (c.rubro().equals("MENSUALIDAD")) {
                    mensualidades += total;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== Reporte de ventas (").append(desde).append(" a ").append(hasta).append(") ===\n");
        sb.append(String.format("  Tienda:        $%.2f%n", tienda));
        sb.append(String.format("  Cafetería:     $%.2f%n", cafeteria));
        sb.append(String.format("  Mensualidades: $%.2f%n", mensualidades));
        sb.append(String.format("  TOTAL:         $%.2f", tienda + cafeteria + mensualidades));
        return sb.toString();
    }

    public String reporteDeportivo(LocalDate desde, LocalDate hasta) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Reporte deportivo (").append(desde).append(" a ").append(hasta).append(") ===\n");
        sb.append("- Sesiones de entrenamiento:\n");
        for (SesionEntrenamiento s : club.getSesiones()) {
            if (enRango(s.getFecha(), desde, hasta)) {
                sb.append("    ").append(s.getFecha()).append(" ").append(s.resumenAsistencia()).append("\n");
            }
        }
        sb.append("- Partidos internos:\n");
        for (PartidoInterno p : club.getPartidosInternos()) {
            if (enRango(p.getFecha(), desde, hasta)) {
                sb.append("    ").append(p.getLocal().getNombre()).append(" ")
                        .append(p.getMarcadorLocal()).append("-").append(p.getMarcadorVisitante())
                        .append(" ").append(p.getVisitante().getNombre()).append("\n");
            }
        }
        sb.append("- Competencias:\n");
        for (Competencia comp : club.getCompetencias()) {
            sb.append("    ").append(comp.getNombre()).append(": ");
            for (PartidoCompetencia p : comp.getPartidos()) {
                sb.append(p.getMarcadorLocal()).append("-").append(p.getMarcadorVisitante())
                        .append(" vs ").append(p.getInstitucionRival()).append("; ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Object[] historialCompleto() {
        List<Object> historial = new ArrayList<>();
        historial.addAll(club.getTodasLasCompras());
        historial.addAll(club.getPartidosInternos());
        historial.addAll(club.getCompetencias());
        historial.addAll(club.getSesiones());
        return historial.toArray();
    }

    public String estadoInventario() {
        StringBuilder sb = new StringBuilder("=== Estado del inventario ===\n");
        for (Articulo a : club.getArticulos()) {
            sb.append("  ").append(a).append("\n");
        }
        return sb.toString();
    }
}
