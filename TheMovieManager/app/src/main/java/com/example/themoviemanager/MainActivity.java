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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TextInputLayout passwordTIL, emailTIL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        passwordTIL = binding.textInputLayoutPassword;
        emailTIL = binding.textInputLayoutEmail;
        Pattern password = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&–:;',?$]).{6,20}$");
        Pattern emailAddress = Pattern
                .compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");
        setEmailTIL(emailAddress);
        setPasswordTIL(password);
        setButtonClick(emailAddress, password);
    }

    private void setEmailTIL(Pattern email){
        emailTIL.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!email.matcher(charSequence).matches())
                {
                    emailTIL.setError("Your email should be like \"abc@d.e\".");
                    emailTIL.setErrorEnabled(true);
                } else {
                    emailTIL.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setPasswordTIL(Pattern password){
        passwordTIL.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            /*
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!password.matcher(charSequence).matches()){
                    passwordTIL.setError("* At least one digit\n* At least one lowercase letter\n" +
                            "* At least one uppercase letter\n* At least one special character (!@#&–:;',?$)\n* At least 6 characters");
                    passwordTIL.setErrorEnabled(true);
                }
                else
                    passwordTIL.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setButtonClick(Pattern email, Pattern pass){
        binding.buttonLogin.setOnClickListener(v->{
            if(email.matcher( binding.emailET.getText()).matches() && pass.matcher(binding.passwordET.getText()).matches())
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            else
                Snackbar.make(v, "Email or password is incorrect", Snackbar.LENGTH_SHORT).show();
        });
    }
}