package ec.edu.uce.vista;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ConfiguracionesActivity extends AppCompatActivity {

    private RadioButton rbAcs, rbDesc;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);

        rbAcs =  findViewById(R.id.rbAsc);
        rbDesc = findViewById(R.id.rbDesc);

        preferences = getSharedPreferences("Orden", Context.MODE_PRIVATE);
        String orden = preferences.getString("orden", " ");

        if(orden.equalsIgnoreCase("ascendente")){
            rbAcs.setChecked(true);
        }
        if(orden.equalsIgnoreCase("descendente")){
            rbDesc.setChecked(true);
        }
    }

    public void ordenar(View view){
        if(rbAcs.isChecked()){
           SharedPreferences.Editor editor = preferences.edit();
           editor.putString("orden", "ascendente");
           editor.commit();
        }

        if(rbDesc.isChecked()){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("orden", "descendente");
            editor.commit();
        }

        Intent intent = new Intent(ConfiguracionesActivity.this, ListadoVehiculosActivity.class);
        startActivity(intent);
    }
}
