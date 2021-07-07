package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.demo.databinding.ActivityDrawerBinding;
import com.example.demo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{
    private Integer status;
    private ActivityMainBinding binding;
    private MainActivityModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        model = new ViewModelProvider(this).get(MainActivityModel.class);
        model.getCurrentStatus().observe(this, new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                binding.enter.setEnabled(integer != 7);
                binding.leave.setEnabled(integer != 0);
            }
        });



        binding.enter.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v)
            {
                status = model.getCurrentStatus().getValue();
                if (status == 0)
                {
                    binding.slotThree.setText(R.string.inuse);
                    status = 1;
                } else if (status == 1)
                {
                    binding.slotTwo.setText(R.string.inuse);
                    status = 3;
                } else if (status == 2)
                {
                    binding.slotThree.setText(R.string.inuse);
                    status = 3;
                } else if (status == 3)
                {
                    binding.slotOne.setText(R.string.inuse);
                    status = 7;
                } else if (status == 4)
                {
                    binding.slotThree.setText(R.string.inuse);
                    status = 5;
                } else if (status == 5)
                {
                    binding.slotTwo.setText(R.string.inuse);
                    status = 7;
                } else if (status == 6)
                {
                    binding.slotThree.setText(R.string.inuse);
                    status = 7;
                }
                binding.status.setText("当前状态：" + status.toString());
                model.getCurrentStatus().setValue(status);
            }
        });
        binding.leave.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v)
            {
                status = model.getCurrentStatus().getValue();
                if (status == 7)
                {
                    binding.slotThree.setText("");
                    status = 6;
                } else if (status == 1)
                {
                    binding.slotThree.setText("");
                    status = 0;
                } else if (status == 2)
                {
                    binding.slotTwo.setText("");
                    status = 0;
                } else if (status == 3)
                {
                    binding.slotThree.setText("");
                    status = 2;
                } else if (status == 4)
                {
                    binding.slotOne.setText("");
                    status = 0;
                } else if (status == 5)
                {
                    binding.slotThree.setText("");
                    status = 4;
                } else if (status == 6)
                {
                    binding.slotTwo.setText("");
                    status = 4;
                }
                model.getCurrentStatus().setValue(status);
                binding.status.setText("当前状态：" + status.toString());
            }
        });
        binding.exit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,Drawer.class);
                startActivity(intent);
            }
        });
    }
}