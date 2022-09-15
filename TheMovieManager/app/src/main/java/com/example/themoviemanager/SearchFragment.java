package com.example.themoviemanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.themoviemanager.databinding.FragmentSearchBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private ArrayList<Results> results;
    private SearchRVAdapter adapter;
    private APIDao apiDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        apiDao = APIUtils.getSearch();
        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.textInputLayoutSearch.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                apiDao.getSearch("10ecb5b341b4d8b5894c009609610670", String.valueOf(charSequence)).enqueue(new Callback<Responses>() {
                    @Override
                    public void onResponse(Call<Responses> call, Response<Responses> response) {
                        Log.e("i", String.valueOf(i));
                        if(response.isSuccessful()){
                            results = response.body().getResults();
                            adapter = new SearchRVAdapter(requireContext(), results);
                            binding.rv.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<Responses> call, Throwable t) {
                            Snackbar.make(binding.textInputLayoutSearch, t.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                });

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }
}