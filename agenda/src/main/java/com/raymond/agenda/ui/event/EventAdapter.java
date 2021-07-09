package com.raymond.agenda.ui.event;

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

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>
{
    private List<Event> eventList;
    private Context context;

    public EventAdapter(List<Event> eventList, Context context)
    {
        this.eventList = eventList;
        this.context = context;
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
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_per_line,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NotNull @NonNull EventAdapter.ViewHolder holder, int position)
    {
        Event event = eventList.get(position);
        if (event.getDone() == 1)
        {
            holder.button.setText("已完成");
            holder.button.setEnabled(false);
        }

        String display  = eventList.get(position).getMessage();

        if(display.length() >= 10)
        {
            display = display.substring(0,9) + "...";
        }
        holder.textView.setText(display);

        holder.button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                eventList.get(position).setDone(1);
                SharedPreferences localEvent = context.getSharedPreferences("eventData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = localEvent.edit();

                holder.button.setText("已完成");
                holder.button.setEnabled(false);

                Gson gson = new Gson();
                String data = gson.toJson(eventList);
                editor.putString("eventDataString", data);
                editor.apply();
            }
        });
        holder.textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String display  = eventList.get(position).getMessage();
                Intent intent = new Intent(context,EventEdit.class);
                intent.putExtra("index",position);
                intent.putExtra("display",display);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return eventList.size();
    }
}
