package mx.gob.villahermosa.siacentro.ui.usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import mx.gob.villahermosa.siacentro.MainActivity;
import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.databases.UserDB;
import mx.gob.villahermosa.siacentro.classes.databases.UserEntity;
import mx.gob.villahermosa.siacentro.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {


    private UserEntity userEntity;
    public Context context;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityProfileBinding binding;
    public NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        userEntity = UserDB.getUserFromId(1);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView txtTitulo = binding.appBarMenu.txtTitulo;
        txtTitulo.setText(R.string.ver_perfil);


        setSupportActionBar(binding.appBarMenu.toolbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.ver_perfil);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setElevation(8);
        }

        LinearLayout ll1 = binding.llProfile;
        ScrollView scv1 = binding.scProfile;
        ll1.setVisibility(View.VISIBLE);
        scv1.setVisibility(View.VISIBLE);

        String imageUri = userEntity.getURLImagenArchivo();
        ImageView ivBasicImage = binding.imgHomeUserFrag;

        int dr = R.drawable.empty_user_female;
        if (this.userEntity.getGenero() == 1){
            dr = R.drawable.empty_user_male;
        }
        Picasso
                .get()
                .load(imageUri)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(dr)
                .error(dr)
                .into(ivBasicImage);

        TextView txtUsername = binding.FullName;
        txtUsername.setText(userEntity.getFullName());

        TextView txtlblUsername = binding.lblFullName;
        String lbl = "Bienvenido!!";
        if ( userEntity.getGenero() == 0 )
            lbl = "Bienvenda!";
        txtlblUsername.setText(lbl);

        TextView txtCURP = binding.CURP;
        txtCURP.setText(userEntity.getCurp());

        TextView txtGenero = binding.Genero;

        if (this.userEntity.getGenero() == 1){
            txtGenero.setText(R.string.Hombre);
        }else{
            txtGenero.setText(R.string.Mujer);
        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}