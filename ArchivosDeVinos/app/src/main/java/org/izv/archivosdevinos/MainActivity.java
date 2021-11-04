package org.izv.archivosdevinos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.izv.archivosdevinos.data.Vino;
import org.izv.archivosdevinos.util.Csv;
import org.izv.archivosdevinos.util.EditTextVino;
import org.izv.archivosdevinos.util.Fichero;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName() + "xyzyx";

    private static EditText etMainId;
    private static ArrayList<Vino> listaVinos;
    private Context contexto;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        contexto = this;
        etMainId = findViewById(R.id.etMainId);
        linearLayout = findViewById(R.id.linearLayout);
        listaVinos = new ArrayList<>();
        escribirVinos();

        Button btAdd;
        btAdd = findViewById(R.id.btAdd);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSecondaryActivity();
            }
        });

        Button btEdit;
        btEdit = findViewById(R.id.btEdit);

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarVinoPorId();
            }
        });
    }

    private void openSecondaryActivity() {
        Intent intencion = new Intent(this, SecondaryActivity.class);
        startActivity(intencion);
    }

    private void escribirVinos(){
        String[] vinos = Fichero.getFileLines(getExternalFilesDir(null), "archivo.csv");
        if (vinos != null){
            for (String linea : vinos) {
                Vino vino = Csv.getVino(linea);
                listaVinos.add(vino);
                EditTextVino vt = new EditTextVino(this, vino);
                linearLayout.addView(vt);
            }
        }
    }

    public static EditText getEtMainId(){
        return etMainId;
    }

    public static ArrayList<Vino> getListaVinos() {
        return listaVinos;
    }

    private void seleccionarVinoPorId(){
        for (int i = 0; i < listaVinos.size(); i++) {
            if (!etMainId.getText().toString().isEmpty()) {
                if (listaVinos.get(i).getId() == Long.parseLong(etMainId.getText().toString())) {
                    EditTextVino vt = new EditTextVino(this, listaVinos.get(i));
                    Intent intencion = vt.createIntent(contexto, ThirdActivity.class);
                    contexto.startActivity(intencion);
                }
            }
        }
    }

}