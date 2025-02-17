package com.example.examennoviembre.entidades;

import java.util.Date;

public class Pais {
    private String nombre;
    private String idioma;
    private int poblacion;
    private Date fechaFundacion;

    public Pais(String nombre, String idioma, int poblacion, Date fechaFundacion) {
        this.nombre = nombre;
        this.idioma = idioma;
        this.poblacion = poblacion;
        this.fechaFundacion = fechaFundacion;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(int poblacion) {
        this.poblacion = poblacion;
    }

    public Date getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(Date fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }
}
