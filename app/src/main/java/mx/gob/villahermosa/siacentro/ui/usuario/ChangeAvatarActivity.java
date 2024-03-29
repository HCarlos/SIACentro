package mx.gob.villahermosa.siacentro.ui.usuario;

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
//import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import mx.gob.villahermosa.siacentro.MainActivity;
import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.Singleton;
import mx.gob.villahermosa.siacentro.classes.controllers.Permissions;
import mx.gob.villahermosa.siacentro.classes.controllers.PhotoUtils;
import mx.gob.villahermosa.siacentro.classes.databases.UserDB;
import mx.gob.villahermosa.siacentro.classes.databases.UserEntity;
import mx.gob.villahermosa.siacentro.classes.others.Funciones;
import mx.gob.villahermosa.siacentro.classes.responses.ComboResponse;
import mx.gob.villahermosa.siacentro.data.adapters.ApiAdapter;
import mx.gob.villahermosa.siacentro.databinding.ActivityChangeAvatarBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeAvatarActivity extends AppCompatActivity implements Callback<ComboResponse> {

    private UserEntity userEntity;
    public Context context;
    public ProgressBar progress;
    public Button btnCambiarImagen;
    public ImageButton btnRotateImagen;
    public Button btnSaveImagen;
    public Bitmap imagenBitMapASubir;
    public Uri photoURI;
    public Uri selectedImage;
    private ImageView avatar;
    public Boolean IsIMage;
    public String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userEntity = UserDB.getUserFromId(1);
        mx.gob.villahermosa.siacentro.databinding.ActivityChangeAvatarBinding binding = ActivityChangeAvatarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.context = getApplicationContext();

        TextView txtTitulo = binding.appBarMenu.txtTitulo;
        txtTitulo.setText(R.string.cambiar_avatar);

        setSupportActionBar(binding.appBarMenu.toolbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setElevation(8);
        }

        Permissions permisos = new Permissions(this, getApplicationContext());

        if ( permisos.chechPermission(this) ){
//            GPSTracker___delete gps = new GPSTracker___delete(getApplicationContext());
//            Location location = gps.getLocation();
//            Singleton.setLatitude(location.getLatitude());
//            Singleton.setLongitude(location.getLongitude());
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Singleton.setLatitude(location.getLatitude());
                            Singleton.setLongitude(location.getLongitude());
                            //Toast.makeText(getApplicationContext(), "Latitud : " + Singleton.getLatitude() + ",\n Longitud : " + Singleton.getLongitude(), Toast.LENGTH_SHORT).show();
                        }
                    });


        }



        avatar = findViewById(R.id.avatar);

        progress = findViewById(R.id.loading_avatar);
        btnCambiarImagen = findViewById(R.id.btnCallMenu);
        btnCambiarImagen.setOnClickListener(v -> showBottomSheetDialog());

        btnRotateImagen = findViewById(R.id.btnRotateImagen);
        btnRotateImagen.setVisibility(View.INVISIBLE);
        btnRotateImagen.setOnClickListener(v -> rotarImagen(imagenBitMapASubir));

        btnSaveImagen = findViewById(R.id.btnSaveImabe);
        btnSaveImagen.setOnClickListener(v -> savePhoto());
        btnSaveImagen.setEnabled(false);

        showImage();

        IsIMage = false;

    }



    @SuppressLint({"IntentReset", "QueryPermissionsNeeded"})
    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setTitle("Escoja una opción:");
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_layout);

        LinearLayout btnTomarFoto = bottomSheetDialog.findViewById(R.id.btnTomarFoto);
        LinearLayout btnSeleccionarFoto = bottomSheetDialog.findViewById(R.id.btnSeleccionarFoto);

        Objects.requireNonNull(btnTomarFoto).setOnClickListener(v -> {

            photoURI = null;
            try {
                photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", createImageFile());
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (photoURI != null) {
                    i.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                    tomarFoto.launch(i);
                }
            } catch (IOException ignore) {
            }
            bottomSheetDialog.dismiss();

        });

        Objects.requireNonNull(btnSeleccionarFoto).setOnClickListener(v -> {
            @SuppressLint("IntentReset") Intent gallery = new Intent(Intent.ACTION_PICK, Images.Media.INTERNAL_CONTENT_URI);
            gallery.setType("image/*");
            if (gallery.resolveActivity(getPackageManager()) != null) {
                seleccionarFoto.launch(gallery);
            }
            bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.show();

    }



    // Procesamiento de la Fotografìa
    ActivityResultLauncher<Intent> tomarFoto = registerForActivityResult(
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
                        avatar.setImageBitmap(imagenBitMapASubir);
                        btnSaveImagen.setEnabled(true);
                        btnRotateImagen.setVisibility(View.VISIBLE);
                        IsIMage = true;
                    }

                }
            }
        }
    });

    // Procesamiento de la Obtenciòn de la Imagen del Carrete
    ActivityResultLauncher<Intent> seleccionarFoto = registerForActivityResult(
    new ActivityResultContracts.StartActivityForResult(),
    new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                selectedImage = Objects.requireNonNull(data).getData();
                String selectedPath=selectedImage.getPath();
                if (selectedPath != null) {
                    InputStream imageStream;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);
//                        Singleton.setImagen(avatar);
                        Singleton.setUriData(PhotoUtils.getImageUri(context, bmp));
                        imagenBitMapASubir = bmp;
                        btnSaveImagen.setEnabled(true);
                        IsIMage = true;
                        avatar.setImageBitmap(bmp);
                    } catch (FileNotFoundException ignore) {
//                                    e.printStackTrace();
                    }
                }
            }
        }
    });



    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void savePhoto(){
        progress.setVisibility(View.VISIBLE);
        btnCambiarImagen.setEnabled(false);
        btnSaveImagen.setEnabled(false);
        IsIMage = false;

        userEntity = UserDB.getUserFromId(1);
        String autoriza = userEntity.getToken();
        Funciones funciones = new Funciones();
        String image_user = funciones.base64String(imagenBitMapASubir);

        int user_id = userEntity.getUser_id();

        Call<ComboResponse> call = ApiAdapter.getApiService().setAvatar("Bearer " + autoriza, image_user, user_id);
        call.enqueue(this);

    }


    @Override
    public void onResponse(@NonNull Call<ComboResponse> call, Response<ComboResponse> response) {
        if (response.isSuccessful()) {

            ComboResponse combo_response = response.body();
            assert combo_response != null;

            showImage();

            Toast.makeText(getApplicationContext(), combo_response.getMsg(), Toast.LENGTH_LONG).show();

        }
        progress.setVisibility(View.INVISIBLE);
        btnCambiarImagen .setEnabled(true);
        btnSaveImagen.setEnabled(false);
        btnRotateImagen.setVisibility(View.INVISIBLE);

        IsIMage = true;

    }

    @Override
    public void onFailure(@NonNull Call<ComboResponse> call, Throwable t) {
        progress .setVisibility(View.INVISIBLE);
        btnRotateImagen.setVisibility(View.INVISIBLE);
        btnCambiarImagen .setEnabled(true);
        btnSaveImagen.setEnabled(false);
        IsIMage = true;
//        Log.d("FallaUsuario", "tamaño de usuario --->   " + t.getMessage());
        Toast.makeText(getApplicationContext(),  t.getMessage(), Toast.LENGTH_LONG).show();

    }

    public void showImage(){

        String imageUri = userEntity.getURLImagenArchivo();

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
                .into(avatar);

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

    public void rotarImagen(Bitmap bitmap){
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        imagenBitMapASubir = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        avatar.setImageBitmap(imagenBitMapASubir);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
