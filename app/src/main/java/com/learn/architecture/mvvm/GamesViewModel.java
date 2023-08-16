package com.learn.architecture.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.architecture.model.Game;
import com.learn.architecture.model.GamesService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GamesViewModel extends ViewModel {

    private final MutableLiveData<List<String>> gamesList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> gamesError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> gamesLoading = new MutableLiveData<>();


    private GamesService service;
    public GamesViewModel() {
        service = new GamesService();
        fetchGames();
    }


    public LiveData<List<String>> getGamesList() {
        return gamesList;
    }

    public LiveData<Boolean> getGamesError() {
        return gamesError;
    }

    public LiveData<Boolean> getGamesLoading() {
        return gamesLoading;
    }

    private void fetchGames() {
        gamesLoading.setValue(true);
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
                        // just updates its internal state
                        gamesList.setValue(gameNames);
                        gamesError.setValue(false);
                        gamesLoading.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // just updates its internal state
                        gamesError.setValue(true);
                        gamesLoading.setValue(false);
                    }
                });
    }

    public void refresh()
    {
        fetchGames();
    }
}
