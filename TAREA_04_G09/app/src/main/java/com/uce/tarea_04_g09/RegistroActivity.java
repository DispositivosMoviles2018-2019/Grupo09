package com.uce.tarea_04_g09;

import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.uce.entity.Persona;
import com.uce.utils.MyDatePicker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                //validacion numero
                String num = celular.getText().toString();
                String nCelular = "null";
                String ema = email.getText().toString();
                String nEmail = "null";
                if (isNumberValid(num) && isEmailValid(ema)) {
                    nCelular = num;
                    nEmail = ema;
                    registrar();
                }
            }
        });
    }

    private void registrar() {

        try {
            List<String> materias = new ArrayList<>();
            if (checkSociales.isChecked()) {
                materias.add("Sociales");
            }
            if (checkMineria.isChecked()) {
                materias.add("Mineria");
            }
            if (checkGestion.isChecked()) {
                materias.add("Gestion");
            }
            if (checkDistribuida.isChecked()) {
                materias.add("Distribuida");
            }
            if (checkMatematica.isChecked()) {
                materias.add("Matematica");
            }
            int genero = 0;
            boolean beca = false;
            if (swBeca.isChecked()) {
                beca = true;
            }
            if (radioMasculino.isChecked()) {
                genero = 1;
            } else {
                genero = 0;
            }

            Date date = new Date(fecha.getYear(), fecha.getMonth(), fecha.getDayOfMonth());
            FileOutputStream out = new FileOutputStream(dataFile, true);
            ObjectOutputStream ost = new ObjectOutputStream(out);
            ost.writeObject(new Persona(usuario.getText().toString(), clave.getText().toString(),
                    nombre.getText().toString(), apellido.getText().toString(), email.getText().toString(),
                    celular.getText().toString(), genero, date, beca, materias));
            ost.close();
            Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
            startActivity(intent);
            //System.out.println("Escrito Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new MyDatePicker();
        newFragment.show(getSupportFragmentManager(), "date picker");
    }

    //validacion del Email
    public boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        } else {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("aviso");
            dlg.setMessage("campo email invalido");
            dlg.setNeutralButton("ok", null);
            dlg.show();
            getApplicationContext();
        }
        return isValid;
    }

    //validacion celular
    public boolean isNumberValid(String cel) {
        boolean isValid = false;
        String expresionNumeros = "^[0-9]{10}$";
        CharSequence inputStr = cel;

        Pattern patt = Pattern.compile(expresionNumeros, Pattern.CASE_INSENSITIVE);
        Matcher match = patt.matcher(inputStr);
        if (match.matches()) {
            isValid = true;
        } else {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("aviso");
            dlg.setMessage("campos celular invalido");
            dlg.setNeutralButton("ok", null);
            dlg.show();
            getApplicationContext();

        }
        return isValid;
    }


}
