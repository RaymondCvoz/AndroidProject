package com.raymond.agenda.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.raymond.agenda.MainFrame;
import com.raymond.agenda.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity
{
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText username = binding.username;
        EditText password = binding.password;
        Button login = binding.login;
        login.setEnabled(false);

        username.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        password.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        login.setEnabled(true);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                User user = new User(username.getText().toString(), password.getText().toString());
                SharedPreferences sharedPreferences = getSharedPreferences("userData",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                Intent intent = new Intent(LoginActivity.this,MainFrame.class);
                startActivity(intent);
            }
        });
    }
}