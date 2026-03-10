package com.example.habittracker.infra.repository.cursor;

import android.database.Cursor;

import com.example.habittracker.domain.habit.Habit;
import com.example.habittracker.infra.db.DatabaseContract;

public class HabitCursorMapper implements CursorMapper<Habit>{

    @Override
    public Habit map(Cursor cursor) {
        return new Habit(
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.HabitTable.COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.HabitTable.COL_NAME)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.HabitTable.COL_DAILY_GOAL)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.HabitTable.COL_ACTIVE_ALARMS)) == 1
        );
    }

}
