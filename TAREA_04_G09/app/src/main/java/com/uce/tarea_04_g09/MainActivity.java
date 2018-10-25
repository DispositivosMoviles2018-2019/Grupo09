package com.uce.tarea_04_g09;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uce.entity.Persona;
import com.uce.utils.IOStream;
import com.uce.utils.MyDatePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText clave;
    private Button ingreso;
    private Button registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = (EditText) findViewById(R.id.txtUsuarioMain);
        clave = (EditText) findViewById(R.id.pswClaveMain);
        ingreso = (Button) findViewById(R.id.btnIngresar);
        registro = (Button) findViewById(R.id.btnRegistro);

        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    irListado(usuario.getText().toString(), clave.getText().toString());
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irRegistro();
            }
        });

    }

    private void irRegistro() {
        Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
        startActivity(intent);
    }

    private void irListado(String user, String pass) throws IOException, ClassNotFoundException {
        System.out.println("Usuario: "+ user +"- clave: " + pass);
        List<Persona> lista = new ArrayList<>();
        IOStream io = new IOStream();
        lista = io.listarTodos();
        boolean bandera=false;
        for (Persona per: lista) {
            System.out.println("Usuario: "+per.getUsuario());
            System.out.println("Clave: "+per.getClave());
            System.out.println("Nombre: "+per.getNombre());
            System.out.println("Fecha: "+per.getFecha());
            System.out.println("Genero: "+per.getGenero());
            System.out.println("Beca: "+per.getBeca());
            System.out.println("Materias Asignadas: "+per.getAsignaturas().size());
            if(user.equals(per.getUsuario()) && pass.equals(per.getClave())) {
               bandera=true;
            }
        }
        if(bandera){
            Intent intent = new Intent(MainActivity.this, ListadoActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Usuario y Clave no válido", Toast.LENGTH_SHORT).show();
        }


    }



}
