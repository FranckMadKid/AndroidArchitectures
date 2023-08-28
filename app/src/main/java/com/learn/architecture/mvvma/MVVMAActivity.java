package com.learn.architecture.mvvma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.learn.architecture.R;
import com.learn.architecture.databinding.ActivityMvvmaactivityBinding;
import com.learn.architecture.model.Game;
import com.learn.architecture.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class MVVMAActivity extends AppCompatActivity {

    private List<Game> gamesList = new ArrayList<>();
    private GameAdapter adapter;

    private GamesViewModelAdv viewModel;

    private ActivityMvvmaactivityBinding binding;



    public static Intent getIntent(Context context) {
        return new Intent(context, MVVMAActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMvvmaactivityBinding.inflate(getLayoutInflater()):

        viewModel =  new ViewModelProvider(this).get(GamesViewModelAdv.class);
        adapter = new GameAdapter(this, gamesList);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        setContentView(binding.getRoot());

        binding.gamesRL.setAdapter(adapter);

        observeViewModel();
    }


    private void observeViewModel() {
        viewmodel.getGamesList().observe(this,
                games -> {
                    if(games != null) {
                        setValues(games);
                    } else {
                        UiUtils.hideView(binding.gamesRL);
                        UiUtils.hideView(binding.searchED);
                    }
                });
    }

    private void setValues(List<Game> games) {
        gamesList.clear();
        gamesList.addAll(games);
        adapter.notifyDataSetChanged();
        showDataList();
    }

    private void showDataList() {
        UiUtils.showView(binding.searchED);
        UiUtils.showView(binding.gamesRL);
        UiUtils.hideView(binding.loader);
    }

    private void showRetryButton() {
        UiUtils.showView(binding.retry);
        UiUtils.hideView(binding.loader);
    }

    private void showLoader() {
        UiUtils.hideView(binding.retry);
        UiUtils.showView(binding.loader);
    }
}