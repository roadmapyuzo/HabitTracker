package com.example.habittracker.infra.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.habittracker.app.DateProvider;
import com.example.habittracker.app.StreakManager;

public class StreakRepository implements StreakManager {

    private SharedPreferences prefs;
    private DateProvider dateProvider;

    public StreakRepository(Context context, DateProvider dateProvider) {
        prefs = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
        this.dateProvider = dateProvider;
    }

    public int getStreak() {
        return prefs.getInt("totalStreak", 0);
    }


    public void saveStreak(int streak) {
        prefs.edit()
                .putInt("totalStreak", streak)
                .apply();
    }

    public String getLastStreakDate() {
        return prefs.getString("lastStreakDate", dateProvider.today().minusDays(1).toString());
    }


    public void saveLastStreakDate(String date) {
        prefs.edit()
                .putString("lastStreakDate", date)
                .apply();
    }

}
