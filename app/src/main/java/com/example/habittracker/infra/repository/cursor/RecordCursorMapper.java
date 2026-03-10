package com.example.habittracker.infra.repository.cursor;

import android.database.Cursor;
import com.example.habittracker.domain.dailyRecord.Record;
import com.example.habittracker.infra.db.DatabaseContract;

import java.time.LocalDate;

public class RecordCursorMapper implements CursorMapper<Record> {

    @Override
    public Record map(Cursor cursor) {
        return new Record(
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RecordTable.COL_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RecordTable.COL_HABIT_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RecordTable.COL_NUMBER_OF_TIMES)),
                LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.RecordTable.COL_DATE)))
        );
    }
}