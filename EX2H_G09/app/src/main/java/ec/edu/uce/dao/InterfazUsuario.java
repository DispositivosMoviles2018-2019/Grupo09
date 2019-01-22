package ec.edu.uce.dao;

import ec.edu.uce.modelo.Usuario;

public interface InterfazUsuario extends InterfazCRUD<Usuario> {
    public boolean validar(String usuario, String clave);
}
