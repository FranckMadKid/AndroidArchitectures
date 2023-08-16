package com.learn.architecture.mvp;

import com.learn.architecture.model.Game;
import com.learn.architecture.model.GamesService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GamesPresenter {
    private UiView view;
    private GamesService service;

    public GamesPresenter(UiView view) {
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
/*                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }*/
                        List<String> gameNames = new ArrayList<>();
                        for(Game game: games) {
                            gameNames.add(game.title);
                        }
                        Collections.sort(gameNames, String.CASE_INSENSITIVE_ORDER);
                        view.setValues(gameNames);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.onError();
                    }
                });
    }

    public void refresh()
    {
        fetchGames();
    }

    public interface UiView {
        void setValues(List<String> games);
        void onError();
    }
}
