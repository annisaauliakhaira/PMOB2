package com.example.tugas_besar.dosen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugas_besar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class KelasDosenAdapter extends RecyclerView.Adapter<KelasDosenAdapter.ListViewHolder> {

    private JSONArray datas;
    public OnItemClickListener listener;

    public KelasDosenAdapter(JSONArray datas, OnItemClickListener listener) {
        this.datas = datas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_kelas, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        try{
            holder.tvKelas.setText(datas.getJSONObject(position).getString("nama_kelas"));
            holder.tvMatakuliah.setText(datas.getJSONObject(position).getString("nama_mk"));
            holder.tvwaktu.setText(datas.getJSONObject(position).getString("tanggal_ujian")+' '+datas.getJSONObject(position).getString("mulai"));
            holder.bind(datas.getJSONObject(position), listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return datas.length();
    }

    public interface RecyclerViewItemClick{
        public void onItemClickListener(JSONObject holder, int position);
    }


    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMatakuliah, tvKelas, tvwaktu;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKelas = itemView.findViewById(R.id.tv_kelas);
            tvMatakuliah = itemView.findViewById(R.id.tv_matakuliah);
            tvwaktu = itemView.findViewById(R.id.tv_waktu);
//            itemView.setOnClickListener(this);
        }

        public void bind(final JSONObject item, final OnItemClickListener l){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        listener.onItemClick(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(JSONObject item) throws JSONException;
    }

}
