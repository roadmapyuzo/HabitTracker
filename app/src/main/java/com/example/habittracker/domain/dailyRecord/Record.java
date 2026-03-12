package com.example.habittracker.domain.dailyRecord;

import java.time.LocalDate;

public class Record {

    Integer id;
    Integer habitId;
    int numberOfTimes;
    LocalDate date;

    public Record (Integer id, Integer habitId, int times, LocalDate date) {

        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        this.id = id;
        this.habitId = habitId;
        this.numberOfTimes = times;
        this.date = date;

    }

    public Integer getId() {
        return id;
    }

    public Integer getHabitId() {
        return habitId;
    }

    public int getNumberOfTimes() {
        return numberOfTimes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setNumberOfTimes(int numberOfTimes) {
        this.numberOfTimes = numberOfTimes;
    }

    public void increment() {

        this.numberOfTimes += 1;

    }

    public void assignId(Integer id) {

        if (this.id != null) {
            throw new IllegalArgumentException("Id already assigned");
        } else {
            this.id = id;
        }

    }
}
