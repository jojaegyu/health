package com.example.myapplication.Schedule;

import java.time.LocalTime;

public class Schedule {
    String scheduleName;
    LocalTime startTime;
    LocalTime endTime;

    public Schedule(String scheduleName, LocalTime startDateTime, LocalTime endDateTime) {
        this.scheduleName = scheduleName;
        this.startTime = startDateTime;
        this.endTime = endDateTime;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
