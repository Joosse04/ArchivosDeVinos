package org.izv.archivosdevinos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.izv.archivosdevinos.data.Vino;
import org.izv.archivosdevinos.util.Csv;
import org.izv.archivosdevinos.util.Fichero;

public class ThirdActivity extends AppCompatActivity {

    private EditText etId, etNombre, etBodega, etColor, etOrigen, eTGraduacion, eTFecha;
    private EditText[] editText = new EditText[7];
    private Vino vino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        initialize();
    }

    private void initialize() {
        Bundle bundle = getIntent().getExtras();
        vino = bundle.getParcelable("vino");

        etId = findViewById(R.id.etId);
        etId.setText(String.valueOf(vino.getId()));
        etId.setEnabled(false);
        etNombre = findViewById(R.id.etNombre);
        etNombre.setText(vino.getNombre());
        etBodega = findViewById(R.id.etBodega);
        etBodega.setText(vino.getBodega());
        etColor = findViewById(R.id.etColor);
        etColor.setText(vino.getColor());
        etOrigen = findViewById(R.id.etOrigen);
        etOrigen.setText(vino.getOrigen());
        eTGraduacion = findViewById(R.id.etGraduacion);
        eTGraduacion.setText(String.valueOf(vino.getGraduacion()));
        eTFecha = findViewById(R.id.etFecha);
        eTFecha.setText(String.valueOf(vino.getFecha()));

        editText[0] = etId;
        editText[1] = etNombre;
        editText[2] = etBodega;
        editText[3] = etColor;
        editText[4] = etOrigen;
        editText[5] = eTGraduacion;
        editText[6] = eTFecha;

        Button btSecEdit = findViewById(R.id.btSecEdit);
        btSecEdit.setOnClickListener(view -> {
            editarRegistro();
        });

        Button btDelete = findViewById(R.id.btDelete);
        btDelete.setOnClickListener(view -> {
            borrarLinea();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        Button btCancel = findViewById(R.id.btCancel);
        btCancel.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }

    private void editarRegistro() {
        if (editarVino()) {
            if (sobreEscribirLinea()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    private boolean editarVino() {
        boolean resultado = true;
        String[] atributos = new String[editText.length];
        for (int i = 0; i < atributos.length; i++) {
            atributos[i] = editText[i].getText().toString();
        }
        try {
            vino.setId(Long.parseLong(atributos[0].trim()));
        } catch(NumberFormatException e){
            resultado = false;
        }

        vino.setNombre(atributos[1].trim());
        vino.setBodega(atributos[2].trim());
        vino.setColor(atributos[3].trim());
        vino.setOrigen(atributos[4].trim());

        try {
            vino.setGraduacion(Double.parseDouble(atributos[5].trim()));
        } catch (NumberFormatException e) {
            resultado = false;
        }
        try {
            vino.setFecha(Integer.parseInt(atributos[6].trim()));
        } catch (NumberFormatException e) {
            resultado = false;
        }
        return resultado;
    }

    private boolean borrarLinea() {
        return Fichero.deleteLine(getExternalFilesDir(null), "archivo.csv", Long.toString(vino.getId()));
    }

    private boolean escribirLinea() {
        return Fichero.writeLine(getExternalFilesDir(null), "archivo.csv", Csv.getCsv(vino));
    }

    private boolean sobreEscribirLinea() {
        boolean borrado = borrarLinea();
        if (borrado){
            boolean escrito = escribirLinea();
            if (escrito){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}