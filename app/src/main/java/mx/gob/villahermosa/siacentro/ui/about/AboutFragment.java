package mx.gob.villahermosa.siacentro.ui.about;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Objects;

import mx.gob.villahermosa.siacentro.R;
import mx.gob.villahermosa.siacentro.classes.AppConfig;
import mx.gob.villahermosa.siacentro.classes.controllers.Utilidades;
import mx.gob.villahermosa.siacentro.classes.others.MyWebViewClient;
import mx.gob.villahermosa.siacentro.classes.others.WebAppInterface;
import mx.gob.villahermosa.siacentro.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment {

    private WebView webView;
    Activity activity;
    private ProgressDialog pDialog;
    private Utilidades Utl;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mx.gob.villahermosa.siacentro.databinding.FragmentAboutBinding binding = FragmentAboutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        activity = getActivity();

        webView = root.findViewById(R.id.wv_about_app);

        webView.setWebViewClient(new MyWebViewClient(getActivity()));
//        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new WebAppInterface(getContext()), "Android");
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setTitle("Loading...");
                activity.setProgress(progress * 100);
                if (progress == 100)
                    activity.setTitle(R.string.app_name);
                }
            });

        webView.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getAction() == MotionEvent.ACTION_UP
                    && webView.canGoBack()) {
                Objects.requireNonNull(handler.get()).sendEmptyMessage(1);
                    return true;
                }
               return false;
            });

        webView.loadUrl(AppConfig.URL_ABOUT);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Cargando...");
        Utl = new Utilidades(pDialog);
        Utl.showDialog();

        webView.setWebViewClient( new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pDialog.setMessage("...");
                Utl = new Utilidades(pDialog);
                Utl.hideDialog();
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });


        return root;


    }

    @SuppressLint("HandlerLeak")
    private final ThreadLocal<Handler> handler = new ThreadLocal<Handler>() {
        public void handleMessage(@NonNull Message message) {
            if (message.what == 1) {
                webViewGoBack();
            }
        }
    };

    private void webViewGoBack() {
        webView.goBack();
    }



}