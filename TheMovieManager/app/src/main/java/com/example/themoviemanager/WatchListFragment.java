package com.example.themoviemanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.themoviemanager.databinding.FragmentWatchListBinding;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class WatchListFragment extends Fragment {
    private FragmentWatchListBinding binding;
    private Databases db;
    private MoviesDao dao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWatchListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.rvWatchlist.setHasFixedSize(true);
        binding.rvWatchlist.setLayoutManager(new LinearLayoutManager(requireContext()));
        db = Databases.getDatabases(requireContext());
        dao = db.getMoviesDao();
        dao.getWatchlist().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<WatchList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<WatchList> watchLists) {
                        WatchListRVAdapter adapter = new WatchListRVAdapter(requireContext(), watchLists);
                        binding.rvWatchlist.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        return view;
    }
}