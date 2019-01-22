package ec.edu.uce.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpcionesMenuActivity extends AppCompatActivity {

    private Button btnVehiculos, btnReservas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_menu);
        btnVehiculos = findViewById(R.id.btnVehiculos);
        btnReservas = findViewById(R.id.btnReservas);

    }

    public void irVehiculos(View view){
        Intent intent = new Intent( OpcionesMenuActivity.this, ListadoVehiculosActivity.class);
        startActivity(intent);
    }

    public void irReservas(View view){
        Intent intent = new Intent( OpcionesMenuActivity.this, OpcionesReservaActivity.class);
        startActivity(intent);

    }
}
