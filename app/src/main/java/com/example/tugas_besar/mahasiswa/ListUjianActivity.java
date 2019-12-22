package com.example.tugas_besar.mahasiswa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tugas_besar.LoginActivity;
import com.example.tugas_besar.R;
import com.example.tugas_besar.apihelper.BaseApiService;
import com.example.tugas_besar.apihelper.SharedPrefManager;
import com.example.tugas_besar.apihelper.UtilsApi;
import com.example.tugas_besar.dosen.KelasDosenActivity;
import com.example.tugas_besar.dosen.KelasDosenAdapter;
import com.example.tugas_besar.dosen.mahasiswaKelasDosenActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListUjianActivity extends AppCompatActivity {

    private RecyclerView rvlistujian;
    RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(this);
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    String accessToken;
    Context mContext;
    JSONArray ujians;
    RecyclerView.Adapter iAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPrefManager = new SharedPrefManager(this);
        super.onCreate(savedInstanceState);

        if (sharedPrefManager.getToken().equals("")){
            Intent intent = new Intent(ListUjianActivity.this, LoginActivity.class);
            startActivity(intent);
        } else{
            setContentView(R.layout.activity_list_ujian);
            mContext = this;
            accessToken = sharedPrefManager.getToken();
            mApiService = UtilsApi.getAPIService();
            refresh();
        }
    }

    private void initComponents() {
        rvlistujian = findViewById(R.id.rv_listujian);
        rvlistujian.setHasFixedSize(true);
    }

    private void refresh() {
        mApiService.getClassMhs(accessToken)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                ujians = jsonRESULTS.getJSONArray("ujians");
                                rvlistujian = findViewById(R.id.rv_listujian);
                                rvlistujian.setHasFixedSize(true);
                                rvlistujian.setLayoutManager(layoutmanager);
                                iAdapter = new ListUjianAdapter(ujians, new ListUjianAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(JSONObject item){
                                        sharedPrefManager.saveToken(accessToken);
                                        Intent intent = new Intent(ListUjianActivity.this, KartuUjian.class);
                                        intent.putExtra("data", item.toString());
                                        startActivity(intent);
                                    }
                                });
                                rvlistujian.setAdapter(iAdapter);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "Terjadi Error di aplikasi", Toast.LENGTH_SHORT).show();
                            Log.d("onResponse", "onResponse: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }
}
