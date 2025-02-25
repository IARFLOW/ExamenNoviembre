package com.example.examennoviembre.actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.examennoviembre.R;
import com.example.examennoviembre.fragmentos.ListadoFragment;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Declaración de controles de la interfaz
    private EditText editTextNum;
    private Button buttonFiltrar;
    private Toolbar toolbar;

    // ----------------------------------------------------------------
    // MÉTODOS PARA CONFIGURAR Y CARGAR EL IDIOMA (LOCALIZACIÓN)
    // ----------------------------------------------------------------

    /**
     * Configura el idioma de la aplicación.
     * @param idioma Código del idioma (por ejemplo, "en" para inglés, "es" para español).
     */
    private void setLocale(String idioma) {
        Locale nuevaLocale = new Locale(idioma);
        Locale.setDefault(nuevaLocale);

        Resources res = getResources();
        Configuration config = res.getConfiguration();
        config.setLocale(nuevaLocale);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    /**
     * Lee el idioma guardado en SharedPreferences y lo aplica.
     * Si no hay un idioma guardado, se usa inglés por defecto.
     */
    private void cargarLocale() {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String idioma = prefs.getString("Idioma", "en"); // inglés por defecto
        setLocale(idioma);
    }

    // ----------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Antes de inflar el layout, se carga el idioma guardado para que la app muestre los textos en el idioma correcto
        cargarLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Configuración del Toolbar: se obtiene la referencia y se asigna como ActionBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Se establece el título de la aplicación según el idioma actual (usando el recurso de cadena)
        getSupportActionBar().setTitle(getString(R.string.app_name));

        // Enlace de los controles definidos en el layout
        editTextNum = findViewById(R.id.editTextNumber);
        buttonFiltrar = findViewById(R.id.buttonFiltrar);
        // En este caso, no se utiliza textViewMensaje, se maneja el mensaje a través de Toast

        // No se carga el listado de países por defecto, el usuario debe pulsar "Filtrar"
        buttonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = -1; // Valor por defecto (-1 indica que se mostrarán todos)
                String valor = editTextNum.getText().toString();

                if (!valor.isEmpty()) {
                    // Si el usuario ingresó un valor, se intenta parsearlo a entero
                    try {
                        num = Integer.parseInt(valor);
                    } catch (NumberFormatException e) {
                        // Si el parseo falla, se muestra un Toast con el mensaje de error y se sale del onClick
                        Toast.makeText(MainActivity.this, getString(R.string.error_number), Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    // Si no se ingresó ningún valor, se muestra un Toast informativo
                    Toast.makeText(MainActivity.this,
                            "No se ingresó el número de resultados. Mostrando todos",
                            Toast.LENGTH_LONG).show();
                }

                // Se crea el fragmento ListadoFragment pasándole el número de resultados a mostrar (-1 para todos)
                ListadoFragment fragment = ListadoFragment.newInstance(num);
                // Se reemplaza el contenedor de fragmentos con el fragmento creado
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });
    }

    // ----------------------------------------------------------------
    // MÉTODOS DEL MENÚ
    // ----------------------------------------------------------------

    /**
     * Infla el menú principal (con las opciones "Añadir" y "Salir") definido en menu_principal.xml.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    /**
     * Maneja la selección de elementos del menú.
     * - "Añadir": Lanza EditarPaisActivity en modo "anadir".
     * - "Salir": Finaliza la actividad.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_anadir) {
            // Inicia la actividad para añadir un país
            Intent intent = new Intent(MainActivity.this, com.example.examennoviembre.actividades.EditarPaisActivity.class);
            intent.putExtra("modo", "anadir");
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_salir) {
            // Finaliza la actividad (y por tanto la app, si es la única actividad)
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
