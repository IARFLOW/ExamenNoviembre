package com.example.examennoviembre.actividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.examennoviembre.R;
import java.util.Locale;

public class EditarPaisActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextIdioma, editTextPoblacion, editTextFecha;
    private ImageButton buttonBandera;
    private Button buttonGuardar, buttonCancelar;

    // -------------- MÉTODOS PARA FORZAR Y GUARDAR EL IDIOMA ----------------
    private void setLocale(String idioma) {
        Locale nuevaLocale = new Locale(idioma);
        Locale.setDefault(nuevaLocale);
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        config.setLocale(nuevaLocale);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    private void cambiarLocale(String idioma) {
        setLocale(idioma);
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Idioma", idioma);
        editor.apply();
        recreate(); // reinflar la actividad con los nuevos strings
    }

    private void cargarLocale() {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String idioma = prefs.getString("Idioma", "en");
        setLocale(idioma);
    }
    // -----------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Primero cargamos el idioma antes de inflar el layout
        cargarLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pais);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextIdioma = findViewById(R.id.editTextIdioma);
        editTextPoblacion = findViewById(R.id.editTextPoblacion);
        editTextFecha = findViewById(R.id.editTextFecha);
        buttonBandera = findViewById(R.id.buttonBandera);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        buttonCancelar = findViewById(R.id.buttonCancelar);

        // El campo de idioma no se edita directamente
        editTextIdioma.setEnabled(false);

        // Comprobar el modo (editar o añadir)
        String modo = getIntent().getStringExtra("modo");
        if ("editar".equals(modo)) {
            // Recuperar datos pasados por intent
            String idiomaPais = getIntent().getStringExtra("idioma");
            editTextNombre.setText(getIntent().getStringExtra("nombre"));
            editTextIdioma.setText(idiomaPais);
            editTextPoblacion.setText(String.valueOf(getIntent().getIntExtra("poblacion", 0)));
            editTextFecha.setText(getIntent().getStringExtra("fecha"));

            // Ajustar bandera según el idioma que se recibió
            if (getString(R.string.opcion_espanol).equals(idiomaPais)) {
                buttonBandera.setImageResource(R.drawable.bandera_es);
            } else {
                buttonBandera.setImageResource(R.drawable.bandera_uk);
            }
        } else {
            // Modo "añadir": por defecto ponemos Español (ejemplo)
            editTextIdioma.setText(getString(R.string.opcion_espanol));
            buttonBandera.setImageResource(R.drawable.bandera_es);
        }

        // Botón de la bandera: diálogo para elegir idioma
        buttonBandera.setOnClickListener(view -> {
            final String[] opciones = {
                    getString(R.string.opcion_espanol),
                    getString(R.string.opcion_ingles)
            };
            String currentIdioma = editTextIdioma.getText().toString();
            int defaultSelection = getString(R.string.opcion_espanol).equals(currentIdioma) ? 0 : 1;
            final int[] seleccion = { defaultSelection };

            new AlertDialog.Builder(EditarPaisActivity.this)
                    .setTitle(getString(R.string.seleccionar_idioma))
                    .setSingleChoiceItems(opciones, defaultSelection, (dialogInterface, which) -> {
                        seleccion[0] = which;
                    })
                    .setPositiveButton(getString(R.string.asignar), (dialog, i) -> {
                        if (seleccion[0] == 0) {
                            // Español
                            editTextIdioma.setText(getString(R.string.opcion_espanol));
                            buttonBandera.setImageResource(R.drawable.bandera_es);
                            cambiarLocale("es");

                            // Importante: Actualizamos también el intent para que
                            // la próxima vez que onCreate lea "Español"
                            getIntent().putExtra("idioma", getString(R.string.opcion_espanol));

                        } else {
                            // Inglés
                            editTextIdioma.setText(getString(R.string.opcion_ingles));
                            buttonBandera.setImageResource(R.drawable.bandera_uk);
                            cambiarLocale("en");

                            // Actualizamos el intent
                            getIntent().putExtra("idioma", getString(R.string.opcion_ingles));
                        }
                    })
                    .setNegativeButton(getString(R.string.cancelar), null)
                    .show();
        });

        // Botón Guardar
        buttonGuardar.setOnClickListener(view -> {
            Toast.makeText(EditarPaisActivity.this, getString(R.string.datos_guardados), Toast.LENGTH_SHORT).show();
            // Aquí podrías devolver datos al MainActivity o guardar en base de datos
            finish();
        });

        // Botón Cancelar
        buttonCancelar.setOnClickListener(view -> {
            Toast.makeText(EditarPaisActivity.this, getString(R.string.operacion_cancelada), Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}