package com.uc.madweek6.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailResponse {

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("runtime")
    private String runtime;

    @SerializedName("number_of_episodes")
    private String episodes;

    @SerializedName("genres")
    @Expose
    private List<Genre> genres;

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getEpisodes() { return episodes; }

    public void setEpisodes(String episodes) { this.episodes = episodes; }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}