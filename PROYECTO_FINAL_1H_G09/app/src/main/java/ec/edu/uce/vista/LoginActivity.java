package ec.edu.uce.vista;

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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import ec.edu.uce.modelo.Usuario;
import ec.edu.uce.modelo.Vehiculo;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsuario;
    private EditText txtClave;

    private final String ARCHIVO_VEHICULO = "vehiculos.json";

    File dataFileVehiculo = new File(Environment.getExternalStorageDirectory(), ARCHIVO_VEHICULO);

    Set<Usuario> lista = new LinkedHashSet<>();
    Set<Vehiculo> listaVehiculo = new LinkedHashSet<>();

    Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            //Creacion del archivo en caso de no existir
            if (dataFileVehiculo.createNewFile()) {
                crearPorDefecto();
                FileWriter fileWriter = new FileWriter(dataFileVehiculo, false);
                PrintWriter out = new PrintWriter(fileWriter);
                String json = gson.toJson(listaVehiculo);
                out.println(json);
                out.flush();
                out.close();
            }
            listaVehiculo = listarVehiculos();
            if (listaVehiculo.isEmpty()) {
                crearPorDefecto();
                FileWriter fileWriter = new FileWriter(dataFileVehiculo, false);
                PrintWriter out = new PrintWriter(fileWriter);
                String json = gson.toJson(listaVehiculo);
                out.println(json);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Toast.makeText(this, "No se pudo Crear el archivo", Toast.LENGTH_SHORT).show();
        }


    }

    public void registro(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistroUsuarioActivity.class);
        startActivity(intent);
    }

    public void ingresar(View view) {
        txtUsuario = (EditText) findViewById(R.id.txtUser);
        txtClave = (EditText) findViewById(R.id.txtClave);
        RegistroUsuarioActivity ac = new RegistroUsuarioActivity();
        lista = ac.leer();
        boolean bandera = false;
        for (Usuario us : lista) {
            if (us.getUsuario().equalsIgnoreCase(txtUsuario.getText().toString())
                    && us.getClave().equalsIgnoreCase(txtClave.getText().toString())) {
                bandera = true;
            }
        }
        if (bandera) {
            Intent intent = new Intent(LoginActivity.this, VehiculosActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Usuario o Clave no válido", Toast.LENGTH_SHORT).show();
        }

    }

    public Set<Vehiculo> listarVehiculos() {
        Set<Vehiculo> list = new LinkedHashSet<>();
        try {
            if (dataFileVehiculo.exists()) {
                FileReader fileReader = new FileReader(dataFileVehiculo);
                BufferedReader buffer = new BufferedReader(fileReader);
                Type collectionType = new TypeToken<Set<Vehiculo>>() {
                }.getType();
                list = gson.fromJson(buffer.readLine(), collectionType);
            }

        } catch (Exception e) {
            Toast.makeText(this, "No se pudo leer", Toast.LENGTH_SHORT).show();
        }
        return list;
    }

    public void crearPorDefecto() {
        Vehiculo vehiculoUno = new Vehiculo();
        vehiculoUno.setPlaca("XTR-9784");
        vehiculoUno.setMarca("Audi");
        vehiculoUno.setFecFabricacion(LocalDate.parse("2015-11-13"));
        vehiculoUno.setCosto(79990.0);
        vehiculoUno.setMatriculado(true);
        vehiculoUno.setColor("Negro");

        Vehiculo vehiculoDos = new Vehiculo();
        vehiculoDos.setPlaca("CDD-0789");
        vehiculoDos.setMarca("Honda");
        vehiculoDos.setFecFabricacion(LocalDate.parse("1998-03-05"));
        vehiculoDos.setCosto(15340.0);
        vehiculoDos.setMatriculado(false);
        vehiculoDos.setColor("Blanco");

        listaVehiculo.add(vehiculoUno);
        listaVehiculo.add(vehiculoDos);


    }
}
