package com.vluver.cbj.colegio.Adaptador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vluver.cbj.colegio.Docente.Modelo.HorarioDocenteModel;
import com.vluver.cbj.colegio.R;
import com.vluver.cbj.colegio.model.ScheduleModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PerfilHorarioDocenteAdaptador extends RecyclerView.Adapter {
    private List<ScheduleModel> items;
    private Context context;
    private int mPreviousPosition;
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    String diadelasemana;
    public PerfilHorarioDocenteAdaptador(List<ScheduleModel> items, Context context, String diadelasemana) {
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
    public void update(List<ScheduleModel> models) {
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
        TextView tiempo,materia;
        ImageView doneicon;
        HorarioHolder(View view){
            super(view);
            tiempo = view.findViewById(R.id.txttiempo);
            materia = view.findViewById(R.id.txtmateria);
            doneicon = view.findViewById(R.id.ic_done);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "Evento clickeado" , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @SuppressLint({"Range", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ScheduleModel estudianteModel = items.get(position);
        ((HorarioHolder) holder).materia.setText(estudianteModel.getCurso());
        ((HorarioHolder) holder).tiempo.setText(estudianteModel.getHora_ini()+" - "+estudianteModel.getHora_fin());
        boolean correct = diadelasemana.equals(estudianteModel.getDia());
        if (correct) {
            try {
                String th = sdf.format(cal.getTime());
                String hi = estudianteModel.getHora_ini();
                String hf = estudianteModel.getHora_fin();
                Date tiempoHoy = sdf.parse(th);
                Date horaini =  sdf.parse(hi);
                Date horafinal = sdf.parse(hf);
                if (Objects.requireNonNull(horaini).before(tiempoHoy)){
                    ((HorarioHolder) holder).doneicon.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark));

                    ((HorarioHolder) holder).doneicon.setVisibility(View.VISIBLE);
                }

                if (Objects.requireNonNull(horaini).before(tiempoHoy) && Objects.requireNonNull(tiempoHoy).before(horafinal) || Objects.requireNonNull(tiempoHoy).equals(horaini)){
                    ((HorarioHolder) holder).materia.setSelected(true);
                    ((HorarioHolder) holder).doneicon.setVisibility(View.GONE);
                    ((HorarioHolder) holder).materia.setTextColor(context.getResources().getColor(android.R.color.white));
                    ((HorarioHolder) holder).tiempo.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                    ((HorarioHolder) holder).tiempo.setTypeface(Typeface.DEFAULT_BOLD);
                }

            } catch (Exception e) {
                //Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        mPreviousPosition = position;

    }
}