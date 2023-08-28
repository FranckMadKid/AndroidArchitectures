package com.learn.architecture.mvvma;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.architecture.model.Game;
import com.learn.architecture.model.GamesService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GamesViewModelAdv extends ViewModel {

    private final MutableLiveData<List<Game>> gamesList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> gamesError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> gamesLoading = new MutableLiveData<>();


    private GamesService service;
    public GamesViewModelAdv() {
        service = new GamesService();
        fetchGames();
    }


    public LiveData<List<Game>> getGamesList() {
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
                        // just updates its internal state
                        gamesList.setValue(games);
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
