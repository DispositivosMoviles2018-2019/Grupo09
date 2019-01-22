package ec.edu.uce.modelo;

import java.time.LocalDate;

public class Usuario {
    private String usuario;
    private byte[] clave;

    public Usuario() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public byte[] getClave() {
        return clave;
    }

    public void setClave(byte[] clave) {
        this.clave = clave;
    }
}
