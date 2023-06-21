package mx.gob.villahermosa.siacentro.data.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.SoundPool;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.controllers.Utilidades;
import mx.gob.villahermosa.siacentro.classes.responses.RespuestasResponse;

public class RespuestasDenunciaAdapter extends RecyclerView.Adapter<RespuestasDenunciaAdapter.RespuestasDenunciaViewHolder> {

    private final ArrayList<RespuestasResponse> IR;
    private final Activity activity;
    private final Context context;
    private final int denuncia_id;

    public RespuestasDenunciaAdapter(Activity _activity, Context _context, ArrayList<RespuestasResponse> _dr, int _denuncia_id) {
        this.activity = _activity;
        this.context = _context;
        this.IR = _dr;
        this.denuncia_id = _denuncia_id;

    }


    @NonNull
    @Override
    public RespuestasDenunciaAdapter.RespuestasDenunciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_respuestas, parent, false);
        return new RespuestasDenunciaAdapter.RespuestasDenunciaViewHolder(v);
//        return null;
    }



    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull RespuestasDenunciaAdapter.RespuestasDenunciaViewHolder holder, int position) {
        final RespuestasResponse dr = IR.get(position);

        if ( dr.getRoleuser().equals("Administrator")) {
            holder.txtRespuestaIzquierda.setVisibility(View.GONE);
            holder.txtRespuestaDerecha.setText(dr.getRespuesta());
        }else {
            holder.txtRespuestaDerecha.setVisibility(View.GONE);
            holder.txtRespuestaIzquierda.setText(dr.getRespuesta());
        }


    }

    @Override
    public void onViewRecycled(@NonNull RespuestasDenunciaViewHolder holder) {
        super.onViewRecycled(holder);
//        holder.getBindingAdapterPosition();
        Log.e("POSITION","posotion: " + holder.getBindingAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return IR.size();
    }



    public static class RespuestasDenunciaViewHolder extends RecyclerView.ViewHolder{

        final TextView txtRespuestaIzquierda;
        final TextView txtRespuestaDerecha;


        public RespuestasDenunciaViewHolder(View itemView) {
            super(itemView);

            txtRespuestaIzquierda = itemView.findViewById(R.id.txtMessageIzquierda);
            txtRespuestaDerecha = itemView.findViewById(R.id.txtMessageDerecha);

        }



    }


    private void getIntentDenuncias(MisDenunciasAdapter.DenunciaViewHolder holder){

//        Singleton.setDenuncia_id( 1 );
//        Intent intent = new Intent(activity, MiDenunciaActivity.class);
//        activity.startActivity(intent);

    }








}
