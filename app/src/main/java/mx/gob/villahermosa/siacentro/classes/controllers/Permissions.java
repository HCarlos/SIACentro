package mx.gob.villahermosa.siacentro.classes.controllers;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class Permissions {

    private final Context context;
    private final Activity activity;

    public Permissions(Activity __activity, Context __context) {
        context = __context;
        activity = __activity;
    }

    public Boolean chechPermission(Activity _activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if (
                _activity.checkSelfPermission(INTERNET) == PackageManager.PERMISSION_DENIED ||
                _activity.checkSelfPermission(CAMERA) == PackageManager.PERMISSION_DENIED
            ){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.requestStoragePermission();
                }
                return false;
            }else{
                return true;
            }
        }
        // OJO: en el 'activity' de donde viene, debe estar sobre escrito el método 'onRequestPermissionsResult'
        return true;
    }

    public Boolean chechPermisoLatLong(Activity _activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if (
                _activity.checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
            ){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.requestStoragePermission();
                }
                return false;
            }else{
                return true;
            }
        }
        // OJO: en el 'activity' de donde viene, debe estar sobre escrito el método ' onRequestPermissionsResult'
        return true;
    }

    public String getDataPhone(Activity activity, Context context) {
//        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                requestStoragePermission();
//            }
//            return "";
//        }
//        String mPhoneNumber = tMgr.getLine1Number();
//        return mPhoneNumber;
        return ""; //mPhoneNumber;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void requestStoragePermission(){
        ActivityCompat.requestPermissions(activity
                ,new String[]{INTERNET, CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_NETWORK_STATE, READ_PHONE_NUMBERS, READ_PHONE_STATE},1234);
    }




}
