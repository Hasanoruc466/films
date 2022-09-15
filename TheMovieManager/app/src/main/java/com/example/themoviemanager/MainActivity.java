package com.example.themoviemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.themoviemanager.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding.textInputLayoutEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Pattern emailAddress = Pattern
                        .compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
                                + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                                + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");
                if(!emailAddress.matcher(charSequence).matches())
                {
                    binding.textInputLayoutEmail.setError("Your email should be like \"abc@d.e\".");
                    binding.textInputLayoutEmail.setErrorEnabled(true);
                } else {
                    binding.textInputLayoutEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.textInputLayoutPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
/*
                    */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Pattern password = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&–:;',?$]).{6,20}$");
                if(!password.matcher(charSequence).matches()){
                    binding.textInputLayoutPassword.setError("* At least one digit\n* At least one lowercase letter\n" +
                            "* At least one uppercase letter\n* At least one special character (!@#&–:;',?$)\n* At least 6 characters");
                    binding.textInputLayoutPassword.setErrorEnabled(true);
                }
                else
                    binding.textInputLayoutPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.buttonLogin.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
        });
    }
}