package mx.gob.villahermosa.siacentro.ui.usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import mx.gob.villahermosa.siacentro.classes.Usuario;
import mx.gob.villahermosa.siacentro.classes.databases.UserDB;
import mx.gob.villahermosa.siacentro.classes.databases.UserEntity;
import mx.gob.villahermosa.siacentro.classes.others.Funciones;
import mx.gob.villahermosa.siacentro.classes.responses.ComboResponse;
import mx.gob.villahermosa.siacentro.classes.responses.UsuarioResponse;
import mx.gob.villahermosa.siacentro.data.adapters.ApiAdapter;
import mx.gob.villahermosa.siacentro.databinding.ActivityChangeAvatarBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeAvatarActivity extends AppCompatActivity implements Callback<ComboResponse> {


    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int SELECT_FILE = 2;

    private UserEntity userEntity;
    public Context context;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityChangeAvatarBinding binding;
    public NavController navController;
    public ProgressBar progress;
    public Button btnCambiarImagen;
    public Bitmap imagenBitMapASubir;
    public Uri photoURI;
    String currentPhotoPath;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGE = 1;

    private static final int
            IMAGE_CAPTURE_CODE = 1000,
            GALLERY_REQUEST_CODE=1001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userEntity = UserDB.getUserFromId(1);
        binding = ActivityChangeAvatarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FileProvider fileProvider;

        this.context = getApplicationContext();

        TextView txtTitulo = (TextView) binding.appBarMenu.txtTitulo;
        txtTitulo.setText(R.string.cambiar_avatar);

        setSupportActionBar(binding.appBarMenu.toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setElevation(8);
        }

        showImage();
        ProgressBar progress = (ProgressBar) findViewById(R.id.loading_avatar);
        this.progress = progress;
        this.btnCambiarImagen = (Button) findViewById(R.id.btnCallMenu);
        this.btnCambiarImagen.setOnClickListener(v -> showBottomSheetDialog());

    }



    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setTitle("Escoja una opción:");
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_layout);

        LinearLayout btnTomarFoto = bottomSheetDialog.findViewById(R.id.btnTomarFoto);
        LinearLayout btnSeleccionarFoto = bottomSheetDialog.findViewById(R.id.btnSeleccionarFoto);

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//                }
                dispatchTakePictureIntent();
                bottomSheetDialog.dismiss();

            }
        });

        btnSeleccionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                if (gallery.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(gallery, PICK_IMAGE);
//                }

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Seleccione una imagen"),
                        SELECT_FILE);


                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
//            }
//            if (photoFile != null) {
//                photoURI = FileProvider.getUriForFile(this,
//                        "mx.gob.villahermosa.siacentro.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                Log.w("ENTRADA","SI ENTRO POR AQUI");
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            }
//        }
//


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImageUri = null;
        Uri selectedImage;

//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            ImageView avatar = (ImageView) findViewById(R.id.avatar);
//            avatar.setImageBitmap(imageBitmap);
//            savePhoto(imageBitmap);
//        }
//
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ImageView avatar = (ImageView) findViewById(R.id.avatar);
                    savePhoto(imageBitmap);
                    avatar.setImageBitmap(imageBitmap);
//                    showImage();
                }
                break;
            case SELECT_FILE:
                if (resultCode == RESULT_OK) {
                    selectedImage = data.getData();
                    String selectedPath=selectedImage.getPath();
                    if (requestCode == SELECT_FILE) {

                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                            ImageView mImg = (ImageView) findViewById(R.id.avatar);
                            savePhoto(bmp);

                            mImg.setImageBitmap(bmp);

//                            showImage();

                        }
                    }
                }
                break;
        }

    }

    public void savePhoto(Bitmap imageBitmap){
        this.progress.setVisibility(View.VISIBLE);
        this.btnCambiarImagen.setEnabled(false);

        userEntity = UserDB.getUserFromId(1);
        String autoriza = userEntity.getToken();
        Funciones funciones = new Funciones();
        String image_user = funciones.base64String(imageBitmap);

        int user_id = userEntity.getUser_id();

        Call<ComboResponse> call = ApiAdapter.getApiService().setAvatar("Bearer " + autoriza, image_user, user_id);
        call.enqueue(this);

    }


    @Override
    public void onResponse(Call<ComboResponse> call, Response<ComboResponse> response) {
        if (response.isSuccessful()) {

            ComboResponse combo_response = response.body();
            assert combo_response != null;
            int status = combo_response.getStatus();

            showImage();

            Toast.makeText(getApplicationContext(), combo_response.getMsg(), Toast.LENGTH_LONG).show();

        }
        this.progress .setVisibility(View.INVISIBLE);
        this.btnCambiarImagen .setEnabled(true);

    }

    @Override
    public void onFailure(Call<ComboResponse> call, Throwable t) {
        this.progress .setVisibility(View.INVISIBLE);
        this.btnCambiarImagen .setEnabled(true);
        Log.d("FallaUsuario", "tamaño de usuario --->   " + t.getMessage());
        Toast.makeText(getApplicationContext(),  t.getMessage(), Toast.LENGTH_LONG).show();

    }

    private File createImageFile() throws IOException {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        currentPhotoPath = image.getAbsolutePath();
//        return image;


        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String prefix = "JPEG_" + timeStamp + "_";
        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                prefix,
                ".jpg",
                directory
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;

    }

    public void showImage(){

        String imageUri = userEntity.getURLImagenArchivo();

        ImageView avatar = (ImageView) findViewById(R.id.avatar);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
