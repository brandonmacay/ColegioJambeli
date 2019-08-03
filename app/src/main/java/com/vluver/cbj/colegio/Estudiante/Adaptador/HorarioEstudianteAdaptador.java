package com.vluver.cbj.colegio.Estudiante.Adaptador;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vluver.cbj.colegio.Estudiante.Modelo.HorarioEstudianteModel;
import com.vluver.cbj.colegio.R;

import java.util.List;

public class HorarioEstudianteAdaptador extends RecyclerView.Adapter {
    private List<HorarioEstudianteModel> items;
    private Context context;
    private int mPreviousPosition;
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
        final HorarioEstudianteModel estudianteModel = items.get(position);
        ((HorarioHolder) holder).materia.setText(estudianteModel.getMateria());
        ((HorarioHolder) holder).tiempo.setText(estudianteModel.getHoraInicial()+" - "+estudianteModel.getHoraFinal());

        if (position > mPreviousPosition) {
            animate1(holder, true);
        } else {
            animate1(holder, false);
        }
        mPreviousPosition = position;

    }
    public void animate1(RecyclerView.ViewHolder holder, boolean goesDown) {
        int holderHeight = holder.itemView.getHeight();
        holder.itemView.setPivotY(goesDown == true ? 0 : holderHeight);

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown == true ? 150 : 0, 0);
        animatorTranslateY.setInterpolator(new AccelerateInterpolator());
        animatorTranslateY.setDuration(150);
        animatorTranslateY.start();
    }


}
