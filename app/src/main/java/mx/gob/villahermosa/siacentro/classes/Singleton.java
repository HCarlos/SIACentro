package mx.gob.villahermosa.siacentro.classes;

import android.net.Uri;

public class Singleton {

    private static String Denuncia = "";
    private static final String UUID = "";
    private static String Device = "";
    private static int Combo_Id = 0;
    private static String Method = "POST";
    private static double Longitude = 0;
    private static double Latitude = 0;
    private static double Altitud = 0;
    private static String DireccionGoogle = "";
    private static final String pathFile = null;
    private static Uri UriData = null;
    public static final String access_token = "";

    private static Singleton ourInstance = new Singleton();
    static Singleton getInstance() {
        return ourInstance;
    }
    public Singleton() { }

    public static String getDenuncia() {
        return Denuncia;
    }
    public static void setDenuncia(String denuncia) {
        Denuncia = denuncia;
    }

    public static String getUUID() { return UUID; }
    public static void setUUID(String UUID) { UUID = UUID; }

    public static String getDevice() { return Device; }
    public static void setDevice(String device) { Device = device;}

    public static String getMethod() { return Method; }
    public static void setMethod(String method) { Method = method; }

    public static int getCombo_Id() {
        return Combo_Id;
    }
    public static void setCombo_Id(int combo_Id) {
        Combo_Id = combo_Id;
    }

    public static double getLongitude() {
        return Longitude;
    }
    public static void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public static double getLatitude() {
        return Latitude;
    }
    public static void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public static double getAltitud() {
        return Altitud;
    }
    public static void setAltitud(double altitud) {
        Altitud = altitud;
    }

    public static String getDireccionGoogle() {
        return DireccionGoogle;
    }
    public static void setDireccionGoogle(String direccionGoogle) { DireccionGoogle = direccionGoogle;}

    public static String getPathFile() {
        return pathFile;
    }
    public static void setPathFile(String pathFile) {
        pathFile = pathFile;
    }

    public static Uri getUriData() {
        return UriData;
    }
    public static void setUriData(Uri uriData) {
        UriData = uriData;
    }

    public static void reset() { ourInstance = new Singleton(); }


}
