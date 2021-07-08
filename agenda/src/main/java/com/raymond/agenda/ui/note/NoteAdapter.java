package com.raymond.agenda.ui.note;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.raymond.agenda.R;
import com.raymond.agenda.ui.note.Note;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>
{
    private List<Note> noteList;
    private Context context;

    public NoteAdapter(List<Note> noteList, Context context)
    {
        this.noteList = noteList;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        Button button;

        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            textView = itemView.findViewById(R.id.note_content_title);
        }
    }

    @NonNull
    @NotNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_per_line, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NotNull @NonNull NoteAdapter.ViewHolder holder, int position)
    {
        Note note = noteList.get(position);
        holder.textView.setText(noteList.get(position).getContent());
        holder.textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,NoteEdit.class);
                intent.putExtra("index",position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return noteList.size();
    }
}
