package com.uce.oscar.tarea_03_g09;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText user;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.txtNombre);
        password = (EditText) findViewById(R.id.txtApellido);
        login = (Button) findViewById(R.id.btnIngresar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(user.getText().toString(), password.getText().toString());
            }
        });
    }

    private void validate(String userName, String userPassword) {
        if (((userName.equals("Oscar")) && (userPassword.equals("Montalvo"))) || ((userName.equals("Daniel")) && (userPassword.equals("Larenas"))) || ((userName.equals("Ricardo")) && (userPassword.equals("Lascano")))) {
            Intent intent = new Intent(MainActivity.this, Activity2.class);
            startActivity(intent);
        }
    }
}
