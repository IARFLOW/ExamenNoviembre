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

    // Constructor público vacío requerido para los Fragmentos.
    public ListadoFragment() {
    }

    /**
     * Método de fábrica para crear una nueva instancia del fragmento con argumentos.
     * @param numMostrar Número de elementos a mostrar (-1 para todos)
     * @return Una nueva instancia de ListadoFragment con el argumento indicado.
     */
    public static ListadoFragment newInstance(int numMostrar) {
        ListadoFragment fragment = new ListadoFragment();
        // Se crea un Bundle para pasar parámetros al fragmento.
        Bundle args = new Bundle();
        args.putInt("numMostrar", numMostrar); // Se almacena el número de elementos a mostrar.
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Inflamos el layout del fragmento.
     * @param inflater LayoutInflater para inflar la vista.
     * @param container Contenedor donde se insertará el fragmento.
     * @param savedInstanceState Estado guardado del fragmento.
     * @return La vista inflada.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Infla el layout definido en fragment_listado.xml y lo retorna.
        return inflater.inflate(R.layout.fragment_listado, container, false);
    }

    /**
     * Se llama después de que la vista ha sido creada.
     * Aquí se inicializan los componentes y se configura la lógica del fragmento.
     * @param view La vista inflada del fragmento.
     * @param savedInstanceState Estado guardado del fragmento.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Se obtiene la referencia al ListView definido en fragment_listado.xml.
        ListView listView = view.findViewById(R.id.listViewPaises);

        // Se recupera el argumento que indica cuántos elementos mostrar.
        int numMostrar = -1;  // Valor por defecto: -1 indica que se mostrarán todos.
        if (getArguments() != null) {
            numMostrar = getArguments().getInt("numMostrar", -1);
        }

        // Se obtiene la lista de países usando la clase ObtenerDatos y el parámetro numMostrar.
        ArrayList<Pais> paises = new ObtenerDatos().obtenerListaPaises(numMostrar);

        // Se crea el adaptador pasando el contexto (getActivity()) y la lista de países.
        PaisAdapter adapter = new PaisAdapter(getActivity(), paises);

        // Se asigna el adaptador al ListView para mostrar los datos.
        listView.setAdapter(adapter);

        // Configuración del evento al pulsar un elemento de la lista.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
                // Se obtiene el objeto Pais correspondiente a la posición seleccionada.
                Pais pais = paises.get(position);

                // Se crea un Intent para iniciar la actividad EditarPaisActivity en modo "editar".
                Intent intent = new Intent(getActivity(), EditarPaisActivity.class);
                intent.putExtra("modo", "editar");
                // Se pasan los datos del país seleccionado para que se muestren en la pantalla de edición.
                intent.putExtra("nombre", pais.getNombre());
                intent.putExtra("idioma", pais.getIdioma());
                intent.putExtra("poblacion", pais.getPoblacion());

                // Se formatea la fecha de fundación del país (formato "dd/MM/yyyy") y se añade al Intent.
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                intent.putExtra("fecha", sdf.format(pais.getFechaFundacion()));

                // Se inicia la actividad EditarPaisActivity.
                startActivity(intent);
            }
        });
    }
}
