package mx.gob.villahermosa.siacentro.ui.usuario;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.FragmentKt;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.Singleton;
import mx.gob.villahermosa.siacentro.classes.Usuario;
import mx.gob.villahermosa.siacentro.classes.databases.UserDB;
import mx.gob.villahermosa.siacentro.classes.databases.UserEntity;
import mx.gob.villahermosa.siacentro.classes.interfases.VolleyTaskListener;
import mx.gob.villahermosa.siacentro.classes.others.SiacDialogBasicFragment;
import mx.gob.villahermosa.siacentro.classes.responses.UsuarioResponse;
import mx.gob.villahermosa.siacentro.classes.volley.LoginVolleyTaskListener;
import mx.gob.villahermosa.siacentro.data.adapters.ApiAdapter;
import mx.gob.villahermosa.siacentro.databinding.FragmentIngresarBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngresarFragment extends Fragment implements Callback<UsuarioResponse>, VolleyTaskListener{

    public UserEntity userEntity;
    private FragmentIngresarBinding binding;
    public Context context;
    public FragmentManager fragmentManager;
//    private FragmentKt NavigationHostFragment;
    private String Username;
    private String Password;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentIngresarBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.context = view.getContext(); // requireActivity().getApplicationContext();
        UserDB.get(this.context);

        this.context = requireActivity().getApplicationContext();
        this.fragmentManager = requireActivity().getSupportFragmentManager();

        final Button btnIngresar = binding.login;
        final Button btnCerrarSession = binding.logout;
        final ProgressBar loadingProgressBar = binding.loading;
        evalLogin();


        btnIngresar.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            btnIngresar.setEnabled(false);
            login();

//            throw new IllegalStateException();
        });

        btnCerrarSession.setOnClickListener(v -> {
            userEntity = UserDB.getUserFromId(1);
            FloatingActionButton fab = requireActivity().findViewById(R.id.fab);
            fab.setVisibility(View.GONE);
//            requireActivity().getMenuInflater().inflate(R.id.menuProfile);

//            if (userEntity != null) {
                UserDB.removeUser(userEntity);
                evalLogin();
//            }
        });



    }

    public void evalLogin(){

        final LinearLayout llSinLogear = binding.SinLoguear;
        final LinearLayout llLogueada = binding.Logueada;

        this.userEntity = UserDB.getUserFromId(1);

        if (this.userEntity != null){
            llSinLogear.setVisibility(View.GONE);
            llLogueada.setVisibility(View.VISIBLE);
            showDataUserLoged();
        }else{
            llSinLogear.setVisibility(View.VISIBLE);
            llLogueada.setVisibility(View.GONE);
        }

    }

    public void login() {

        Username = binding.username.getText().toString();
        Password = binding.password.getText().toString();

        String deviceToken = Singleton.getDeviceToken();
        String marca = "ANDROID";

        Call<UsuarioResponse> call = ApiAdapter.getApiService().getLogin(Username,Password,deviceToken,marca);
        call.enqueue(this);

    }

    @Override
    public void onResponse(@NonNull Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
        if (response.isSuccessful()) {
            try {
                UsuarioResponse usuario_response = response.body();
                assert usuario_response != null;

                Usuario.setUser_response(usuario_response);
                Usuario.setUser(usuario_response.getUser());

                onSuccess();

            }catch (Exception e) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else{
            Log.d("FalloLogin", "No hubo respuesta..." + response );
            Toast.makeText(context, "No hubo respuesta... " + Singleton.getDeviceToken(), Toast.LENGTH_SHORT).show();
        }
    }
        public void onFailure (@NonNull Call < UsuarioResponse > call, Throwable t){
            binding.loading.setVisibility(View.INVISIBLE);
            binding.login.setEnabled(true);
            Toast.makeText(context, "Failure " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }

        public void onSuccess () {
            String welcome;
            if (Usuario.getUser_response() != null) {
                if (Usuario.getUser_response().getStatus() == 0) {
                    welcome = Usuario.getUser_response().getMsg();
                    onErrorResponse(welcome);
                } else if (Usuario.getUser_response().getStatus() == 1) {
                    UserDB.get(context);
                    userEntity = UserDB.getUserFromId(1);
                    if (userEntity != null) {
                        UserDB.removeUser(userEntity);
                        UserDB.InsertUserEntity();
                    } else {
                        UserDB.InsertUserEntity();
                    }
                    userEntity = UserDB.getUserFromId(1);
                    onSuccesResponse();
                }
                binding.loading.setVisibility(View.GONE);
                binding.login.setEnabled(true);
            }else{
                Toast.makeText(context, "No se reicibió el cuerpo del mensaje. " , Toast.LENGTH_SHORT).show();
            }

        }

        private void onSuccesResponse (){

            final FloatingActionButton fab = binding.getRoot().findViewById(R.id.fab);
            if ( fab != null ) fab.setVisibility(View.VISIBLE);

            FragmentKt.findNavController(this).navigate(R.id.nav_home);
            evalLogin();
        }

        private void onErrorResponse (String errosStream){
            FragmentManager fm = this.fragmentManager;
            SiacDialogBasicFragment editNameDialogFragment = new SiacDialogBasicFragment(errosStream);
            editNameDialogFragment.show(fm, "fragment_edit_name");

            login2();

        }


    private void showDataUserLoged(){
        this.userEntity = UserDB.getUserFromId(1);
        String imageUri = this.userEntity.getURLImagenArchivo()+"?=" + System.currentTimeMillis();
        ImageView ivBasicImage = binding.imgHomeUserFrag;

        int dr = R.drawable.empty_user_female;
        if (this.userEntity.getGenero() == 1){
            dr = R.drawable.empty_user_male;
        }
        Picasso
                .get()
                .load(imageUri)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(dr)
                .error(dr)
                .into(ivBasicImage);

        TextView txtlblUsername = binding.lblFullName;
        String lbl = "Haz iniciado sesión como:";
        txtlblUsername.setText(lbl);

        TextView txtUsername = binding.FullName;
        txtUsername.setText(this.userEntity.getFullName());

        TextView txtCURP = binding.CURP;
        txtCURP.setText(this.userEntity.getCurp());

    }

    public void login2() {

        Username = binding.username.getText().toString();
        Password = binding.password.getText().toString();

        LoginVolleyTaskListener login = new LoginVolleyTaskListener(getActivity(), binding.getRoot().getContext(), this);
        login.Login(Username, Password);

    }

    @Override
    public void goBackActivity() {
        binding.loading.setVisibility(View.INVISIBLE);
        binding.login.setEnabled(true);

        onSuccess();

    }


}