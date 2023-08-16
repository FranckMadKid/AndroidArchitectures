package com.learn.architecture.model;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GamesService {

    public static final String BASE_URL = "https://www.freetogame.com/api/";

    private GameApi api;

    public GamesService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        api = retrofit.create(GameApi.class);
    }

    public Single<List<Game>> getGames() { return api.getGames(); }
}
