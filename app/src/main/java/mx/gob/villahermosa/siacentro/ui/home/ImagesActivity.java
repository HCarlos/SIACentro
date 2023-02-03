package mx.gob.villahermosa.siacentro.ui.home;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.Singleton;
import mx.gob.villahermosa.siacentro.classes.responses.ImagenesResponse;
import mx.gob.villahermosa.siacentro.data.adapters.ImagenesDenunciaAdapter;

public class ImagesActivity extends AppCompatActivity {

    Activity activity;
    public FloatingActionButton fab;
    public int denuncia_id = 0;

    @SuppressLint({"SetJavaScriptEnabled", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        mx.gob.villahermosa.siacentro.databinding.ActivityImagesBinding binding = mx.gob.villahermosa.siacentro.databinding.ActivityImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;

        denuncia_id = Singleton.getDenuncia_id();

        fab = this.findViewById(R.id.fab2);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(activity, AddImageActivity.class);
            intent.putExtra("denuncia_id", denuncia_id );
            activity.startActivity(intent);

        });

        ArrayList<ImagenesResponse> Imgs = Singleton.getImagenes();

        TextView txtTitulo = binding.appBarMenu.txtTitulo;
        txtTitulo.setText(String.format("Imágenes del folio: %d", denuncia_id));
        txtTitulo.setTextSize(16);

        setSupportActionBar(binding.appBarMenu.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setElevation(8);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(VERTICAL);
        ImagenesDenunciaAdapter adapter = new ImagenesDenunciaAdapter(this,this, Imgs, denuncia_id);
        RecyclerView rvDenuncia = this.findViewById(R.id.rvImegenesDenuncia);
        rvDenuncia.setLayoutManager(llm);
        rvDenuncia.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ImagesActivity.class);
        startActivity(intent);
        finish();
    }

}