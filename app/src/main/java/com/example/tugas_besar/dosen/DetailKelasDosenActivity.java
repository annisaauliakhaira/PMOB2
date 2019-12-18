package com.example.tugas_besar.dosen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tugas_besar.LoginActivity;
import com.example.tugas_besar.R;
import com.example.tugas_besar.apihelper.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class DetailKelasDosenActivity extends AppCompatActivity {

    JSONObject data;
    TextView mataKuliah;
    Context mContext;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mContext = this;

        Intent intent = getIntent();
        try {
            data = new JSONObject(Objects.requireNonNull(intent.getStringExtra("data")));
            Toast.makeText(mContext, "Data " + data.toString(), Toast.LENGTH_SHORT).show();
            Log.d("onResponse", "onResponse: " + data.toString());
        } catch (JSONException e) {
            Toast.makeText(mContext, "Terjadi Error di aplikasi", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
