package ec.edu.uce.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.controlador.Utilitario;
import ec.edu.uce.dao.VehiculoDAO;
import ec.edu.uce.modelo.Vehiculo;

public class BuscarAutoActivity extends AppCompatActivity {

    private VehiculoDAO dao;
    private EditText txtParametro;
    private Button btnBuscar;
    private Utilitario util;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_auto);

        txtParametro = findViewById(R.id.txtBusPlaca);
        btnBuscar = findViewById(R.id.btnBusVehiculo);
        dao = new VehiculoDAO(this);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(util.isPlaca(txtParametro.getText().toString())){
                    buscar(txtParametro.getText().toString());
                }

            }
        });
        util = new Utilitario();
    }

    public void buscar(String parametro){
        Vehiculo vehiculo;
        vehiculo = dao.buscarPorParametro(dao.listar(), parametro);
        if(vehiculo.getPlaca() != null){
            Intent intent = new Intent(BuscarAutoActivity.this, ResultadoBusquedaActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("vehiculo", vehiculo);
            intent.putExtra("bundle", b);
            startActivity(intent);
        }else{
            Toast.makeText(this, "No se ha encontrado", Toast.LENGTH_SHORT).show();
        }
    }
}
