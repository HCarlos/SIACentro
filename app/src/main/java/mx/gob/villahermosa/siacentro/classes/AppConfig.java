package mx.gob.villahermosa.siacentro.classes;

public class AppConfig {


    public static String TEXT_HTML = "text/html";
    public static String UTF_8 = "UTF-8";

//    public static String URL_HOME_API = "http://10.0.2.2:8000/api/v1/";
//    public static String URL_HOME = "https://servimun.mx/api/v1/";

    public static String URL_HOME = "https://servimun.mx/";
    public static String URL_HOME_API = "https://servimun.mx/api/v1/";

    public static String LOGIN = URL_HOME_API;
    public static String LOGOUT = URL_HOME_API + "logout";

    public static String LOGIN_VOLLEY = URL_HOME_API + "login";

    public static String URL_ABOUT = URL_HOME + "about_app";

    public static String URL_AVISO_PRIVACIDAD = URL_HOME + "aviso_app";

}
