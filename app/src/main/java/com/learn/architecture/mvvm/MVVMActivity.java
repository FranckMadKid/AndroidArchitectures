package com.learn.architecture.mvvm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.learn.architecture.R;
import com.learn.architecture.databinding.ActivityMvvmactivtyBinding;
import com.learn.architecture.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class MVVMActivity extends AppCompatActivity {
    private List<String> listValues = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private GamesViewModel viewModel;
    private ActivityMvvmactivtyBinding binding;


    public static Intent getIntent(Context context) {
        return new Intent(context, MVVMActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMvvmactivtyBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(this).get(GamesViewModel.class);
        adapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listText, listValues);


        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        setContentView(binding.getRoot());

        binding.list.setAdapter(adapter);
        binding.list.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast.makeText(this, "You clicked " + listValues.get(i), Toast.LENGTH_SHORT).show();
        });

        observeViewModel();
    }

    private void observeViewModel() {
       viewModel.getGamesList().observe(this,
               games -> {
                    if( games != null ) {
                        setValues(games);
                    } else {
                        UiUtils.hideView(binding.list);
                    }
               });

       viewModel.getGamesError().observe(this,
                isError -> {
                    if(isError == true ) {
                        Toast.makeText(this, "Could not load data from the net", Toast.LENGTH_SHORT).show();
                        showRetryButton();
                    } else {
                        UiUtils.hideView(binding.retry);
                    }
                });

       viewModel.getGamesLoading().observe(this,
               isLoading -> {
                    if(isLoading == true) {
                        showLoader();
                    }
               });
    }

    private void setValues(List<String> games) {
        listValues.clear();
        listValues.addAll(games);
        adapter.notifyDataSetChanged();
        showDataList();
    }

    private void showDataList() {
        UiUtils.showView(binding.list);
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