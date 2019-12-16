package com.example.tugas_besar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tugas_besar.apihelper.BaseApiService;
import com.example.tugas_besar.apihelper.SharedPrefManager;
import com.example.tugas_besar.apihelper.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    private RecyclerView rvKelas;
    private ArrayList<Kelas> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvKelas = findViewById(R.id.rv_kelas);
        rvKelas.setHasFixedSize(true);

        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();
        sharedPrefManager = new SharedPrefManager(this);
//
//        list.addAll(KelasData.getListData());
//        showRecyclerList();
    }

    private void initComponents() {

    }

    public void refresh(){
        RecyclerView  list_kelas = findViewById(R.id.list_kelas);
        list_kelas.setLayoutManager(new LinearLayoutManager(this));
        list_kelas.setHasFixedSize(true);
        BaseApiService service = UtilsApi.getAPIService();
        Call call
    }

}
