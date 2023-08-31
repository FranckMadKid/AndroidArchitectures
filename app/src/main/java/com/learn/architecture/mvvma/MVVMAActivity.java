package com.learn.architecture.mvvma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.learn.architecture.databinding.ActivityMvvmaactivityBinding;
import com.learn.architecture.model.Game;
import com.learn.architecture.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class MVVMAActivity extends AppCompatActivity {

    private List<Game> gamesList = new ArrayList<>();
    private GameAdapter adapter;

    private GamesViewModelAdv viewmodel;

    private ActivityMvvmaactivityBinding binding;



    public static Intent getIntent(Context context) {
        return new Intent(context, MVVMAActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMvvmaactivityBinding.inflate(getLayoutInflater());

        viewmodel =  new ViewModelProvider(this).get(GamesViewModelAdv.class);
        adapter = new GameAdapter(this, gamesList);

        binding.setViewModel(viewmodel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        setContentView(binding.getRoot());

        binding.gamesRL.setAdapter(adapter);
        binding.gamesRL.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        initUi();

        observeViewModel();
    }


    private void initUi() {
        binding.searchED.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewmodel.onSearchQuery(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
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