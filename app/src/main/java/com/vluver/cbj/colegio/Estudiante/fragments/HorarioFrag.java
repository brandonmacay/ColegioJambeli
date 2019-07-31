package com.vluver.cbj.colegio.Estudiante.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.vluver.cbj.colegio.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HorarioFrag extends Fragment implements View.OnClickListener{
    View view;
    Button lu,ma,mi,ju,vi;
    RecyclerView recyclerView;
    public HorarioFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_horario, container, false);
        lu = view.findViewById(R.id.btnlunes);
        ma = view.findViewById(R.id.btnmartes);
        mi = view.findViewById(R.id.btnmiercoles);
        ju = view.findViewById(R.id.btnjueves);
        vi = view.findViewById(R.id.btnviernes);
        recyclerView = view.findViewById(R.id.rvHorario);
        lu.setOnClickListener(this);
        ma.setOnClickListener(this);
        mi.setOnClickListener(this);
        ju.setOnClickListener(this);
        vi.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (Calendar.MONDAY == dayOfWeek) {
            setCurretDay(0);
        } else if (Calendar.TUESDAY == dayOfWeek) {
            setCurretDay(1);
        } else if (Calendar.WEDNESDAY == dayOfWeek) {
            setCurretDay(2);
        } else if (Calendar.THURSDAY == dayOfWeek) {
            setCurretDay(3);
        } else if (Calendar.FRIDAY == dayOfWeek) {
            setCurretDay(4);
        } else if (Calendar.SATURDAY == dayOfWeek) {
            setCurretDay(0);
        } else if (Calendar.SUNDAY == dayOfWeek) {
            setCurretDay(0);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnlunes:
                setCurretDay(0);
                break;
            case R.id.btnmartes:
                setCurretDay(1);
                break;
            case R.id.btnmiercoles:
                setCurretDay(2);
                break;
            case R.id.btnjueves:
                setCurretDay(3);
                break;
            case R.id.btnviernes:
                setCurretDay(4);
                break;
        }
    }


    private void setCurretDay(int stateint){
        switch (stateint){
            case 0:
                lu.setSelected(true);
                ma.setSelected(false);
                mi.setSelected(false);
                ju.setSelected(false);
                vi.setSelected(false);
                lu.setTextColor(getResources().getColor(R.color.blanco));
                ma.setTextColor(getResources().getColor(R.color.negro));
                mi.setTextColor(getResources().getColor(R.color.negro));
                ju.setTextColor(getResources().getColor(R.color.negro));
                vi.setTextColor(getResources().getColor(R.color.negro));
                break;
            case 1:
                lu.setSelected(false);
                ma.setSelected(true);
                mi.setSelected(false);
                ju.setSelected(false);
                vi.setSelected(false);
                lu.setTextColor(getResources().getColor(R.color.negro));
                ma.setTextColor(getResources().getColor(R.color.blanco));
                mi.setTextColor(getResources().getColor(R.color.negro));
                ju.setTextColor(getResources().getColor(R.color.negro));
                vi.setTextColor(getResources().getColor(R.color.negro));
                break;
            case 2:
                lu.setSelected(false);
                ma.setSelected(false);
                mi.setSelected(true);
                ju.setSelected(false);
                vi.setSelected(false);
                lu.setTextColor(getResources().getColor(R.color.negro));
                ma.setTextColor(getResources().getColor(R.color.negro));
                mi.setTextColor(getResources().getColor(R.color.blanco));
                ju.setTextColor(getResources().getColor(R.color.negro));
                vi.setTextColor(getResources().getColor(R.color.negro));
                break;
            case 3:
                lu.setSelected(false);
                ma.setSelected(false);
                mi.setSelected(false);
                ju.setSelected(true);
                vi.setSelected(false);
                lu.setTextColor(getResources().getColor(R.color.negro));
                ma.setTextColor(getResources().getColor(R.color.negro));
                mi.setTextColor(getResources().getColor(R.color.negro));
                ju.setTextColor(getResources().getColor(R.color.blanco));
                vi.setTextColor(getResources().getColor(R.color.negro));
                break;
            case 4:
                lu.setSelected(false);
                ma.setSelected(false);
                mi.setSelected(false);
                ju.setSelected(false);
                vi.setSelected(true);
                lu.setTextColor(getResources().getColor(R.color.negro));
                ma.setTextColor(getResources().getColor(R.color.negro));
                mi.setTextColor(getResources().getColor(R.color.negro));
                ju.setTextColor(getResources().getColor(R.color.negro));
                vi.setTextColor(getResources().getColor(R.color.blanco));
                break;
        }
    }


}
