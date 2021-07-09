package com.raymond.agenda.ui.note;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raymond.agenda.MainFrame;
import com.raymond.agenda.R;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>
{
    private List<Note> noteList;
    private Context context;
    private Activity activity;
    public NoteAdapter(List<Note> noteList, Context context, Activity activity)
    {
        this.noteList = noteList;
        this.context = context;
        this.activity = activity;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;

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
        String display = noteList.get(position).getContent();

        if (display.length() >= 30)
        {
            display = display.substring(0, 30) + "......";
        }
        holder.textView.setText(display);

        holder.textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, NoteEdit.class);
                String display = noteList.get(position).getContent();
                intent.putExtra("index", position);
                intent.putExtra("display", display);
                context.startActivity(intent);
            }
        });

        holder.textView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                PopupMenu popupMenu = new PopupMenu(context,holder.textView);
                popupMenu.getMenuInflater().inflate(R.menu.note_pop_up_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        if(item.getItemId() == R.id.noteDelete)
                        {
                            noteList.get(position).setStatus(1);

                            SharedPreferences localNote = context.getSharedPreferences("noteData", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = localNote.edit();

                            Gson gson = new Gson();
                            String data = gson.toJson(noteList);
                            editor.putString("noteDataString", data);
                            editor.apply();
                            Toast.makeText(context,"已删除",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(activity, MainFrame.class);
                            intent.putExtra("navTo",2);
                            activity.startActivity(intent);
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return noteList.size();
    }
}
