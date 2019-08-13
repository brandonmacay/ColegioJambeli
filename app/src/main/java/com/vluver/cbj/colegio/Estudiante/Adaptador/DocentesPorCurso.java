package com.vluver.cbj.colegio.Estudiante.Adaptador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.vluver.cbj.colegio.Estudiante.Modelo.DocentesPorCursoModel;
import com.vluver.cbj.colegio.Estudiante.Modelo.HorarioEstudianteModel;
import com.vluver.cbj.colegio.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DocentesPorCurso extends RecyclerView.Adapter {
    private List<DocentesPorCursoModel> items;
    private Context context;
    private int mPreviousPosition;
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    String diadelasemana;
    public DocentesPorCurso(List<DocentesPorCursoModel> items, Context context) {
        this.context = context;
        this.items = items;
        this.diadelasemana=diadelasemana;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void remove(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }
    public void update(List<DocentesPorCursoModel> models) {
        items.clear();
        items.addAll(models);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_docente_por_curso, parent, false);
        return new HorarioHolder(v);

    }

    public class HorarioHolder extends RecyclerView.ViewHolder{
        TextView tiempo,materia;
        HorarioHolder(View view){
            super(view);
            tiempo = view.findViewById(R.id.txttiempo);
            materia = view.findViewById(R.id.txtmateria);
        }



    }

    @SuppressLint({"Range", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final DocentesPorCursoModel estudianteModel = items.get(position);
        ((HorarioHolder) holder).materia.setText(estudianteModel.getNombredocente());
        ((HorarioHolder) holder).tiempo.setText(estudianteModel.getMateria());

    }
}