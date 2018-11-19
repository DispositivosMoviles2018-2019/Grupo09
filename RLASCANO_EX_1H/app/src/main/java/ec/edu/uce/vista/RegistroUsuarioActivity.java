package ec.edu.uce.vista;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

import ec.edu.uce.R;
import ec.edu.uce.modelo.Usuario;

public class RegistroUsuarioActivity extends AppCompatActivity {

    private EditText txtUsuario;
    private EditText txtClave;
    private final String ARCHIVO = "usuarios.json";
    File dataFile = new File(Environment.getExternalStorageDirectory(), ARCHIVO);
    Set<Usuario> lista = new LinkedHashSet<>();
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

    }

    public void registrar(View view) {
        txtUsuario = (EditText) findViewById(R.id.txtUser);
        txtClave = (EditText) findViewById(R.id.txtClave);

        lista = leer();
        Usuario usuario = new Usuario();
        usuario.setUsuario(txtUsuario.getText().toString());
        usuario.setClave(txtClave.getText().toString());

        if (lista.add(usuario)) {
            try {
                FileWriter fileWriter = new FileWriter(dataFile, false);
                PrintWriter out = new PrintWriter(fileWriter);
                String json = gson.toJson(lista);
                out.println(json);
                out.flush();
                out.close();
                System.out.println("se ha guardado en " + dataFile.getAbsolutePath());
                Toast.makeText(this, "guardado correctamente", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegistroUsuarioActivity.this, LoginActivity.class);
                startActivity(intent);
            } catch (IOException e) {
                Toast.makeText(this, "No se pudo guardar", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Este Usuario ya existe", Toast.LENGTH_SHORT).show();
        }


    }

    public Set<Usuario> leer() {
        Set<Usuario> list = new LinkedHashSet<>();
        try {
            if (dataFile.exists()) {
                FileReader fileReader = new FileReader(dataFile);
                BufferedReader buffer = new BufferedReader(fileReader);
                Type collectionType = new TypeToken<Set<Usuario>>() {
                }.getType();
                list = gson.fromJson(buffer.readLine(), collectionType);
            }

        } catch (Exception e) {
            Toast.makeText(this, "No se pudo leer", Toast.LENGTH_SHORT).show();
        }
        return list;

    }
}
