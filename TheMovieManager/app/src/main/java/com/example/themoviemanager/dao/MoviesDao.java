package com.example.themoviemanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.themoviemanager.models.Favorites;
import com.example.themoviemanager.models.WatchList;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface MoviesDao {
    @Query("Select * From favorites")
    Single<List<Favorites>> getFavorites();

    @Insert
    Completable addFavorites(Favorites fav);

    @Delete
    Completable deleteFavorites(Favorites fav);

    @Query("Select * From watchlist")
    Single<List<WatchList>> getWatchlist();

    @Insert
    Completable addWatchlist(WatchList watch);

    @Delete
    Completable deleteWatchlist(WatchList watchList);

}
