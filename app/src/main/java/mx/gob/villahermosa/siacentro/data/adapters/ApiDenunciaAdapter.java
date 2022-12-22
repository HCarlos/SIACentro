package mx.gob.villahermosa.siacentro.data.adapters;

import mx.gob.villahermosa.siacentro.classes.AppConfig;
import mx.gob.villahermosa.siacentro.data.interfaces.ApiDenunciaServiceInterface;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiDenunciaAdapter {



    private static ApiDenunciaServiceInterface API_SERVICE;

    /**
     * Localhost IP for AVD emulators: 10.0.2.2
     */
    private static final AppConfig Conn = new AppConfig();

    /**
     * Default constructor
     */
    private static final String BASE_URL = Conn.LOGIN;

    public static ApiDenunciaServiceInterface getApiService() {

        // Creamos un interceptor y le indicamos el log level a usar
        final HttpLoggingInterceptor denuncia = new HttpLoggingInterceptor();
        denuncia.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociamos el interceptor a las peticiones
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(denuncia);

        if (API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            API_SERVICE = retrofit.create(ApiDenunciaServiceInterface.class);
        }

        return API_SERVICE;
    }

}
