package com.vluver.cbj.colegio.Adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vluver.cbj.colegio.PerfilDocente;
import com.vluver.cbj.colegio.PerfilPersona;
import com.vluver.cbj.colegio.R;
import com.vluver.cbj.colegio.model.SearchDocente;
import com.vluver.cbj.colegio.model.SearchUser;
import com.vluver.cbj.colegio.utilidades.GlideLoadImages;

import java.util.List;


/**
 * Created by Vlover on 21/04/2018.
 */

public class SearchDocenteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<SearchDocente> data;

    public SearchDocenteAdapter(Context context, List<SearchDocente> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.adaptador_busqueda_docentes, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }
    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }
    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        SearchDocente current=data.get(position);
        myHolder.textUserName.setText(current.getUserName());

        //GlideLoadImages.loadAvatar(context, current.getUserAvatar(),myHolder.imageUserAvatar);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textUserName;

        ImageView imageUserAvatar;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textUserName = (TextView) itemView.findViewById(R.id.search_user_name);
            imageUserAvatar = (ImageView) itemView.findViewById(R.id.circleImageView_search_user);

            itemView.setOnClickListener(this);
        }


        // Click event for all items
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, PerfilDocente.class);
            intent.putExtra("userName", data.get((getAdapterPosition())).getUserName());
            intent.putExtra("userCedula", data.get((getAdapterPosition())).getUserCedula());
            context.startActivity(intent);

        }


    }
}
