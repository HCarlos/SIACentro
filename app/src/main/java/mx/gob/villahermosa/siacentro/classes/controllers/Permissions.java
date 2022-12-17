package mx.gob.villahermosa.siacentro.classes.controllers;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.MANAGE_DOCUMENTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static androidx.core.content.PermissionChecker.PERMISSION_DENIED;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permissions {

    private Context context;
    private Activity activity;

    public Permissions(Activity __activity, Context __context) {
        context = __context;
        activity = __activity;
    }

    public Boolean chechPermission(Activity _activity) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if (
                _activity.checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED ||
                _activity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                _activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
            ){
                this.requestStoragePermission();
//                Toast.makeText(activity, "No ha autorizado utilizar la cámara ó guardar imaganes en su teléfono.", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                return true;
            }
        }
        // OJO: en el 'activity' de donde viene, debe estar sobre escrito el método ' onRequestPermissionsResult'
        return true;
    }

    public void requestStoragePermission(){
        ActivityCompat.requestPermissions(activity
                ,new String[]{INTERNET, CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_NETWORK_STATE},1234);
    }


    
}
