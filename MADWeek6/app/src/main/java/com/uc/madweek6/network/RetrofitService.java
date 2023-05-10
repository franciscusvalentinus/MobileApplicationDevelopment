package com.uc.madweek6.network;

import com.uc.madweek6.model.CastResponse;
import com.uc.madweek6.model.DetailResponse;
import com.uc.madweek6.model.MovieResponse;
import com.uc.madweek6.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.uc.madweek6.util.Constants;

public class RetrofitService {
    private ApiEndpoints api_key;
    private static RetrofitService service;

    private RetrofitService() {
        api_key = new Retrofit.Builder()
                .baseUrl(Constants.BaseSetting.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiEndpoints.class);
    }

    public static RetrofitService getInstance() {
        if (service == null) {
            service = new RetrofitService();
        }
        return service;
    }

    public Call<MovieResponse> getMovies() {
        return api_key.getMovies(Constants.BaseSetting.API_KEY);
    }

    public Call<TvShowResponse> getTvShows() {
        return api_key.getTvShows(Constants.BaseSetting.API_KEY);
    }

    public Call<DetailResponse> getDetails(String type, int id) {
        return api_key.getDetails(type, id, Constants.BaseSetting.API_KEY);
    }

    public Call<CastResponse> getCasts(String type, int id) {
        return api_key.getCasts(type, id, Constants.BaseSetting.API_KEY);
    }
}