package mx.gob.villahermosa.siacentro.data.adapters;

import java.util.concurrent.TimeUnit;

import mx.gob.villahermosa.siacentro.classes.AppConfig;
import mx.gob.villahermosa.siacentro.data.interfaces.ApiLoginServiceInterface;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapter {


    private static ApiLoginServiceInterface API_SERVICE;

    /**
     * Localhost IP for AVD emulators: 10.0.2.2
     */
    private static final AppConfig Conn = new AppConfig();

    /**
     * Default constructor
     */
    private static final String BASE_URL = AppConfig.LOGIN;

    public static ApiLoginServiceInterface getApiService() {

        // Creamos un interceptor y le indicamos el log level a usar
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociamos el interceptor a las peticiones
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        if (API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            API_SERVICE = retrofit.create(ApiLoginServiceInterface.class);
        }

        return API_SERVICE;
    }


}
