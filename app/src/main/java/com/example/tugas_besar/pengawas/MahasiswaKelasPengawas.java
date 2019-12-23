package com.example.tugas_besar.pengawas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tugas_besar.LoginActivity;
import com.example.tugas_besar.R;
import com.example.tugas_besar.apihelper.BaseApiService;
import com.example.tugas_besar.apihelper.SharedPrefManager;
import com.example.tugas_besar.apihelper.UtilsApi;
import com.example.tugas_besar.dosen.AbsenActivity;
import com.example.tugas_besar.dosen.KelasDosenActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MahasiswaKelasPengawas extends AppCompatActivity {
    TextView kelas, tanggal, ruangan, waktu;
    Button button_absen;
    private RecyclerView rv_list_detail_kelas;
    RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
    SharedPrefManager sharedPrefManager;
    RecyclerView.Adapter iAdapter;
    BaseApiService baseApiService;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPrefManager = new SharedPrefManager(this);
        super.onCreate(savedInstanceState);if (sharedPrefManager.getToken().equals("")){
            Intent intent = new Intent(MahasiswaKelasPengawas.this, LoginActivity.class);
            startActivity(intent);
        } else{
            setContentView(R.layout.activity_mahasiswa_kelas_pengawas);
            mContext = this;
            try {
                Intent intent = getIntent();
                JSONObject kelasDetail = new JSONObject(Objects.requireNonNull(intent.getStringExtra("data")));
                initComponents(kelasDetail);
                getDetailKelas(sharedPrefManager.getToken(), kelasDetail.getInt("kelas_id"),
                        kelasDetail.getInt("ruangan_id"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.id_logout){
            sharedPrefManager.saveToken("");
            Toast.makeText(MahasiswaKelasPengawas.this, "Berhasil logout", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MahasiswaKelasPengawas.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void openabsen(){
        Intent intent = new Intent(mContext, AbsenActivity.class);
        startActivity(intent);

    }

    private void initComponents(JSONObject kelasDetail) {
        kelas = findViewById(R.id.kelas);
        tanggal = findViewById(R.id.tanggal);
        ruangan = findViewById(R.id.ruangan);
        waktu = findViewById(R.id.waktu);
        baseApiService = UtilsApi.getAPIService();

        try {
            kelas.setText(kelasDetail.getString("nama_kelas"));
            tanggal.setText(kelasDetail.getString("tanggal_ujian"));
            ruangan.setText(kelasDetail.getString("ruang_ujian"));
            waktu.setText(kelasDetail.getString("mulai")+" - "+kelasDetail.getString("selesai"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getDetailKelas(String token, int kelas_id, int ruangan_id) {

        baseApiService.getMhsPengawas(token, String.valueOf(kelas_id), String.valueOf(ruangan_id)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());

                        rv_list_detail_kelas = findViewById(R.id.rv_list_detail_kelas);
                        rv_list_detail_kelas.setHasFixedSize(true);
                        rv_list_detail_kelas.setLayoutManager(lm);

                        iAdapter = new MahasiswaKelasPengawasAdapter(jsonObject.getJSONArray("mahasiswas"), new MahasiswaKelasPengawasAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(JSONObject item) {
                                Toast.makeText(MahasiswaKelasPengawas.this, item.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                        rv_list_detail_kelas.setAdapter(iAdapter);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MahasiswaKelasPengawas.this, "Failed to get data :(", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MahasiswaKelasPengawas.this, "Connection error :(", Toast.LENGTH_LONG).show();

            }
        });
    }
}
