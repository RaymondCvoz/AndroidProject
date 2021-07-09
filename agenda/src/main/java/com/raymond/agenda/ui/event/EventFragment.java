package com.raymond.agenda.ui.event;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raymond.agenda.R;
import com.raymond.agenda.databinding.FragmentEventBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventFragment extends Fragment
{

    private FragmentEventBinding binding;
    private List<Event> eventList = new ArrayList<>();
    private FloatingActionButton floatingActionButton;

    private void addToList(Event event)
    {
        eventList.add(event);
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        binding = FragmentEventBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //load local event data before loading page
        SharedPreferences localEvent = getActivity().getSharedPreferences("eventData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("eventData", Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String data = localEvent.getString("eventDataString", "");
        Type listType = new TypeToken<List<Event>>()
        {
        }.getType();
        if (gson.fromJson(data, listType) != null)
        {
            eventList = gson.fromJson(data, listType);
        }


        // remove finished event before loading page
        Iterator<Event> iterator = eventList.iterator();
        while(iterator.hasNext())
        {
            Event event = iterator.next();
            if(event.getDone() == 1)
            {
                iterator.remove();
            }
        }
        data = gson.toJson(eventList);
        editor.putString("eventDataString", data);
        editor.apply();

        //loading page
        RecyclerView eventRecyclerView = root.findViewById(R.id.event_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        eventRecyclerView.setLayoutManager(linearLayoutManager);
        EventAdapter eventAdapter = new EventAdapter(eventList, getContext());
        eventRecyclerView.setAdapter(eventAdapter);

        //adding add button to page
        floatingActionButton = binding.addEvent;
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Event current = new Event("", 0);
                addToList(current);
                String data = gson.toJson(eventList);
                editor.putString("eventDataString", data);
                editor.apply();
                EventAdapter eventAdapter = new EventAdapter(eventList, getContext());
                eventRecyclerView.setAdapter(eventAdapter);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }
}