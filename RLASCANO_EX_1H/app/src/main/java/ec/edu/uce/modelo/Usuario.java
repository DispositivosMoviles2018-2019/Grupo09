package ec.edu.uce.modelo;

import java.util.Objects;

public class Usuario {
    private String usuario;
    private String clave;

    public Usuario(){

    }
    public Usuario(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario1 = (Usuario) o;
        return Objects.equals(usuario, usuario1.usuario);
    }

    @Override
    public int hashCode() {

        return Objects.hash(usuario);
    }
}
