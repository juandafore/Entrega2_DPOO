package modelo.comun;

public interface Autenticable {
    boolean autenticar(String login, String password);

    void cambiarPassword(String nueva);
}
