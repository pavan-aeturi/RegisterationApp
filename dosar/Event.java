package com.example.dosar;

import androidx.annotation.NonNull;

public class Event {
    private String event;
    private String eventContext;
    public Event(String event,String Context)
    {
        this.event=event;
        this.eventContext=Context;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventContext() {
        return eventContext;
    }

    public void setEventContext(String eventContext) {
        this.eventContext = eventContext;
    }

    @NonNull
    @Override
    public String toString() {
        return event;
    }
}
