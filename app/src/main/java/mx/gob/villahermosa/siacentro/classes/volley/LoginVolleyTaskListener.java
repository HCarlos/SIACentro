package mx.gob.villahermosa.siacentro.classes.volley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mx.gob.villahermosa.siacentro.classes.AppConfig;
import mx.gob.villahermosa.siacentro.classes.interfases.LoginAsyncTaskListener;
import mx.gob.villahermosa.siacentro.classes.parser.ParserJSON;
import mx.gob.villahermosa.siacentro.ui.usuario.IngresarFragment;

public class LoginVolleyTaskListener implements LoginAsyncTaskListener {

    private final Activity activity;
    private final Context context;
    private RequestQueue rQueue;
    private final String url = AppConfig.LOGIN_VOLLEY;
    private ArrayList<HashMap<String, String>> arraylist;
    public Bitmap bitmap;
    private static final String TAG = "RESPUESTA";
    private ProgressDialog pDialog;
    private final IngresarFragment listener;
    public String username = "";
    public String password = "";
    public ParserJSON pj;

    public LoginVolleyTaskListener(Activity activity, Context context, IngresarFragment _iF) {
        this.activity = activity;
        this.context = context;
        this.listener = _iF;
        pj = new ParserJSON();

    }

    @Override
    public void Login(String _username, String _password){
        username = _username;
        password = _password;

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("consultando datos...");
        pDialog.setCancelable(false);

        pj.showDialog(pDialog);

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                response -> {
                    Log.d(TAG,new String(response.data));
                    rQueue.getCache().clear();
                    pj.hideDialog(pDialog);
                    try {
                        pj.parse_user(listener, activity, context, response);
                    } catch (JSONException e) {
                        pj.hideDialog(pDialog);
                        e.printStackTrace();
                    }
                },
                error -> {
                    pj.hideDialog(pDialog);
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }

        };


        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rQueue = Volley.newRequestQueue(context);
        rQueue.add(volleyMultipartRequest);
    }

    public void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }


    public void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



}

