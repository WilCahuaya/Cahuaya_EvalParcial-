package com.example.cahuaya_evalparcial.Entidades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import com.example.cahuaya_evalparcial.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.CursoHolder> {
    List<Curso> listaCurso;

    public CursoAdapter(List<Curso> listaCurso){
        this.listaCurso=listaCurso;
    }

    @NonNull
    @Override
    public CursoHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.curso_imagen_lista,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new CursoHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NotNull CursoAdapter.CursoHolder holder, int position) {
        holder.txtNombre.setText(String.valueOf(listaCurso.get(position).getNombre()));
        holder.txtDescripcion.setText(String.valueOf(listaCurso.get(position).getDescripcion()));
        holder.txtNivel.setText(String.valueOf(listaCurso.get(position).getNivel()));
        if (listaCurso.get(position).getImagen() !=null)
            holder.imagen.setImageBitmap(listaCurso.get(position).getImagen());
        else
            holder.imagen.setImageResource(R.drawable.ic_launcher_background);

    }
    @Override
    public int getItemCount() {
        return listaCurso.size();
    }

    public class CursoHolder extends RecyclerView.ViewHolder{
        TextView  txtNombre,txtDescripcion,txtNivel;
        ImageView imagen;

        public CursoHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre=itemView.findViewById(R.id.txtNameViewCurso);
            txtDescripcion=itemView.findViewById(R.id.txtDescripcionViewCurso);
            txtNivel=itemView.findViewById(R.id.txtNivelLC);
            imagen=itemView.findViewById(R.id.imgIcon);
        }
    }
}
