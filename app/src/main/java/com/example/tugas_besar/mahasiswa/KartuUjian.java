package com.example.tugas_besar.mahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tugas_besar.R;
import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class KartuUjian extends AppCompatActivity {
    ImageView qrCode;
    TextView matkul;
    TextView statUjian;
    JSONObject data;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartu_ujian);

        qrCode = findViewById(R.id.qrCode);
        matkul = findViewById(R.id.mk);
        statUjian = findViewById(R.id.status);

        Intent intent = getIntent();
        try {
            data = new JSONObject(Objects.requireNonNull(intent.getStringExtra("data")));
            String status = data.getString("status_ujian");
            String keterangan = "Status Tidak di Ketahui";;
            if(status.equals("0")){
                keterangan = "Belum Ujian";
            }else if(status.equals("1")){
                keterangan = "Sudah Ujian";
            }else if(status.equals("2")){
                keterangan = "Tidak Ujian";
            }
            String passcode = data.getString("passcode");
            String mk = data.getString("nama_mk");
            QRGEncoder qrgEncoder = new QRGEncoder(passcode, null, QRGContents.Type.TEXT, 300);

            // Getting QR-Code as Bitmap
            bitmap = qrgEncoder.encodeAsBitmap();

            // Setting Bitmap to ImageView
            qrCode.setImageBitmap(bitmap);

            statUjian.setText(keterangan);
            matkul.setText(mk);
        } catch (JSONException | WriterException e) {
            e.printStackTrace();
        }

    }
}
