package com.example.administrator.all;

/**
 * Created by Administrator on 2017/5/27 0027.
 */

public class Data_event {
    String event_tag;
    String event;

    public Data_event(String event_tag, String event) {
        this.event_tag = event_tag;
        this.event = event;
    }

    public String getEvent_tag() {
        return event_tag;
    }

    public void setEvent_tag(String event_tag) {
        this.event_tag = event_tag;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}

