package com.uce.utils;

import android.os.Environment;

import com.uce.entity.Persona;
import com.uce.tarea_04_g09.RegistroActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IOStream {

    private final String ARCHIVO = "data.obj";
    File dataFile = new File(Environment.getExternalStorageDirectory(), ARCHIVO);
    public List<Persona> listarTodos() throws ClassNotFoundException, IOException{
        ObjectInputStream ois = null;
        List<Persona> lista = new ArrayList<>();
        try{
            FileInputStream fis = new FileInputStream(dataFile);
            ois = new ObjectInputStream(fis);
            Object aux = ois.readObject();
            while(true){

                Persona per = new Persona();
                per = (Persona) aux;
                /*
                System.out.println("Usuario: "+per.getUsuario());
                System.out.println("Clave: "+per.getClave());
                System.out.println("Nombre: "+per.getNombre());
                System.out.println("**************************");
                */
                lista.add(per);
               // System.out.println("tamaño lista: "+lista.size());

                ObjectInputStream oin = new ObjectInputStream(fis);
                aux = oin.readObject();
            }

        } catch (IOException io){
            System.out.println("\n************FIN**************");
            //io.printStackTrace();
        } finally {
            ois.close();
           // System.out.println("tamaño lista Finally: "+lista.size());
        }
        return lista;
    }
}
