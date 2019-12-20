package com.example.tugas_besar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tugas_besar.apihelper.BaseApiService;
import com.example.tugas_besar.apihelper.SharedPrefManager;
import com.example.tugas_besar.apihelper.UtilsApi;
import com.example.tugas_besar.dosen.KelasDosenActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText username;
    Spinner sp_level;
    EditText password;
    Button b_login;
    TextView textLogin;
    String[] pilihan = { "Mahasiswa", "Dosen", "Pengawas" };
    String level;
    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPrefManager = new SharedPrefManager(this);
        super.onCreate(savedInstanceState);
        if (sharedPrefManager.getToken().equals("")){
            setContentView(R.layout.activity_login);

            mContext = this;
            mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
            initComponents();
        }else {
            Intent intent = new Intent(LoginActivity.this, KelasDosenActivity.class);
            startActivity(intent);
        }
    }

    private void initComponents() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        textLogin = (TextView) findViewById(R.id.textLogin);
        b_login = (Button) findViewById(R.id.b_login);

        sp_level = (Spinner) findViewById(R.id.sp_level);
        sp_level.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, pilihan);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_level.setAdapter(aa);


        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(level.equals("Mahasiswa")){
                    requestLoginMahasiswa();
                }else if(level.equals("Dosen")){
                    requestLoginDosen();
                }else if(level.equals("Pengawas")){
                    requestLoginPengawas();
                }else{
                    Toast.makeText(mContext, "Level Tidak di Temukan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position,   long id) {
        Toast.makeText(this, "Anda Memilih: " + pilihan[position],Toast.LENGTH_LONG).show();
        level = pilihan[position];
    }
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Silahkan Pilih Negara", Toast.LENGTH_LONG).show();
    }


    private void requestLoginDosen() {
        mApiService.logindosen(username.getText().toString(), password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                String token = "Bearer "+jsonRESULTS.getString("access_token");
                                sharedPrefManager.saveToken(token);
                                Intent intent = new Intent(mContext, KelasDosenActivity.class);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "Unknown login or wrong password for login " + username, Toast.LENGTH_SHORT).show();
                            Log.d("onResponse", "onResponse: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }

    private void requestLoginMahasiswa() {
        mApiService.loginmahasiswa(username.getText().toString(), password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                String token = "Bearer "+jsonRESULTS.getString("access_token");
                                sharedPrefManager.saveToken(token);
                                Toast.makeText(mContext, "Selamat Datang Mahasiswa", Toast.LENGTH_SHORT).show();

//                                Intent intent = new Intent(mContext, KelasDosenActivity.class);
//                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "Unknown login or wrong password for login " + username, Toast.LENGTH_SHORT).show();
                            Log.d("onResponse", "onResponse: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }

    private void requestLoginPengawas() {
        mApiService.loginpengawas(username.getText().toString(), password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                String token = "Bearer "+jsonRESULTS.getString("access_token");
                                sharedPrefManager.saveToken(token);
                                Toast.makeText(mContext, "Selamat Datang Pengawas", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(mContext, KelasDosenActivity.class);
//                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "Unknown login or wrong password for login " + username, Toast.LENGTH_SHORT).show();
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
