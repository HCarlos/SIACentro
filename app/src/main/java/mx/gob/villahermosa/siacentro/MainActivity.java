package mx.gob.villahermosa.siacentro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.Toast;
//import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import mx.gob.villahermosa.siacentro.classes.Singleton;
import mx.gob.villahermosa.siacentro.classes.controllers.GPSTracker;
import mx.gob.villahermosa.siacentro.classes.controllers.Permissions;
import mx.gob.villahermosa.siacentro.classes.controllers.PhotoUtils;
import mx.gob.villahermosa.siacentro.classes.databases.UserDB;
import mx.gob.villahermosa.siacentro.classes.databases.UserEntity;
import mx.gob.villahermosa.siacentro.databinding.ActivityMainBinding;
import mx.gob.villahermosa.siacentro.ui.home.DenunciaActivity;
import mx.gob.villahermosa.siacentro.ui.usuario.ChangeAvatarActivity;
import mx.gob.villahermosa.siacentro.ui.usuario.ChangePasswordActivity;
import mx.gob.villahermosa.siacentro.ui.usuario.ProfileActivity;


public class MainActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public Context context;
    public NavController navController;
    FloatingActionButton fab;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_ingresar, R.id.nav_gallery, R.id.nav_about, R.id.nav_registar)
                .setOpenableLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Bundle bundle = new Bundle();
        bundle.putString("param1", "Hola");
        bundle.putString("param2", "Mundo");

        fab = (FloatingActionButton) findViewById(R.id.fab);

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            fab.setVisibility(View.INVISIBLE);
            int id=menuItem.getItemId();
            switch (id) {
                case R.id.nav_home:
                    navController.navigate(R.id.nav_home);
                    UserEntity userentity = UserDB.getUserFromId(1);
                    if (userentity!= null) {
                        fab.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.nav_ingresar:
                    navController.navigate(R.id.nav_ingresar);
                    break;
                case R.id.nav_registar:
                    navController.navigate(R.id.nav_registar, bundle);
                    break;
                case R.id.nav_gallery:
                    navController.navigate(R.id.nav_gallery);
                    break;
                case R.id.nav_about:
                    navController.navigate(R.id.nav_about);
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(v, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                getServicios(v);

            }
        });

        Permissions permisos = new Permissions(this, getApplicationContext());
        if ( permisos.chechPermission(this) ){
            GPSTracker gps = new GPSTracker(getApplicationContext());
            Location location = gps.getLocation();
            Singleton.setLatitude(location.getLatitude());
            Singleton.setLongitude(location.getLongitude());
            Toast.makeText(getApplicationContext(), "Latitud : "+Singleton.getLatitude() + ", Longitud : " + Singleton.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        UserDB.get(context);

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opt_profile: {
                Intent intdn = new Intent(this, ProfileActivity.class);
//                intdn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intdn);
                return true;
            }
            case R.id.opt_change_password:  {
                Intent intdn = new Intent(this, ChangePasswordActivity.class);
                intdn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intdn);
                return true;
            }
            case R.id.opt_change_avatar:  {
                Intent intdn = new Intent(this, ChangeAvatarActivity.class);
                intdn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intdn);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_varon:
                if (checked)
                    Singleton.setCombo_Id(1);
                break;
            case R.id.radio_hembra:
                if (checked)
                    Singleton.setCombo_Id(0);
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1234:if(grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                // Do_SOme_Operation();
            }
            default:super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    public void getServicios(View view){
        PopupMenu popup = new PopupMenu(MainActivity.this, fab);
        popup.getMenuInflater().inflate(R.menu.popup_servicios, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                procesaMenu(item);
            return true;
            }
        });
        popup.show();//showing popup menu
    }

    public boolean procesaMenu(MenuItem item){
        switch (item.getItemId()) {
            default:
                Intent i = new Intent(this, DenunciaActivity.class);
                String id = (String) String.valueOf(item.getItemId());
                String servicio = (String) item.getTitle();
                Bundle bundle = new Bundle();
                bundle.putString("menu_id", id);
                bundle.putString("menu_title", servicio);
                i.putExtras(bundle);
                startActivity(i);
                return true;
        }
    }



}