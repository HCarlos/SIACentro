package mx.gob.villahermosa.siacentro.ui.home;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.databases.UserDB;
import mx.gob.villahermosa.siacentro.classes.databases.UserEntity;
import mx.gob.villahermosa.siacentro.classes.interfases.ActualizarDemandasInterface;
import mx.gob.villahermosa.siacentro.classes.responses.DenunciasHeaderResponse;
import mx.gob.villahermosa.siacentro.classes.responses.DenunciasResponse;
import mx.gob.villahermosa.siacentro.data.adapters.ApiDenunciaAdapter;
import mx.gob.villahermosa.siacentro.data.adapters.MisDenunciasAdapter;
import mx.gob.villahermosa.siacentro.databinding.FragmentHomeBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements Callback<DenunciasHeaderResponse>, ActualizarDemandasInterface {

    private List<DenunciasResponse> denuncias_response;
    private FragmentHomeBinding binding;
    private UserEntity userEntity;
    private Context context;
    public FloatingActionButton fab;
    private View root;
    private RecyclerView rvDenuncia;



    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        fab = requireActivity().findViewById(R.id.fab);

        return root;
    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.context = view.getContext();
        UserDB.get(this.context);

        fab = requireActivity().findViewById(R.id.fab);

        this.mostrar_usuario_logeado(this.context);

    }



    public void mostrar_usuario_logeado(Context context){

        userEntity = UserDB.getUserFromId(1);
        LinearLayout ll1 = binding.lnHrz;
        RelativeLayout scv1 = binding.scroll1;
        if  (userEntity != null) {

            ll1.setVisibility(View.VISIBLE);
            scv1.setVisibility(View.VISIBLE);

            LinearLayout llSinLogueo = binding.llSinLogueo;
            llSinLogueo.setVisibility(View.INVISIBLE);

            if ( fab != null ) fab.setVisibility(View.VISIBLE);

            obtenerDenuncias();


        }else{

            ll1.setVisibility(View.INVISIBLE);
            scv1.setVisibility(View.INVISIBLE);

            LinearLayout llSinLogueo = binding.llSinLogueo;
            llSinLogueo.setVisibility(View.VISIBLE);

            Button btnIngresar1 = binding.btnIngresar1;
            Button btnRegistry1 = binding.btnRegistry1;

            btnIngresar1.setOnClickListener(v -> {
                onClickIngresar1();
            });

            btnRegistry1.setOnClickListener(v -> {
                assert getParentFragment() != null;
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.nav_registar);
            });

            if (  fab != null ) fab.setVisibility(View.VISIBLE);

        }

    }

    public void onClickIngresar1() {
        assert getParentFragment() != null;
        FragmentKt.findNavController(getParentFragment()).navigate(R.id.nav_ingresar);

    }


    public void obtenerDenuncias(){
        userEntity = UserDB.getUserFromId(1);
        String autoriza = userEntity.getToken();
        int user_id = userEntity.getUser_id();

        Call<DenunciasHeaderResponse> call = ApiDenunciaAdapter.getApiService().getDenuncias(
                "Bearer " + autoriza,
                user_id
        );
        call.enqueue(this);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResponse(@NonNull Call<DenunciasHeaderResponse> call, Response<DenunciasHeaderResponse> response) {

        if (response.isSuccessful()) {

            DenunciasHeaderResponse denuncia_header_response = response.body();
            assert denuncia_header_response != null;
//            int status = denuncia_header_response.getStatus();

            denuncias_response = denuncia_header_response.getDenuncias();

            LlenarDatos();

        }

    }

    @Override
    public void onFailure(@NonNull Call<DenunciasHeaderResponse> call, Throwable t) {
        Toast.makeText(context, "Código de Error => A9510 : " + t.getMessage(), Toast.LENGTH_LONG).show();
    }

    public void LlenarDatos(){
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(VERTICAL);
        MisDenunciasAdapter adapter = new MisDenunciasAdapter(this.getActivity(),context,denuncias_response);

        rvDenuncia = requireActivity().findViewById(R.id.rvMisDenuncias);
        rvDenuncia.setLayoutManager(llm);
        rvDenuncia.setAdapter(adapter);
    }

    @Override
    public void actualizarDemandas() {
        obtenerDenuncias();
    }

}