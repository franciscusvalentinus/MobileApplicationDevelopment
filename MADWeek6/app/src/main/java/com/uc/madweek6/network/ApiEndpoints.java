package com.uc.madweek6.network;

import com.uc.madweek6.model.CastResponse;
import com.uc.madweek6.model.DetailResponse;
import com.uc.madweek6.model.MovieResponse;
import com.uc.madweek6.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpoints {
    @GET("discover/movie")
    Call<MovieResponse> getMovies(@Query("api_key")String apiKey);

    @GET("discover/tv")
    Call<TvShowResponse> getTvShows(@Query("api_key") String apiKey);

    @GET("{type}/{id}")
    Call<DetailResponse> getDetails(@Path("type") String type, @Path("id") int id, @Query("api_key") String apiKey);

    @GET("{type}/{id}/credits")
    Call<CastResponse> getCasts(@Path("type") String type, @Path("id") int id, @Query("api_key") String apiKey);
}