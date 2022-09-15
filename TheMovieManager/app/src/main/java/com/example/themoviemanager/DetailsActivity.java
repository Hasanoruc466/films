package com.example.themoviemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.themoviemanager.databinding.ActivityDetailsBinding;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    private Databases databases;
    private MoviesDao dao;
    private APIDao apiDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        databases = Databases.getDatabases(this);
        dao = databases.getMoviesDao();
        apiDao = APIUtils.getSearch();
        int which = getIntent().getIntExtra("which", 0);
        if(which == 0){
            Results results = (Results) getIntent().getSerializableExtra("result");
            setViews(results);
        }
        else if(which == 1){
            Favorites fav = (Favorites) getIntent().getSerializableExtra("fav");
            apiDao.getSearch("10ecb5b341b4d8b5894c009609610670", fav.getMovie_title()).enqueue(new Callback<Responses>() {
                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {
                    List<Results> resultsList = response.body().getResults();
                    for(int i=0; i<resultsList.size(); i++){
                        if(fav.getMovie_id() == Integer.parseInt(resultsList.get(i).getId())){
                            setViews(resultsList.get(i));
                        }
                    }
                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {
                    Snackbar.make(binding.imageView2, t.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            });
        }
        else if(which == 2){
            WatchList watchList = (WatchList) getIntent().getSerializableExtra("watchlist");
            apiDao.getSearch("10ecb5b341b4d8b5894c009609610670", watchList.getMovie_title()).enqueue(new Callback<Responses>() {
                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {
                    List<Results> resultsList = response.body().getResults();
                    for(int i=0; i<resultsList.size(); i++){
                        if(watchList.getMovie_id() == Integer.parseInt(resultsList.get(i).getId())){
                            setViews(resultsList.get(i));
                        }
                    }
                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {
                    Snackbar.make(binding.imageView2, t.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setViews(Results r){
        String url = "https://image.tmdb.org/t/p/w500" + r.getBackdrop_path();
        Picasso.with(DetailsActivity.this).load(url).into(binding.imageView2);
        binding.textViewTitle.setText(r.getTitle());
        binding.textViewOverview.setText("      "+ r.getOverview());
        binding.textViewRate.setText(r.getVote_average());
        String date = r.getRelease_date();
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8,date.length());
        binding.textViewDate.setText(day + "/" + month + "/" + year);
        dao.getFavorites().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Favorites>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Favorites> favorites) {
                        if(!favorites.isEmpty()){
                            for(int i = 0; i < favorites.size(); i++){
                                if(Integer.parseInt(r.getId())==favorites.get(i).getMovie_id()){
                                    binding.toggleButtonFavorites.setChecked(true);
                                    binding.toggleButtonFavorites.setButtonDrawable(R.drawable.vector_favorites_red);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        dao.getWatchlist().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<WatchList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<WatchList> watchLists) {
                        if(!watchLists.isEmpty()){
                            for(int i = 0; i < watchLists.size(); i++){
                                if(Integer.parseInt(r.getId()) == watchLists.get(i).getMovie_id()){
                                    binding.toggleButtonWatchList.setChecked(true);
                                    binding.toggleButtonWatchList.setButtonDrawable(R.drawable.vector_list_blue);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        binding.toggleButtonFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.toggleButtonFavorites.isChecked()){
                    binding.toggleButtonFavorites.setButtonDrawable(R.drawable.vector_favorites_red);
                    Favorites fav = new Favorites(Integer.parseInt(r.getId()), r.getPoster_path(), r.getTitle());
                    dao.addFavorites(fav).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onComplete() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            });
                }
                else{
                    binding.toggleButtonFavorites.setButtonDrawable(R.drawable.vector_favorites_white);
                    Favorites fav = new Favorites(Integer.parseInt(r.getId()), r.getPoster_path(), r.getTitle());
                    dao.deleteFavorites(fav).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onComplete() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            });
                }
            }
        });
        binding.toggleButtonWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.toggleButtonWatchList.isChecked()){
                    binding.toggleButtonWatchList.setButtonDrawable(R.drawable.vector_list_blue);
                    WatchList watchList = new WatchList(Integer.parseInt(r.getId()), r.getPoster_path(), r.getTitle());
                    dao.addWatchlist(watchList).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onComplete() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            });
                }
                else{
                    binding.toggleButtonWatchList.setButtonDrawable(R.drawable.vector_list_white);
                    WatchList watchList = new WatchList(Integer.parseInt(r.getId()), r.getPoster_path(), r.getTitle());
                    dao.deleteWatchlist(watchList).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onComplete() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            });
                }
            }
        });
    }
}