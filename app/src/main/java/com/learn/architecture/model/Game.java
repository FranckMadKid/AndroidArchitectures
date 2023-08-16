package com.learn.architecture.model;

import com.google.gson.annotations.SerializedName;

public class Game {
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("short_description")
    public String description;
    @SerializedName("game_url")
    public String gameUrl;
    @SerializedName("genre")
    public String genre;
    @SerializedName("platform")
    public String platform;
    @SerializedName("publisher")
    public String publisher;
    @SerializedName("developer")
    public String developer;
    @SerializedName("release_date")
    public String releaseDate;
    @SerializedName("freetogame_profile_url")
    public String freeToGameProfileUrl;
}
