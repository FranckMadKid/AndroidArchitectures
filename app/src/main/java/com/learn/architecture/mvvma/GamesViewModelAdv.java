package com.learn.architecture.mvvma;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.learn.architecture.model.Game;
import com.learn.architecture.model.GamesService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GamesViewModelAdv extends ViewModel {

    // Application Data
    private final MutableLiveData<String> queryLiveData = new MutableLiveData<>();
    private final MediatorLiveData<List<Game>> mediatorLiveData = new MediatorLiveData<>();
    private final MutableLiveData<List<Game>> allGamesLiveData = new MutableLiveData<>();
    private final LiveData<List<Game>> filteredGamesLiveData;

    // Appliction state
    private final MutableLiveData<Boolean> gamesError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> gamesLoading = new MutableLiveData<>();



    private GamesService service;
    public GamesViewModelAdv() {

        filteredGamesLiveData = Transformations.switchMap(queryLiveData,  query -> searchByQuery(query) );

        mediatorLiveData.addSource(allGamesLiveData, games -> mediatorLiveData.setValue(games));
        mediatorLiveData.addSource(filteredGamesLiveData, games-> mediatorLiveData.setValue(games));



        service = new GamesService();
        fetchAllGames();
    }


    public LiveData<List<Game>> getGamesList() {
        return mediatorLiveData;
    }

    public LiveData<Boolean> getGamesError() {
        return gamesError;
    }

    public LiveData<Boolean> getGamesLoading() {
        return gamesLoading;
    }

    private void fetchAllGames() {
        gamesLoading.setValue(true);
        service.getGames().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new io.reactivex.rxjava3.observers.DisposableSingleObserver<List<Game>>() {
                    @Override
                    public void onSuccess(@NonNull List<Game> games) {
                        // just updates its internal state
                        allGamesLiveData.setValue(games);
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
        fetchAllGames();
    }

    public void onSearchQuery(String query) {
        if(query.isEmpty())
            fetchAllGames();
        else
            queryLiveData.setValue(query);
    }

    private LiveData<List<Game>> searchByQuery(String query) {
        MutableLiveData<List<Game>> list  = new MutableLiveData<>();
        list.setValue(service.filterGames(query));
        return list;
    }

}
