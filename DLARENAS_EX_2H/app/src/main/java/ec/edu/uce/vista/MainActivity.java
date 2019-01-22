package ec.edu.uce.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.dao.UsuarioDAO;

public class MainActivity extends AppCompatActivity {
    private EditText txtUsuario, txtClave;
    private Button btnLogin;
    private UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtClave = findViewById(R.id.txtClave);
        dao = new UsuarioDAO(this);

    }

    public void registro(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterUserActivity.class);
        startActivity(intent);

    }

    public void ingresar(View view) {
        String usuario = txtUsuario.getText().toString();
        String clave = txtClave.getText().toString();
        if (!usuario.isEmpty() && !clave.isEmpty()) {
            boolean bandera = dao.validar(usuario, clave);
            if(bandera){
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, OpcionesMenuActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Usuario y clave mal Ingresado", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
