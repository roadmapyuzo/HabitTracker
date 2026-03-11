package com.example.habittracker.infra.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.habittracker.app.dailyRecord.DailyRecordRepository;
import com.example.habittracker.domain.dailyRecord.Record;
import com.example.habittracker.infra.db.DatabaseContract;
import com.example.habittracker.infra.db.DatabaseHelper;
import com.example.habittracker.infra.repository.cursor.RecordCursorMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyRecordRepositoryImpl extends BaseSQLRepository<Record> implements DailyRecordRepository {

    public DailyRecordRepositoryImpl(DatabaseHelper dbHelper, RecordCursorMapper mapper) {
        super(
                dbHelper,
                com.example.habittracker.infra.db.DatabaseContract.RecordTable.TABLE_NAME,
                new String[]{
                        com.example.habittracker.infra.db.DatabaseContract.RecordTable.COL_ID,
                        com.example.habittracker.infra.db.DatabaseContract.RecordTable.COL_HABIT_ID,
                        com.example.habittracker.infra.db.DatabaseContract.RecordTable.COL_NUMBER_OF_TIMES,
                        com.example.habittracker.infra.db.DatabaseContract.RecordTable.COL_DATE
                },
                mapper
        );
    }

    @Override
    public void save(Record record) {

        System.out.println("CHEGOU AQUI NO SAVE "+record.getId());

        System.out.println("ESSE AQI: "+record.getNumberOfTimes());

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.RecordTable.COL_HABIT_ID, record.getHabitId());
        values.put(DatabaseContract.RecordTable.COL_NUMBER_OF_TIMES, record.getNumberOfTimes());
        values.put(DatabaseContract.RecordTable.COL_DATE, record.getDate().toString());

        if (record.getId() == null) {
            long id = insert(values);
            record.assignId((int) id);
        } else {
            update(
                    values,
                    DatabaseContract.RecordTable.COL_ID + " = ?",
                    new String[]{String.valueOf(record.getId())}
            );
        }
    }

    @Override
    public Record findById(int id) {
        return super.findById(DatabaseContract.RecordTable.COL_ID, id);
    }

    @Override
    public List<Record> findByHabitId(int habitId) {
        String whereClause = DatabaseContract.RecordTable.COL_HABIT_ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(habitId)};

        SQLiteDatabase db = readable();
        Cursor cursor = db.query(
                tableName,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                DatabaseContract.RecordTable.COL_DATE + " ASC"
        );

        List<Record> records = new ArrayList<>();
        while (cursor.moveToNext()) {
            records.add(mapper.map(cursor));
        }

        cursor.close();
        db.close();
        return records;
    }

    @Override
    public void delete(int id) {
        super.delete(DatabaseContract.RecordTable.COL_ID + " = ?", new String[]{String.valueOf(id)});
    }
    @Override
    public Record findByHabitAndDate(int habitId, LocalDate date) {

        String whereClause =
                DatabaseContract.RecordTable.COL_HABIT_ID + " = ? AND " +
                        DatabaseContract.RecordTable.COL_DATE + " = ?";

        String[] args = new String[]{
                String.valueOf(habitId),
                date.toString()
        };

        SQLiteDatabase db = readable();

        Cursor cursor = db.query(
                tableName,
                columns,
                whereClause,
                args,
                null,
                null,
                null
        );

        Record record = null;

        if (cursor.moveToFirst()) {
            record = mapper.map(cursor);
        }

        cursor.close();
        db.close();

        return record;
    }
}