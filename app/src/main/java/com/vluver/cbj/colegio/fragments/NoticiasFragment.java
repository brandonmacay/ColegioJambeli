package com.vluver.cbj.colegio.fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.vluver.cbj.colegio.Adaptador.SliderAdapterExample;
import com.vluver.cbj.colegio.MainActivity;
import com.vluver.cbj.colegio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasFragment extends Fragment {

    View view;
    String url = "https://educarplus.com";
    WebView miVisorWeb;
    public NoticiasFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_noticias, container, false);
        SliderView sliderView = view.findViewById(R.id.imageSlider);

        SliderAdapterExample adapter = new SliderAdapterExample(getContext());

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
        miVisorWeb = (WebView) view.findViewById(R.id.visorWeb);

        final WebSettings ajustesVisorWeb = miVisorWeb.getSettings();
        ajustesVisorWeb.setJavaScriptEnabled(true);
//improve webView performance
        miVisorWeb.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        miVisorWeb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        miVisorWeb.getSettings().setAppCacheEnabled(true);
        miVisorWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        ajustesVisorWeb.setDomStorageEnabled(true);
        ajustesVisorWeb.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        ajustesVisorWeb.setUseWideViewPort(true);
        ajustesVisorWeb.setSavePassword(true);
        ajustesVisorWeb.setSaveFormData(true);
        ajustesVisorWeb.setEnableSmoothTransition(true);
        miVisorWeb.loadUrl(url);
        //force links open in webview only
        miVisorWeb.setWebViewClient(new MyWebviewClient());
        return view;
    }

    private class MyWebviewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("https://educarplus.com")) {
                //open url contents in webview
                return false;
            } else {
                //here open external links in external browser or app
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }

        }
        //ProgressDialogue
        ProgressDialog pd = null;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            pd=new ProgressDialog(getContext());
            pd.setTitle("Por favor espera..");
            pd.setMessage("Cargando educarplus..");
            pd.show();
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            pd.dismiss();
            super.onPageFinished(view, url);
        }
    }

    //goto previous page when pressing back button


}
