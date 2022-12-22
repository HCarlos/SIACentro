package mx.gob.villahermosa.siacentro.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.databases.UserDB;
import mx.gob.villahermosa.siacentro.classes.databases.UserEntity;
import mx.gob.villahermosa.siacentro.databinding.ActivityDenunciaBinding;
import mx.gob.villahermosa.siacentro.databinding.ActivityProfileBinding;

public class DenunciaActivity extends AppCompatActivity {
    private UserEntity userEntity;
    public Context context;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDenunciaBinding binding;
    public NavController navController;
    public String servicioId;
    public String servicioTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

        Bundle bundle = getIntent().getExtras();
        servicioId = bundle.getString("menu_id");
        servicioTitulo = (String) bundle.getString("menu_title");


        userEntity = UserDB.getUserFromId(1);
        binding = ActivityDenunciaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView txtUsername = (TextView) binding.FullName;
        txtUsername.setText(servicioTitulo);

        TextView txtTitulo = (TextView) binding.appBarDenuncia.txtTitulo;
        txtTitulo.setText(servicioTitulo);

        setSupportActionBar(binding.appBarDenuncia.toolbardenuncia);

        Toolbar toolbar = (Toolbar) findViewById(binding.appBarDenuncia.toolbardenuncia.getId());
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setElevation(8);
        }


    }
}