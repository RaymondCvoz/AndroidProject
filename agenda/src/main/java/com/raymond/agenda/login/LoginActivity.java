package com.raymond.agenda.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.raymond.agenda.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity
{
    private ActivityLoginBinding binding;
    private LoginActivityModel loginActivityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginActivityModel = new ViewModelProvider(this).get(LoginActivityModel.class);


        loginActivityModel.getLoginFormStatusMutableLiveData().observe(this, new Observer<LoginStatus>()
        {
            @Override
            public void onChanged(LoginStatus loginStatus)
            {
                binding.login.setEnabled(loginStatus.checkValid(binding.username.toString(),binding.password.toString()));
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                User user = new User(binding.username.toString(),binding.password.toString());
            }
        });


    }
}