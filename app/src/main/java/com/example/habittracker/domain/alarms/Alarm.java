package com.example.habittracker.domain.alarms;

public class Alarm {

    Integer id;
    Integer habitId;
    int hour;
    int minute;

    public Alarm (Integer id, Integer habitId, int hour, int minute) {

        this.id = id;
        this.habitId = habitId;
        this.hour = hour;
        this.minute = minute;

    }

    public Integer getId() {
        return id;
    }

    public Integer getHabitId() {
        return habitId;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void change(int hour, int minute) {

        this.hour = hour;
        this.minute = minute;

    }

    public void assignId(Integer id) {

        if (this.id != null) {
            throw new IllegalArgumentException("Id already assigned");
        } else {
            this.id = id;
        }

    }
}
