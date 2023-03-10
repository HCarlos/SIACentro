package mx.gob.villahermosa.siacentro.classes.others;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import mx.gob.villahermosa.siacentro.R;

public class BottomSheetDialogOne extends BottomSheetDialogFragment {

    private Activity activity;
    private Context context;

    private BottomSheetListener mListener;
    private Button btnTomarFoto, btnSeleccionarFoto, btnCapturarVideo,
            btnSeleccionarVideo, btnSeleccionarAudio, btnSeleccionarArchivo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        activity = this.getActivity();
        context = this.getContext();

        return v;

    }

    public interface BottomSheetListener{
        void onButtonBSDClicked(int Tipo);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context + " fallo la Implementación del BottomSheet");
        }
    }
}
