package com.example.habittracker.app;

public interface StreakManager {

    int getStreak();

    void saveStreak(int streak);

    String getLastStreakDate();

    void saveLastStreakDate(String date);

}
