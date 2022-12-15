package mx.gob.villahermosa.siacentro.classes.others;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class SiacDialogBasicFragment extends DialogFragment {

    private String titulo = "SAC";
    private String Mensaje = "!";

//    Tambien se puede usar esto
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setMessage("Hola welcome")
//                    .setTitle("Mundo");
//            AlertDialog dialog = builder.create();
//            dialog.show();


    public SiacDialogBasicFragment(String Mensaje) {
        this.Mensaje = Mensaje;

    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(this.Mensaje)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }


}
