package com.uc.madweek6.ui.main.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.uc.madweek6.model.Cast;
import com.uc.madweek6.model.Genre;
import com.uc.madweek6.model.Movie;
import com.uc.madweek6.repository.MovieRepository;
import com.uc.madweek6.repository.TvShowRepository;

import java.util.List;

public class DetailViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private TvShowRepository tvShowRepository;

    public DetailViewModel() {
        movieRepository = MovieRepository.getInstance();
        tvShowRepository = TvShowRepository.getInstance();
    }

    public LiveData<String> getRuntime(int id) {
        return movieRepository.getRuntime(id);
    }

    public LiveData<String> getHomepage(int id) {
        return movieRepository.getHomepage(id);
    }

    public LiveData<List<Cast>> getCast(int id) {
        return movieRepository.getCasts(id);
    }

    public LiveData<List<Genre>> getMovieGenre(int id) {
        return movieRepository.getGenres(id);
    }

    public LiveData<String> getTVHomepage(int id) {
        return tvShowRepository.getHomepage(id);
    }

    public LiveData<List<Cast>> getTVCast(int id) {
        return tvShowRepository.getCasts(id);
    }

    public LiveData<List<Genre>> getTVGenre(int id) {
        return tvShowRepository.getGenres(id);
    }

    public LiveData<String> getEpisodes(int id) {return tvShowRepository.getEpisodes(id);}
}