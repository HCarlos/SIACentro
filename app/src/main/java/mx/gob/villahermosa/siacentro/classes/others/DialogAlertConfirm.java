package mx.gob.villahermosa.siacentro.classes.others;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import mx.gob.villahermosa.siacentro.R;

public class DialogAlertConfirm {
    private Activity activity;
    private Context context;

    public DialogAlertConfirm(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public AlertDialog confirm(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MaterialDialogSheet);
        builder.setTitle(R.string.nav_header_title)
                .setMessage(message)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
           return builder.create();

    }

}
