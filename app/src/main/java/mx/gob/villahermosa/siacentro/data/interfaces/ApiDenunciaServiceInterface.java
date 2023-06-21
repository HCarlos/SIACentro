package mx.gob.villahermosa.siacentro.data.interfaces;

import mx.gob.villahermosa.siacentro.classes.Singleton;
import mx.gob.villahermosa.siacentro.classes.responses.ComboResponse;
import mx.gob.villahermosa.siacentro.classes.responses.DenunciasHeaderResponse;
import mx.gob.villahermosa.siacentro.classes.responses.ImagenesResponse;
import mx.gob.villahermosa.siacentro.classes.responses.RespuestasResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiDenunciaServiceInterface {


    @FormUrlEncoded
    @POST("denuncia/getlist")
    @Headers("Accept: application/json")
    Call<DenunciasHeaderResponse> getDenuncias(
        @Header("Authorization") String autoriza,
        @Field("user_id") Integer user_id
    );

    @FormUrlEncoded
    @POST("denuncia/insert")
    @Headers("Accept: application/json")
    Call<ComboResponse> denunciaSend(
            @Header("Authorization") String autoriza,
            @Field("imagen") String imagen,
            @Field("denuncia") String descripcion,
            @Field("servicio_id") String servicio_id,
            @Field("servicio") String servicio,
            @Field("latitud") Double latitud,
            @Field("longitud") Double longitud,
            @Field("tipo_mobile") String tipo_mobile,
            @Field("marca_mobile") String marca_mobile,
            @Field("ubicacion_id") int ubicacion_id,
            @Field("ubicacion") String ubicacion,
            @Field("ubicacion_google") String ubicacion_google,
            @Field("user_id") int user_id,
            @Field("deviceToken") String deviceToken,
            @Field("device_name") String marca
    );

    @FormUrlEncoded
    @POST("denuncia/add/image")
    @Headers("Accept: application/json")
    Call<ComboResponse> addImageDenunciaSend(
            @Header("Authorization") String autoriza,
            @Field("imagen") String imagen,
            @Field("denunciamobile_id") int denunciamobile_id,
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("denuncia/add/respuesta")
    @Headers("Accept: application/json")
    Call<ComboResponse> addRespuestaDenunciaSend(
            @Header("Authorization") String autoriza,
            @Field("respuesta") String imagen,
            @Field("denunciamobile_id") int denunciamobile_id,
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("denuncia/list/imagenes")
    @Headers("Accept: application/json")
    Call<ImagenesResponse> getImagenesFromDenunciaMobile(
            @Header("Authorization") String autoriza,
            @Field("denunciamobile_id") int denunciamobile_id,
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("denuncia/list/respuestas")
    @Headers("Accept: application/json")
    Call<RespuestasResponse> getRespuestasFromDenunciaMobile(
            @Header("Authorization") String autoriza,
            @Field("denunciamobile_id") int denunciamobile_id,
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("denuncia/getimagesdenuncialist")
    @Headers("Accept: application/json")
    Call<ImagenesResponse> getImagesDenunciaList(
            @Header("Authorization") String autoriza,
            @Field("denunciamobile_id") int denunciamobile_id,
            @Field("user_id") Integer user_id
    );



}
