package com.raymond.agenda.ui.note;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raymond.agenda.R;
import com.raymond.agenda.databinding.FragmentNoteBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NoteFragment extends Fragment
{

    private FragmentNoteBinding binding;
    private List<Note> noteList = new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;

    private void addToList(Note note)
    {
        noteList.add(note);
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;
        Log.i("NoteFragment", "onCreateView: success");
        binding = FragmentNoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences localNote = getActivity().getSharedPreferences("noteData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = localNote.edit();
        Gson gson = new Gson();
        String data = localNote.getString("noteDataString", "");
        Type listType = new TypeToken<List<Note>>()
        {
        }.getType();
        if (gson.fromJson(data, listType) != null)
        {
            noteList = gson.fromJson(data, listType);
        }


        // remove finished event before loading page
        Iterator<Note> iterator = noteList.iterator();
        while(iterator.hasNext())
        {
            Note note = iterator.next();
            if(note.getStatus() == 1)
            {
                iterator.remove();
            }
        }
        data = gson.toJson(noteList);
        editor.putString("noteDataString", data);
        editor.apply();

        RecyclerView recyclerView = root.findViewById(R.id.note_recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        NoteAdapter noteAdapter = new NoteAdapter(noteList,getContext(),getActivity());
        recyclerView.setAdapter(noteAdapter);

        floatingActionButton = binding.addNote;
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Note current = new Note("",0);
                addToList(current);
                String data = gson.toJson(noteList);
                editor.putString("noteDataString",data);
                editor.apply();
                NoteAdapter noteAdapter = new NoteAdapter(noteList,getContext(),getActivity());
                recyclerView.setAdapter(noteAdapter);
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