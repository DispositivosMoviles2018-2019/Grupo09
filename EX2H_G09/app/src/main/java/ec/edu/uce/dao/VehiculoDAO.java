package ec.edu.uce.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ec.edu.uce.controlador.BaseDatosHelper;
import ec.edu.uce.modelo.Vehiculo;

public class VehiculoDAO implements InterfazVehiculo {
    private BaseDatosHelper helper;
    private SQLiteDatabase db;

    public VehiculoDAO(Context context) {
        helper = new BaseDatosHelper(context, "optativa", null, 1);
        db = helper.getWritableDatabase();
    }

    public void cerrarConexion() {
        db.close();
    }

    @Override
    public String crear(Vehiculo obj) {
        String mensaje = null;
        if (obj != null && db != null) {
            ContentValues registro = new ContentValues();
            registro.put("placa", obj.getPlaca());
            registro.put("marca", obj.getMarca());
            registro.put("fec_fabricacion", obj.getFecFabricacion().toString());
            registro.put("costo", obj.getCosto());
            registro.put("matriculado", obj.getMatriculado());
            registro.put("color", obj.getColor());
            registro.put("foto", obj.getFoto());
            registro.put("estado", true);
            registro.put("tipo", obj.getTipo());
            long estado = db.insert("vehiculo", null, registro);
            if (estado == -1) {
                mensaje = "La placa ya existe, usar nueva";
            } else {
                mensaje = "Vehiculo creado";
                cerrarConexion();
            }

        }
        return mensaje;
    }

    @Override
    public String actualizar(Vehiculo obj) {
        String mensaje = null;
        if (obj != null && db != null) {
            ContentValues registro = new ContentValues();
            registro.put("placa", obj.getPlaca());
            registro.put("marca", obj.getMarca());
            registro.put("fec_fabricacion", obj.getFecFabricacion().toString());
            registro.put("costo", obj.getCosto());
            registro.put("matriculado", obj.getMatriculado());
            registro.put("color", obj.getColor());
            registro.put("foto", obj.getFoto());
            registro.put("estado", true);
            registro.put("tipo", obj.getTipo());

            int estado = db.update("vehiculo", registro, "placa = '" + obj.getPlaca() + "'", null);
            if (estado == 1) {
                mensaje = "Vehiculo modificado";
                db.close();
            } else {
                mensaje = "No se ha podido modificar";
            }
        }
        return mensaje;
    }

    @Override
    public String borrar(String parametro) {
        String mensaje = null;
        int estado = db.delete("vehiculo", "placa = '" + parametro + "'", null);
        if (estado != 0) {
            mensaje = "Vehiculo Borrado";
        } else {
            mensaje = "No se ha podido eliminar";
        }

        return mensaje;
    }

    @Override
    public Vehiculo buscarPorParametro(List<Vehiculo> lista, String parametro) {

        Vehiculo vehiculo = new Vehiculo();
        for (Vehiculo vh : lista) {
            if (vh.getPlaca().equalsIgnoreCase(parametro)) {
                vehiculo = vh;
            }
        }
        return vehiculo;

    }

    @Override
    public List<Vehiculo> listar() {
        Cursor cursor = db.rawQuery("select * from vehiculo", null);
        List<Vehiculo> lista = new ArrayList<>();
        if (cursor.moveToFirst()) {
            //iteramos sobre el cursor de resultados
            //llenamos en la lista
            while (cursor.isAfterLast() == false) {
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setPlaca(cursor.getString(cursor.getColumnIndex("placa")));
                vehiculo.setMarca(cursor.getString(cursor.getColumnIndex("marca")));
                vehiculo.setColor(cursor.getString(cursor.getColumnIndex("color")));
                vehiculo.setFecFabricacion(LocalDate.parse(cursor.getString(cursor.getColumnIndex("fec_fabricacion"))));
                vehiculo.setCosto(cursor.getDouble(cursor.getColumnIndex("costo")));
                vehiculo.setMatriculado((cursor.getInt(cursor.getColumnIndex("matriculado")) == 1) ? true : false);//1 verdadero, 0 falso
                vehiculo.setColor(cursor.getString(cursor.getColumnIndex("color")));
                vehiculo.setFoto(cursor.getBlob(cursor.getColumnIndex("foto")));
                vehiculo.setEstado((cursor.getInt(cursor.getColumnIndex("estado"))) == 1 ? true : false);
                vehiculo.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
                lista.add(vehiculo);
                cursor.moveToNext();
            }
        }
        return lista;
    }

    public boolean isAvailable(String placa) {
        Cursor cursor = db.rawQuery("select * from vehiculo where placa = '" + placa + "'", null);
        boolean bandera = false;
        if (cursor.getCount() == 0) {
            bandera = false;
        } else {
            bandera = true;
        }
        return bandera;
    }
}
