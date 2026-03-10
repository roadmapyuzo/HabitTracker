package com.example.habittracker.infra.repository;

import android.content.ContentValues;
import android.content.Context;

import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.domain.habit.Habit;
import com.example.habittracker.infra.db.DatabaseContract;
import com.example.habittracker.infra.db.DatabaseHelper;
import com.example.habittracker.infra.repository.cursor.HabitCursorMapper;

import java.util.List;

public class HabitRepositoryImpl extends BaseSQLRepository<Habit> implements HabitRepository {

    public HabitRepositoryImpl(DatabaseHelper dbHelper, HabitCursorMapper mapper) {
        super(
                dbHelper,
                com.example.habittracker.infra.db.DatabaseContract.HabitTable.TABLE_NAME,
                new String[]{
                        com.example.habittracker.infra.db.DatabaseContract.HabitTable.COL_ID,
                        com.example.habittracker.infra.db.DatabaseContract.HabitTable.COL_NAME,
                        com.example.habittracker.infra.db.DatabaseContract.HabitTable.COL_DAILY_GOAL,
                        com.example.habittracker.infra.db.DatabaseContract.HabitTable.COL_ACTIVE_ALARMS
                },
                mapper
        );
    }

    @Override
    public void save(Habit habit) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.HabitTable.COL_NAME, habit.getName());
        values.put(DatabaseContract.HabitTable.COL_DAILY_GOAL, habit.getDailyGoal());
        values.put(DatabaseContract.HabitTable.COL_ACTIVE_ALARMS, habit.isActiveAlarms() ? 1 : 0);

        if (habit.getId() == null) {
            long id = insert(values);
            habit.assignId((int) id);
        } else {
            update(
                    values,
                    DatabaseContract.HabitTable.COL_ID + " = ?",
                    new String[]{String.valueOf(habit.getId())}
            );
        }
    }

    @Override
    public Habit findById(int id) {
        return super.findById(DatabaseContract.HabitTable.COL_ID, id);
    }

    @Override
    public List<Habit> findAll() {
        return super.findAll(DatabaseContract.HabitTable.COL_ID + " ASC");
    }

    @Override
    public void delete(int id) {
        super.delete(DatabaseContract.HabitTable.COL_ID + " = ?", new String[]{String.valueOf(id)});
    }
}