package com.mercado.mercadol.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.mercado.mercadol.R;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ViewHolderDatos> implements View.OnClickListener{

    public ArrayList<ItemsVo> listItems;
    private View.OnClickListener listener;
    int posicionmarcada = 0;
    public static Context context;

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

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.name.setText(listItems.get(position).getNombre());
        holder.info.setText(listItems.get(position).getInfo());
        holder.credit.setText(listItems.get(position).getCredit());

        //Picasso.get().load(listItems.get(position).getImagen()).error(R.mipmap.user_icon).into(holder.foto);

        try {
            Glide.with(holder.itemView.getContext())
                    .load(new URL(listItems.get(position).getImagen()))
                    .into(holder.foto);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


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
