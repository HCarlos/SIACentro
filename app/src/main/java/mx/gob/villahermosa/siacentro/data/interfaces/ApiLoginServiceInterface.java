package mx.gob.villahermosa.siacentro.data.interfaces;

import java.util.List;

import mx.gob.villahermosa.siacentro.classes.Usuario;
import mx.gob.villahermosa.siacentro.classes.responses.ComboResponse;
import mx.gob.villahermosa.siacentro.classes.responses.RegistryResponse;
import mx.gob.villahermosa.siacentro.classes.responses.UsuarioResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiLoginServiceInterface {

//    @GET("diseases")
//    Call<DiseasesResponse> getDiseases();
//
//    @FormUrlEncoded
//    @POST("upload/photo")
//    Call<SimpleResponse> postPhoto(
//            @Field("image") String base64,
//            @Field("extension") String extension,
//            @Field("user_id") String user_id
//    );
//
    @FormUrlEncoded
    @POST("login")
    Call<UsuarioResponse> getLogin(
            @Field("username") String username,
            @Field("password") String password,
            @Field("deviceToken") String deviceToken,
            @Field("device_name") String marca
    );

    @FormUrlEncoded
    @POST("register")
    Call<RegistryResponse> setRegistry(
            @Field("curp") String username,
            @Field("ap_paterno") String ap_paterno,
            @Field("ap_materno") String ap_mterno,
            @Field("nombre") String nombre,
            @Field("email") String email,
            @Field("domicilio") String domicilio,
            @Field("genero") Integer genero,
            @Field("device_name") String device_name,
            @Field("deviceToken") String deviceToken

    );

    @Headers({"application-id: MY-APPLICATION-ID",
            "secret-key: MY-SECRET-KEY",
            "application-type: REST"})
    @GET("Music")
    Call<List<Usuario>> getMusicList();


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("user")
    Call<Usuario> getUser(
            @Query("id") Integer id,
            @Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("user/image")
    @Headers("Accept: application/json")
    Call<ComboResponse> setAvatar(
            @Header("Authorization") String autoriza,
            @Field("photo") String image_user,
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("user/change/password")
    @Headers("Accept: application/json")
    Call<ComboResponse> setChangePassword(
            @Header("Authorization") String autoriza,
            @Field("password_actual") String password_actual,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Field("user_id") int user_id
    );




}
