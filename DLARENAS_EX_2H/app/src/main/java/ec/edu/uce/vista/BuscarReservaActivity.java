package ec.edu.uce.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ec.edu.uce.controlador.Utilitario;
import ec.edu.uce.dao.ReservaDAO;
import ec.edu.uce.modelo.Reserva;

public class BuscarReservaActivity extends AppCompatActivity {

    private ReservaDAO dao;
    private EditText parametro;
    private Utilitario util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_reserva);

        parametro = findViewById(R.id.txtParametro);

        dao = new ReservaDAO(this);
        util = new Utilitario();
    }

    public void buscar(View view) {
        Reserva reserva = new Reserva();
        dao = new ReservaDAO(this);

        String param = parametro.getText().toString();

        reserva = dao.buscarPorParametro(dao.listar(), param);
        if(reserva.getPlaca() != null){
            if (util.isPlaca(parametro.getText().toString()) || util.isNumReserva(parametro.getText().toString())) {
                Intent intent = new Intent(BuscarReservaActivity.this, ConfirmacionActivity.class);
                intent.putExtra("placa", reserva.getPlaca());
                startActivity(intent);

            } else {
                Toast.makeText(this, "parametro ingresado incorrecto", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "no existe reserva", Toast.LENGTH_SHORT).show();
        }



    }


}
