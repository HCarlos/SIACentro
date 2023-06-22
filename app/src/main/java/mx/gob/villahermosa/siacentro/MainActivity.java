package mx.gob.villahermosa.siacentro;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import mx.gob.villahermosa.siacentro.classes.Singleton;
import mx.gob.villahermosa.siacentro.classes.controllers.Permissions;
import mx.gob.villahermosa.siacentro.classes.controllers.Utilidades;
import mx.gob.villahermosa.siacentro.classes.databases.UserDB;
import mx.gob.villahermosa.siacentro.classes.databases.UserEntity;
import mx.gob.villahermosa.siacentro.databinding.ActivityMainBinding;
import mx.gob.villahermosa.siacentro.ui.home.DenunciaActivity;
import mx.gob.villahermosa.siacentro.ui.usuario.ChangeAvatarActivity;
import mx.gob.villahermosa.siacentro.ui.usuario.ChangePasswordActivity;
import mx.gob.villahermosa.siacentro.ui.usuario.ProfileActivity;


public class MainActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    public Context context;
    public NavController navController;
    public FloatingActionButton fab;
    public UserEntity userentity;



    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mx.gob.villahermosa.siacentro.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_ingresar, R.id.nav_registar, R.id.nav_about, R.id.nav_aviso)
                .setOpenableLayout(drawer)
                .build();


        userentity = UserDB.getUserFromId(1);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Bundle bundle = new Bundle();
        bundle.putString("param1", "Hola");
        bundle.putString("param2", "Mundo");

        fab = findViewById(R.id.fab);
        if (userentity == null){
            fab.setVisibility(View.INVISIBLE);
        }

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            fab.setVisibility(View.INVISIBLE);
            int id=menuItem.getItemId();
            switch (id) {
                case R.id.nav_home:
                    if (userentity != null) {
                        fab.setVisibility(View.VISIBLE);
                    }
                    navController.navigate(R.id.nav_home);
                    break;
                case R.id.nav_ingresar:
                    if (userentity != null) {
                        fab.setVisibility(View.VISIBLE);
                    }
                    navController.navigate(R.id.nav_ingresar);
                    break;
                case R.id.nav_registar:
                    navController.navigate(R.id.nav_registar, bundle);
                    break;
                case R.id.nav_about:
                    navController.navigate(R.id.nav_about);
                    break;
                case R.id.nav_aviso:
                    navController.navigate(R.id.nav_aviso);
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        //                Snackbar.make(v, "Here's a Snackbar", Snackbar.LENGTH_LONG)
        //                        .setAction("Action", null).show();
        fab.setOnClickListener(this::getServicios);




        Permissions permisos = new Permissions(this, getApplicationContext());
        if ( permisos.chechPermisoLatLong(this) ){

            Utilidades.getLocation(this, getApplicationContext());

        }else{
            Toast.makeText(getApplicationContext(), "No se ha dado permiso a la Geolocalización", Toast.LENGTH_SHORT).show();
        }

        if ( !permisos.chechPermission(this) ){
            Toast.makeText(getApplicationContext(), "No se ha dado permisos de acceso a la Cámara o Internet.", Toast.LENGTH_SHORT).show();
        }

        askNotificationPermission();


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();

                    Singleton.setDeviceToken(token);

                    // Log and toast
                    //String msg = getString(R.string.msg_token_fmt, token);
                    Log.d(TAG, token);
                    // Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                });


    }


    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED || shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }


    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });



    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (userentity!= null) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.opt_profile) {
            Intent intdn = new Intent(this, ProfileActivity.class);
            startActivity(intdn);
        }else if (id == R.id.opt_change_password) {
            Intent intdn = new Intent(this, ChangePasswordActivity.class);
            intdn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intdn);
        }else if (id == R.id.opt_change_avatar) {
            Intent intdn = new Intent(this, ChangeAvatarActivity.class);
            intdn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intdn);
        }else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        int id = view.getId();

        if (id == R.id.radio_varon) {
            if (checked)
                Singleton.setCombo_Id(1);
        }else if (id == R.id.radio_hembra) {
            if (checked)
                Singleton.setCombo_Id(0);
        }

    }

    public void getServicios(View view){
        PopupMenu popup = new PopupMenu(MainActivity.this, fab);
        popup.getMenuInflater().inflate(R.menu.popup_servicios, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            procesaMenu(item);
        return true;
        });
        popup.show();//showing popup menu
    }

    public void procesaMenu(MenuItem item){
//        switch (item.getItemId()) {
//            default:
                Intent i = new Intent(this, DenunciaActivity.class);
                String id = String.valueOf(item.getItemId());
                String servicio = (String) item.getTitle();
                Bundle bundle = new Bundle();
                bundle.putString("menu_id", id);
                bundle.putString("menu_title", servicio);
                i.putExtras(bundle);
                startActivity(i);
        //        }
    }




}