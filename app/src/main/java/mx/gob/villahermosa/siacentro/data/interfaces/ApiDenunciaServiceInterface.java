package mx.gob.villahermosa.siacentro.data.interfaces;

import mx.gob.villahermosa.siacentro.classes.responses.ComboResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiDenunciaServiceInterface {

    @FormUrlEncoded
    @POST("denuncia/send")
    @Headers("Accept: application/json")
    Call<ComboResponse> denunciaSend(
            @Header("Authorization") String autoriza,
            @Field("imagen") String imagen,
            @Field("descripcion") String descripcion,
            @Field("servicio_id") String servicio_id,
            @Field("servicio") String servicio,
            @Field("latitud") Double latitud,
            @Field("longitud") Double longitud,
            @Field("user_id") int user_id
    );

}
