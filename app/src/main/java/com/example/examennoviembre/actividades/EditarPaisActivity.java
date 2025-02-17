package com.example.examennoviembre.actividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.examennoviembre.R;

public class EditarPaisActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextIdioma, editTextPoblacion, editTextFecha;
    private ImageButton buttonBandera;
    private Button buttonGuardar, buttonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pais);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextIdioma = findViewById(R.id.editTextIdioma);
        editTextPoblacion = findViewById(R.id.editTextPoblacion);
        editTextFecha = findViewById(R.id.editTextFecha);
        buttonBandera = findViewById(R.id.buttonBandera);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        buttonCancelar = findViewById(R.id.buttonCancelar);

        // Deshabilitar el campo de idioma
        editTextIdioma.setEnabled(false);

        // Comprobar el modo (editar o añadir)
        String modo = getIntent().getStringExtra("modo");
        if ("editar".equals(modo)) {
            editTextNombre.setText(getIntent().getStringExtra("nombre"));
            editTextIdioma.setText(getIntent().getStringExtra("idioma"));
            editTextPoblacion.setText(String.valueOf(getIntent().getIntExtra("poblacion", 0)));
            editTextFecha.setText(getIntent().getStringExtra("fecha"));
        }

        // Configurar el botón de la bandera para abrir un diálogo de selección de idioma
        buttonBandera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] opciones = { getString(R.string.opcion_espanol), getString(R.string.opcion_ingles) };
                final int[] seleccion = {0};
                AlertDialog.Builder builder = new AlertDialog.Builder(EditarPaisActivity.this);
                builder.setTitle(getString(R.string.seleccionar_idioma))
                        .setSingleChoiceItems(opciones, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                seleccion[0] = which;
                            }
                        })
                        .setPositiveButton(getString(R.string.asignar), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (seleccion[0] == 0) {
                                    editTextIdioma.setText(getString(R.string.opcion_espanol));
                                    buttonBandera.setImageResource(R.drawable.bandera_es);
                                } else {
                                    editTextIdioma.setText(getString(R.string.opcion_ingles));
                                    buttonBandera.setImageResource(R.drawable.bandera_uk);
                                }
                            }
                        })
                        .setNegativeButton(getString(R.string.cancelar), null)
                        .show();
            }
        });

        // Botón Guardar: mostrar Toast y finalizar la actividad
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditarPaisActivity.this, getString(R.string.datos_guardados), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Botón Cancelar: mostrar Toast y finalizar la actividad
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditarPaisActivity.this, getString(R.string.operacion_cancelada), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}