package com.vluver.cbj.colegio.Estudiante.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vluver.cbj.colegio.DatabaseHandler;
import com.vluver.cbj.colegio.EstadoSesion;
import com.vluver.cbj.colegio.Estudiante.Adaptador.DocentesPorCurso;
import com.vluver.cbj.colegio.Estudiante.Adaptador.HorarioEstudianteAdaptador;
import com.vluver.cbj.colegio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocentesFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    DatabaseHandler databaseHandler;
    private DocentesPorCurso adapter;
    public DocentesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_docente_en_estudiantes, container, false);
        EstadoSesion estadoSesion = new EstadoSesion(getContext());

        recyclerView = view.findViewById(R.id.recyclerde);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        databaseHandler = new DatabaseHandler(getContext());
        adapter = new DocentesPorCurso(databaseHandler.getDocentePorCurso(), getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

}
