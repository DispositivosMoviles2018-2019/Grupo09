package ec.edu.uce.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ec.edu.uce.R;
import ec.edu.uce.modelo.Vehiculo;


public class Adaptador extends BaseAdapter {
    private Context context;
    private Set<Vehiculo> lista;

    public Adaptador(Context context, Set<Vehiculo> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        List<Vehiculo> listaTemp = new ArrayList<>(lista);
        return listaTemp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_vehiculo, null);
        }
        TextView placa = (TextView) convertView.findViewById(R.id.tvPlaca);
        TextView marca = (TextView) convertView.findViewById(R.id.tvMarca);
        TextView costo = (TextView) convertView.findViewById(R.id.tvCosto);
        TextView matriculado = (TextView) convertView.findViewById(R.id.tvMatricula);
        TextView color = (TextView) convertView.findViewById(R.id.tvColor);
        TextView fecha = (TextView) convertView.findViewById(R.id.tvFecha);
        List<Vehiculo> listaTemp = new ArrayList<>(lista);
        placa.setText(listaTemp.get(position).getPlaca());
        marca.setText(listaTemp.get(position).getMarca());
        costo.setText(String.valueOf(listaTemp.get(position).getCosto()));
        matriculado.setText(listaTemp.get(position).isMatriculado()?"Si":"No");
        color.setText(listaTemp.get(position).getColor());
        fecha.setText(listaTemp.get(position).getFecFabricacion().toString());
        return convertView;
    }
}
