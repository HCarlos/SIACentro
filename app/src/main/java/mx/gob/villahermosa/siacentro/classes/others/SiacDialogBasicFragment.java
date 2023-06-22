package mx.gob.villahermosa.siacentro.classes.others;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class SiacDialogBasicFragment extends DialogFragment {

    private final String titulo = "SIACentro";
    public  String Mensaje = "!";

//    Tambien se puede usar esto
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setMessage("Hola welcome")
//                    .setTitle("Mundo");
//            AlertDialog dialog = builder.create();
//            dialog.show();


    public SiacDialogBasicFragment(String _Mensaje) {
        Mensaje = _Mensaje;

    }


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(Mensaje)
                .setPositiveButton("Aceptar", (dialog, id) -> {
                });
        return builder.create();
    }


}
