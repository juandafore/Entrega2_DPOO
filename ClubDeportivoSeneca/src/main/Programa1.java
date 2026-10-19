package main;

import modelo.Club;
import modelo.usuarios.Administrador;
import modelo.usuarios.Entrenador;
import modelo.usuarios.Fisioterapeuta;
import modelo.usuarios.Socio;
import modelo.usuarios.Usuario;
import persistencia.PersistenciaClub;

public class Programa1 {

    public static void main(String[] args) throws Exception {
        String carpeta = args.length > 0 ? args[0] : "datos_programa1";
        System.out.println("### PROGRAMA 1: Persistencia y autenticación ###");
        System.out.println("Carpeta de datos: " + carpeta + "\n");

        Club club = new Club();
        Administrador admin = new Administrador("admin", "admin123", "Ana Admin");
        club.setAdministrador(admin);
        Entrenador carlos = new Entrenador("carlos", "e123", "Carlos Coach");
        Fisioterapeuta fabiola = new Fisioterapeuta("fabiola", "f123", "Fabiola Fisio");
        club.agregarEmpleado(carlos);
        club.agregarEmpleado(fabiola);
        Socio juan = new Socio("juan", "s123", "Juan Pérez", 20);
        club.registrarSocio(juan);

        System.out.println("Estado antes de guardar:");
        imprimirUsuarios(club);

        PersistenciaClub.guardar(club, carpeta);
        System.out.println("\n-> Datos guardados en " + carpeta + "/" + PersistenciaClub.ARCHIVO);
        Club recargado = PersistenciaClub.cargar(carpeta);
        System.out.println("-> Datos recargados desde archivo\n");

        System.out.println("Estado después de recargar:");
        imprimirUsuarios(recargado);

        System.out.println("\nPruebas de autenticación:");
        probarLogin(recargado, "admin", "admin123");
        probarLogin(recargado, "carlos", "e123");
        probarLogin(recargado, "fabiola", "f123");
        probarLogin(recargado, "juan", "s123");
        probarLogin(recargado, "juan", "clave-mala");
        probarLogin(recargado, "fantasma", "x");
    }

    private static void imprimirUsuarios(Club club) {
        for (Usuario u : club.todosLosUsuarios()) {
            System.out.println("   - " + u);
        }
    }

    private static void probarLogin(Club club, String login, String pass) {
        Usuario u = club.autenticar(login, pass);
        if (u != null) {
            System.out.println("   [OK]    " + login + " -> autenticado como " + u.tipo());
        } else {
            System.out.println("   [FALLO] " + login + " / '" + pass + "' -> credenciales inválidas");
        }
    }
}
