package com.example.habittracker.presentation.alarms;

import com.example.habittracker.domain.alarms.Alarm;
import com.example.habittracker.domain.dailyRecord.Record;
import com.example.habittracker.domain.habit.Habit;

import java.util.List;

public class AlarmsDisplayDataHolder {

    private Habit habit;

    private List<Alarm> alarms;

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }
}
