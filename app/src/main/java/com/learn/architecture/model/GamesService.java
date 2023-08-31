package com.learn.architecture.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GamesService {

    public static final String BASE_URL = "https://www.freetogame.com/api/";

    private GameApi api;

    private ArrayList<Game> allGames = new ArrayList<>();

    public GamesService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        api = retrofit.create(GameApi.class);
    }

    public Single<List<Game>> getGames() {
        Single<List<Game>> allGames = api.getGames();
        this.allGames.clear();
        this.allGames.addAll(allGames.blockingGet());
        return allGames;
    }

    public List<Game> filterGames(String queryString) {
        return allGames.stream().filter(game -> game.title.toLowerCase().contains(queryString)).collect(Collectors.toList());
    }
}
