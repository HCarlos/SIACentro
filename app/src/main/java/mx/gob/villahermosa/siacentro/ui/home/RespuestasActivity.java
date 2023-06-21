package mx.gob.villahermosa.siacentro.ui.home;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import mx.gob.villahermosa.siacentro.MainActivity;
import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.Singleton;
import mx.gob.villahermosa.siacentro.classes.databases.UserDB;
import mx.gob.villahermosa.siacentro.classes.databases.UserEntity;
import mx.gob.villahermosa.siacentro.classes.responses.ComboResponse;
import mx.gob.villahermosa.siacentro.classes.responses.RespuestasResponse;
import mx.gob.villahermosa.siacentro.data.adapters.ApiDenunciaAdapter;
import mx.gob.villahermosa.siacentro.data.adapters.RespuestasDenunciaAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RespuestasActivity extends AppCompatActivity implements Callback<ComboResponse> {

    private UserEntity userEntity;

    public int denuncia_id = 0;
    private RespuestasResponse RR;
    private mx.gob.villahermosa.siacentro.databinding.ActivityRespuestasBinding binding;

    @SuppressLint({"SetJavaScriptEnabled", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuestas);
        binding = mx.gob.villahermosa.siacentro.databinding.ActivityRespuestasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.RR = null;

        userEntity = UserDB.getUserFromId(1);

        denuncia_id = Singleton.getDenuncia_id();

        TextView txtTitulo = binding.appBarMenu.txtTitulo;
        txtTitulo.setText(String.format("Respuestas del folio: %d", denuncia_id));
        txtTitulo.setTextSize(16);

        setSupportActionBar(binding.appBarMenu.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setElevation(8);


        EditText txtRespuesta = binding.txtRespuesta;
        Button btnSendRespuesta = binding.btnSaveRespuesta;
        btnSendRespuesta.setOnClickListener(v -> {

            userEntity = UserDB.getUserFromId(1);
            String autoriza = userEntity.getToken();
            int user_id = userEntity.getUser_id();

            if (! String.valueOf(txtRespuesta.getText()).equals("") ) {
                Call<ComboResponse> call = ApiDenunciaAdapter.getApiService().addRespuestaDenunciaSend(
                        "Bearer " + autoriza,
                        String.valueOf(txtRespuesta.getText()),
                        denuncia_id,
                        user_id
                );
                call.enqueue(RespuestasActivity.this);
            }

        });

        llenarRecivleView();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            Log.e("PASO", "home");
            super.onBackPressed();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onResponse(@NonNull Call<ComboResponse> call, Response<ComboResponse> response) {
        if (response.isSuccessful()) {
            ComboResponse combo_response = response.body();
            assert combo_response != null;

            EditText txtRespuesta = binding.txtRespuesta;
            this.RR = new RespuestasResponse();
            this.RR.setFecha("HOY");
            this.RR.setRespuesta(String.valueOf(txtRespuesta.getText()));
            this.RR.setId(0);
            this.RR.setObservaciones("");
            this.RR.setRoleuser("");
            this.RR.setUsername("");
            txtRespuesta.setText("");

            llenarRecivleView();
        }

    }

    @Override
    public void onFailure(@NonNull Call<ComboResponse> call, Throwable t) {
        Toast.makeText(getApplicationContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
    }


    public void llenarRecivleView() {

        ArrayList<RespuestasResponse> Imgs;
        Imgs = Singleton.getRespuestas();
        if (!(this.RR == null)){
            Imgs.add(this.RR);
        }
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(VERTICAL);
        RespuestasDenunciaAdapter adapter = new RespuestasDenunciaAdapter(this,this, Imgs, denuncia_id);
        RecyclerView rvRespuestasDenuncia = this.findViewById(R.id.rvRespuestasDenuncia);
        rvRespuestasDenuncia.setLayoutManager(llm);
        rvRespuestasDenuncia.setAdapter(adapter);
        rvRespuestasDenuncia.scrollToPosition(Imgs.size() - 1);

    }



}