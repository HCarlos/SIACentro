package mx.gob.villahermosa.siacentro.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.databases.UserDB;
import mx.gob.villahermosa.siacentro.classes.databases.UserEntity;
import mx.gob.villahermosa.siacentro.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private UserEntity userEntity;
    private Context context;
    private FragmentKt NavigationHostFragment;



    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.context = view.getContext();
        UserDB.get(this.context);

        this.mostrar_usuario_logeado(this.context);


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

            String imageUri = userentity.getURLImagenArchivo();
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

            TextView txtUsername = (TextView) binding.FullName;
            txtUsername.setText(userentity.getFullName());

            TextView txtlblUsername = (TextView) binding.lblFullName;
            String lbl = "Bienvenido!!";
            if ( userentity.getGenero() == 0 )
                lbl = "Bienvenda!";
            txtlblUsername.setText(lbl);

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

//            btnIngresar1.setOnClickListener(v -> {
//                FragmentKt.findNavController(this).navigate(R.id.nav_ingresar);
//            });

            btnRegistry1.setOnClickListener(v -> {
                assert getParentFragment() != null;
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.nav_registar);
//                Navigation.findNavController(requireView()).navigate(R.id.nav_about);
            });

//            btnRegistry1.setOnClickListener(v -> {
//                FragmentKt.findNavController(this).navigate(R.id.nav_about);
//            });

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