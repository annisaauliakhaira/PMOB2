package com.example.tugas_besar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ListKelasAdapter extends RecyclerView.Adapter<ListKelasAdapter.ListViewHolder> {

    Context c;
    ArrayList<Kelas> listKelas;

    public ListKelasAdapter(Context c, ArrayList<Kelas> list){
        this.c = c;
        this.listKelas=list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_kelas, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Kelas kelas = listKelas.get(position);

        Glide.with(holder.itemView.getContext())
                .load(kelas.getPhoto())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imgPhoto);
        holder.tvKelas.setText(kelas.getName());
        holder.tvMatakuliah.setText(kelas.getMatakuliah());
        holder.tvSks.setText(kelas.getSks());
//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onItemClickListener(View v, int position) {
//
//
//                Intent intent = new Intent(c, ListMahasiswaActivity.class);
//                c.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listKelas.size();
    }


    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvKelas, tvMatakuliah, tvSks;
        ItemClickListener itemClickListener;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvKelas = itemView.findViewById(R.id.tv_kelas);
            tvMatakuliah = itemView.findViewById(R.id.tv_matakuliah);
            tvSks = itemView.findViewById(R.id.tv_sks);
//            itemView.setOnClickListener(this);
        }
    }
}
