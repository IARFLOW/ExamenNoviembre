package com.example.examennoviembre.mock;

import com.example.examennoviembre.entidades.Pais;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ObtenerDatos {

    public ArrayList<Pais> obtenerListaPaises(int numMostrar) {
        ArrayList<Pais> paises = new ArrayList<>();


        paises.add(new Pais("España", "Español", 47350000, obtenerFecha(1492, Calendar.OCTOBER, 12)));
        paises.add(new Pais("Francia", "Francés", 67000000, obtenerFecha(843, Calendar.JUNE, 25)));
        paises.add(new Pais("Alemania", "Alemán", 83100000, obtenerFecha(1871, Calendar.JANUARY, 18)));
        paises.add(new Pais("Italia", "Italiano", 59550000, obtenerFecha(1861, Calendar.MARCH, 17)));
        paises.add(new Pais("Japón", "Japonés", 125800000, obtenerFecha(660, Calendar.FEBRUARY, 11)));
        paises.add(new Pais("México", "Español", 126200000, obtenerFecha(1810, Calendar.SEPTEMBER, 16)));
        paises.add(new Pais("Brasil", "Portugués", 212600000, obtenerFecha(1822, Calendar.SEPTEMBER, 7)));
        paises.add(new Pais("Canadá", "Inglés y Francés", 38000000, obtenerFecha(1867, Calendar.JULY, 1)));
        paises.add(new Pais("China", "Chino Mandarín", 1402000000, obtenerFecha(-221, Calendar.SEPTEMBER, 1)));
        paises.add(new Pais("Australia", "Inglés", 25690000, obtenerFecha(1901, Calendar.JANUARY, 1)));

        if(numMostrar>-1) {
            numMostrar = Math.min(numMostrar, paises.size());
        }
        else{
            numMostrar= paises.size();
        }
        return new ArrayList<>(paises.subList(0, numMostrar));
    }

    // Método auxiliar para crear fechas
    private Date obtenerFecha(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
