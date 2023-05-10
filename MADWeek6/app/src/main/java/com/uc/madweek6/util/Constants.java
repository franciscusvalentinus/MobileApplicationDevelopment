package com.uc.madweek6.util;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class Constants {

    @Retention(SOURCE)
    @StringDef
    public @interface BaseSetting {
        String BASE_URL = "https://api.themoviedb.org/3/";
        String API_KEY = "4b0b356658d51cf0b2e3eb4c3a762c2d";
        String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    }

    @Retention(SOURCE)
    @StringDef
    public @interface Type {
        String MOVIES = "movie";
        String TV_SHOWS = "tv";
    }
}