package com.example.tugas_besar.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseApiService {
    // Fungsi ini untuk memanggil API api/login/dosen
    @FormUrlEncoded
    @POST("api/login/dosen")
    Call<ResponseBody> logindosen(@Field("username") String username,
                                    @Field("password") String password);

       @FormUrlEncoded
    @POST("api/login/users")
    Call<ResponseBody> loginmahasiswa(@Field("username") String username,
                                  @Field("password") String password);

    @FormUrlEncoded
    @POST("api/login/admin")
    Call<ResponseBody> loginpengawas(@Field("username") String username,
                                  @Field("password") String password);

    //Fungsi ini untuk mengambil data kelas dosen
    @POST("api/kartu-ujian/kelasdosen")
    Call<ResponseBody> getClass(@Header("Authorization") String authToken);

    //Fungsi ini untuk mengambil data mahasiswa dalam 1 kelas
    @POST("api/kartu-ujian/mhskelas/{kelas_id}")
    Call<ResponseBody> getDetailKelas(@Header("Authorization") String authToken, @Path("kelas_id") String kelas_id);

    @POST("api/kartu-ujian/kelasmhs")
    Call<ResponseBody> getClassMhs(@Header("Authorization") String authToken);

    //Fungsi ini untuk mengambil data kelas pengawas
    @POST("api/kartu-ujian/kelasadmin")
    Call<ResponseBody> getClassPengawas(@Header("Authorization") String authToken);

    //Fungsi ini untuk mengambil data mahasiswa yang diawasi pengawas
    @POST("api/kartu-ujian/mhsruang/{kelas_id}/{ruangan_id}")
    Call<ResponseBody> getMhsPengawas(@Header("Authorization") String authToken, @Path("kelas_id") String kelas_id,
                                      @Path("ruangan_id") String ruangan_id);


    //Verifikasi kartu ujian
    @FormUrlEncoded
    @POST("api/kartu-ujian/verifikasi")
    Call<ResponseBody> verify(@Header("Authorization") String token, @Field("passcode") String passcode, @Field("kelas_id") String kelas_id);
}
