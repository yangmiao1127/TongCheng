package com.example.ttt.model;

import org.litepal.crud.DataSupport;

/**
 * Created by 1 on 2015/10/1.
 */
public class Event extends DataSupport{

    private String title;
    private String startTime;
    private String endTime;
    private String eventId;


    /*public Event(String title, String startTime, String endTime, String id) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = id;
    }*/

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
