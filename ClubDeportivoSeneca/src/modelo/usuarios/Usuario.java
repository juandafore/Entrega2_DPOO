package modelo.usuarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import modelo.comun.Autenticable;
import modelo.tienda.Compra;

public abstract class Usuario implements Autenticable, Serializable {

    protected String login;
    protected String password;
    protected String nombre;
    protected boolean activo = true;
    protected final List<Compra> compras = new ArrayList<>();

    protected Usuario(String login, String password, String nombre) {
        this.login = login;
        this.password = password;
        this.nombre = nombre;
    }

    @Override
    public boolean autenticar(String login, String password) {
        return activo && this.login.equals(login) && this.password.equals(password);
    }

    @Override
    public void cambiarPassword(String nueva) {
        this.password = nueva;
    }

    public void comprar(Compra c) {
        compras.add(c);
    }

    public String getLogin() {
        return login;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public abstract String tipo();

    @Override
    public String toString() {
        return tipo() + " " + nombre + " (" + login + ")";
    }
}
