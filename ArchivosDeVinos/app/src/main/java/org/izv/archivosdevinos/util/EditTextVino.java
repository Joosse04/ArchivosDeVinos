package org.izv.archivosdevinos.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import org.izv.archivosdevinos.MainActivity;
import org.izv.archivosdevinos.ThirdActivity;
import org.izv.archivosdevinos.data.Vino;

public class EditTextVino extends androidx.appcompat.widget.AppCompatTextView implements View.OnClickListener {
    Vino vino;

    /*
     * Constructor de la clase, en el que se inserta un texto y se pone una escucha.
     */
    public EditTextVino(@NonNull Context context, Vino vino) {
        super(context);
        this.vino = vino;
        this.setText(vino.getId() + ", "+ vino.getNombre() + ", " + vino.getBodega() + ", " + vino.getColor() + ", " + vino.getFecha());
        this.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ponerIdEditTextIdEditarMain(vino.getId());
        EditarVino();
    }

    //Con este método, se evita repetir código cada vez que se cree un Intent
    public Intent createIntent(Context contexto, Class clase){
        Intent intent = new Intent(contexto, clase);
        Bundle bundle = new Bundle();
        bundle.putParcelable("vino", this.vino);
        intent.putExtras(bundle);
        return intent;
    }

    //Se lanza la actividad correspondiente al intent creado en el método createIntent
    private void EditarVino(){
        this.getContext().startActivity(createIntent(this.getContext(), ThirdActivity.class));
    }

    /*
     * En el EditText perteneciente a la interfaz activity_main se establece el id relacionado
     * con el vino que se va a editar
     */
    private void ponerIdEditTextIdEditarMain(long id){
        MainActivity.getEtMainId().setText(String.valueOf(id));
    }
}
