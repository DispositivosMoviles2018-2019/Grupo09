package ec.edu.uce.controlador;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.DAYS;

public class Utilitario {

    public boolean isCorreo(String correo) {
        boolean bandera;
        Pattern patron = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)@[a-z0-9-]+(.[a-z0-9-]+)\\.(.[a-z]{2,4})$");
                   Matcher encaja = patron.matcher(correo);
        if (encaja.find()) {
            bandera = true;
        } else {
            bandera = false;
        }

        return bandera;
    }

    public boolean isCelular(String celular) {
        boolean bandera;
        Pattern patron = Pattern.compile("[0-9]{10}");
        Matcher encaja = patron.matcher(celular);
        if (encaja.find()) {
            bandera = true;
        } else {
            bandera = false;
        }
        return bandera;
    }

    public boolean isPlaca(String placa) {
        boolean bandera;
        Pattern patron = Pattern.compile("[A-Z]{3}-[0-9]{4}");
        Matcher encaja = patron.matcher(placa);
        if (encaja.find()) {
            bandera=true;
        } else {
            bandera=false;
        }
        return bandera;
    }

    public boolean isNumReserva(String parametro){
        boolean bandera;
        Pattern patron = Pattern.compile("[0-9]{4}");
        Matcher matcher = patron.matcher(parametro);
        if(matcher.find()){
            bandera = true;
        }else{
            bandera = false;
        }
        return bandera;
    }


}
