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

public class mahasiswaKelasDosenAdapter extends  RecyclerView.Adapter<mahasiswaKelasDosenAdapter.ListViewHolder> {

    private JSONArray datas;
    public OnItemClickListener listener;

    public mahasiswaKelasDosenAdapter(JSONArray datas, OnItemClickListener listener) {
        this.datas = datas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_detailkelasdosen, viewGroup, false);
        return new mahasiswaKelasDosenAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        try{
            holder.tv_nama_mahasiswa.setText(datas.getJSONObject(position).getString("nama"));
            holder.tv_nim.setText(datas.getJSONObject(position).getString("nim"));
            String status = datas.getJSONObject(position).getString("status_ujian");
            String keterangan = "Status Tidak di Ketahui";;
            if(status.equals("0")){
                keterangan = "Belum Ujian";
            }else if(status.equals("1")){
                keterangan = "Sudah Ujian";
            }else if(status.equals("2")){
                keterangan = "Tidak Ujian";
            }
            holder.tv_status.setText(keterangan);
            holder.bind(datas.getJSONObject(position), listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return datas.length();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nama_mahasiswa, tv_nim, tv_status;

        //mengmbil data dari row layout
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama_mahasiswa = itemView.findViewById(R.id.tv_nama_mahasiswa);
            tv_nim = itemView.findViewById(R.id.tv_nim);
            tv_status = itemView.findViewById(R.id.tv_status);
//            itemView.setOnClickListener(this);
        }

        public void bind(final JSONObject item, final OnItemClickListener listener){
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
