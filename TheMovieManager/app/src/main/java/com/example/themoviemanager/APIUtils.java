package com.example.themoviemanager;

public class APIUtils {
    private static String baseUrl = "https://api.themoviedb.org";
    public static APIDao getSearch(){
        return RetrofitClient.getRetrofit(baseUrl).create(APIDao.class);
    }
}
