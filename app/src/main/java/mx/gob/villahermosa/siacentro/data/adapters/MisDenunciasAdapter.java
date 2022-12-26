package mx.gob.villahermosa.siacentro.data.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.Singleton;
import mx.gob.villahermosa.siacentro.classes.responses.DenunciasResponse;
import mx.gob.villahermosa.siacentro.classes.responses.ImagenesResponse;

public class MisDenunciasAdapter extends RecyclerView.Adapter<MisDenunciasAdapter.DenunciaViewHolder> {
    private static final String TAG = "RESPUESTA OP";
    private List<DenunciasResponse> DR;
    private Activity activity;
    private Context context;

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



    @Override
    public void onBindViewHolder(@NonNull MisDenunciasAdapter.DenunciaViewHolder holder, int position) {
        final DenunciasResponse dr = DR.get(position);
        holder.tvServicio.setText(dr.getServicio());
        holder.tvFecha.setText(dr.getFecha());
        holder.tvDenuncia.setText(dr.getDenuncia());
        holder.tvUbicacion.setText(dr.getUbicacion());

        ImageView iButton = holder.btnGetDenuncia;


        List<ImagenesResponse> ir = dr.getImagenes();

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

        Log.e("ErrorVH", DR.get(0).getServicio());
    }

    @Override
    public int getItemCount() {
        return DR.size();
    }


    public static class DenunciaViewHolder extends RecyclerView.ViewHolder{

//        int status;
//        String label;
//        String VString1;
          final TextView tvServicio;
          final TextView tvFecha;
          final ImageView btnGetDenuncia;
          final TextView tvDenuncia;
          final TextView tvUbicacion;


          //        LinearLayout leggendLLCrv2;

//        int id = 0;
//        String denuncia = "";
//        String fecha = "";
//        Double latitud = 0.00;
//        Double longitud = 0.00;
//        String ubicacion = "";
//        String ubicacion_google = "";
//        String servicio = "";
//        List<ImagenesResponse> imagenes = null;



        public DenunciaViewHolder(View itemView) {
            super(itemView);
//            label = "";
//            VString1 = "";
            tvServicio = (TextView) itemView.findViewById(R.id.tvServicio);
            tvFecha = (TextView) itemView.findViewById(R.id.tvFecha);
            btnGetDenuncia = (ImageView) itemView.findViewById(R.id.den_filename);
            tvDenuncia = (TextView) itemView.findViewById(R.id.tvDenuncia);
            tvUbicacion = (TextView) itemView.findViewById(R.id.tvUbicacion);

//            id = 0;
//            denuncia = "";
//            fecha = "";
//            latitud = 0.00;
//            longitud = 0.00;
//            ubicacion = "";
//            ubicacion_google = "";
//            servicio = "";
//            imagenes = null;



        }

    }


    private void getIntentDenuncias(DenunciaViewHolder holder){

//        Singleton.setDenuncia_id( 1 );
//        Intent intent = new Intent(activity, MiDenunciaActivity.class);
//        activity.startActivity(intent);



    }






}
