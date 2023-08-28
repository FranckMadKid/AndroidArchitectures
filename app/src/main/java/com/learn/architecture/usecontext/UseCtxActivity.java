package com.learn.architecture.usecontext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.learn.architecture.R;
import com.learn.architecture.databinding.ActivityUseCtxBinding;
import com.learn.architecture.mvvma.MVVMAActivity;

public class UseCtxActivity extends AppCompatActivity {

    private ActivityUseCtxBinding binding;

    private ViewModelWithCtx viewmodel;

    public static Intent getIntent(Context context) {
        return new Intent(context, UseCtxActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUseCtxBinding.inflate(getLayoutInflater());

        // The provider understands that the ViewModel extends a special type
        // of ViewModel adn inject the context.
        // For complex ViewModel initialization you need to implement a Factory
        viewmodel = new ViewModelProvider(this).get(ViewModelWithCtx.class);

        setContentView(binding.getRoot());

        observeViewModel();
    }


    private void observeViewModel() {
        viewmodel.getDirName().observe(this,
                dirname -> {
                    binding.folderTV.setText(dirname);
            });
    }
}

