package com.raymond.agenda.ui.event;

public class Event
{
    private String message;
    private Integer done;

    public Event(String message, Integer done)
    {
        this.message = message;
        this.done = done;
    }

    public Integer getDone()
    {
        return done;
    }

    public String getMessage()
    {
        return message;
    }

    public void setDone(Integer done)
    {
        this.done = done;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
