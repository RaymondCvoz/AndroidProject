package com.raymond.agenda.ui.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.raymond.agenda.R;
import com.raymond.agenda.databinding.FragmentEventBinding;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment
{

    private EventViewModel eventViewModel;
    private FragmentEventBinding binding;
    private List<Event> eventList = new ArrayList<>();
    private FloatingActionButton floatingActionButton;

    private String filename = "eventFile";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        eventViewModel =
                new ViewModelProvider(this).get(EventViewModel.class);

        binding = FragmentEventBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Event event1 = new Event();
        Event event2 = new Event();
        eventList.add(event1);
        eventList.add(event2);
        RecyclerView eventRecyclerView = root.findViewById(R.id.event_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        eventRecyclerView.setLayoutManager(linearLayoutManager);
        EventAdapter eventAdapter = new EventAdapter(eventList);
        eventRecyclerView.setAdapter(eventAdapter);


        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

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