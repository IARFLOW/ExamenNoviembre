package com.example.examennoviembre.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.examennoviembre.R;
import com.example.examennoviembre.actividades.EditarPaisActivity;
import com.example.examennoviembre.adaptadores.PaisAdapter;
import com.example.examennoviembre.entidades.Pais;
import com.example.examennoviembre.mock.ObtenerDatos;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ListadoFragment extends Fragment {

    public ListadoFragment() {
        // Constructor público vacío requerido
    }

    public static ListadoFragment newInstance(int numMostrar) {
        ListadoFragment fragment = new ListadoFragment();
        Bundle args = new Bundle();
        args.putInt("numMostrar", numMostrar);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_listado, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView listView = view.findViewById(R.id.listViewPaises);
        int numMostrar = -1;
        if (getArguments() != null) {
            numMostrar = getArguments().getInt("numMostrar", -1);
        }
        ArrayList<Pais> paises = new ObtenerDatos().obtenerListaPaises(numMostrar);
        PaisAdapter adapter = new PaisAdapter(getActivity(), paises);
        listView.setAdapter(adapter);

        // Al pulsar un elemento, iniciar EditarPaisActivity en modo edición
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
                Pais pais = paises.get(position);
                Intent intent = new Intent(getActivity(), EditarPaisActivity.class);
                intent.putExtra("modo", "editar");
                intent.putExtra("nombre", pais.getNombre());
                intent.putExtra("idioma", pais.getIdioma());
                intent.putExtra("poblacion", pais.getPoblacion());
                // Formatear la fecha como dd/MM/yyyy
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                intent.putExtra("fecha", sdf.format(pais.getFechaFundacion()));
                startActivity(intent);
            }
        });
    }
}