package com.learn.architecture.mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.learn.architecture.R;
import com.learn.architecture.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class MVPActivity extends AppCompatActivity implements GamesPresenter.UiView {
    private List<String> listValues = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ProgressBar loader;
    private ListView dataList;
    private Button retryButton;
    private GamesPresenter presenter;

    public static Intent getIntent(Context context) {
        return new Intent(context, MVPActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvpactivity);
        presenter = new GamesPresenter(this);
        adapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listText, listValues);

        getViewReferences();

        dataList.setAdapter(adapter);
        dataList.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast.makeText(this, "You clicked " + listValues.get(i), Toast.LENGTH_SHORT).show();
        });
    }



    public void onRetry(View view) {
        UiUtils.showView(loader);
        UiUtils.hideView(retryButton);
        presenter.refresh();
    }

    @Override
    public void setValues(List<String> games) {
        listValues.clear();
        listValues.addAll(games);
        adapter.notifyDataSetChanged();
        showDataList();
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Could not load data from the net", Toast.LENGTH_SHORT).show();
        showRetryButton();
    }

    public void showDataList() {
        UiUtils.showView(dataList);
        UiUtils.hideView(loader);
    }

    public void showRetryButton() {
        UiUtils.showView(retryButton);
        UiUtils.hideView(loader);
    }

    private void getViewReferences() {
        loader = findViewById(R.id.loader);
        retryButton = findViewById(R.id.retry);
        dataList = findViewById(R.id.list);
    }
}