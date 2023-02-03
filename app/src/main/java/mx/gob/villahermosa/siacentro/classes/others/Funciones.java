package mx.gob.villahermosa.siacentro.classes.others;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.util.Base64;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;

public class Funciones {

    public String base64String(Bitmap bitmap) {
        System.gc();  //For memory efficiency
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bb = bos.toByteArray();
        return Base64.encodeToString(bb, Base64.DEFAULT);
    }

    public String getCompleteAddressString(Context context, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("ERROR_ADDRESS", strReturnedAddress.toString());
            } else {
                Log.w("ERROR_ADDRESS", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("ERROR_ADDRESS", "Canont get Address!");
        }
        return strAdd;
    }



}
