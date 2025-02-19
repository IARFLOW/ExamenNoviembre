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

    private Context context;
    private ArrayList<Pais> paises;
    private LayoutInflater inflater;

    public PaisAdapter(Context context, ArrayList<Pais> paises) {
        this.context = context;
        this.paises = paises;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return paises.size();
    }

    @Override
    public Object getItem(int position) {
        return paises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.layout_item_pais, parent, false);
        }
        Pais pais = paises.get(position);

        TextView textViewNombre = view.findViewById(R.id.textViewNombre);
        TextView textViewIdioma = view.findViewById(R.id.textViewIdioma);
        TextView textViewPoblacion = view.findViewById(R.id.textViewPoblacion);

        textViewNombre.setText(pais.getNombre());
        textViewIdioma.setText(pais.getIdioma());
        textViewPoblacion.setText(String.valueOf(pais.getPoblacion()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        return view;
    }
}