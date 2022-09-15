package com.example.themoviemanager.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "watchlist")
public class WatchList implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    @NonNull
    private int movie_id;
    @ColumnInfo(name = "poster_path")
    @NonNull
    private String poster_path;
    @ColumnInfo(name = "movie_title")
    @NonNull
    private String movie_title;

    public WatchList() {
    }

    public WatchList(int movie_id, @NonNull String poster_path, @NonNull String movie_title) {
        this.movie_id = movie_id;
        this.poster_path = poster_path;
        this.movie_title = movie_title;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    @NonNull
    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(@NonNull String poster_path) {
        this.poster_path = poster_path;
    }

    @NonNull
    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(@NonNull String movie_title) {
        this.movie_title = movie_title;
    }
}
