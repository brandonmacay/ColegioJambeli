package com.vluver.cbj.colegio.fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.common.api.Api;
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

    public NoticiasFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_noticias, container, false);
        return view;
    }



    //goto previous page when pressing back button


}
