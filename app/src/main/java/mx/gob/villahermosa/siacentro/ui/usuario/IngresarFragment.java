package mx.gob.villahermosa.siacentro.ui.usuario;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentKt;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.ui.NavigationUiSaveStateControl;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import mx.gob.villahermosa.siacentro.MainActivity;
import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.Usuario;
import mx.gob.villahermosa.siacentro.classes.databases.UserDB;
import mx.gob.villahermosa.siacentro.classes.databases.UserEntity;
import mx.gob.villahermosa.siacentro.classes.others.SiacDialogBasicFragment;
import mx.gob.villahermosa.siacentro.classes.responses.UsuarioResponse;
import mx.gob.villahermosa.siacentro.data.LoginDataSource;
import mx.gob.villahermosa.siacentro.data.adapters.ApiAdapter;
import mx.gob.villahermosa.siacentro.databinding.FragmentIngresarBinding;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngresarFragment extends Fragment implements Callback<UsuarioResponse> {

    private UserEntity userEntity = UserDB.getUserFromId(1);
    private FragmentIngresarBinding binding;
    public Context context;
    public FragmentManager fragmentManager;
    private FragmentKt NavigationHostFragment;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentIngresarBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        return root;

    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.context = view.getContext(); // requireActivity().getApplicationContext();
        UserDB.get(this.context);

        this.context = requireActivity().getApplicationContext();
        this.fragmentManager = requireActivity().getSupportFragmentManager();

        final EditText txtUsername = binding.username;
        final EditText txtPassword = binding.password;
        final Button btnIngresar = binding.login;
        final Button btnCerrarSession = binding.logout;
        final ProgressBar loadingProgressBar = binding.loading;
        final LinearLayout llSinLogear = binding.SinLoguear;
        final LinearLayout llLogueada = binding.Logueada;
        evalLogin();


        // Valida la entrada de datos por teclado
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
//                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
            }
        };
        txtUsername.addTextChangedListener(afterTextChangedListener);
        txtPassword.addTextChangedListener(afterTextChangedListener);
        btnIngresar.addTextChangedListener(afterTextChangedListener);


        txtPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login( txtUsername.getText().toString(), txtPassword.getText().toString());
            }
            return false;
        });

        btnIngresar.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            btnIngresar.setEnabled(false);
            login( txtUsername.getText().toString(), txtPassword.getText().toString() );
        });

        btnCerrarSession.setOnClickListener(v -> {
            this.userEntity = UserDB.getUserFromId(1);
            if (this.userEntity != null) {
                UserDB.removeUser(this.userEntity);
//                UserDB.InsertUserEntity();
                evalLogin();
            }
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
//        Toast.makeText(context.getApplicationContext(), "entro : ", Toast.LENGTH_SHORT).show();

    }

    public void login(String username, String password) {
        Call<UsuarioResponse> call = ApiAdapter.getApiService().getLogin(username,password);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
        if (response.isSuccessful()) {

            UsuarioResponse usuario_response = response.body();
            assert usuario_response != null;
            int status = usuario_response.getStatus();
            Usuario.setUser_response(usuario_response);
            Usuario.setUser(usuario_response.getUser());
            onSuccess();
        }
    }
        public void onFailure (Call < UsuarioResponse > call, Throwable t){
            binding.loading.setVisibility(View.INVISIBLE);
            binding.login.setEnabled(true);
            Log.d("FallaUsuario", "tamaño de usuario --->   " + t.getMessage());

        }

        public void onSuccess () {
            String welcome;
            if (Usuario.getUser_response() != null) {
                if (Usuario.getUser_response().getStatus() == 0) {
                    welcome = Usuario.getUser_response().getMsg();
                    onErrorResponse(welcome);
                } else if (Usuario.getUser_response().getStatus() == 1) {
                    UserDB.get(this.context);
                    UserEntity userEntity = UserDB.getUserFromId(1);
                    if (userEntity != null) {
                        UserDB.removeUser(userEntity);
                        UserDB.InsertUserEntity();
                    } else {
                        UserDB.InsertUserEntity();
                    }
                    userEntity = UserDB.getUserFromId(1);
                    onSuccesResponse(userEntity);
                }
                binding.loading.setVisibility(View.GONE);
                binding.login.setEnabled(true);
            }

        }

        private void onSuccesResponse (UserEntity userEntity){
            String msg = "";
            if (userEntity.getGenero() == 0) {
                msg = "Bienvenida";
            } else {
                msg = "Bienvenido";
            }
            NavigationHostFragment.findNavController(this).navigate(R.id.nav_home);
            evalLogin();
        }

        private void onErrorResponse (String errosStream){
            FragmentManager fm = this.fragmentManager;
            SiacDialogBasicFragment editNameDialogFragment = new SiacDialogBasicFragment(errosStream);
            editNameDialogFragment.show(fm, "fragment_edit_name");
        }


    private void showDataUserLoged(){
        this.userEntity = UserDB.getUserFromId(1);
        String imageUri = this.userEntity.getURLImagenArchivo()+"?=" + System.currentTimeMillis();
        ImageView ivBasicImage = (ImageView) binding.imgHomeUserFrag;

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


        TextView txtlblUsername = (TextView) binding.lblFullName;
        String lbl = "Haz iniciado sesión como:";
        txtlblUsername.setText(lbl);

        TextView txtUsername = (TextView) binding.FullName;
        txtUsername.setText(this.userEntity.getFullName());

        TextView txtCURP = (TextView) binding.CURP;
        txtCURP.setText(this.userEntity.getCurp());


    }

}