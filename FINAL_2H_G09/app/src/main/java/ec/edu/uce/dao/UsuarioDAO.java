package ec.edu.uce.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import ec.edu.uce.controlador.BaseDatosHelper;
import ec.edu.uce.controlador.Encriptacion;
import ec.edu.uce.modelo.Usuario;

public class UsuarioDAO implements InterfazUsuario{

    private BaseDatosHelper helper;
    private SQLiteDatabase db;

    public UsuarioDAO(Context context) {
        helper = new BaseDatosHelper(context, "optativa", null, 1);
        db = helper.getWritableDatabase();
    }

    public void cerrarConexion(){
        db.close();
    }

    @Override
    public String crear(Usuario obj) {
        //si hemos abierto correctamente la base de datos
        String mensaje = null;
        if (db != null && obj != null) {
            ContentValues registro = new ContentValues();
            registro.put("usuario", obj.getUsuario());
            registro.put("clave", obj.getClave());
            long estado = db.insert("usuario", "", registro);
            if (estado != -1) {
                mensaje = "Creado correctamente";
            } else {
                mensaje = "No se acepta usuarios con el mismo nombre";
            }

        } else {
            mensaje = "No se ha creado, Inserte los datos";
        }
        cerrarConexion();
        return mensaje;
    }

    @Override
    public boolean validar(String usuario, String clave) {
        boolean bandera = false;
        try{

            if (!usuario.isEmpty() && !clave.isEmpty()) {
                Cursor cursor = db.rawQuery("select usuario, clave from usuario where usuario = '" + usuario + "'", null);
                if (cursor.moveToFirst()) { //si encuentra el resultado
                    String us = cursor.getString(0);
                    Encriptacion en = new Encriptacion();
                    String claveDescifrada = en.descifra(cursor.getBlob(1));
                    if (usuario.equalsIgnoreCase(us) && clave.equalsIgnoreCase(claveDescifrada)) {
                        bandera = true;
                    } else {
                        bandera = false;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return bandera;
    }

    @Override
    public String actualizar(Usuario obj) {
        return null;
    }

    @Override
    public String borrar(String parametro) {
        return null;
    }

    @Override
    public Usuario buscarPorParametro(List<Usuario> lista, String parametro) {
        return null;
    }

    @Override
    public List<Usuario> listar() {
        return null;
    }
}
