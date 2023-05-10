package com.example.lottie;

import android.os.Parcel;
import android.os.Parcelable;


public class User implements Parcelable {
    public String nama;
    public String alamat;
    public String umur;

    public User(String nama, String alamat, String umur) {
        this.nama = nama;
        this.alamat = alamat;
        this.umur = umur;

    }

    protected User(Parcel in) {
        nama = in.readString();
        alamat = in.readString();
        umur = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getUmur() {
        return umur;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(alamat);
        dest.writeString(umur);
    }


}