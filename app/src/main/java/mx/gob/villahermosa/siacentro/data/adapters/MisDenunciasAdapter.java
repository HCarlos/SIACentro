package mx.gob.villahermosa.siacentro.data.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.Singleton;
import mx.gob.villahermosa.siacentro.classes.responses.DenunciasResponse;
import mx.gob.villahermosa.siacentro.classes.responses.ImagenesResponse;
import mx.gob.villahermosa.siacentro.classes.responses.RespuestasResponse;
import mx.gob.villahermosa.siacentro.ui.home.ImagesActivity;
import mx.gob.villahermosa.siacentro.ui.home.RespuestasActivity;

public class MisDenunciasAdapter extends RecyclerView.Adapter<MisDenunciasAdapter.DenunciaViewHolder> {
    private final List<DenunciasResponse> DR;
    private final Activity activity;
    private final Context context;

    public MisDenunciasAdapter(Activity _activity, Context _context, List<DenunciasResponse> _dr) {
        this.activity = _activity;
        this.context = _context;
        this.DR = _dr;

    }


    @NonNull
    @Override
    public DenunciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_mis_denuncias, parent, false);
        return new DenunciaViewHolder(v);
//        return null;
    }



    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MisDenunciasAdapter.DenunciaViewHolder holder, int position) {
        final DenunciasResponse dr = DR.get(position);
        holder.tvServicio.setText(dr.getServicio());
        holder.tvFecha.setText(dr.getFecha());
        holder.tvDenuncia.setText(dr.getDenuncia());
        holder.tvUbicacion.setText(dr.getUbicacion());
        holder.tvLatLoc.setText(String.format("%s, %s", dr.getLatitud(), dr.getLongitud()));

        ImageView iButton = holder.btnGetDenuncia;


        ArrayList<ImagenesResponse> ir = dr.getImagenes();
        Singleton.setImagenes(ir);
        if ( dr.getImagenes().size() == 1 ){
            holder.txtTotalImages.setText("1 imagen");
        }else{
            holder.txtTotalImages.setText(String.format("%d imágenes", dr.getImagenes().size()));
        }

        ArrayList<RespuestasResponse> rr = dr.getRespuestas();
        Singleton.setRespuestas(rr);
        if ( dr.getRespuestas().size() == 1 ){
            holder.txtTotalComments.setText("1 respuesta");
        }else{
            holder.txtTotalComments.setText(String.format("%d respuestas", dr.getRespuestas().size()));
        }

        String imageUri = ir.get(0).getUrl_thumb();
        int drw = R.drawable.ic_baseline_spa_24;

        Picasso
                .get()
                .load(imageUri)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(drw)
                .error(drw)
                .into(iButton);

        holder.btnAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ImagesActivity.class);
                Singleton.setImagenes(dr.getImagenes());
                intent.putExtra("denuncia_id", dr.getId());
                Singleton.setDenuncia_id(dr.getId());
                activity.startActivity(intent);
            }
        });

        holder.btnAddComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, RespuestasActivity.class);
                Singleton.setRespuestas(dr.getRespuestas());
                intent.putExtra("denuncia_id", dr.getId());
                Singleton.setDenuncia_id(dr.getId());
                activity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return DR.size();
    }


    public static class DenunciaViewHolder extends RecyclerView.ViewHolder{

          final TextView tvServicio;
          final TextView tvFecha;
          final ImageView btnGetDenuncia;
          final TextView tvDenuncia;
          final TextView tvUbicacion;
          final TextView tvLatLoc;
          final TextView txtTotalImages;
          final TextView txtTotalComments;
          final ImageButton btnAddComments;
          final ImageButton btnAddImages;


        public DenunciaViewHolder(View itemView) {
            super(itemView);

            tvServicio = itemView.findViewById(R.id.tvServicio);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            btnGetDenuncia = itemView.findViewById(R.id.den_filename);
            tvDenuncia = itemView.findViewById(R.id.tvDenuncia);
            tvUbicacion = itemView.findViewById(R.id.tvUbicacion);
            tvLatLoc = itemView.findViewById(R.id.tvLatLoc);
            txtTotalImages = itemView.findViewById(R.id.txtTotalImages);
            txtTotalComments = itemView.findViewById(R.id.txtTotalComments);
            btnAddComments = itemView.findViewById(R.id.btnAddComments);
            btnAddImages = itemView.findViewById(R.id.btnAddImages);


        }

    }


    private void getIntentDenuncias(DenunciaViewHolder holder){

//        Singleton.setDenuncia_id( 1 );
//        Intent intent = new Intent(activity, MiDenunciaActivity.class);
//        activity.startActivity(intent);

    }






}
