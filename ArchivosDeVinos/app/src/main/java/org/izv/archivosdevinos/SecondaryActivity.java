package org.izv.archivosdevinos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.izv.archivosdevinos.data.Vino;
import org.izv.archivosdevinos.util.Csv;
import org.izv.archivosdevinos.util.Fichero;

public class SecondaryActivity extends AppCompatActivity {

    private EditText etId, etNombre, etBodega, etColor, etOrigen, etGraduacion, etFecha;
    private TextView tvText;
    private EditText[] editText = new EditText[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        initialize();
    }

    private void initialize() {
        etId = findViewById(R.id.etId);
        etNombre = findViewById(R.id.etNombre);
        etBodega = findViewById(R.id.etBodega);
        etColor = findViewById(R.id.etColor);
        etOrigen = findViewById(R.id.etOrigen);
        etGraduacion = findViewById(R.id.etGraduacion);
        etFecha = findViewById(R.id.etFecha);
        tvText = findViewById(R.id.tvScroll);

        editText[0] = etId;
        editText[1] = etNombre;
        editText[2] = etBodega;
        editText[3] = etColor;
        editText[4] = etOrigen;
        editText[5] = etGraduacion;
        editText[6] = etFecha;

        Button btSecAdd = findViewById(R.id.btSecAdd);

        btSecAdd.setOnClickListener((View v) -> {
            crearVino();
        });

        Button btCancel = findViewById(R.id.btCancel);

        btCancel.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    private Vino obtenerVino(){
        Vino v = new Vino();
        try {
            v.setId(Long.parseLong(editText[0].getText().toString().trim()));
        } catch(NumberFormatException e){
            e.getMessage();
        }

        v.setNombre(editText[1].getText().toString().trim());
        v.setBodega(editText[2].getText().toString().trim());
        v.setColor(editText[3].getText().toString().trim());
        v.setOrigen(editText[4].getText().toString().trim());

        try {
            v.setGraduacion(Double.parseDouble(editText[5].getText().toString()));
        } catch (NumberFormatException e) {
            e.getMessage();
        }
        try {
            v.setFecha(Integer.parseInt(editText[6].getText().toString()));
        } catch (NumberFormatException e) {
            e.getMessage();
        }
        return v;
    }

    private boolean editTextsRellenos(){
        boolean relleno = true;
        for (EditText campo: editText) {
            if(campo.getText().toString().isEmpty()){
                relleno = false;
            }
        }
        return relleno;
    }

    private boolean existeVino(Vino vino){
        boolean existe = false;
        for (Vino v: MainActivity.getListaVinos()) {
            if(v.getId() == vino.getId()){
                existe = true;
            }
        }
        return existe;
    }

    private void crearVino(){
        if(editTextsRellenos()) {
            Vino vino = obtenerVino();
            if (!existeVino(vino)) {
                String lineaCSV = Csv.getCsv(vino);
                Fichero.writeLine(getExternalFilesDir(null), "archivo.csv", lineaCSV);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}