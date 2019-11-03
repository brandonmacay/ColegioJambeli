package com.vluver.cbj.colegio.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.vluver.cbj.colegio.Adaptador.SliderAdapterExample;
import com.vluver.cbj.colegio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EducarPlusFragment extends Fragment {

    View view;
    String url = "https://educarplus.com";
    WebView miVisorWeb;
    RelativeLayout progreso;
    public EducarPlusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_educar_plus, container, false);
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
        progreso = (RelativeLayout) view.findViewById(R.id.rltprogress);
        progreso.setVisibility(View.VISIBLE);
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
        ajustesVisorWeb.setLoadWithOverviewMode(true);
        ajustesVisorWeb.setAllowFileAccess(true);

        if (Build.VERSION.SDK_INT >= 19) {
            miVisorWeb.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else if(Build.VERSION.SDK_INT >=15 && Build.VERSION.SDK_INT < 19) {
            miVisorWeb.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        ajustesVisorWeb.setSavePassword(true);
        ajustesVisorWeb.setSaveFormData(true);
        ajustesVisorWeb.setEnableSmoothTransition(true);

        //force links open in webview only
        miVisorWeb.setWebViewClient(new EducarPlusFragment.MyWebviewClient());
        miVisorWeb.loadUrl(url);
        return view;
    }
    private class MyWebviewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("https://educarplus.com")) {
                //open url contents in webview
                miVisorWeb.loadUrl(url);
                return false;
            } else {
                //here open external links in external browser or app
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }

        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {


            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progreso.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

    }

}
