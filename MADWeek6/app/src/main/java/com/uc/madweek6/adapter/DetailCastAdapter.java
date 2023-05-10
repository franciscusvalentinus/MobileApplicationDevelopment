package com.uc.madweek6.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uc.madweek6.R;
import com.uc.madweek6.model.Cast;

import java.util.List;

import com.uc.madweek6.util.Constants;

public class DetailCastAdapter extends RecyclerView.Adapter<DetailCastAdapter.CastViewHolder>{
    private Context context;
    private List<Cast> castsData;

    public DetailCastAdapter(Context context){this.context = context;};

    public void setCasts(List<Cast> casts) {
        this.castsData = casts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cast, parent, false);
        return new DetailCastAdapter.CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailCastAdapter.CastViewHolder castViewHolder, int i) {
        Cast m = castsData.get(i);
        Glide.with(context)
                .load(Constants.BaseSetting.BASE_IMAGE_URL + m.getImg_url())
                .into(castViewHolder.cast_img);
        castViewHolder.cast_name.setText(m.getName());
        castViewHolder.cast_role.setText(m.getRole());
    }

    @Override
    public int getItemCount() {
        return castsData.size();
    }

    class CastViewHolder extends RecyclerView.ViewHolder {
        ImageView cast_img;
        TextView cast_name, cast_role;
        CastViewHolder(@NonNull View itemView) {
            super(itemView);
            cast_img = itemView.findViewById(R.id.cast_img);
            cast_name = itemView.findViewById(R.id.cast_name);
            cast_role = itemView.findViewById(R.id.cast_role);
        }
    }
}