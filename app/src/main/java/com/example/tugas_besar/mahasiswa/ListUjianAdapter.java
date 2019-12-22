package com.example.tugas_besar.mahasiswa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugas_besar.R;
import com.example.tugas_besar.dosen.KelasDosenAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListUjianAdapter extends RecyclerView.Adapter<ListUjianAdapter.ListViewHolder> {

    private JSONArray datas;
    public OnItemClickListener listener;

    public ListUjianAdapter(JSONArray datas, OnItemClickListener listener) {
        this.datas = datas;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_row_detail_listujian, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        try{
            holder.matakuliah.setText(datas.getJSONObject(position).getString("nama_mk"));
            holder.tanggal.setText(datas.getJSONObject(position).getString("tanggal"));
            holder.jam.setText(datas.getJSONObject(position).getString("mulai")+' '+datas.getJSONObject(position).getString("selesai"));
            holder.ruang.setText(datas.getJSONObject(position).getString("ruangan"));
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
        private TextView matakuliah, tanggal, jam, ruang;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            matakuliah = itemView.findViewById(R.id.matakuliah);
            tanggal = itemView.findViewById(R.id.tanggal);
            jam = itemView.findViewById(R.id.jam);
            ruang = itemView.findViewById(R.id.ruangan);
        }



        public void bind(final JSONObject item, final OnItemClickListener listener) {
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
