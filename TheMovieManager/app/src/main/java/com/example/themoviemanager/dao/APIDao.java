package com.example.themoviemanager.dao;

import com.example.themoviemanager.models.Responses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIDao {
    @GET("/3/search/movie")
    Call<Responses> getSearch(@Query("api_key") String api_key, @Query("query") String query);
}
