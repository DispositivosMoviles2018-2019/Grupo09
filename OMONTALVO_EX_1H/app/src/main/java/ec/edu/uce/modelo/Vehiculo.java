package ec.edu.uce.modelo;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Vehiculo{
    private String placa;
    private String marca;
    private LocalDate fecFabricacion;
    private Double costo;
    private boolean matriculado;
    private String color;

    public Vehiculo() {
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public boolean isMatriculado() {
        return matriculado;
    }

    public void setMatriculado(boolean matriculado) {
        this.matriculado = matriculado;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDate getFecFabricacion() {
        return fecFabricacion;
    }

    public void setFecFabricacion(LocalDate fecFabricacion) {
        this.fecFabricacion = fecFabricacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(placa, vehiculo.placa);
    }

    @Override
    public int hashCode() {

        return Objects.hash(placa);
    }
}
