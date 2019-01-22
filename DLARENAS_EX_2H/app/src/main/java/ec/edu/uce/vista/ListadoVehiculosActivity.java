package ec.edu.uce.vista;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import ec.edu.uce.controlador.AdaptadorVehiculo;
import ec.edu.uce.dao.ReservaDAO;
import ec.edu.uce.dao.VehiculoDAO;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.Vehiculo;

public class ListadoVehiculosActivity extends AppCompatActivity {

    private ListView listView;
    private VehiculoDAO dao;
    private ReservaDAO daoReserva;
    private AdaptadorVehiculo adapter;
    private List<Vehiculo> lista;
    private SharedPreferences preferences;
    private final String ARCHIVO_VEHICULO = "vehiculo-"+LocalDateTime.now().toString()+".xml";
    private final String ARCHIVO_RESERVAS = "reserva-"+LocalDateTime.now().toString()+".xml";
    File dataFileVehiculo = new File(Environment.getExternalStorageDirectory(), ARCHIVO_VEHICULO);
    File dataFileReserva = new File(Environment.getExternalStorageDirectory(), ARCHIVO_RESERVAS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_vehiculos);

        preferences = getSharedPreferences("Orden", Context.MODE_PRIVATE);
        listView = findViewById(R.id.listViewAuto);
        dao = new VehiculoDAO(this);
        daoReserva = new ReservaDAO(this);

        lista = dao.listar();
        String orden = preferences.getString("orden", " ");
        if(orden.equalsIgnoreCase("ascendente")){
            Collections.sort(lista, new Comparator<Vehiculo>() {
                @Override
                public int compare(Vehiculo o1, Vehiculo o2) {
                    return new String(o1.getPlaca()).compareTo(new String(o2.getPlaca()));
                }
            });
        }

        if(orden.equalsIgnoreCase("descendente")){
            Collections.sort(lista, new Comparator<Vehiculo>() {
                @Override
                public int compare(Vehiculo o1, Vehiculo o2) {
                    return new String(o2.getPlaca()).compareTo(new String(o1.getPlaca()));
                }
            });
        }

        adapter = new AdaptadorVehiculo(this, lista);

        listView.setAdapter(adapter);

