package mx.gob.villahermosa.siacentro.classes.others;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import mx.gob.villahermosa.siacentro.R;

public class DialogAlertConfirm {
    private final Activity activity;
    private final Context context;

    public DialogAlertConfirm(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public AlertDialog confirm(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MaterialDialogSheet);
        builder.setTitle(R.string.nav_header_title)
                .setMessage(message)
                .setPositiveButton("Si", (dialog, id) -> {
                })
                .setNegativeButton("No", (dialog, which) -> {
                });
           return builder.create();

    }

}
