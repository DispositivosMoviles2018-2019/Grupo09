package ec.edu.uce.vista;

import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
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
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ec.edu.uce.R;
import ec.edu.uce.controlador.MyDatePicker;
import ec.edu.uce.modelo.Vehiculo;

public class VehiculoRegistroActivity extends AppCompatActivity {

    private DatePicker date;
    private EditText txtPlaca, txtMarca, txtCosto;
    private Switch matriculado;
    private RadioButton rbBlanco, rbNegro, rbOtro;
    private final String ARCHIVO = "vehiculos.json";
    private Button botonEdicion, botonAgregar;
    private Spinner colores;
    File dataFile = new File(Environment.getExternalStorageDirectory(), ARCHIVO);
    Set<Vehiculo> lista = new LinkedHashSet<>();
    Gson gson = new Gson();
    String cadPlaca = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo_registro);
        txtPlaca = (EditText) findViewById(R.id.txtPlaca);
        txtMarca = (EditText) findViewById(R.id.txtMarca);
        txtCosto = (EditText) findViewById(R.id.txtCosto);
        matriculado = (Switch) findViewById(R.id.swMatricula);
        rbBlanco = (RadioButton) findViewById(R.id.rbtBlanco);
        rbNegro = (RadioButton) findViewById(R.id.rbtNegro);
        rbOtro = (RadioButton) findViewById(R.id.rbtOtro);
        date = (DatePicker) findViewById(R.id.spFecha);
        botonAgregar = (Button) findViewById(R.id.btnRegistro);
        colores = (Spinner) findViewById(R.id.spColores);
        //botonEdicion = (Button) findViewById(R.id.btnEdicion);


        String modo = getIntent().getStringExtra("editar");

        if(modo != null ){
            //botonEdicion.setEnabled(true);
            //botonAgregar.setEnabled(false);
            botonAgregar.setText("Editar");
            cadPlaca = getIntent().getStringExtra("placa");
            String cadMarca = getIntent().getStringExtra("marca");
            String cadCost = String.valueOf(getIntent().getDoubleExtra("costo", 0.0));
            String cadColor = getIntent().getStringExtra("color");
            System.out.println("COLOR"+cadColor);
            if(cadColor.equalsIgnoreCase("Blanco")){
                rbBlanco.setChecked(true);
            }
            if(cadColor.equalsIgnoreCase("Negro")){
                rbNegro.setChecked(true);
            }
            if(cadColor.equalsIgnoreCase("Otro")){
                rbOtro.setChecked(true);
            }

            boolean banMatriculado = getIntent().getBooleanExtra("matriculado", false);
            String fec = getIntent().getStringExtra("fecha");
            System.out.println("Fecha"+fec);
            if(txtPlaca.getText().toString().isEmpty()){
                txtPlaca.setText(cadPlaca);
                txtPlaca.setEnabled(false);
                txtMarca.setText(cadMarca);
                txtCosto.setText(cadCost);
                matriculado.setChecked(banMatriculado);

                // Date tiempo = new Date(fec);
                // Calendar time = Calendar.getInstance();
                // time.setTime(tiempo);
                LocalDate nuevaFecha = LocalDate.parse(fec);
                date.updateDate(nuevaFecha.getYear(),nuevaFecha.getMonthValue(),nuevaFecha.getDayOfMonth());
                botonAgregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editar();
                    }
                });
            }
        }else{
            //botonEdicion.setEnabled(false);
            //botonAgregar.setEnabled(true);
            botonAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registrar();
                }
            });
        }


    }

    public void registrar() {

        // Date fec = new Date(date.getYear(), date.getMonth(), date.getDayOfMonth());
        String color = "";
        boolean mat;
        String patron = "[a-zA-Z]{3}-\\d{4}";
        Pattern p = Pattern.compile(patron);
        Matcher m = p.matcher(txtPlaca.getText().toString());
        if(m.matches()){
            lista = listar();
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setPlaca(txtPlaca.getText().toString());
            vehiculo.setMarca(txtMarca.getText().toString());
            int cost = (int) Double.parseDouble(txtCosto.getText().toString());

            // validacion de costo
            int num1= 15000;
            int num2 = 35000;
            if (num1 < cost && cost < num2){
                vehiculo.setCosto(Double.parseDouble(txtCosto.getText().toString()));
                vehiculo.setFecFabricacion(LocalDate.of(date.getYear(),date.getMonth(),date.getDayOfMonth()));

                vehiculo.setMatriculado(matriculado.isChecked());
                if(rbNegro.isChecked()){
                    color = "Negro";
                }
                if(rbBlanco.isChecked()){
                    color = "Blanco";
                }
                if(rbOtro.isChecked()){
                    color="Otro";
                }
                vehiculo.setColor(color);

                if(lista.add(vehiculo)){
                    try {
                        FileWriter fileWriter = new FileWriter(dataFile, false);
                        PrintWriter out = new PrintWriter(fileWriter);
                        String json = gson.toJson(lista);
                        out.println(json);
                        out.flush();
                        out.close();
                        System.out.println("se ha guardado en " + dataFile.getAbsolutePath());
                        Toast.makeText(this, "guardado correctamente", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(VehiculoRegistroActivity.this, VehiculosActivity.class);
                        startActivity(intent);
                    } catch (IOException e) {
                        Toast.makeText(this, "No se pudo guardar", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "El vehiculo con esta placa ya existe", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Ingrese un valor correcto", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "No es una Placa válida", Toast.LENGTH_SHORT).show();
        }




    }

    public Set<Vehiculo> listar(){
        Set<Vehiculo> list = new LinkedHashSet<>();
        try {
            if (dataFile.exists()) {
                FileReader fileReader = new FileReader(dataFile);
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

    public void showDatePicker(View view) {
        DialogFragment newFragment = new MyDatePicker();
        newFragment.show(getSupportFragmentManager(), "date picker");
    }

    public void editar(){

        Set<Vehiculo> lst = listar();
        ArrayList<Vehiculo> arr = new ArrayList<>(lst);
        Vehiculo vh = new Vehiculo();
        for(Vehiculo it:arr){
            if(it.getPlaca().equalsIgnoreCase(cadPlaca)){
                arr.remove(it);
                break;
            }
        }
        String color = "";
        boolean mat;
        vh.setPlaca(txtPlaca.getText().toString());
        vh.setMarca(txtMarca.getText().toString());
        vh.setCosto(Double.parseDouble(txtCosto.getText().toString()));
        vh.setFecFabricacion(LocalDate.of(date.getYear(),date.getMonth(),date.getDayOfMonth()));
        vh.setMatriculado(matriculado.isChecked());
        if(rbNegro.isChecked()){
            color = "Negro";
        }
        if(rbBlanco.isChecked()){
            color = "Blanco";
        }
        if(rbOtro.isChecked()){
            color="Otro";
        }
        vh.setColor(color);
        Set<Vehiculo> nuevaSet = new LinkedHashSet<>();
        arr.add(vh);
        for(Vehiculo v:arr){
            nuevaSet.add(v);
        }
        lista = nuevaSet;
        try{
            FileWriter fileWriter = new FileWriter(dataFile, false);
            PrintWriter out = new PrintWriter(fileWriter);
            String json = gson.toJson(lista);
            out.println(json);
            out.flush();
            out.close();
            System.out.println("se ha guardado en " + dataFile.getAbsolutePath());
            Toast.makeText(this, "Editado correctamente", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            e.printStackTrace();
        }
        Intent intent = new Intent(this, VehiculosActivity.class);
        startActivity(intent);
    }


}
