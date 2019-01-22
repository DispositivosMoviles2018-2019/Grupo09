package ec.edu.uce.vista;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ec.edu.uce.dao.ReservaDAO;
import ec.edu.uce.dao.VehiculoDAO;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.Vehiculo;
import harmony.java.awt.Color;

public class ConfirmacionActivity extends AppCompatActivity {

    private TextView placa, numReserva, email, celular, fechaPrestamo, fechaEntrega, valor;
    private ImageView imagen;
    private ReservaDAO daoReserva;
    private VehiculoDAO daoVehiculo;
    private Button btnGenerar;
    private final static String NOMBRE_DIRECTORIO = "MiPdf";
    private final static String NOMBRE_DOCUMENTO = "reserva";
    private final static String ETIQUETA_ERROR = "ERROR";
    private Vehiculo vehiculo;
    private Reserva reserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion);
        //confirmaciones para el pdf
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1000);
        } else {
        }
        btnGenerar = findViewById(R.id.btnResBusMain);
        daoReserva = new ReservaDAO(this);
        daoVehiculo = new VehiculoDAO(this);
        placa = findViewById(R.id.tvConPlaca);
        numReserva = findViewById(R.id.tvConNumero);
        email = findViewById(R.id.tvConEmail);
        celular = findViewById(R.id.tvConCelular);
        fechaPrestamo = findViewById(R.id.tvConFechaPres);
        fechaEntrega = findViewById(R.id.tvConFechaEnt);
        valor = findViewById(R.id.tvConValor);
        imagen = findViewById(R.id.imgConfVehiculo);

        String placaIntent = getIntent().getStringExtra("placa");
        vehiculo = daoVehiculo.buscarPorParametro(daoVehiculo.listar(), placaIntent);
        reserva = daoReserva.buscarPorParametro(daoReserva.listar(), placaIntent);
        if(reserva.getPlaca() != null){
            placa.setText(reserva.getPlaca());
            numReserva.setText(reserva.getNumero());
            email.setText(reserva.getEmail());
            celular.setText(reserva.getCelular());
            fechaPrestamo.setText(reserva.getFechaPrestamo().toString());
            fechaEntrega.setText(reserva.getFechaEntrega().toString());
            valor.setText(String.valueOf(reserva.getValorReserva()));
            imagen.setImageBitmap(BitmapFactory.decodeByteArray(vehiculo.getFoto(), 0, vehiculo.getFoto().length));

        }

        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarPDF();
                Toast.makeText(ConfirmacionActivity.this, "Se creo tu archivo pdf", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void generarPDF(){
        // Creamos el documento.
        Document documento = new Document();

        try {

            File f = crearFichero(NOMBRE_DOCUMENTO+reserva.getNumero()+".pdf");

            // Creamos el flujo de datos de salida para el fichero donde
            // guardaremos el pdf.
            FileOutputStream ficheroPdf = new FileOutputStream(
                    f.getAbsolutePath());

            // Asociamos el flujo que acabamos de crear al documento.
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

            // Incluimos el pie de pagina y una cabecera
            HeaderFooter cabecera = new HeaderFooter(new Phrase(
                    "PROYECTO FINAL OPTATIVA 3"), false);
            HeaderFooter pie = new HeaderFooter(new Phrase(
                    "Grupo 9: Lascano, Larenas, Montalvo"), false);

            documento.setHeader(cabecera);
            documento.setFooter(pie);

            // Abrimos el documento.
            documento.open();

            // Añadimos un titulo con la fuente por defecto.
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 28, Font.BOLD, Color.RED);
            documento.add(new Paragraph("RESERVA"));

            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicacion.
            Bitmap bitmap = BitmapFactory.decodeByteArray(vehiculo.getFoto(), 0, vehiculo.getFoto().length);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            Image imagen = Image.getInstance(stream.toByteArray());

            documento.add(new Paragraph("Placa: "+ reserva.getPlaca()) );
            documento.add(new Paragraph("Numero de reserva: "+ reserva.getNumero()) );
            documento.add(new Paragraph("Email: "+ reserva.getEmail()) );
            documento.add(new Paragraph("Celular: "+ reserva.getCelular()) );
            documento.add(new Paragraph("Fecha de Préstamo: "+ reserva.getFechaPrestamo()) );
            documento.add(new Paragraph("Fecha de Entrega: "+ reserva.getFechaEntrega()) );
            documento.add(new Paragraph("Valor de Reserva: "+ reserva.getValorReserva()) );
            documento.add(imagen);

            // Agregar marca de agua
            //font = FontFactory.getFont(FontFactory.HELVETICA, 42, Font.BOLD);
            ColumnText.showTextAligned(writer.getDirectContentUnder(),
                    Element.ALIGN_CENTER, new Paragraph(
                            "GRUPO 9"), 297.5f, 421,
                    writer.getPageNumber() % 2 == 1 ? 45 : -45);

        } catch (DocumentException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } catch (IOException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } finally {
            // Cerramos el documento.
            documento.close();
        }
    }

    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }
    public static File getRuta() {

        // El fichero sera almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        } else {
        }

        return ruta;
    }



}
