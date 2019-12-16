package com.example.tugas_besar;

import android.os.Parcel;
import android.os.Parcelable;

public class Kelas implements Parcelable {
    private String kelas;
    private String matakuliah;
    private String sks;
    private int photo;

    public  Kelas(){

    }

    protected Kelas(Parcel in) {
        kelas = in.readString();
        matakuliah = in.readString();
        sks = in.readString();
        photo = in.readInt();
    }

    public static final Creator<Kelas> CREATOR = new Creator<Kelas>() {
        @Override
        public Kelas createFromParcel(Parcel in) {
            return new Kelas(in);
        }

        @Override
        public Kelas[] newArray(int size) {
            return new Kelas[size];
        }
    };

    public String getName() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getMatakuliah() {

        return matakuliah;
    }

    public void setMatakuliah(String detail) {

        this.matakuliah = matakuliah;
    }

    public String getSks() {

        return sks;
    }

    public void setSks(String sks) {

        this.sks = sks;
    }

    public int getPhoto() {

        return photo;
    }

    public void setPhoto(int photo) {

        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.photo);
        dest.writeString(this.kelas);
        dest.writeString(this.matakuliah);
        dest.writeString(this.sks);
    }
}
