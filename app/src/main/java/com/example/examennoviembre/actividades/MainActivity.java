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
import com.example.examennoviembre.R;
import com.example.examennoviembre.fragmentos.ListadoFragment;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNum;
    private Button buttonFiltrar;
    private TextView textViewMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        editTextNum = findViewById(R.id.editTextNumber);
        buttonFiltrar = findViewById(R.id.buttonFiltrar);
        textViewMensaje = findViewById(R.id.textViewMensaje);

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
                // Reemplazar el fragmento según el número de elementos
                ListadoFragment fragment = ListadoFragment.newInstance(num);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });

        // Cargar el fragmento por defecto (mostrando todos)
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ListadoFragment.newInstance(-1))
                .commit();
    }

    // Inflar el menú: Añadir y Salir
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