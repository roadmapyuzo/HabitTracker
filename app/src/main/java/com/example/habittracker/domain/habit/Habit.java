package com.example.habittracker.domain.habit;

public class Habit {

    Integer id;
    String name;
    int dailyGoal;
    boolean activeAlarms;
    int streak;

    public Habit (Integer id,String name, int goal, boolean alarms) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null");
        }

        if (goal < 0) {
            throw new IllegalArgumentException("Goal cannot be negative");
        }

        this.id = id;
        this.name = name;
        this.dailyGoal = goal;
        this.activeAlarms = alarms;
        this.streak = 0;

    }

    public int getDailyGoal() {
        return dailyGoal;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public boolean isActiveAlarms() {
        return activeAlarms;
    }

    public int getStreak() {
        return streak;
    }

    public void changeName(String name) {

        if (!name.isBlank()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Name cannot be null");
        }

    }

    public void changeGoal(int goal) {

        if (goal > 0) {
            this.dailyGoal = goal;
        } else {
            throw new IllegalArgumentException("Goal needs to be higher than 0");
        }

    }

    public void activateAlarms() {

        if (!this.activeAlarms) {
            this.activeAlarms = true;
        } else {
            throw new IllegalArgumentException("Alarms are already on");
        }

    }

    public void deactivateAlarms() {

        if (this.activeAlarms) {
            this.activeAlarms = false;
        } else {
            throw new IllegalArgumentException("Alarms are already off");
        }

    }

    public void assignId(Integer id) {

        if (this.id != null) {
            throw new IllegalArgumentException("Id already assigned");
        } else {
            this.id = id;
        }

    }

    public void resetStreak() {

        this.streak = 0;

    }

    public void incrementStreak() {

        this.streak += 1;

    }

    @Override
    public String toString() {
        return name;
    }


}
