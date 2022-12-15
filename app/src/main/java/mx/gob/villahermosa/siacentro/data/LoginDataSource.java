package mx.gob.villahermosa.siacentro.data;

import android.util.Log;
import android.view.View;

import mx.gob.villahermosa.siacentro.classes.Usuario;
import mx.gob.villahermosa.siacentro.classes.responses.UsuarioResponse;
import mx.gob.villahermosa.siacentro.data.adapters.ApiAdapter;
import mx.gob.villahermosa.siacentro.databinding.FragmentIngresarBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource implements Callback<UsuarioResponse> {
    private FragmentIngresarBinding fmIngre;
    public Result login(String username, String password) {
        Call<UsuarioResponse> call = ApiAdapter.getApiService().getLogin(username,password);
        call.enqueue(this);
        return new Result.Success<Usuario>();
    }

    public void login2(String username, String password, FragmentIngresarBinding fmIngre) {
        this.fmIngre = fmIngre;
        login(username, password);
        Call<UsuarioResponse> call = ApiAdapter.getApiService().getLogin(username,password);
        call.enqueue(this);
//        return true;
    }


    public void logout() {
        // TODO: revoke authentication
    }

    @Override
    public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
        if (response.isSuccessful()){

            UsuarioResponse usuario_response = response.body();
            assert usuario_response != null;
            int status = usuario_response.getStatus();

            Usuario.setUser_response(usuario_response);
            Usuario.setUser(usuario_response.getUser());

            Log.w("STATUS", String.valueOf(status));
            if (status == 1) {
                Log.d("Login_Message", Usuario.getUser().getUsername() + " se Logueado con: " + Usuario.getUser_response().getAccess_token());
            }else{
                Log.d("Login_Message", "Error: " + Usuario.getUser_response().getMsg());
            }

        }
    }

    @Override
    public void onFailure(Call<UsuarioResponse> call, Throwable t) {
        fmIngre.loading.setVisibility(View.INVISIBLE);
        fmIngre.login.setEnabled(true);
        Log.d("FallaUsuario", "tamaño de usuario --->   " + t.getMessage());

    }
}