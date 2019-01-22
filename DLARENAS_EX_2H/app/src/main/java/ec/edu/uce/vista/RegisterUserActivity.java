package ec.edu.uce.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.controlador.Encriptacion;
import ec.edu.uce.dao.UsuarioDAO;
import ec.edu.uce.modelo.Usuario;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText txtUsuario, txtClave;
    private UsuarioDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        txtUsuario = findViewById(R.id.txtUsuario);
        txtClave = findViewById(R.id.txtClave);
        dao = new UsuarioDAO(this);

    }


    public void insertar(View view) {

        try {
            Usuario us = new Usuario();
            us.setUsuario(txtUsuario.getText().toString());
            Encriptacion en = new Encriptacion();
            byte[] claveEncriptada = en.cifra(txtClave.getText().toString());
            us.setClave(claveEncriptada);
            String mensaje = dao.crear(us);
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            if (mensaje.equalsIgnoreCase("Creado correctamente")) {
                Intent intent = new Intent(RegisterUserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
