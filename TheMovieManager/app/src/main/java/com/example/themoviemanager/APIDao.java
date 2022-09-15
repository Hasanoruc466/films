package com.example.themoviemanager;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIDao {
    @GET("/3/search/movie")
    Call<Responses> getSearch(@Query("api_key") String api_key, @Query("query") String query);
}
