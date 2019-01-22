package ec.edu.uce.vista;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;

import ec.edu.uce.controlador.ConvertidorImages;
import ec.edu.uce.controlador.Utilitario;
import ec.edu.uce.dao.VehiculoDAO;
import ec.edu.uce.modelo.Vehiculo;

public class RegisterVehiculoActivity extends AppCompatActivity {

    private EditText txtPlaca, txtMarca, txtCosto;
    private DatePicker fecha;
    private Switch matricula;
    private Spinner color, tipo;
    private Button btnAgregar, btnSubirFoto;
    private ImageView imagen;

    private VehiculoDAO dao;
    private ConvertidorImages convertidor;
    private static final int SELECT_FILE = 1;
    private boolean bandera;
    private Utilitario util;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehiculo);

        txtPlaca = findViewById(R.id.txtPlaca);
        txtMarca = findViewById(R.id.txtMarca);
        txtCosto = findViewById(R.id.txtCosto);
        fecha = findViewById(R.id.pickerFecha);
        matricula = findViewById(R.id.swMatricula);
        color = findViewById(R.id.spinnerColor);
        tipo = findViewById(R.id.spinnerTipo);
        imagen = findViewById(R.id.ivFoto);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnSubirFoto = findViewById(R.id.btnSubirFoto);

        String[] opcionesColor = {"Blanco", "Negro", "Otro"};
        ArrayAdapter<String> adapterColor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesColor);
        color.setAdapter(adapterColor);

        String[] opcionesTipo = {"automovil", "camioneta", "furgoneta"};
        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesTipo);
        tipo.setAdapter(adapterTipo);

        util = new Utilitario();
        dao = new VehiculoDAO(this);
        btnAgregar.setText("Agregar");
        //para la edición

        Bundle b = getIntent().getBundleExtra("envio");
        if(b!= null){

            btnAgregar.setText("Editar");
            Vehiculo vh = (Vehiculo) b.get("vehiculo");
            txtPlaca.setText(vh.getPlaca());
            txtPlaca.setEnabled(false);
            txtMarca.setText(vh.getMarca());
            txtCosto.setText(vh.getCosto().toString());
            fecha.updateDate(vh.getFecFabricacion().getYear(), vh.getFecFabricacion().getMonthValue(),
                    vh.getFecFabricacion().getDayOfMonth());
            matricula.setChecked(vh.getMatriculado());
            if(vh.getColor().equalsIgnoreCase("Blanco")){
                color.setSelection(0, true);
            }else if(vh.getColor().equalsIgnoreCase("Negro")){
                color.setSelection(1, true);
            }else{
                color.setSelection(2, true);
            }

            if(vh.getTipo().equalsIgnoreCase("automovil")){
                tipo.setSelection(0, true);
            }else if(vh.getTipo().equalsIgnoreCase("camioneta")){
                tipo.setSelection(1, true);
            }else{
                tipo.setSelection(2, true);
            }
            imagen.setImageBitmap(BitmapFactory.decodeByteArray(vh.getFoto(), 0, vh.getFoto().length));
            btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actualizar();
                }
            });

        }else{
            btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    agregar();
                }
            });
        }

    }

    public void agregar(){
        Vehiculo vehiculo = new Vehiculo();
        convertidor = new ConvertidorImages();
        String placaValida = txtPlaca.getText().toString();
        if(util.isPlaca(placaValida)){
            vehiculo.setPlaca(txtPlaca.getText().toString());
            vehiculo.setMarca(txtMarca.getText().toString());
            vehiculo.setCosto(Double.parseDouble(txtCosto.getText().toString()));
            vehiculo.setFecFabricacion(LocalDate.of(fecha.getYear(), fecha.getMonth()+1, fecha.getDayOfMonth()));
            vehiculo.setMatriculado(matricula.isChecked());
            vehiculo.setColor(color.getSelectedItem().toString());
            vehiculo.setTipo(tipo.getSelectedItem().toString());
            vehiculo.setFoto(convertidor.codificar(imagen.getDrawable()));
            String mensaje = dao.crear(vehiculo);
            if(mensaje.equalsIgnoreCase("Vehiculo creado")){
                Intent intent = new Intent(RegisterVehiculoActivity.this, ListadoVehiculosActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "No se ha podido crear", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Ingrese una placa correcta", Toast.LENGTH_SHORT).show();
        }



    }

    public void actualizar(){
        Vehiculo vehiculo = new Vehiculo();
        convertidor = new ConvertidorImages();
        vehiculo.setPlaca(txtPlaca.getText().toString());
        vehiculo.setMarca(txtMarca.getText().toString());
        vehiculo.setCosto(Double.parseDouble(txtCosto.getText().toString()));
        vehiculo.setFecFabricacion(LocalDate.of(fecha.getYear(), fecha.getMonth()+1, fecha.getDayOfMonth()));
        vehiculo.setMatriculado(matricula.isChecked());
        vehiculo.setColor(color.getSelectedItem().toString());
        vehiculo.setTipo(tipo.getSelectedItem().toString());
        vehiculo.setFoto(convertidor.codificar(imagen.getDrawable()));
        String mensaje = dao.actualizar(vehiculo);
        if(mensaje.equalsIgnoreCase("Vehiculo Modificado")){
            Intent intent = new Intent(RegisterVehiculoActivity.this, ListadoVehiculosActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "No se ha podido crear", Toast.LENGTH_SHORT).show();
        }
    }

    public void mostrarOpciones(View view) {
        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterVehiculoActivity.this);
        builder.setTitle("Elige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Tomar foto"){
                    //abrir camara
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                    bandera = true;
                }else if(option[which] == "Elegir de galeria"){
                    //elegir de la galeria
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(
                            Intent.createChooser(intent, "Seleccione una imagen"),
                            SELECT_FILE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (bandera) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imagen.setImageBitmap(bitmap);
            bandera = false;
        } else {
            Uri selectedImage;
            switch (requestCode) {
                case SELECT_FILE:
                    if (resultCode == RegisterVehiculoActivity.RESULT_OK) {
                        selectedImage = data.getData();
                        String selectedPath = selectedImage.getPath();
                        if (requestCode == SELECT_FILE) {
                            if (selectedPath != null) {
                                InputStream imageStream = null;
                                try {
                                    imageStream = getContentResolver().openInputStream(
                                            selectedImage);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                                Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                                // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                                imagen.setImageBitmap(bmp);

                            }
                        }
                    }
                    break;
            }
        }

    }


}
