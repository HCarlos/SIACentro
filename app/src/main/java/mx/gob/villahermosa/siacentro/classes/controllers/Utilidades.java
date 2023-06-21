package mx.gob.villahermosa.siacentro.classes.controllers;
import static mx.gob.villahermosa.siacentro.classes.databases.UserDB.context;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;

import android.provider.OpenableColumns;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.Objects;

import mx.gob.villahermosa.siacentro.classes.Singleton;

public class Utilidades {

    private static final String TAG = "RESPUESTA";
    private final ProgressDialog pDialog;
    @SuppressLint("StaticFieldLeak")
    private static Activity activity;
    private static LocationManager lm;
    private static int tipo;
    private static double current_altitud = 0.0;
    private static final int SELECT_PICTURE = 2;

    public Utilidades(ProgressDialog pDialog) {
        this.pDialog = pDialog;
    }

    public void showDialog() {
        if (!pDialog.isShowing())
            this.pDialog.show();
    }

    public void hideDialog() {
        if (pDialog.isShowing())
            this.pDialog.dismiss();
    }

    public static void GetSMSData(Activity _activity, LocationManager _lm, int _tipo) {

        activity = _activity;
        lm = _lm;
        tipo = _tipo;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS},
                    123);
        } else {
            if (tipo == 1) {
                getSMSData();
            }
        }
    }

    public static void GetCamera(Activity _activity, int _tipo) {

        activity = _activity;
        Context context = activity.getApplicationContext();
        tipo = _tipo;
//        Singleton.setIsCameraPresent(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                    123);
        }

    }

    @SuppressLint("MissingPermission")
    public static void getSMSData() {
//        TelephonyManager tManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        TelephonyManager tManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
//        Singleton.setNombre(tManager.getSimOperatorName());
//        Singleton.setCelular(tManager.getLine1Number());
    }

    public static void setFoto(Activity activity) {
        try {
            Log.e("ENTRO", "SI");
            Intent intent;
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            int code = 1;
            activity.startActivityForResult(intent, code);
        } catch (Exception e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isNetworkConnected(Activity _activity) {
        activity = _activity;
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null;
    }


    @SuppressLint("Range")
    public static String getFilenameFromUri(Activity activity, Uri uri) {

        Log.e("URI: ",uri.getScheme());

        String result;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
//                    result = null;
                    cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                Objects.requireNonNull(cursor).close();
            }
        }
        result = uri.getPath();
        int cut = result.lastIndexOf('/');
        if (cut != -1) {
            result = result.substring(cut + 1);
        }
        return result;
    }


    public static String getfileExtensionFromUri(Activity activity, Uri uri) {
        String extension;
        ContentResolver contentResolver = activity.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        extension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        return extension;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }
        return phrase.toString();
    }

    public static void getLocation(Activity activity, Context context){
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
            .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Singleton.setLatitude(location.getLatitude());
                        Singleton.setLongitude(location.getLongitude());
                        //Toast.makeText(context, "Latitud : " + Singleton.getLatitude() + ",\n Longitud : " + Singleton.getLongitude(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }



}