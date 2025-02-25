package com.example.examennoviembre.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.examennoviembre.R;
import com.example.examennoviembre.entidades.Pais;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PaisAdapter extends BaseAdapter {

    // Contexto de la aplicación o actividad
    private Context context;
    // Lista de países que se va a mostrar en el ListView
    private ArrayList<Pais> paises;
    // Inflater para convertir el layout XML en objetos View
    private LayoutInflater inflater;

    /**
     * Constructor del adaptador.
     * @param context Contexto en el que se usa el adaptador.
     * @param paises Lista de objetos Pais a mostrar.
     */
    public PaisAdapter(Context context, ArrayList<Pais> paises) {
        this.context = context;
        this.paises = paises;
        // Se obtiene el inflater a partir del contexto
        inflater = LayoutInflater.from(context);
    }

    /**
     * Devuelve la cantidad de elementos de la lista.
     */
    @Override
    public int getCount() {
        return paises.size();
    }

    /**
     * Devuelve el objeto Pais en la posición indicada.
     * @param position Índice del elemento.
     */
    @Override
    public Object getItem(int position) {
        return paises.get(position);
    }

    /**
     * Devuelve el ID del elemento, en este caso usamos la posición.
     * @param position Índice del elemento.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Es el método que se encarga de crear o reciclar la vista para cada elemento del ListView.
     * @param position Posición del elemento en la lista.
     * @param convertView Vista reciclada (puede ser nula).
     * @param parent Contenedor de la vista.
     * @return La vista que se mostrará para el elemento en la posición indicada.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        // Si no hay una vista reciclada, se infla el layout del ítem
        if (view == null) {
            view = inflater.inflate(R.layout.layout_item_pais, parent, false);
        }
        // Se obtiene el objeto Pais correspondiente a la posición actual
        Pais pais = paises.get(position);

        // Se referencian los controles del layout del ítem
        TextView textViewNombre = view.findViewById(R.id.textViewNombre);
        TextView textViewIdioma = view.findViewById(R.id.textViewIdioma);
        TextView textViewPoblacion = view.findViewById(R.id.textViewPoblacion);

        // Se asignan los valores de cada país a los TextView correspondientes
        textViewNombre.setText(pais.getNombre());
        textViewIdioma.setText(pais.getIdioma());
        // Convertimos la población a String para asignarla al TextView
        textViewPoblacion.setText(String.valueOf(pais.getPoblacion()));

        // Se crea un SimpleDateFormat para formatear la fecha (aunque en este ejemplo no se usa en la vista)
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        // Si quisieras mostrar la fecha en el layout, podrías obtener el TextView y asignarle:
        // textViewFecha.setText(sdf.format(pais.getFechaFundacion()));

        // Devuelve la vista ya configurada para ser mostrada en el ListView
        return view;
    }
}
