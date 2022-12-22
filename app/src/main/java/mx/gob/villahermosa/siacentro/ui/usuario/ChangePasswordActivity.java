package mx.gob.villahermosa.siacentro.ui.usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import mx.gob.villahermosa.siacentro.MainActivity;
import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.databases.UserDB;
import mx.gob.villahermosa.siacentro.classes.databases.UserEntity;
import mx.gob.villahermosa.siacentro.classes.others.DialogAlertConfirm;
import mx.gob.villahermosa.siacentro.classes.others.SiacDialogBasicFragment;
import mx.gob.villahermosa.siacentro.classes.responses.ComboResponse;
import mx.gob.villahermosa.siacentro.data.adapters.ApiAdapter;
import mx.gob.villahermosa.siacentro.databinding.ActivityChangePasswordBinding;
import mx.gob.villahermosa.siacentro.databinding.ActivityProfileBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity implements Callback<ComboResponse> {


    private UserEntity userEntity;
    public Context context;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityChangePasswordBinding binding;
    public NavController navController;

    private String pas1;
    private String pas2;
    private String pas3;

    private Button btnChnangePassword;
    private ProgressBar loading_change_paword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = ChangePasswordActivity.this;

        userEntity = UserDB.getUserFromId(1);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView txtTitulo = (TextView) binding.appBarMenu.txtTitulo;
        txtTitulo.setText(R.string.cambiar_password);

        setSupportActionBar(binding.appBarMenu.toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setElevation(8);
        }

        loading_change_paword = binding.loadingChangePassword;

        btnChnangePassword = binding.btnChangePassword;

        btnChnangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loading_change_paword.setVisibility(View.VISIBLE);
                pas1 = binding.password1.getText().toString().trim();
                pas2 = binding.password2.getText().toString().trim();
                pas3 = binding.password3.getText().toString().trim();
                if (Validate()) {
                    DialogAlertConfirm d = new DialogAlertConfirm(ChangePasswordActivity.this, context);
                    AlertDialog ad = d.confirm("Desea continuar?\n\nEste cambio es irreversible.\n\n");
                    ad.show();
                    ad.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sendData();
                            ad.dismiss();
                        }
                    });
                }

            }
        });
    }

    private boolean Validate() {
        boolean retorno = false;
        String errores = "";

        if (Objects.equals(pas1, "")){
            errores += "Su password actual no puede estar vacío.\n";
        } else if (Objects.equals(pas2, "")){
            errores += "Su nuevo password no puede estar vacío.\n";
        } else if (Objects.equals(pas3, "")){
            errores += "La repetición de su nuevo password no puede estar vacío.\n";
        } else if (!pas2.equals(pas3)){
            errores += "Su nuevo password ("+pas2+") y la repetición de su nuevo password ("+pas3+") no coinciden.\n";
        } else {
            retorno = true;
        }
        if (!errores.equals("")){
            SiacDialogBasicFragment sd = new SiacDialogBasicFragment(errores);
            sd.show(getSupportFragmentManager(),"");
        }

        return retorno;

    }


    public void sendData(){
        loading_change_paword.setVisibility(View.VISIBLE);
        btnChnangePassword.setEnabled(false);

        userEntity = UserDB.getUserFromId(1);
        String autoriza = userEntity.getToken();
        int user_id = userEntity.getUser_id();
        Call<ComboResponse> call = ApiAdapter.getApiService().setChangePassword("Bearer " + autoriza, pas1, pas2, pas3, user_id);
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<ComboResponse> call, Response<ComboResponse> response) {
        if (response.isSuccessful()) {

            ComboResponse combo_response = response.body();
            assert combo_response != null;
            int status = combo_response.getStatus();

//            showImage();

            Toast.makeText(getApplicationContext(), combo_response.getMsg(), Toast.LENGTH_LONG).show();

        }
        loading_change_paword.setVisibility(View.INVISIBLE);
        btnChnangePassword .setEnabled(true);

    }

    @Override
    public void onFailure(Call<ComboResponse> call, Throwable t) {
        loading_change_paword.setVisibility(View.INVISIBLE);
        btnChnangePassword .setEnabled(true);
        Toast.makeText(getApplicationContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }



}