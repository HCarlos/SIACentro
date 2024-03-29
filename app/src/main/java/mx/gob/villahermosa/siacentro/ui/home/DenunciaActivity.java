package mx.gob.villahermosa.siacentro.ui.home;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import mx.gob.villahermosa.siacentro.MainActivity;
import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.Singleton;
import mx.gob.villahermosa.siacentro.classes.controllers.Permissions;
import mx.gob.villahermosa.siacentro.classes.controllers.Utilidades;
import mx.gob.villahermosa.siacentro.classes.databases.UserDB;
import mx.gob.villahermosa.siacentro.classes.databases.UserEntity;
import mx.gob.villahermosa.siacentro.classes.others.Funciones;
import mx.gob.villahermosa.siacentro.classes.responses.ComboResponse;
import mx.gob.villahermosa.siacentro.data.adapters.ApiDenunciaAdapter;
import mx.gob.villahermosa.siacentro.databinding.ActivityDenunciaBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DenunciaActivity extends AppCompatActivity implements Callback<ComboResponse> {
    protected UserEntity userEntity;
    protected Context context;
    protected ActivityDenunciaBinding binding;
    protected String servicioId;
    protected String servicioTitulo;
    protected ImageView ivImageToSend;
    protected Bitmap imagenBitMapASubir;
    protected Button btnObtenerImagen;
    protected ImageButton btnRotateImagen;
    protected Button btnEnviarImagen;
    protected Uri photoURI;
    protected String photoPath;
    protected ProgressBar loading_image_send;
    protected Permissions permisos;
    protected FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

        Bundle bundle = getIntent().getExtras();
        servicioId = bundle.getString("menu_id");
        servicioTitulo = bundle.getString("menu_title");

        this.context = getApplicationContext();

        userEntity = UserDB.getUserFromId(1);
        binding = ActivityDenunciaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView txtTitulo = binding.appBarDenuncia.txtTitulo;
        txtTitulo.setText(servicioTitulo);

        setSupportActionBar(binding.appBarDenuncia.toolbardenuncia);

        Toolbar toolbar = findViewById(binding.appBarDenuncia.toolbardenuncia.getId());
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setElevation(8);
        }
        permisos = new Permissions(this, getApplicationContext());
        if (permisos.chechPermisoLatLong(this)) {

            Utilidades.getLocation(this, getApplicationContext());

        }

        ivImageToSend = findViewById(R.id.imageToSend);
        loading_image_send = findViewById(R.id.loadingImageToSend);
        loading_image_send.setVisibility(View.GONE);

        btnRotateImagen = binding.btnRotateImagen;
        btnRotateImagen.setVisibility(View.GONE);
        btnRotateImagen.setOnClickListener(v -> rotarImagen(imagenBitMapASubir));

        btnObtenerImagen = binding.btnObtenerImagenDen;
        btnObtenerImagen.setOnClickListener(this::getObtenerImagenDesdeLaCamara);

        btnEnviarImagen = binding.btnImageToSend;
        btnEnviarImagen.setOnClickListener(v -> enviarDatos());




    }

    public void rotarImagen(Bitmap bitmap){
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        imagenBitMapASubir = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        ivImageToSend.setImageBitmap(imagenBitMapASubir);
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private File createImageFile() throws IOException {
        photoPath = null;
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "SIACentro_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );
        photoPath  = image.getAbsolutePath();
        Log.w("NOMBRE_ARCHIVO",photoPath);
        return image;
    }

    public void getObtenerImagenDesdeLaCamara(View view){
        photoURI = null;
        try {
            photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", createImageFile());
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (photoURI != null) {
                i.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                obtenerImagen.launch(i);
            }
        } catch (IOException ignore) {
        }

    }

    // Procesamiento de la Fotografìa
    ActivityResultLauncher<Intent> obtenerImagen = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            photoPath = getRealPathFromURI(getApplicationContext(), data.getData());
                        }
                        if (photoPath != null) {
                            File imgFile = new  File(photoPath);
                            if(imgFile.exists()) {
                                Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
                                imagenBitMapASubir = Bitmap.createScaledBitmap(bitmap, 800, 600, true);
                                ivImageToSend.setImageBitmap(imagenBitMapASubir);
                                btnEnviarImagen.setEnabled(true);
                                btnRotateImagen.setVisibility(View.VISIBLE);
//                                IsIMage = true;
                            }

                        }
                    }
                }
            });

    public void enviarDatos(){
        if (imagenBitMapASubir != null){

            loading_image_send.setVisibility(View.VISIBLE);
            btnObtenerImagen.setEnabled(false);
            btnRotateImagen.setEnabled(false);
            btnEnviarImagen.setEnabled(false);
//        IsIMage = false;

            userEntity = UserDB.getUserFromId(1);
            String autoriza = userEntity.getToken();
            Funciones funciones = new Funciones();
            String image = funciones.base64String(imagenBitMapASubir);
            String descripcionImageToSend = binding.descripcionImageToSend.getText().toString();
            int user_id = userEntity.getUser_id();

            String Ubicacion = funciones.getCompleteAddressString(context, Singleton.getLatitude(), Singleton.getLongitude());
            String Marca = permisos.getDataPhone(this, context);

            if (! image.equals("") ) {
                Call<ComboResponse> call = ApiDenunciaAdapter.getApiService().denunciaSend(
                        "Bearer " + autoriza,
                        image,
                        descripcionImageToSend,
                        "1",
                        servicioTitulo,
                        Singleton.getLatitude(),
                        Singleton.getLongitude(),
                        "ANDROID",
                        Marca,
                        1,
                        Ubicacion,
                        Ubicacion,
                        user_id,
                        Singleton.getDeviceToken(),
                        Marca
                );
                call.enqueue(this);
            }else{
                Toast.makeText(context, "Imagen vacía", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, "Debe tomar una foto", Toast.LENGTH_SHORT).show();
        }


    }

//                Singleton.getLongitude(),

    @Override
    public void onResponse(@NonNull Call<ComboResponse> call, Response<ComboResponse> response) {
        if (response.isSuccessful()) {

            ComboResponse combo_response = response.body();
            assert combo_response != null;
            int status = combo_response.getStatus();

            Toast.makeText(getApplicationContext(), combo_response.getMsg(), Toast.LENGTH_LONG).show();
            if (status == 1)
                onBackPressed();

        }
        loading_image_send.setVisibility(View.INVISIBLE);
        btnObtenerImagen.setEnabled(true);
        btnRotateImagen.setEnabled(true);
        btnEnviarImagen.setEnabled(true);

    }

    @Override
    public void onFailure(@NonNull Call<ComboResponse> call, Throwable t) {
        loading_image_send.setVisibility(View.INVISIBLE);
        btnObtenerImagen .setEnabled(true);
        btnRotateImagen.setEnabled(true);
        btnEnviarImagen.setEnabled(true);
        Toast.makeText(getApplicationContext(),  "Código de Error => A1258 : " + t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }



}