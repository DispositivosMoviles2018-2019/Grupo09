package com.uce.tarea_04_g09;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.uce.entity.Persona;
import com.uce.utils.CustomAdapter;
import com.uce.utils.IOStream;

import java.util.ArrayList;
import java.util.List;

public class ListadoActivity extends AppCompatActivity {

    private ListView lista;
    private TextView empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        try{
            List<Persona> list = new ArrayList<>();
            IOStream st = new IOStream();
            list = st.listarTodos();
            lista = (ListView) findViewById(R.id.mylist);
            empty = (TextView) findViewById(R.id.myempty);
            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), list);
            lista.setAdapter(customAdapter);
        }catch(Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item1){

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }


        return super.onOptionsItemSelected(item);
    }

}
