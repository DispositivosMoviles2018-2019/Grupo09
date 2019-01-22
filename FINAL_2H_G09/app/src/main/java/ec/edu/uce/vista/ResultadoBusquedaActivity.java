package ec.edu.uce.vista;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ec.edu.uce.controlador.AdaptadorVehiculo;
import ec.edu.uce.dao.VehiculoDAO;
import ec.edu.uce.modelo.Vehiculo;

public class ResultadoBusquedaActivity extends AppCompatActivity {

    private TextView placa, marca, fabricacion, costo, matriculado, color, estado, tipo;
    private ImageView imagen;
    private VehiculoDAO dao;
    private Vehiculo vh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busqueda);

        placa = findViewById(R.id.tvResPlaca);
        marca = findViewById(R.id.tvResMarca);
        fabricacion = findViewById(R.id.tvResFabricacion);
        costo = findViewById(R.id.tvResCosto);
        matriculado = findViewById(R.id.tvResMatriculado);
        color = findViewById(R.id.tvResColor);
        estado = findViewById(R.id.tvResEstado);
        tipo = findViewById(R.id.tvResTipo);
        imagen = findViewById(R.id.imgResVehiculo);

        dao = new VehiculoDAO(this);
        Bundle b = getIntent().getBundleExtra("bundle");
        if(b!= null){
            vh = (Vehiculo) b.get("vehiculo");
            placa.setText(vh.getPlaca());
            marca.setText(vh.getMarca());
            costo.setText(vh.getCosto().toString());
            fabricacion.setText(vh.getFecFabricacion().toString());
            matriculado.setText(vh.getMatriculado()==true?"SI":"NO");
            color.setText(vh.getColor());
            estado.setText(vh.getEstado()==true?"Disponible":"Ocupado");
            tipo.setText(vh.getTipo());
            imagen.setImageBitmap(BitmapFactory.decodeByteArray(vh.getFoto(), 0, vh.getFoto().length));


        }
        registerForContextMenu(imagen);
    }

    public void irMenu(View view){
        Intent intent = new Intent(ResultadoBusquedaActivity.this, OpcionesMenuActivity.class);
        startActivity(intent);
    }

    //para el menu de contexto
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("Opciones");
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
        String mensaje = dao.borrar(vh.getPlaca());
        if(mensaje.equalsIgnoreCase("Vehiculo Borrado")){
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ResultadoBusquedaActivity.this, BuscarAutoActivity.class);
            startActivity(intent);
        }

    }

    private void editarVehiculo(AdapterView.AdapterContextMenuInfo info){
        Intent intent = new Intent(ResultadoBusquedaActivity.this, RegisterVehiculoActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("vehiculo", vh);
        intent.putExtra("envio", b);
        startActivity(intent);
    }
}
