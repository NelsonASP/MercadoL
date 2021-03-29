package com.mercado.mercadol.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.mercado.mercadol.Fragments.FragmentItem;
import com.mercado.mercadol.MainActivity;
import com.mercado.mercadol.R;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ViewHolderDatos> implements View.OnClickListener{

    public ArrayList<ItemsVo> listItems;
    private View.OnClickListener listener;
    public static Context context;
    Intent intent;

    public AdapterItem(ArrayList<ItemsVo> listDatos) {
        this.listItems = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = 0;

        if (Utils.visualizacion == Utils.LIST){
            layout = R.layout.item_list;
        }else {
            layout = R.layout.item_list_grid;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,null, false);

        view.setOnClickListener(this);

        intent = new Intent(view.getContext(), MainActivity.class);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderDatos holder, final int position) {
        holder.name.setText(listItems.get(position).getNombre());
        holder.info.setText(listItems.get(position).getInfo());
        holder.credit.setText(listItems.get(position).getCredit());


        try {
            Glide.with(holder.itemView.getContext())
                    .load(new URL(listItems.get(position).getImagen()))
                    .into(holder.foto);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public void onClick(View view) {

        if (listener != null){
         listener.onClick(view);
        }
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView name, info, credit;
        ImageView foto;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.idNombre);
            info = itemView.findViewById(R.id.idInfo);
            credit = itemView.findViewById(R.id.idCredit);
            foto = itemView.findViewById(R.id.idImagen);

        }

       // public void asignarDatos(String datos) {
         //   name.setText(datos);
       // }
    }

    public void setOnclickListener(View.OnClickListener listener){
        this.listener = listener;

    }

}
