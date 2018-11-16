package com.uce.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uce.entity.Persona;
import com.uce.tarea_04_g09.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<Persona> lista;
    LayoutInflater inflater;

    public CustomAdapter(Context context, List<Persona> lista) {
        this.context = context;
        this.lista = lista;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_listado, null);
        TextView empty = (TextView) convertView.findViewById(R.id.myempty);
        empty.setText(lista.get(position).toString());
        return convertView;
    }
}
