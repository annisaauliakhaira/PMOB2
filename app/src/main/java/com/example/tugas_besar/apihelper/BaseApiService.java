package com.example.tugas_besar.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BaseApiService {
    // Fungsi ini untuk memanggil API api/login/dosen
    @FormUrlEncoded
    @POST("api/login/dosen")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);

    @POST("api/kartu-ujian/kelasdosen")
    Call<ResponseBody> getClass(@Header("Authorization") String authToken);
}
