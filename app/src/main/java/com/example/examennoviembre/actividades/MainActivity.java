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
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.examennoviembre.R;
import com.example.examennoviembre.fragmentos.ListadoFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNum;
    private Button buttonFiltrar;
    private TextView textViewMensaje;
    private Toolbar toolbar;

    // ---------------- MÉTODOS PARA FORZAR Y GUARDAR EL IDIOMA (opcional) ----------------
    private void setLocale(String idioma) {
        Locale nuevaLocale = new Locale(idioma);
        Locale.setDefault(nuevaLocale);

        Resources res = getResources();
        Configuration config = res.getConfiguration();
        config.setLocale(nuevaLocale);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    private void cargarLocale() {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String idioma = prefs.getString("Idioma", "en"); // inglés por defecto
        setLocale(idioma);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Forzamos el idioma guardado antes de inflar el layout
        cargarLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Configuramos el Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Establecemos el título según el idioma actual
        getSupportActionBar().setTitle(getString(R.string.app_name));

        editTextNum = findViewById(R.id.editTextNumber);
        buttonFiltrar = findViewById(R.id.buttonFiltrar);
        textViewMensaje = findViewById(R.id.textViewMensaje);

        // NO cargamos el listado por defecto. El usuario debe pulsar "Filtrar".
        buttonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = -1;
                String valor = editTextNum.getText().toString();
                if (!valor.isEmpty()) {
                    try {
                        num = Integer.parseInt(valor);
                        textViewMensaje.setText("");
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, getString(R.string.error_number), Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    textViewMensaje.setText(getString(R.string.mensaje_mostrando_todos));
                }
                // Cargar el fragmento con el número indicado
                ListadoFragment fragment = ListadoFragment.newInstance(num);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });
    }

    // Inflar el menú con "Añadir" y "Salir"
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    // Manejar la selección de opciones del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_anadir) {
            Intent intent = new Intent(MainActivity.this, EditarPaisActivity.class);
            intent.putExtra("modo", "anadir");
            // Podrías pasar otros datos si fuera necesario
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_salir) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}