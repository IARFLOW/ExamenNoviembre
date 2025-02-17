package com.example.examennoviembre.actividades;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private EditText editTextNum;
    private Button buttonFiltrar;
    private TextView textViewMensaje;
    // Agregamos el Toolbar
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Configuramos el Toolbar (se debe haber incluido en el XML)
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Establecemos el título
        getSupportActionBar().setTitle(getString(R.string.app_name));

        editTextNum = findViewById(R.id.editTextNumber);
        buttonFiltrar = findViewById(R.id.buttonFiltrar);
        textViewMensaje = findViewById(R.id.textViewMensaje);

        // No cargamos el fragmento automáticamente
        // El usuario debe pulsar "Filtrar" para cargar la lista

        // Acción del botón Filtrar
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

    // Inflar el menú (Añadir y Salir)
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
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_salir) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
