package com.example.tugas_besar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button b_login;
    TextView textLogin;

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
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLogin();
            }
        });
    }

    private void requestLogin() {
        mApiService.loginRequest(username.getText().toString(), password.getText().toString())
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
}
