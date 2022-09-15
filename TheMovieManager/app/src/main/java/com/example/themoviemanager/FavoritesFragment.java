package com.example.themoviemanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.themoviemanager.databinding.FragmentFavoritesBinding;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FavoritesFragment extends Fragment {
    private FragmentFavoritesBinding binding;
    private Databases databases;
    private MoviesDao dao;
    private FavoirtesRVAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.rvFavorites.setHasFixedSize(true);
        binding.rvFavorites.setLayoutManager(new LinearLayoutManager(requireContext()));
        databases = Databases.getDatabases(requireContext());
        dao = databases.getMoviesDao();
        dao.getFavorites().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Favorites>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Favorites> favorites) {
                        adapter = new FavoirtesRVAdapter(requireContext(), favorites);
                        binding.rvFavorites.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        return view;
    }
}