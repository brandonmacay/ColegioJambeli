package com.vluver.cbj.colegio.Estudiante.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vluver.cbj.colegio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocentesFragment extends Fragment {


    public DocentesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmente_configuracion_estudiante, container, false);
    }

}
