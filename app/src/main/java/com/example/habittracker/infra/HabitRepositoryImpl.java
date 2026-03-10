package com.example.habittracker.infra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.infra.DatabaseHelper;
import com.example.habittracker.domain.habit.Habit;

import java.util.ArrayList;
import java.util.List;

public class HabitRepositoryImpl implements HabitRepository {

    private DatabaseHelper dbHelper;

    public HabitRepositoryImpl(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    @Override
    public void save(Habit habit) {
        if (habit.getId() == null) {
            insert(habit);
        } else {
            update(habit);
        }
    }

    private void insert(Habit habit) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", habit.getName());
        values.put("dailyGoal", habit.getDailyGoal());
        values.put("activeAlarms", habit.isActiveAlarms() ? 1 : 0);

        long id = db.insert("habit", null, values);
        habit.assignId((int) id);
        db.close();
    }

    @Override
    public void update(Habit habit) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", habit.getName());
        values.put("dailyGoal", habit.getDailyGoal());
        values.put("activeAlarms", habit.isActiveAlarms() ? 1 : 0);

        db.update("habit", values, "id = ?", new String[]{String.valueOf(habit.getId())});
        db.close();
    }

    @Override
    public Habit findById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                "habit",
                new String[]{"id", "name", "dailyGoal", "activeAlarms"},
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        Habit habit = null;
        if (cursor.moveToFirst()) {
            habit = new Habit(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("dailyGoal")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("activeAlarms")) == 1
            );
        }

        cursor.close();
        db.close();
        return habit;
    }

    @Override
    public List<Habit> findAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                "habit",
                new String[]{"id", "name", "dailyGoal", "activeAlarms"},
                null,
                null,
                null,
                null,
                "id ASC"
        );

        List<Habit> habits = new ArrayList<>();
        while (cursor.moveToNext()) {
            Habit habit = new Habit(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("dailyGoal")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("activeAlarms")) == 1
            );
            habits.add(habit);
        }

        cursor.close();
        db.close();
        return habits;
    }

    @Override
    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("habit", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}