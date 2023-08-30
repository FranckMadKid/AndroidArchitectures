package com.learn.architecture.viewmodelstate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.learn.architecture.databinding.ActivitySaveStateBinding;
import com.learn.architecture.databinding.ActivityShareDataBinding;

public class SaveStateActivity extends AppCompatActivity {

    private ActivitySaveStateBinding binding;
    private StateViewModel viewmodel;

    public static Intent getIntent(Context context) {
        return new Intent(context, SaveStateActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySaveStateBinding.inflate(getLayoutInflater());

        viewmodel = new ViewModelProvider(this).get(StateViewModel.class);

        setContentView(binding.getRoot());
        observeViewModel();
    }


    private void observeViewModel() {
        viewmodel.getCounter().observe(this,
                counter -> binding.maincounterTV.setText(counter.toString()));
    }

    public void onIncrementCounter(View view) {
        viewmodel.incrementCounter();
    }

}