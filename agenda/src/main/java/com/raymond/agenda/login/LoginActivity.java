package com.raymond.agenda.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        login.setEnabled(true);




        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String uname = username.getText().toString();
                String pwd = password.getText().toString();
                if(uname.equals(""))
                    Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                else if(pwd.equals(""))
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                else
                {
                    LoginTask loginTask = (LoginTask) new LoginTask(new LoginTask.AsyncResponse()
                    {
                        @Override
                        public void processFinished(Boolean output)
                        {
                            System.out.println(output);
                            if(output)
                            {
                                Intent intent = new Intent(LoginActivity.this,MainFrame.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).execute(uname,pwd);
                }
            }
        });
    }
}