package com.uc.firebasecrud.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Login implements Parcelable {

    String name, pass;

    public Login(){

    }

    public Login(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    protected Login(Parcel in) {
        name = in.readString();
        pass = in.readString();
    }

    public static final Creator<Login> CREATOR = new Creator<Login>() {
        @Override
        public Login createFromParcel(Parcel in) {
            return new Login(in);
        }

        @Override
        public Login[] newArray(int size) {
            return new Login[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(pass);
    }
}
