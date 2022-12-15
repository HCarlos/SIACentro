package mx.gob.villahermosa.siacentro.classes.controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PhotoUtils {

    private static Context mContext;
    private static Activity activity;
    private BitmapFactory.Options generalOptions;
    private static String cameraFilePath;

    private static final int
            PERMISSION_CODE = 1000,
            PERMISSION_CODE_GALLERY = 1001;


    public PhotoUtils(Context context, Activity _activity) {
        mContext = context;
        activity = _activity;
    }

    public static File createTemporaryFile(String part, String ext,
                                           Context myContext) throws IOException {
        cameraFilePath = myContext.getExternalCacheDir().getAbsolutePath();
        File tempDir = new File(cameraFilePath);
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        File image = File.createTempFile(part, ext, tempDir);
//        cameraFilePath = "file://" + image.getAbsolutePath();
        cameraFilePath = image.getAbsolutePath();
        Log.e("PathFileName: ",cameraFilePath);
        return image;
    }

    public static File createImageFile(String part, String ext,
                                       Context myContext) throws IOException {
        cameraFilePath = "content";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "devch_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }
        File image = File.createTempFile(part, ext, storageDir);
        cameraFilePath = "content:/" + image.getAbsolutePath();
        // cameraFilePath = image.getAbsolutePath();
        Log.e("PathFileName: ",cameraFilePath);
        return image;
    }




}
