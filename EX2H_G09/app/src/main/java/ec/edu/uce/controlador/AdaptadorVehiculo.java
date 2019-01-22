package ec.edu.uce.controlador;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.vista.R;

public class AdaptadorVehiculo extends BaseAdapter {
    private Context context;

    private List<Vehiculo> lista;

    public AdaptadorVehiculo(Context context, List<Vehiculo> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView= layoutInflater.inflate(R.layout.list_item_vehiculo, null );

        TextView placa = convertView.findViewById(R.id.tvPlaca);
        TextView marca = convertView.findViewById(R.id.tvMarca);
        TextView fabricacion = convertView.findViewById(R.id.tvFabricacion);
        TextView costo = convertView.findViewById(R.id.tvCosto);
        TextView matriculado = convertView.findViewById(R.id.tvMatriculado);
        TextView color = convertView.findViewById(R.id.tvColor);
        TextView estado = convertView.findViewById(R.id.tvEstado);
        TextView tipo = convertView.findViewById(R.id.tvTipo);
        ImageView imagen = convertView.findViewById(R.id.imgVehiculo);

        placa.setText(lista.get(position).getPlaca());
        marca.setText(lista.get(position).getMarca());
        fabricacion.setText(lista.get(position).getFecFabricacion().toString());
        costo.setText(lista.get(position).getCosto().toString());
        matriculado.setText(lista.get(position).getMatriculado().toString());
        color.setText(lista.get(position).getColor());
        estado.setText(lista.get(position).getEstado().toString());
        tipo.setText(lista.get(position).getTipo());
        imagen.setImageBitmap(BitmapFactory.decodeByteArray(lista.get(position).getFoto(),0, lista.get(position).getFoto().length));

        return convertView;
    }
}
