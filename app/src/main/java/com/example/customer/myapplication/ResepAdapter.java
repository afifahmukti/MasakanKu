package com.example.customer.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by afi on 19/12/2017.
 */

public class ResepAdapter extends RecyclerView.Adapter<ResepAdapter.ResepViewHolder> {
    List<DataResep>dataResep;
    ClickListener listener;
    Context context;

    public ResepAdapter(List<DataResep> dataResep, ClickListener listener){
        this.dataResep = dataResep;
        this.listener = listener;
    }

    interface ClickListener{
        void onClickPosition(int position);
    }
    @Override
    public ResepAdapter.ResepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View content = LayoutInflater.from(context).inflate(R.layout.row_resep, parent, false);
        return new ResepViewHolder(content);
    }

    @Override
    public void onBindViewHolder(ResepViewHolder holder, int position) {
        holder.nama.setText(dataResep.get(position)
        .getNama());
        Glide.with(context).load(dataResep.get(position).getGambar()).into(holder.gambar);
    }


    @Override
    public int getItemCount() {
        return dataResep == null ? 0 : dataResep.size();
    }

    public class  ResepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView gambar;
        private TextView nama;
        public ResepViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nama = itemView.findViewById(R.id.nama);
            gambar = itemView.findViewById(R.id.gambar);
        }

        @Override
        public void onClick(View v) {
            listener.onClickPosition(getAdapterPosition());
        }
    }
}
