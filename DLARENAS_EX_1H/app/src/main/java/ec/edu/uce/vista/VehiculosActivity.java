package ec.edu.uce.vista;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import ec.edu.uce.controlador.Adaptador;
import ec.edu.uce.modelo.Vehiculo;

public class VehiculosActivity extends AppCompatActivity {

    private ListView listView;
    private Adaptador adapter;
    Set<Vehiculo> list = new LinkedHashSet<>();
    VehiculoRegistroActivity vh = new VehiculoRegistroActivity();
    private final String ARCHIVO = "vehiculos.json";
    File dataFile = new File(Environment.getExternalStorageDirectory(), ARCHIVO);
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);

        list = vh.listar();
        adapter = new Adaptador(this, list);
        listView = (ListView) findViewById(R.id.lvVehiculos);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.itemEdit:
                editarVehiculo(info);
                return true;

            case R.id.itemDelete:
                eliminarVehiculo(info);
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }

    private void eliminarVehiculo(AdapterView.AdapterContextMenuInfo info){
        ArrayList<Vehiculo> lst = new ArrayList<>(list);
        lst.remove(info.position);
        Set<Vehiculo> nueva = new LinkedHashSet<>();
        for (Vehiculo vh: lst) {
            nueva.add(vh);
        }
        list = nueva;
        try {
            FileWriter fileWriter = new FileWriter(dataFile, false);
            PrintWriter out = new PrintWriter(fileWriter);
            String json = gson.toJson(list);
            out.println(json);
            out.flush();
            out.close();
            System.out.println("se ha guardado en " + dataFile.getAbsolutePath());
            Toast.makeText(this, "Eliminado correctamente", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "No se pudo guardar", Toast.LENGTH_SHORT).show();
        }
        adapter = new Adaptador(this, nueva);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private void editarVehiculo(AdapterView.AdapterContextMenuInfo info){
        Intent intent = new Intent(this, VehiculoRegistroActivity.class);
        ArrayList<Vehiculo> lst = new ArrayList<>(list);
        intent.putExtra("placa", lst.get(info.position).getPlaca());
        intent.putExtra("marca", lst.get(info.position).getMarca());
        intent.putExtra("costo", lst.get(info.position).getCosto());
        intent.putExtra("matriculado", lst.get(info.position).isMatriculado());
        intent.putExtra("color", lst.get(info.position).getColor());
        intent.putExtra("fecha", lst.get(info.position).getFecFabricacion().toString());
        intent.putExtra("editar", "edicion");
        startActivity(intent);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_barra, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.itemNuevo){
            Intent intent = new Intent(this, VehiculoRegistroActivity.class);

            startActivity(intent);
        }
        if(item.getItemId() == R.id.itemCerrar){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.itemSalir){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
