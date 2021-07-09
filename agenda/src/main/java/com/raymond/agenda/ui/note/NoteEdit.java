package com.raymond.agenda.ui.note;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raymond.agenda.MainFrame;
import com.raymond.agenda.databinding.ActivityNoteEditBinding;

import java.lang.reflect.Type;
import java.util.List;

public class NoteEdit extends AppCompatActivity
{
    private ActivityNoteEditBinding binding;
    private List<Note> noteList;
    private int index;
    private String display;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SharedPreferences localNote = getSharedPreferences("noteData",MODE_PRIVATE);
        SharedPreferences.Editor editor = localNote.edit();


        Gson gson = new Gson();
        String data = localNote.getString("noteDataString","");
        Type listType = new TypeToken<List<Note>>()
        {
        }.getType();
        if (gson.fromJson(data, listType) != null)
        {
            noteList = gson.fromJson(data, listType);
        }


        Intent intent = getIntent();
        index = intent.getIntExtra("index",0);
        display = intent.getStringExtra("display");
        EditText editText = binding.editNote;

        editText.setText(display);

        Button button = binding.saveNote;

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                noteList.get(index).setContent(binding.editNote.getText().toString());
                String data = gson.toJson(noteList);
                editor.putString("noteDataString",data);
                editor.apply();
                button.setEnabled(false);
                button.setText("保存成功");
                Intent intent1 = new Intent(NoteEdit.this, MainFrame.class);
                intent1.putExtra("navTo",2);
                startActivity(intent1);
            }
        });

    }
}