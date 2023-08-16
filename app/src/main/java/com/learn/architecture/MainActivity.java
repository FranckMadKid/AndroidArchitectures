package com.learn.architecture;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.learn.architecture.mvc.MVCActivity;
import com.learn.architecture.mvp.MVPActivity;
import com.learn.architecture.mvvm.MVVMActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setTitle("Learning Architecture");
    }

    public void onMVC(View view) {
        startActivity(MVCActivity.getIntent(this));
    }

    public void onMVP(View view) {
        startActivity(MVPActivity.getIntent(this));
    }

    public void onMVVM(View view) {
        startActivity(MVVMActivity.getIntent(this));
    }


}