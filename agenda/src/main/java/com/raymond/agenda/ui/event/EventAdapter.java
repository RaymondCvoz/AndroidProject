package com.raymond.agenda.ui.event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.raymond.agenda.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>
{
    private List<Event> eventList;

    public EventAdapter(List<Event> eventList)
    {
        this.eventList = eventList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        Button button;
        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            button = itemView.findViewById(R.id.event_finish_button);
            textView = itemView.findViewById(R.id.event_content_title);
        }
    }
    @NonNull
    @NotNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_per_line,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EventAdapter.ViewHolder holder, int position)
    {
        Event event = eventList.get(position);
        holder.textView.setText("测试");

    }

    @Override
    public int getItemCount()
    {
        return eventList.size();
    }
}
