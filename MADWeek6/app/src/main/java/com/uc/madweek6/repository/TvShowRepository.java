package com.uc.madweek6.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.uc.madweek6.model.Cast;
import com.uc.madweek6.model.CastResponse;
import com.uc.madweek6.model.DetailResponse;
import com.uc.madweek6.model.Genre;
import com.uc.madweek6.model.TvShow;
import com.uc.madweek6.model.TvShowResponse;
import com.uc.madweek6.network.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.uc.madweek6.util.Constants;

public class TvShowRepository {
    private static TvShowRepository tvShowRepository;
    private static final String TAG = "TvShowRepository";
    private RetrofitService service;

    private TvShowRepository(){
        service = RetrofitService.getInstance();
    }

    public static TvShowRepository getInstance(){
        if(tvShowRepository == null) {
            tvShowRepository = new TvShowRepository();
        }
        return tvShowRepository;
    }

    public MutableLiveData<List<TvShow>> getShowCollection() {
        MutableLiveData<List<TvShow>> listShow = new MutableLiveData<>();
        service.getTvShows().enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d(TAG, "onResponse: " + response.body().getResults().size());
                        listShow.postValue(response.body().getResults());
                    }
                }
            }
            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        return listShow;
    }

    public MutableLiveData<List<Genre>> getGenres(int id) {
        MutableLiveData<List<Genre>> listGenres = new MutableLiveData<>();
        service.getDetails(Constants.Type.TV_SHOWS, id).enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d(TAG, "onResponse: " + response.body().getGenres().size());
                        listGenres.postValue(response.body().getGenres());
                    }
                }
            }
            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        return listGenres;
    }

    public MutableLiveData<String> getHomepage(int id) {
        MutableLiveData<String> homepage = new MutableLiveData<>();
        service.getDetails(Constants.Type.TV_SHOWS, id).enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d(TAG, "onResponses: " + response.body().getHomepage());
                        homepage.postValue(response.body().getHomepage());
                    }
                }
            }
            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        return homepage;
    }

    public MutableLiveData<String> getEpisodes(int id) {
        MutableLiveData<String> episodes = new MutableLiveData<>();
        service.getDetails(Constants.Type.TV_SHOWS, id).enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        episodes.postValue(response.body().getEpisodes());
                    }
                }
            }
            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        return episodes;
    }

    public MutableLiveData<List<Cast>> getCasts(int id) {
        MutableLiveData<List<Cast>> listCasts = new MutableLiveData<>();
        service.getCasts(Constants.Type.TV_SHOWS, id).enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d(TAG, "onResponse: " + response.body().getCast().size());
                        listCasts.postValue(response.body().getCast());
                    }
                }
            }
            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        return listCasts;
    }
}