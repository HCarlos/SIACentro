package mx.gob.villahermosa.siacentro.data.interfaces;

import mx.gob.villahermosa.siacentro.classes.Usuario;
import mx.gob.villahermosa.siacentro.classes.responses.ComboResponse;
import mx.gob.villahermosa.siacentro.classes.responses.DenunciasHeaderResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiDenunciaServiceInterface {

    @FormUrlEncoded
    @POST("denuncia/getlist")
    @Headers("Accept: application/json")
    Call<DenunciasHeaderResponse> getDenuncias(
        @Header("Authorization") String autoriza,
        @Field("user_id") Integer user_id);

    @FormUrlEncoded
    @POST("denuncia/add")
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
            @Field("user_id") int user_id
    );



}
