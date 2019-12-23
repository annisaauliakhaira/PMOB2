package com.example.tugas_besar.dosen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tugas_besar.LoginActivity;
import com.example.tugas_besar.R;
import com.example.tugas_besar.apihelper.BaseApiService;
import com.example.tugas_besar.apihelper.SharedPrefManager;
import com.example.tugas_besar.apihelper.UtilsApi;
import com.example.tugas_besar.mahasiswa.ListUjianActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelasDosenActivity extends AppCompatActivity {

    private RecyclerView rvKelas;
    RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(this);
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    String accessToken;
    Context mContext;
    JSONArray classes;
    RecyclerView.Adapter iAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPrefManager = new SharedPrefManager(this);
        super.onCreate(savedInstanceState);
        if (sharedPrefManager.getToken().equals("")){
            Intent intent = new Intent(KelasDosenActivity.this, LoginActivity.class);
            startActivity(intent);
        } else{
            setContentView(R.layout.activity_main);
            mContext = this;
            accessToken = sharedPrefManager.getToken();
            mApiService = UtilsApi.getAPIService();
            refresh();
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
            Toast.makeText(KelasDosenActivity.this, "Berhasil logout", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(KelasDosenActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponents() {
        rvKelas = findViewById(R.id.rv_kelas);
        rvKelas.setHasFixedSize(true);
    }

    public void refresh(){
        mApiService.getClass(accessToken)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                classes = jsonRESULTS.getJSONArray("classes");
                                rvKelas = findViewById(R.id.rv_kelas);
                                rvKelas.setHasFixedSize(true);
                                rvKelas.setLayoutManager(layoutmanager);
                                iAdapter = new KelasDosenAdapter(classes, new KelasDosenAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(JSONObject item){
                                        sharedPrefManager.saveToken(accessToken);
                                        Intent intent = new Intent(KelasDosenActivity.this, mahasiswaKelasDosenActivity.class);
                                        intent.putExtra("data", item.toString());
                                        startActivity(intent);
                                    }
                                });
                                rvKelas.setAdapter(iAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
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
