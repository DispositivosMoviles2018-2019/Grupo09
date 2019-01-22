package ec.edu.uce.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpcionesReservaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_reserva);
    }

    public void irRserva(View view){
        Intent intent = new Intent(OpcionesReservaActivity.this, ReservaActivity.class);
        startActivity(intent);
    }

    public void irBusqueda(View view){
        Intent intent = new Intent(OpcionesReservaActivity.this, BuscarReservaActivity.class);
        startActivity(intent);
    }

    public void irPrincipal(View view){
        Intent intent = new Intent(OpcionesReservaActivity.this, OpcionesMenuActivity.class);
        startActivity(intent);
    }
}
