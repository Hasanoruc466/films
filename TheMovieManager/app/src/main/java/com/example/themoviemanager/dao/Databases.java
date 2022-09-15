package com.example.themoviemanager.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.themoviemanager.models.Favorites;
import com.example.themoviemanager.models.WatchList;

@Database(entities = {Favorites.class, WatchList.class}, version = 1)
public abstract class Databases extends RoomDatabase {
    public abstract MoviesDao getMoviesDao();
    private static Databases databases;
    public static Databases getDatabases(Context context){
        if(databases == null){
            databases = Room.databaseBuilder(context.getApplicationContext(),
                    Databases.class,
                    "movie.sqlite")
                    .createFromAsset("movie.sqlite").build();
        }
        return databases;
    }
}
