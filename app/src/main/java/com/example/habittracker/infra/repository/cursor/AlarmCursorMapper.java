package com.example.habittracker.infra.repository.cursor;

import android.database.Cursor;
import com.example.habittracker.domain.alarms.Alarm;
import com.example.habittracker.infra.db.DatabaseContract;

public class AlarmCursorMapper implements CursorMapper<Alarm> {

    @Override
    public Alarm map(Cursor cursor) {
        return new Alarm(
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AlarmTable.COL_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AlarmTable.COL_HABIT_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AlarmTable.COL_HOUR)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AlarmTable.COL_MINUTE))
        );
    }
}