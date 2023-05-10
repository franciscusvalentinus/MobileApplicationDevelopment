package com.uc.madweek6.ui.main.tvShow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.uc.madweek6.model.Movie;
import com.uc.madweek6.model.TvShow;
import com.uc.madweek6.repository.MovieRepository;
import com.uc.madweek6.repository.TvShowRepository;

import java.util.List;

public class TvShowViewModel extends ViewModel {
    private TvShowRepository repository;

    public TvShowViewModel() {
        repository = TvShowRepository.getInstance();
    }

    public LiveData<List<TvShow>> getTVCollection() {
        return repository.getShowCollection();
    }
}