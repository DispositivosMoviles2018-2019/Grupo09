package ec.edu.uce.controlador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatosHelper extends SQLiteOpenHelper {

    String sqlTablaUsuario = "CREATE TABLE usuario(id INTEGER PRIMARY KEY AUTOINCREMENT, usuario TEXT NOT NULL UNIQUE,clave BLOB)";
    String sqlTablaVehiculo = "CREATE TABLE vehiculo(id_vehiculo INTEGER PRIMARY KEY AUTOINCREMENT, placa TEXT NOT NULL UNIQUE, marca TEXT, fec_fabricacion  NUMERIC, costo REAL, matriculado NUMERIC, color TEXT, foto BLOB, estado NUMERIC, tipo TEXT)";
    String sqlTablaReserva = "CREATE TABLE reserva(id_reserva INTEGER PRIMARY KEY AUTOINCREMENT, numero TEXT NOT NULL UNIQUE, email TEXT, celular TEXT, fecha_prestamo NUMERIC, fecha_entrega NUMERIC, valor_reserva REAL, placa TEXT, FOREIGN KEY (placa) REFERENCES vehiculo (placa) ON DELETE CASCADE ON UPDATE NO ACTION)";


    public BaseDatosHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlTablaUsuario);
        db.execSQL(sqlTablaVehiculo);
        db.execSQL(sqlTablaReserva);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //se elimina la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS reserva");
        db.execSQL("DROP TABLE IF EXISTS vehiculo");

        //se crea la nueva version de la tabla
        db.execSQL(sqlTablaUsuario);
        db.execSQL(sqlTablaVehiculo);
        db.execSQL(sqlTablaReserva);
    }
}
