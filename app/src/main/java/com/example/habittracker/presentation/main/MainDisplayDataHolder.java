package com.example.habittracker.presentation.main;

import com.example.habittracker.domain.dailyRecord.Record;
import com.example.habittracker.domain.habit.Habit;

import java.util.List;

public class MainDisplayDataHolder {

    private Habit habit;

    private List<Boolean> weekStatus;

    private int goalStatus;

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public List<Boolean> getWeekStatus() {
        return weekStatus;
    }

    public void setWeekStatus(List<Boolean> weekStatus) {
        this.weekStatus = weekStatus;
    }

    public int getGoalStatus() {
        return goalStatus;
    }

    public void setGoalStatus(int goalStatus) {
        this.goalStatus = goalStatus;
    }
}
