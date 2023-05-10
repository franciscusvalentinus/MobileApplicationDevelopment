package com.uc.madweek6.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TvShow implements Parcelable {

    @SerializedName("id")
    private String id_show;

    @SerializedName("name")
    private String title;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("overview")
    private String description;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("backdrop_path")
    private String cover;

    @SerializedName("first_air_date")
    private String releaseDate;

    public TvShow() {
    }

    public TvShow(String id_show, String title, String popularity, String description, String poster, String cover, String releaseDate) {
        this.id_show = id_show;
        this.title = title;
        this.popularity = popularity;
        this.description = description;
        this.poster = poster;
        this.cover = cover;
        this.releaseDate = releaseDate;
    }

    public String getId_show() {
        return id_show;
    }

    public void setId_show(String id_show) {
        this.id_show = id_show;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String creator) {
        this.popularity = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_show);
        dest.writeString(this.title);
        dest.writeString(this.popularity);
        dest.writeString(this.description);
        dest.writeString(this.poster);
        dest.writeString(this.cover);
        dest.writeString(this.releaseDate);
    }

    protected TvShow(Parcel in) {
        this.id_show = in.readString();
        this.title = in.readString();
        this.popularity = in.readString();
        this.description = in.readString();
        this.poster = in.readString();
        this.cover = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}