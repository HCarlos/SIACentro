package mx.gob.villahermosa.siacentro.classes.others;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import mx.gob.villahermosa.siacentro.R;

public class Funciones {

    public String base64String(Bitmap bitmap){
        System.gc();  //For memory efficiency
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
        byte[] bb = bos.toByteArray();
        return Base64.encodeToString(bb, Base64.DEFAULT);
    }

}
