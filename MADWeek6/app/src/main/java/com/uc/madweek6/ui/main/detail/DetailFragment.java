package com.uc.madweek6.ui.main.detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uc.madweek6.R;
import com.uc.madweek6.adapter.DetailCastAdapter;
import com.uc.madweek6.model.Genre;
import com.uc.madweek6.model.Movie;
import com.uc.madweek6.model.TvShow;
import com.uc.madweek6.ui.MainActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.uc.madweek6.util.Constants;

import static java.lang.Integer.parseInt;

public class DetailFragment extends Fragment {

    @BindView(R.id.rv_cast)
    RecyclerView rvCasts;

    @BindView(R.id.detail_poster)
    ImageView detailPoster;

    @BindView(R.id.detail_cover)
    ImageView detailCover;

    @BindView(R.id.detail_title)
    TextView detailTitle;

    @BindView(R.id.detail_date)
    TextView detailDate;

    @BindView(R.id.detail_genre)
    TextView detailGenre;

    @BindView(R.id.detail_duration)
    TextView detailDuration;

    @BindView(R.id.detail_description)
    TextView detailDescription;

    @BindView(R.id.detail_pb)
    ProgressBar detailLoading;

    @BindView(R.id.detail_lid)
    ImageView detailLid;

    private DetailViewModel viewModel;
    private DetailCastAdapter castAdapter;
    private String homepage, duration, episodes;
    private Integer durationTemp, hour = 0, minute;
    private Movie movie;
    private TvShow tvShow;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        showLoading(true);
        viewModel = ViewModelProviders.of(requireActivity()).get(DetailViewModel.class);

        rvCasts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        castAdapter = new DetailCastAdapter(getActivity());

        if (getArguments() != null) {
            movie = DetailFragmentArgs.fromBundle(getArguments()).getMovie();
            tvShow = DetailFragmentArgs.fromBundle(getArguments()).getTvShow();
            if (movie != null) {
                observeViewModel(parseInt(movie.getId_movie()));
                initMovie(movie);
            } else {
                observeViewModel(Integer.parseInt(tvShow.getId_show()));
                initShow(tvShow);
            }
        }
    }

    private void observeViewModel(int id) {
        if (movie != null) {
            viewModel.getMovieGenre(id).observe(requireActivity(), genres -> {
                if (genres != null) {
                    for (int i = 0; i < genres.size(); i++) {
                        Genre g = genres.get(i);
                        if (i < genres.size() - 1) {
                            detailGenre.append(g.getName() + " | ");
                        } else {
                            detailGenre.append(g.getName());
                        }
                    }
                }
            });
            viewModel.getRuntime(id).observe(requireActivity(), observeRuntimeViewModel);
            viewModel.getHomepage(id).observe(requireActivity(), observeHomepageViewModel);
            viewModel.getCast(id).observe(requireActivity(), casts -> {
                if (casts != null) {
                    castAdapter.setCasts(casts);
                    castAdapter.notifyDataSetChanged();
                    rvCasts.setAdapter(castAdapter);
                    showLoading(false);
                }
            });
        } else {
            viewModel.getTVGenre(id).observe(requireActivity(), genres -> {
                if (genres != null) {
                    for (int i = 0; i < genres.size(); i++) {
                        Genre g = genres.get(i);
                        if (i < genres.size() - 1) {
                            detailGenre.append(g.getName() + " | ");
                        } else {
                            detailGenre.append(g.getName());
                        }
                    }
                }
            });
            viewModel.getEpisodes(id).observe(requireActivity(), observeEpisodesViewModel);
            viewModel.getTVHomepage(id).observe(requireActivity(), observeHomepageViewModel);
            viewModel.getTVCast(id).observe(requireActivity(), casts -> {
                if (casts != null) {
                    castAdapter.setCasts(casts);
                    castAdapter.notifyDataSetChanged();
                    rvCasts.setAdapter(castAdapter);
                    showLoading(false);
                }
            });
        }
    }

    private Observer<String> observeHomepageViewModel = homepages -> {
        if (homepages != null) {
            homepage = homepages;
        }
    };

    private Observer<String> observeEpisodesViewModel = episode -> {
        if (episode != null) {
            episodes = episode;
            detailDuration.setText(episodes + " Episode");
        }
    };

    private Observer<String> observeRuntimeViewModel = runtime -> {
        if (runtime != null) {
            duration = runtime;
            durationTemp = parseInt(duration);
            while (durationTemp > 60) {
                durationTemp -= 60;
                hour++;
            }
            minute = durationTemp;
            detailDuration.setText(hour + " Hour " + minute + " Minute");
        }
    };

    private void initShow(TvShow tvShow) {
        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle(tvShow.getTitle());
        Glide.with(getActivity()).load(Constants.BaseSetting.BASE_IMAGE_URL + tvShow.getCover()).into(detailCover);
        Glide.with(getActivity()).load(Constants.BaseSetting.BASE_IMAGE_URL + tvShow.getPoster()).into(detailPoster);
        detailTitle.setText(tvShow.getTitle());
        detailDescription.setText(tvShow.getDescription());
        detailDate.setText(tvShow.getReleaseDate());
    }

    private void initMovie(Movie movie) {
        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle(movie.getTitle());
        Glide.with(getActivity()).load(Constants.BaseSetting.BASE_IMAGE_URL + movie.getCover()).into(detailCover);
        Glide.with(getActivity()).load(Constants.BaseSetting.BASE_IMAGE_URL + movie.getPoster()).into(detailPoster);
        detailTitle.setText(movie.getTitle());
        detailDescription.setText(movie.getDescription());
        detailDate.setText(movie.getReleaseDate());
    }

    private void showLoading(Boolean state) {
        if (state) {
            detailLoading.setVisibility(View.VISIBLE);
            detailLid.setVisibility(View.VISIBLE);
        } else {
            detailLoading.setVisibility(View.GONE);
            detailLid.setAlpha(1f);
            detailLid.animate()
                    .alpha(0f)
                    .setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            detailLid.setVisibility(View.GONE);
                        }
                    });
        }
    }
}