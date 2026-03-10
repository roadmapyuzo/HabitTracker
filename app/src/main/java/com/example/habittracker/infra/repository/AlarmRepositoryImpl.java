package com.example.habittracker.infra.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.habittracker.app.alarms.AlarmRepository;
import com.example.habittracker.domain.alarms.Alarm;
import com.example.habittracker.infra.db.DatabaseContract;
import com.example.habittracker.infra.db.DatabaseHelper;
import com.example.habittracker.infra.repository.cursor.AlarmCursorMapper;

import java.util.ArrayList;
import java.util.List;

public class AlarmRepositoryImpl extends BaseSQLRepository<Alarm> implements AlarmRepository {

    public AlarmRepositoryImpl(Context context) {
        super(
                new DatabaseHelper(context),
                DatabaseContract.AlarmTable.TABLE_NAME,
                new String[]{
                        DatabaseContract.AlarmTable.COL_ID,
                        DatabaseContract.AlarmTable.COL_HABIT_ID,
                        DatabaseContract.AlarmTable.COL_HOUR,
                        DatabaseContract.AlarmTable.COL_MINUTE
                },
                new AlarmCursorMapper()
        );
    }

    @Override
    public void save(Alarm alarm) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.AlarmTable.COL_HABIT_ID, alarm.getHabitId());
        values.put(DatabaseContract.AlarmTable.COL_HOUR, alarm.getHour());
        values.put(DatabaseContract.AlarmTable.COL_MINUTE, alarm.getMinute());

        if (alarm.getId() == null) {
            long id = insert(values);
            alarm.assignId((int) id);
        } else {
            update(
                    values,
                    DatabaseContract.AlarmTable.COL_ID + " = ?",
                    new String[]{String.valueOf(alarm.getId())}
            );
        }
    }

    @Override
    public Alarm findById(int id) {
        return super.findById(DatabaseContract.AlarmTable.COL_ID, id);
    }

    @Override
    public List<Alarm> findByHabitId(int habitId) {
        String whereClause = DatabaseContract.AlarmTable.COL_HABIT_ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(habitId)};

        SQLiteDatabase db = readable();
        Cursor cursor = db.query(
                tableName,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                DatabaseContract.AlarmTable.COL_ID + " ASC"
        );

        List<Alarm> alarms = new ArrayList<>();
        while (cursor.moveToNext()) {
            alarms.add(mapper.map(cursor));
        }

        cursor.close();
        db.close();
        return alarms;
    }

    @Override
    public void delete(int id) {
        super.delete(DatabaseContract.AlarmTable.COL_ID + " = ?", new String[]{String.valueOf(id)});
    }
}