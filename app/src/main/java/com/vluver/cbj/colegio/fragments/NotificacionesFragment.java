package com.vluver.cbj.colegio.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vluver.cbj.colegio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificacionesFragment extends Fragment {

    View view;

    public NotificacionesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_notificaciones, container, false);
        return view;
    }

}