        registerForContextMenu(listView);
    }

    //para crear el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vehiculo, menu);
        return true;
    }

    //para manejar los eventos del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nuevoItem:
                Intent intent = new Intent(ListadoVehiculosActivity.this, RegisterVehiculoActivity.class);
                startActivity(intent);
                break;
            case R.id.configItem:
                Intent intent2 = new Intent(ListadoVehiculosActivity.this, ConfiguracionesActivity.class);
                startActivity(intent2);
                break;
            case R.id.findItem:
                Intent intent3 = new Intent(ListadoVehiculosActivity.this, BuscarAutoActivity.class);
                startActivity(intent3);
                break;
            case R.id.respaldoMenu:
                writeXmlFile(lista);
                writeReservaXmlFile(daoReserva.listar());
                break;


        }
        return super.onOptionsItemSelected(item);

    }

    //para el menu de contexto
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(lista.get(info.position).getPlaca());
        inflater.inflate(R.menu.context_menu, menu);
    }

    //manejamos eventos del click del context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.eliminarItem:
                eliminarVehiculo(info);
                break;
            case R.id.editarItem:
                editarVehiculo(info);
                break;
        }

        return super.onContextItemSelected(item);

    }

    //metodos locales
    private void eliminarVehiculo(AdapterView.AdapterContextMenuInfo info) {
        dao.borrar(lista.get(info.position).getPlaca());
        List<Vehiculo> lst = dao.listar();
        adapter = new AdaptadorVehiculo(this, lst);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void editarVehiculo(AdapterView.AdapterContextMenuInfo info){
        Intent intent = new Intent(ListadoVehiculosActivity.this, RegisterVehiculoActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("vehiculo", lista.get(info.position));
        intent.putExtra("envio", b);
        startActivity(intent);
    }

    public void writeXmlFile (List<Vehiculo> list) {

        try {
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            Element root = doc.createElement("Vehiculos");
            doc.appendChild(root);

            for (Vehiculo vh : list) {

                Element Details = doc.createElement("Vehiculo");
                root.appendChild(Details);

                Element placa = doc.createElement("Placa");
                placa.appendChild(doc.createTextNode(String.valueOf(vh.getPlaca())));
                Details.appendChild(placa);


                Element color = doc.createElement("Color");
                color.appendChild(doc.createTextNode(String.valueOf(vh.getColor())));
                Details.appendChild(color);

                Element marca = doc.createElement("Marca");
                marca.appendChild(doc.createTextNode(String.valueOf(vh.getMarca())));
                Details.appendChild(marca);

                Element fec_fabricacion = doc.createElement("FechaFabricacion");
                fec_fabricacion.appendChild(doc.createTextNode(String.valueOf(vh.getFecFabricacion())));
                Details.appendChild(fec_fabricacion);

                Element costo = doc.createElement("Costo");
                costo.appendChild(doc.createTextNode(String.valueOf(vh.getCosto())));
                Details.appendChild(costo);

                Element foto = doc.createElement("foto");
                foto.appendChild(doc.createTextNode(String.valueOf(vh.getFoto())));
                Details.appendChild(foto);

                Element estado = doc.createElement("Estado");
                estado.appendChild(doc.createTextNode(String.valueOf(vh.getEstado())));
                Details.appendChild(estado);

                Element tipo = doc.createElement("Tipo");
                tipo.appendChild(doc.createTextNode(String.valueOf(vh.getTipo())));
                Details.appendChild(tipo);

            }

            TransformerFactory tranfactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranfactory.newTransformer();

            aTransformer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
            aTransformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");
            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            try {
                // location and name of XML file you can change as per need
                FileWriter fos = new FileWriter(dataFileVehiculo);
                StreamResult result = new StreamResult(fos);
                aTransformer.transform(source, result);

            } catch (IOException e) {

                e.printStackTrace();
            }

        } catch (TransformerException ex) {
            System.out.println("Error outputting document");

        } catch (ParserConfigurationException ex) {
            System.out.println("Error building document");
        }


    }

    public void writeReservaXmlFile (List<Reserva> list) {

        try {
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            Element root = doc.createElement("Reservas");
            doc.appendChild(root);

            for (Reserva vh : list) {

                Element Details = doc.createElement("Reserva");
                root.appendChild(Details);

                Element placa = doc.createElement("placa");
                placa.appendChild(doc.createTextNode(String.valueOf(vh.getPlaca())));
                Details.appendChild(placa);


                Element numRes = doc.createElement("NumReserva");
                numRes.appendChild(doc.createTextNode(String.valueOf(vh.getNumero())));
                Details.appendChild(numRes);

                Element emailres = doc.createElement("email");
                emailres.appendChild(doc.createTextNode(String.valueOf(vh.getEmail())));
                Details.appendChild(emailres);

                Element celular = doc.createElement("Celular");
                celular.appendChild(doc.createTextNode(String.valueOf(vh.getCelular())));
                Details.appendChild(celular);

                Element fechaPrestamo = doc.createElement("FechaPrestamo");
                fechaPrestamo.appendChild(doc.createTextNode(String.valueOf(vh.getFechaPrestamo())));
                Details.appendChild(fechaPrestamo);

                Element fechaEntrega = doc.createElement("FechaEntrega");
                fechaEntrega.appendChild(doc.createTextNode(String.valueOf(vh.getFechaEntrega())));
                Details.appendChild(fechaEntrega);

                Element valorres = doc.createElement("ValorReserva");
                valorres.appendChild(doc.createTextNode(String.valueOf(vh.getValorReserva())));
                Details.appendChild(valorres);

            }

            TransformerFactory tranfactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranfactory.newTransformer();

            aTransformer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
            aTransformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");
            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            try {
                // location and name of XML file you can change as per need
                FileWriter fos = new FileWriter(dataFileReserva);
                StreamResult result = new StreamResult(fos);
                aTransformer.transform(source, result);

            } catch (IOException e) {

                e.printStackTrace();
            }

        } catch (TransformerException ex) {
            System.out.println("Error outputting document");

        } catch (ParserConfigurationException ex) {
            System.out.println("Error building document");
        }


    }
}
