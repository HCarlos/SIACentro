package mx.gob.villahermosa.siacentro.data.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.responses.ImagenesResponse;

public class ImagenesDenunciaAdapter extends RecyclerView.Adapter<ImagenesDenunciaAdapter.ImagenesDenunciaViewHolder> {

    private final ArrayList<ImagenesResponse> IR;
    private final Activity activity;
    private final Context context;
    private final int denuncia_id;

    public ImagenesDenunciaAdapter(Activity _activity, Context _context, ArrayList<ImagenesResponse> _dr, int _denuncia_id) {
        this.activity = _activity;
        this.context = _context;
        this.IR = _dr;
        this.denuncia_id = _denuncia_id;

    }


    @NonNull
    @Override
    public ImagenesDenunciaAdapter.ImagenesDenunciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_imagenes, parent, false);
        return new ImagenesDenunciaAdapter.ImagenesDenunciaViewHolder(v);
//        return null;
    }



    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ImagenesDenunciaAdapter.ImagenesDenunciaViewHolder holder, int position) {
        final ImagenesResponse dr = IR.get(position);


        ImageView iButton = holder.iv_imagen;
//        iButton.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;

        String imageUri = dr.getUrl();
        int drw = R.drawable.ic_baseline_spa_24;

        Picasso
                .get()
                .load(imageUri)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(drw)
                .error(drw)
                .into(iButton);

    }

    @Override
    public int getItemCount() {
        return IR.size();
    }


    public static class ImagenesDenunciaViewHolder extends RecyclerView.ViewHolder{

        final ImageView iv_imagen;


        public ImagenesDenunciaViewHolder(View itemView) {
            super(itemView);

            iv_imagen = itemView.findViewById(R.id.iv_imagen);

        }

    }


    private void getIntentDenuncias(MisDenunciasAdapter.DenunciaViewHolder holder){

//        Singleton.setDenuncia_id( 1 );
//        Intent intent = new Intent(activity, MiDenunciaActivity.class);
//        activity.startActivity(intent);

    }








}
