package com.uce.tarea_04_g09;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.uce.entity.Persona;

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
    private Button registro;
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
        registro = (Button) findViewById(R.id.btnRegistro);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
    }

    private void registrar(){

       try{

           FileOutputStream out = new FileOutputStream(dataFile, true);
           ObjectOutputStream ost = new ObjectOutputStream(out);

           //MiObjectOutputStream ost = new MiObjectOutputStream(out);
           ost.writeObject(new Persona(usuario.getText().toString(), clave.getText().toString(),
                   nombre.getText().toString(), apellido.getText().toString(), email.getText().toString(),
                   celular.getText().toString(),"Masculino"));
           ost.close();
           Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
           startActivity(intent);
           //System.out.println("Escrito Correctamente");
       }catch (Exception e){
            e.printStackTrace();
       }
    }

}
