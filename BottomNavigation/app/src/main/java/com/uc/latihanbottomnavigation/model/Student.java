package com.uc.latihanbottomnavigation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

    private String id;
    private String nim;
    private String name;
    private String email;
    private String phone;
    private String gender;

    public Student() {

    }

    public Student(String id, String nim, String name, String email, String phone, String gender) {
        this.id = id;
        this.nim = nim;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nim);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.phone);
        dest.writeString(this.gender);
    }

    protected Student(Parcel in) {
        this.id = in.readString();
        this.nim = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
        this.gender = in.readString();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
