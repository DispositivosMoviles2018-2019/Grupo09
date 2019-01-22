package ec.edu.uce.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Vehiculo implements Serializable {
    private String placa;
    private String marca;
    private LocalDate fecFabricacion;
    private Double costo;
    private Boolean matriculado;
    private String color;
    private byte[] foto;
    private Boolean estado;
    private String tipo;

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

    public LocalDate getFecFabricacion() {
        return fecFabricacion;
    }

    public void setFecFabricacion(LocalDate fecFabricacion) {
        this.fecFabricacion = fecFabricacion;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Boolean getMatriculado() {
        return matriculado;
    }

    public void setMatriculado(Boolean matriculado) {
        this.matriculado = matriculado;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
