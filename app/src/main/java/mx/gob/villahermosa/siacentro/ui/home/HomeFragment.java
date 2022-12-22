package mx.gob.villahermosa.siacentro.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.databases.UserDB;
import mx.gob.villahermosa.siacentro.classes.databases.UserEntity;
import mx.gob.villahermosa.siacentro.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private UserEntity userEntity;
    private Context context;
    private FragmentKt NavigationHostFragment;
    private FloatingActionButton fab;
    private View root;



    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        fab = (FloatingActionButton) root.findViewById(R.id.fab);

        return root;
    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.context = view.getContext();
        UserDB.get(this.context);

        this.mostrar_usuario_logeado(this.context);

        fab = (FloatingActionButton) root.findViewById(R.id.fab);

    }



    public void mostrar_usuario_logeado(Context context){

        context = requireContext().getApplicationContext();
        UserEntity userentity = UserDB.getUserFromId(1);

        if (userentity!= null) {

            this.userEntity = UserDB.getUserFromId(1);

            LinearLayout ll1 = binding.lnHrz;
            ScrollView scv1 = binding.scroll1;
            ll1.setVisibility(View.VISIBLE);
            scv1.setVisibility(View.VISIBLE);

            LinearLayout llSinLogueo = binding.llSinLogueo;
            llSinLogueo.setVisibility(View.INVISIBLE);

            if ( fab != null ) { fab.setVisibility(View.VISIBLE); };


        }else{
            LinearLayout ll1 = binding.lnHrz;
            ScrollView scv1 = binding.scroll1;

            ll1.setVisibility(View.INVISIBLE);
            scv1.setVisibility(View.INVISIBLE);

            LinearLayout llSinLogueo = binding.llSinLogueo;
            llSinLogueo.setVisibility(View.VISIBLE);

            Button btnIngresar1 = (Button) binding.btnIngresar1;
            Button btnRegistry1 = (Button) binding.btnRegistry1;

            Log.e("NAVIGATION", "UNO");

            btnIngresar1.setOnClickListener(v -> {
                Log.e("NAVIGATION", "DOS");
                onClickIngresar1();
                Log.e("NAVIGATION", "TRES");
            });

            btnRegistry1.setOnClickListener(v -> {
                assert getParentFragment() != null;
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.nav_registar);
            });

            if ( fab != null ) { fab.setVisibility(View.VISIBLE); };

        }

    }

    public void onClickIngresar1() {
        assert getParentFragment() != null;
        FragmentKt.findNavController(getParentFragment()).navigate(R.id.nav_ingresar);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}