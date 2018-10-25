package com.uce.tarea_04_g09;

import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.uce.entity.Persona;
import com.uce.utils.MyDatePicker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class RegistroActivity extends AppCompatActivity {
    private EditText usuario;
    private EditText clave;
    private EditText nombre;
    private EditText apellido;
    private EditText email;
    private EditText celular;
    private RadioGroup genero;
    private RadioButton radioMasculino;
    private RadioButton radioFemenino;
    private Button registro;
    private DatePicker fecha;
    private Switch swBeca;
    private CheckBox checkDistribuida;
    private CheckBox checkGestion;
    private CheckBox checkMatematica;
    private CheckBox checkSociales;
    private CheckBox checkMineria;
    private final String ARCHIVO = "data.obj";

    List<Persona> lista = new ArrayList<>();
    File dataFile = new File(Environment.getExternalStorageDirectory(), ARCHIVO);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        usuario = (EditText) findViewById(R.id.txtUsuario);
        clave = (EditText) findViewById(R.id.txtClave);
        nombre = (EditText) findViewById(R.id.txtUsuarioMain);
        apellido = (EditText) findViewById(R.id.txtApellido);
        email = (EditText) findViewById(R.id.txtEmail);
        celular = (EditText) findViewById(R.id.txtCelular);
        genero = (RadioGroup) findViewById(R.id.radioGroup);
        radioMasculino = (RadioButton) findViewById(R.id.radioMasculino);
        radioFemenino = (RadioButton) findViewById(R.id.radioFemenino);
        registro = (Button) findViewById(R.id.btnRegistro);
        fecha = (DatePicker) findViewById(R.id.spFecha);
        swBeca = (Switch) findViewById(R.id.swBeca);
        checkDistribuida = (CheckBox) findViewById(R.id.chbDistribuida);
        checkGestion = (CheckBox) findViewById(R.id.chbGestion);
        checkMatematica = (CheckBox) findViewById(R.id.chbMatematica);
        checkMineria = (CheckBox) findViewById(R.id.chbMineria);
        checkSociales = (CheckBox) findViewById(R.id.chbSociales);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
    }

    private void registrar(){

       try{
           List<String> materias = new ArrayList<>();
           if(checkSociales.isChecked() ){
               materias.add("Sociales");
           }
           if(checkMineria.isChecked()){
               materias.add("Mineria");
           }
           if(checkGestion.isChecked()){
               materias.add("Gestion");
           }
           if(checkDistribuida.isChecked()){
               materias.add("Distribuida");
           }
           if(checkMatematica.isChecked()){
               materias.add("Matematica");
           }
           String genero=null;
           String beca=null;
           if(swBeca.isChecked()){
               beca="si";
           }else{
               beca="no";
           }
            if(radioMasculino.isChecked()){
                genero="Masculino";
            }else{
                genero = "Femenino";
            }

            StringBuilder sb = new StringBuilder();
            sb.append(fecha.getYear()).append("/").append(fecha.getMonth()).append("/").append(fecha.getDayOfMonth());
           FileOutputStream out = new FileOutputStream(dataFile, true);
           ObjectOutputStream ost = new ObjectOutputStream(out);

           //MiObjectOutputStream ost = new MiObjectOutputStream(out);
           ost.writeObject(new Persona(usuario.getText().toString(), clave.getText().toString(),
                   nombre.getText().toString(), apellido.getText().toString(), email.getText().toString(),
                   celular.getText().toString(),genero, sb.toString(), beca, materias));
           ost.close();
           Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
           startActivity(intent);
           //System.out.println("Escrito Correctamente");
       }catch (Exception e){
            e.printStackTrace();
       }
    }

    public void showDatePicker(View view){
        DialogFragment newFragment = new MyDatePicker();
        newFragment.show(getSupportFragmentManager(), "date picker");
    }

}
