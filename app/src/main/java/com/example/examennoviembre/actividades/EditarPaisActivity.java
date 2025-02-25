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

    // Declaración de los controles de la interfaz
    private EditText editTextNombre, editTextIdioma, editTextPoblacion, editTextFecha;
    private ImageButton buttonBandera;
    private Button buttonGuardar, buttonCancelar;

    // ----------------------------------------------------------------
    // MÉTODOS PARA FORZAR Y GUARDAR EL IDIOMA
    // ----------------------------------------------------------------

    /**
     * Configura el idioma de la aplicación.
     * @param idioma Código del idioma (por ejemplo, "es" o "en").
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
     * Cambia el idioma de la app, lo guarda en SharedPreferences y reinicia la actividad.
     * @param idioma Código del idioma seleccionado.
     */
    private void cambiarLocale(String idioma) {
        setLocale(idioma); // Aplica el nuevo idioma
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Idioma", idioma); // Guarda el idioma seleccionado
        editor.apply();
        recreate(); // Reinicia la actividad para que se refresquen los textos
    }

    /**
     * Carga el idioma guardado en SharedPreferences para usarlo al iniciar la actividad.
     */
    private void cargarLocale() {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String idioma = prefs.getString("Idioma", "en"); // Usa inglés por defecto si no se ha guardado nada
        setLocale(idioma);
    }
    // ----------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Primero, carga el idioma guardado antes de inflar el layout
        cargarLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pais);

        // Enlaza los elementos del layout con las variables de la actividad
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextIdioma = findViewById(R.id.editTextIdioma);
        editTextPoblacion = findViewById(R.id.editTextPoblacion);
        editTextFecha = findViewById(R.id.editTextFecha);
        buttonBandera = findViewById(R.id.buttonBandera);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        buttonCancelar = findViewById(R.id.buttonCancelar);

        // Se deshabilita el campo de idioma para que no pueda editarse directamente
        editTextIdioma.setEnabled(false);

        // Se comprueba el modo de la actividad: "editar" o "anadir"
        String modo = getIntent().getStringExtra("modo");
        if ("editar".equals(modo)) {
            // En modo edición, se recuperan los datos enviados mediante el Intent
            String idiomaPais = getIntent().getStringExtra("idioma");
            editTextNombre.setText(getIntent().getStringExtra("nombre"));
            editTextIdioma.setText(idiomaPais);
            editTextPoblacion.setText(String.valueOf(getIntent().getIntExtra("poblacion", 0)));
            editTextFecha.setText(getIntent().getStringExtra("fecha"));

            // Se ajusta la imagen de la bandera según el idioma recibido
            if (getString(R.string.opcion_espanol).equals(idiomaPais)) {
                buttonBandera.setImageResource(R.drawable.bandera_es);
            } else {
                buttonBandera.setImageResource(R.drawable.bandera_uk);
            }
        } else {
            // En modo "añadir", se asigna un valor por defecto (Ejemplo: Español)
            editTextIdioma.setText(getString(R.string.opcion_espanol));
            buttonBandera.setImageResource(R.drawable.bandera_es);
        }

        // Configuración del botón de la bandera: abre un diálogo para seleccionar el idioma
        buttonBandera.setOnClickListener(view -> {
            // Define las opciones disponibles en el diálogo (Español e Inglés)
            final String[] opciones = {
                    getString(R.string.opcion_espanol),
                    getString(R.string.opcion_ingles)
            };
            // Se obtiene el idioma actual para marcar la opción seleccionada por defecto
            String currentIdioma = editTextIdioma.getText().toString();
            int defaultSelection = getString(R.string.opcion_espanol).equals(currentIdioma) ? 0 : 1;
            final int[] seleccion = { defaultSelection };

            // Construye y muestra el AlertDialog
            new AlertDialog.Builder(EditarPaisActivity.this)
                    .setTitle(getString(R.string.seleccionar_idioma))
                    .setSingleChoiceItems(opciones, defaultSelection, (dialogInterface, which) -> {
                        seleccion[0] = which; // Guarda la opción seleccionada
                    })
                    .setPositiveButton(getString(R.string.asignar), (dialog, i) -> {
                        // Al pulsar "Asignar", se actualiza el campo y la bandera según la selección
                        if (seleccion[0] == 0) {
                            // Selección: Español
                            editTextIdioma.setText(getString(R.string.opcion_espanol));
                            buttonBandera.setImageResource(R.drawable.bandera_es);
                            cambiarLocale("es");

                            // Actualiza el Intent para que se refleje el cambio en futuras llamadas
                            getIntent().putExtra("idioma", getString(R.string.opcion_espanol));
                        } else {
                            // Selección: Inglés
                            editTextIdioma.setText(getString(R.string.opcion_ingles));
                            buttonBandera.setImageResource(R.drawable.bandera_uk);
                            cambiarLocale("en");

                            // Actualiza el Intent
                            getIntent().putExtra("idioma", getString(R.string.opcion_ingles));
                        }
                    })
                    .setNegativeButton(getString(R.string.cancelar), null)
                    .show();
        });

        // Configuración del botón "Guardar"
        buttonGuardar.setOnClickListener(view -> {
            // Muestra un mensaje Toast informando que los datos se han guardado
            Toast.makeText(EditarPaisActivity.this, getString(R.string.datos_guardados), Toast.LENGTH_SHORT).show();
            // Aquí podrías devolver datos a la actividad anterior o guardar en una base de datos
            finish(); // Finaliza la actividad
        });

        // Configuración del botón "Cancelar"
        buttonCancelar.setOnClickListener(view -> {
            // Muestra un Toast informando que se canceló la operación
            Toast.makeText(EditarPaisActivity.this, getString(R.string.operacion_cancelada), Toast.LENGTH_SHORT).show();
            finish(); // Finaliza la actividad sin realizar cambios
        });
    }
}
