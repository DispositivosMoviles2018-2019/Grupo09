package ec.edu.uce.vista;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Random;

import ec.edu.uce.controlador.Utilitario;
import ec.edu.uce.dao.ReservaDAO;
import ec.edu.uce.dao.VehiculoDAO;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.Vehiculo;

import static java.time.temporal.ChronoUnit.DAYS;

public class ReservaActivity extends AppCompatActivity {

    private EditText txtPlaca, txtCelular, txtMail;
    private DatePicker pickerPrestamo, pickerEntrega;
    private ReservaDAO daoReserva;
    private VehiculoDAO daoVehiculo;
    private LocalDate fechaPrestamo, fechaEntrega;
    private Utilitario util;
    private Reserva reserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        txtPlaca = findViewById(R.id.txtResPlaca);
        txtCelular = findViewById(R.id.txtResCelular);
        txtMail = findViewById(R.id.txtResMail);
        pickerPrestamo = findViewById(R.id.pickerResFechaPrestamo);
        pickerEntrega = findViewById(R.id.pickerResFechaEntrega);
        daoReserva = new ReservaDAO(this);
        daoVehiculo = new VehiculoDAO(this);
        util = new Utilitario();
        reserva = new Reserva();

    }

    public void reservar(View view) {
        reserva = new Reserva();
        Vehiculo vehiculo;
        vehiculo = daoVehiculo.buscarPorParametro(daoVehiculo.listar(), txtPlaca.getText().toString());
        if (vehiculo.getPlaca() != null && vehiculo.getEstado() == true) {
            if(util.isPlaca(txtPlaca.getText().toString())){
                reserva.setPlaca(txtPlaca.getText().toString());
                if(util.isCelular(txtCelular.getText().toString())){
                    reserva.setCelular(txtCelular.getText().toString());
                    String correodeb =txtMail.getText().toString();
                    System.out.println("******" + correodeb);
                    if(util.isCorreo(correodeb)){
                        reserva.setEmail(txtMail.getText().toString());
                        fechaPrestamo = LocalDate.of(pickerPrestamo.getYear(), pickerPrestamo.getMonth() + 1, pickerPrestamo.getDayOfMonth());
                        reserva.setFechaPrestamo(fechaPrestamo);
                        fechaEntrega = LocalDate.of(pickerEntrega.getYear(), pickerEntrega.getMonth() + 1, pickerEntrega.getDayOfMonth());
                        reserva.setFechaEntrega(fechaEntrega);
                        reserva.setValorReserva(calcularReserva(fechaPrestamo, fechaEntrega, vehiculo.getTipo()));
                        reserva.setNumero(generarRandom());
                        try{
                            daoReserva.crear(reserva);
                            vehiculo.setEstado(false);
                            daoVehiculo.actualizar(vehiculo);
                            Toast.makeText(this, "Se ha realizado una Reserva", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ReservaActivity.this, OpcionesReservaActivity.class);
                            intent.putExtra("placa",vehiculo.getPlaca());
                            startActivity(intent);
                        }catch(SQLiteConstraintException e){
                            Toast.makeText(this, "Error de num Reserva", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this, "correo no valido", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "celular incorrecto", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, "Ingrese una placa correcta", Toast.LENGTH_SHORT).show();
            }



        } else {
            Toast.makeText(this, "No existe vehículo con esa placa, o ya esta reservado", Toast.LENGTH_SHORT).show();

        }

    }

    public double calcularReserva(LocalDate fechaPrestamo, LocalDate fechaEntrega, String tipoVehiculo) {
        Long dias = DAYS.between(fechaPrestamo, fechaEntrega);
        int diasInt = dias.intValue();
        double saldo = 0;
        //String[] opcionesTipo = {"automovil", "camioneta", "furgoneta"};
      if(diasInt > 0){
          if (tipoVehiculo.equalsIgnoreCase("automovil")) {
              saldo = diasInt * 60;
          } else if (tipoVehiculo.equalsIgnoreCase("camioneta")) {
              saldo = diasInt * 75;
          } else {
              saldo = diasInt * 100;
          }

      }else if(diasInt == 0){
          saldo = diasInt+20;
      }else{
          Toast.makeText(this, "Fecha Entrega menor a Reserva", Toast.LENGTH_SHORT).show();
      }

        return saldo;
    }

    public String generarRandom() {
        StringBuilder builder = new StringBuilder();
        builder.append((int)(Math.random()*9)).append((int)(Math.random()*9))
                .append((int)(Math.random()*9)).append((int)(Math.random()*9));
        return builder.toString();
    }

    private void enviar(String[] to, String[] cc,
                        String asunto, String mensaje) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        //String[] to = direccionesEmail;
        //String[] cc = copias;
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email "));
    }
    public void onClick(View v) {
        // Reemplazamos el email por algun otro real
        ReservaDAO daoRes = new ReservaDAO(this);
        
        String[] to = { reserva.getEmail() };
        String[] cc = { "ralascano@uce.edu.ec" };
        enviar(to, cc, "Hola",
                "Esto es un email enviado desde una app de Android");
    }
}