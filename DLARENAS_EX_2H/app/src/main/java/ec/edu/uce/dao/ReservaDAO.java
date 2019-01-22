package ec.edu.uce.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ec.edu.uce.controlador.BaseDatosHelper;
import ec.edu.uce.modelo.Reserva;

public class ReservaDAO implements InterfazReserva{

    private BaseDatosHelper helper;
    private SQLiteDatabase db;

    public ReservaDAO(Context context) {
        helper = new BaseDatosHelper(context, "optativa", null, 1);
        db = helper.getWritableDatabase();
    }

    public void cerrarConexion(){
        db.close();
    }

    @Override
    public String crear(Reserva obj) {
        String mensaje = null;
        if(obj != null && db != null){
            ContentValues registro = new ContentValues();
            registro.put("numero", obj.getNumero());
            registro.put("email", obj.getEmail());
            registro.put("celular", obj.getCelular());
            registro.put("fecha_prestamo", obj.getFechaPrestamo().toString());
            registro.put("fecha_entrega", obj.getFechaEntrega().toString());
            registro.put("valor_reserva", obj.getValorReserva());
            registro.put("placa", obj.getPlaca());

            long estado =  db.insertOrThrow("reserva", null, registro);
            if(estado == -1){
                mensaje = "Vehiculo Reservado, usar otro";
            } else{
                mensaje = "Se ha creado la reserva";

            }

        }
        cerrarConexion();
        return mensaje;
    }

    @Override
    public String actualizar(Reserva obj) {
        return null;
    }

    @Override
    public String borrar(String parametro) {
        return null;
    }

    @Override
    public Reserva buscarPorParametro(List<Reserva> lista, String parametro) {
        Reserva reserva = new Reserva();
        for (Reserva rv: lista) {
            if(rv.getNumero().equalsIgnoreCase(parametro) || rv.getPlaca().equalsIgnoreCase(parametro)){
                reserva = rv;
            }
        }
        return reserva;
    }


    @Override
    public List<Reserva> listar() {
        Cursor cursor = db.rawQuery("select * from reserva", null);
        List<Reserva> lista = new ArrayList<>();
        if(cursor.moveToFirst()){
            //iteramos sobre el cursor de resultados
            //llenamos en la lista
            while(cursor.isAfterLast() == false){
                Reserva reserva = new Reserva();
                reserva.setNumero(cursor.getString(cursor.getColumnIndex("numero")));
                reserva.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                reserva.setCelular(cursor.getString(cursor.getColumnIndex("celular")));
                reserva.setFechaPrestamo(LocalDate.parse(cursor.getString(cursor.getColumnIndex("fecha_prestamo"))));
                reserva.setFechaEntrega(LocalDate.parse(cursor.getString(cursor.getColumnIndex("fecha_entrega"))));
                reserva.setValorReserva(cursor.getDouble(cursor.getColumnIndex("valor_reserva")));
                reserva.setPlaca(cursor.getString(cursor.getColumnIndex("placa")));
                lista.add(reserva);
                cursor.moveToNext();
            }
        }
        cerrarConexion();
        return lista;
    }
}
