package mx.gob.villahermosa.siacentro.classes.parser;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.NetworkResponse;

import org.json.JSONException;
import org.json.JSONObject;

import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.Usuario;
import mx.gob.villahermosa.siacentro.classes.responses.UserResponse;
import mx.gob.villahermosa.siacentro.classes.responses.UsuarioResponse;
import mx.gob.villahermosa.siacentro.ui.usuario.IngresarFragment;

public class ParserJSON {
    private Activity activity;
    private Context context;
    private ProgressDialog pDialog;
    private IngresarFragment listener;

    public void parse_user(IngresarFragment _iF, Activity _activity, Context _context, NetworkResponse response) throws JSONException {
        context = _context;
        activity = _activity;
        listener = _iF;

        UsuarioResponse usuario_response = new UsuarioResponse();

        JSONObject jsonObject = new JSONObject(new String(response.data));
        int status = jsonObject.getInt("status");
        String msg = jsonObject.getString("msg");

        if (status == 1){


            String access_token = jsonObject.getString("access_token");
            String token_type = jsonObject.getString("token_type");
            JSONObject user = new JSONObject( jsonObject.getString("user") );

            usuario_response.setMsg(msg);
            usuario_response.setAccess_token(access_token);
            usuario_response.setToken_type(token_type);
            usuario_response.setStatus(status);

            UserResponse User = new UserResponse();
            int session_id = 0;
            if (user.has("session_id") && !user.isNull("session_id") ){
                session_id = user.getInt("session_id");
            }

            User.id                = user.getInt("id");
            User.username          = user.getString("username");
            User.email             = user.getString("email");
            User.nombre            = user.getString("nombre");
            User.ap_paterno        = user.getString("ap_paterno");
            User.ap_materno        = user.getString("ap_materno");
            User.curp              = user.getString("curp");
            User.emails            = user.getString("emails");
            User.celulares         = user.getString("celulares");
            User.telefonos         = user.getString("telefonos");
            User.fecha_nacimiento  = user.getString("fecha_nacimiento");
            User.genero            = user.getInt("genero");
            User.root              = user.getString("root");
            User.filename          = user.getString("filename");
            User.filename_png      = user.getString("filename_png");
            User.filename_thumb    = user.getString("filename_thumb");
            User.admin             = user.getBoolean("admin");
            User.alumno            = user.getBoolean("alumno");
            User.delegado          = user.getBoolean("delegado");
            User.session_id        = session_id;
            User.status_user       = user.getInt("status_user");
            User.empresa_id        = user.getInt("empresa_id");
            User.ip                = user.getString("ip");
            User.host              = user.getString("host");
            User.logged            = user.getString("logged");
            User.logged_at         = user.getString("logged_at");
            User.logout_at         = user.getString("logout_at");
            User.email_verified_at = user.getString("email_verified_at");
            User.created_at        = user.getString("created_at");
            User.updated_at        = user.getString("updated_at");
            User.user_mig_id       = user.getInt("user_mig_id");
            User.searchtext        = user.getString("searchtext");
            User.ubicacion_id      = user.getInt("ubicacion_id");
            User.imagen_id         = user.getInt("imagen_id");
            User.uuid              = user.getString("uuid");

            usuario_response.setUser(User);

            Usuario.setUser_response(usuario_response);
            Usuario.setUser(User);
            showDialog(activity, msg,true);

        }else{
            showDialog(activity, msg+" : "+ status,false);
        }
    }

    public void showDialog(Dialog pDialog) {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void showDialog(Activity activity, String msg, final boolean isClosed){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        if (!isClosed) dialog.setContentView(R.layout.dialog_error);
        else dialog.setContentView(R.layout.dialog_succefully);

        TextView text = dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(v -> {
            dialog.dismiss();
            if (isClosed) listener.goBackActivity();
        });

        dialog.show();

    }

    public void hideDialog(Dialog pDialog) {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



}
