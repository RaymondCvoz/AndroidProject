package com.raymond.agenda.ui.event;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raymond.agenda.MainFrame;
import com.raymond.agenda.databinding.ActivityEventEditBinding;

import java.lang.reflect.Type;
import java.util.List;

public class EventEdit extends AppCompatActivity
{
    private ActivityEventEditBinding binding;
    private List<Event> eventList;
    private int index;
    private String display;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityEventEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences localEvent = getSharedPreferences("eventData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = localEvent.edit();

        Gson gson = new Gson();
        String data = localEvent.getString("eventDataString", "");
        Type listType = new TypeToken<List<Event>>()
        {
        }.getType();
        if (gson.fromJson(data, listType) != null)
        {
            eventList = gson.fromJson(data, listType);
        }

        Intent intent = getIntent();
        index = intent.getIntExtra("index",0);
        display = intent.getStringExtra("display");
        binding.editEvent.setText(display);


        binding.eventConfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                eventList.get(index).setMessage(binding.editEvent.getText().toString());
                String data = gson.toJson(eventList);
                editor.putString("eventDataString", data);
                editor.apply();


                Intent intent1 = new Intent(EventEdit.this, MainFrame.class);
                startActivity(intent1);
            }
        });
    }
}