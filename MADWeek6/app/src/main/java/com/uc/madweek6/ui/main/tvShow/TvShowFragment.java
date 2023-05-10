package com.uc.madweek6.ui.main.tvShow;

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
import android.widget.ProgressBar;

import com.uc.madweek6.R;
import com.uc.madweek6.adapter.TvShowAdapter;
import com.uc.madweek6.model.TvShow;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvShowFragment extends Fragment {

    @BindView(R.id.progressBarTV)
    ProgressBar loading;

    @BindView(R.id.rv_tv)
    RecyclerView rvTV;

    private TvShowViewModel viewModel;
    private TvShowAdapter tvShowAdapter;

    public TvShowFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        showLoading(true);
        viewModel = ViewModelProviders.of(requireActivity()).get(TvShowViewModel.class);
        viewModel.getTVCollection().observe(requireActivity(), observeViewModel);

        rvTV.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvShowAdapter = new TvShowAdapter(getActivity());
    }

    private Observer<List<TvShow>> observeViewModel = tv -> {
        if (tv != null) {
            tvShowAdapter.setTV(tv);
            tvShowAdapter.notifyDataSetChanged();
            rvTV.setAdapter(tvShowAdapter);
            showLoading(false);
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            rvTV.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
        } else {
            rvTV.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }
    }
}