package com.uc.madweek6.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uc.madweek6.R;
import com.uc.madweek6.model.Movie;

import java.util.List;

import com.uc.madweek6.ui.main.movie.MovieFragmentDirections;
import com.uc.madweek6.util.Constants;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private List<Movie> moviesData;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(List<Movie> movies) {
        this.moviesData = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movie m = moviesData.get(i);
        Glide.with(context)
                .load(Constants.BaseSetting.BASE_IMAGE_URL + m.getCover())
                .into(movieViewHolder.movie_cover);
        Glide.with(context)
                .load(Constants.BaseSetting.BASE_IMAGE_URL + m.getPoster())
                .into(movieViewHolder.movie_poster);
        movieViewHolder.movie_title.setText(m.getTitle());
        movieViewHolder.movie_date.setText(m.getReleaseDate());
        movieViewHolder.itemView.setOnClickListener(view -> {
            MovieFragmentDirections.ActionMovieToDetail action = MovieFragmentDirections.actionMovieToDetail(m, null);
            Navigation.findNavController(view).navigate(action);
        });
    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movie_cover, movie_poster;
        TextView movie_title, movie_date;
        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_cover = itemView.findViewById(R.id.item_cover);
            movie_poster = itemView.findViewById(R.id.item_poster);
            movie_title = itemView.findViewById(R.id.item_title);
            movie_date = itemView.findViewById(R.id.item_date);
        }
    }
}