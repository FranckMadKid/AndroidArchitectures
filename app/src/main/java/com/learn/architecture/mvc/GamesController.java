package com.learn.architecture.mvc;

import android.widget.Toast;

import com.learn.architecture.model.Game;
import com.learn.architecture.model.GamesService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GamesController {
    private MVCActivity view;
    private GamesService service;

    public GamesController(MVCActivity view) {
        this.view = view;
        service = new GamesService();
        fetchGames();
    }

    private void fetchGames() {
        service.getGames().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new io.reactivex.rxjava3.observers.DisposableSingleObserver<List<Game>>() {
                    @Override
                    public void onSuccess(@NonNull List<Game> games) {
                        /*try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }*/
                        List<String> gameNames = new ArrayList<>();
                        for(Game game: games) {
                            gameNames.add(game.title);
                        }
                        Collections.sort(gameNames, String.CASE_INSENSITIVE_ORDER);
                        view.showDataList();
                        view.setValues(gameNames);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(view, "Could not load data from the net", Toast.LENGTH_SHORT).show();
                        view.showRetryButton();
                    }
                });
    }

    public void refresh()
    {
        fetchGames();
    }
}
