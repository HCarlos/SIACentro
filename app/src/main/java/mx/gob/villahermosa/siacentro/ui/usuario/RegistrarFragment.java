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
import android.widget.EditText;
import android.widget.ProgressBar;

import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.Singleton;
import mx.gob.villahermosa.siacentro.classes.connections.Registry;
import mx.gob.villahermosa.siacentro.classes.responses.RegistryResponse;
import mx.gob.villahermosa.siacentro.classes.databases.UserDB;
import mx.gob.villahermosa.siacentro.classes.others.SiacDialogBasicFragment;
import mx.gob.villahermosa.siacentro.data.adapters.ApiAdapter;
import mx.gob.villahermosa.siacentro.databinding.FragmentRegistrarBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarFragment extends Fragment implements Callback<RegistryResponse> {

    private FragmentRegistrarBinding binding;
    public Context context;
    public FragmentManager fragmentManager;
//    private FragmentKt NavigationHostFragment;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        String param1 = this.getArguments().getString("param1");
//        String param2 = this.getArguments().getString("param2");
//        Toast.makeText(context, param1+" "+param2, Toast.LENGTH_SHORT).show();

        binding = FragmentRegistrarBinding.inflate(inflater, container, false);

        context = getContext();

        return binding.getRoot();

    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.context = view.getContext(); // requireActivity().getApplicationContext();
        UserDB.get(this.context);

        this.context = requireActivity().getApplicationContext();
        this.fragmentManager = requireActivity().getSupportFragmentManager();

        final EditText txtUsername = binding.username;
        final EditText txtAp_Paterno = binding.apPaterno;
        final EditText txtAp_Materno = binding.apMaterno;
        final EditText txtNombre = binding.nombre;
        final EditText txtEmail = binding.email;
        final EditText txtDomicilio = binding.domicilio;

        final Button btnRegistrar = binding.registry;
        final ProgressBar loadingProgressBar = binding.loading;

        btnRegistrar.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            btnRegistrar.setEnabled(false);
            Log.d("RegistroOk", "Falle en: ");

//            throw new RuntimeException("Test Crash"); // Force a crash
            registry(
                    txtUsername.getText().toString(),
                    txtAp_Paterno.getText().toString(),
                    txtAp_Materno.getText().toString(),
                    txtNombre.getText().toString(),
                    txtEmail.getText().toString(),
                    txtDomicilio.getText().toString()
            );

        });

//        Button crashButton = new Button(this.context);
//        crashButton.setText("Test Crash");
//        crashButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                throw new RuntimeException("Test Crash"); // Force a crash
//            }
//        });
//
//        self.context.addContentView(crashButton, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));



    }

    public void registry(String username, String ap_paterno, String ap_materno, String nombre, String email, String domicilio) {
        Call<RegistryResponse> call = ApiAdapter.getApiService().setRegistry(username, ap_paterno, ap_materno, nombre, email, domicilio, Singleton.getCombo_Id(),"ANDROID",Singleton.getDeviceToken());
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<RegistryResponse> call, Response<RegistryResponse> response) {
        if (response.body() != null) {

            RegistryResponse registry_response = response.body();
            Registry.setRegistry_response(registry_response);
            int status = registry_response.getStatus();
            String msg = registry_response.getMsg();
            if (status == 0 ){
                Log.d("RegistroOk", "Falle en: " + msg);
                onErrorResponse(msg);
            }else{
                Log.d("RegistroOk", msg);
                FragmentKt.findNavController(this).navigate(R.id.nav_home);
            }
        }

    }

    @Override
    public void onFailure(@NonNull Call<RegistryResponse> call, Throwable t) {
        final ProgressBar loadingProgressBar = binding.loading;
        loadingProgressBar.setVisibility(View.VISIBLE);
        loadingProgressBar.setEnabled(true);
        onErrorResponse(t.getMessage());

    }

    private void onErrorResponse (String errosStream){
        final ProgressBar loadingProgressBar = binding.loading;
        loadingProgressBar.setVisibility(View.INVISIBLE);
        loadingProgressBar.setEnabled(true);
        binding.registry.setEnabled(true);

        Log.d("ERROROK",errosStream);
        FragmentManager fm = this.fragmentManager;
        SiacDialogBasicFragment editNameDialogFragment = new SiacDialogBasicFragment(errosStream);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

}