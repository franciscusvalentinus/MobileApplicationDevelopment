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
import com.uc.madweek6.model.TvShow;

import java.util.List;

import com.uc.madweek6.ui.main.tvShow.TvShowFragmentDirections;
import com.uc.madweek6.util.Constants;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {
    private Context context;
    private List<TvShow> tvData;

    public TvShowAdapter(Context context) {
        this.context = context;
    }

    public void setTV(List<TvShow> tv) {
        this.tvData = tv;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder tvShowViewHolder, int i) {
        TvShow m = tvData.get(i);
        Glide.with(context)
                .load(Constants.BaseSetting.BASE_IMAGE_URL + m.getCover())
                .into(tvShowViewHolder.tv_cover);
        Glide.with(context)
                .load(Constants.BaseSetting.BASE_IMAGE_URL + m.getPoster())
                .into(tvShowViewHolder.tv_poster);
        tvShowViewHolder.tv_title.setText(m.getTitle());
        tvShowViewHolder.tv_date.setText(m.getReleaseDate());
        tvShowViewHolder.itemView.setOnClickListener(view -> {
            TvShowFragmentDirections.ActionTvToDetail action = TvShowFragmentDirections.actionTvToDetail(null, m);
            Navigation.findNavController(view).navigate(action);
        });
    }

    @Override
    public int getItemCount() {
        return tvData.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {
        ImageView tv_cover, tv_poster;
        TextView tv_title, tv_date;
        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_cover = itemView.findViewById(R.id.item_cover);
            tv_poster = itemView.findViewById(R.id.item_poster);
            tv_title = itemView.findViewById(R.id.item_title);
            tv_date = itemView.findViewById(R.id.item_date);
        }
    }
}