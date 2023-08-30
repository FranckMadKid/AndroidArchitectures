package com.learn.architecture.datasharing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.learn.architecture.R;
import com.learn.architecture.databinding.ActivityShareDataBinding;

public class ShareDataActivity extends AppCompatActivity {

    private ActivityShareDataBinding binding;
    private SharedViewModel viewmodel;

    public static Intent getIntent(Context context) {
        return new Intent(context, ShareDataActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShareDataBinding.inflate(getLayoutInflater());

        viewmodel = new ViewModelProvider(this).get(SharedViewModel.class);

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