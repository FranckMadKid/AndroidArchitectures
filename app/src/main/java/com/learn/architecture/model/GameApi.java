package com.learn.architecture.model;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface GameApi {
    @GET("games")
    Single<List<Game>> getGames();
}
