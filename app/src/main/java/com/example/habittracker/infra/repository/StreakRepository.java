package com.example.habittracker.infra.repository;

import android.content.Context;
import android.content.SharedPreferences;

public class StreakRepository {

    private SharedPreferences prefs;

    public StreakRepository(Context context) {
        prefs = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
    }

    public int getStreak() {
        return prefs.getInt("totalStreak", 0); //
    }


    public void saveStreak(int streak) {
        prefs.edit()
                .putInt("totalStreak", streak)
                .apply();
    }

}
