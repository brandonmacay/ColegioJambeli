package com.vluver.cbj.colegio.Estudiante.Adaptador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vluver.cbj.colegio.Estudiante.Modelo.HorarioEstudianteModel;
import com.vluver.cbj.colegio.R;

import java.util.List;

public class HorarioEstudianteAdaptador extends RecyclerView.Adapter {
    private List<HorarioEstudianteModel> items;
    private Context context;
    public HorarioEstudianteAdaptador(List<HorarioEstudianteModel> items, Context context) {
        this.context = context;
        this.items = items;
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
    public void update(List<HorarioEstudianteModel> models) {
        items.clear();
        items.addAll(models);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horario_estudiante, parent, false);

        return new HorarioHolder(v);

    }
    public class HorarioHolder extends RecyclerView.ViewHolder{
        TextView dia,docente,materia;
        HorarioHolder(View view){
            super(view);
            dia = view.findViewById(R.id.txtdia);
            docente = view.findViewById(R.id.txtdocente);
            materia = view.findViewById(R.id.txtmateria);
        }
    }

    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final HorarioEstudianteModel person = items.get(position);
        ((HorarioHolder) holder).materia.setText(person.getMateria());
        ((HorarioHolder) holder).docente.setText(person.getDocente());
        ((HorarioHolder) holder).dia.setText(person.getDia());
    }


}
