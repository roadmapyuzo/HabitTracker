package com.example.habittracker.infra.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "habittracker.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA foreign_keys=ON;");

        db.execSQL(
                "CREATE TABLE " + DatabaseContract.HabitTable.TABLE_NAME + " (" +
                        DatabaseContract.HabitTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DatabaseContract.HabitTable.COL_NAME + " TEXT NOT NULL," +
                        DatabaseContract.HabitTable.COL_DAILY_GOAL + " INTEGER NOT NULL," +
                        DatabaseContract.HabitTable.COL_ACTIVE_ALARMS + " INTEGER NOT NULL," +
                        DatabaseContract.HabitTable.COL_STREAK + " INTEGER NOT NULL DEFAULT 0," +
                        DatabaseContract.HabitTable.COL_START + " TEXT NOT NULL" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE " + DatabaseContract.AlarmTable.TABLE_NAME + " (" +
                        DatabaseContract.AlarmTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DatabaseContract.AlarmTable.COL_HABIT_ID + " INTEGER NOT NULL," +
                        DatabaseContract.AlarmTable.COL_HOUR + " INTEGER NOT NULL," +
                        DatabaseContract.AlarmTable.COL_MINUTE + " INTEGER NOT NULL," +
                        "FOREIGN KEY(" + DatabaseContract.AlarmTable.COL_HABIT_ID + ") REFERENCES " +
                        DatabaseContract.HabitTable.TABLE_NAME + "(" + DatabaseContract.HabitTable.COL_ID + ") ON DELETE CASCADE" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE " + DatabaseContract.RecordTable.TABLE_NAME + " (" +
                        DatabaseContract.RecordTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DatabaseContract.RecordTable.COL_HABIT_ID + " INTEGER NOT NULL," +
                        DatabaseContract.RecordTable.COL_NUMBER_OF_TIMES + " INTEGER NOT NULL," +
                        DatabaseContract.RecordTable.COL_DATE + " TEXT NOT NULL," +
                        "FOREIGN KEY(" + DatabaseContract.RecordTable.COL_HABIT_ID + ") REFERENCES " +
                        DatabaseContract.HabitTable.TABLE_NAME + "(" + DatabaseContract.HabitTable.COL_ID + ") ON DELETE CASCADE" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}