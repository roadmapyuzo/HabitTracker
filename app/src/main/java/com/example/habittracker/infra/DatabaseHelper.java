package com.example.habittracker.infra;

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
                "CREATE TABLE habit (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "dailyGoal INTEGER NOT NULL," +
                        "activeAlarms INTEGER NOT NULL" +
                        ");"
        );


        db.execSQL(
                "CREATE TABLE alarm (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "habitId INTEGER NOT NULL," +
                        "hour INTEGER NOT NULL," +
                        "minute INTEGER NOT NULL," +
                        "FOREIGN KEY(habitId) REFERENCES habit(id) ON DELETE CASCADE" +
                        ");"
        );


        db.execSQL(
                "CREATE TABLE record (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "habitId INTEGER NOT NULL," +
                        "numberOfTimes INTEGER NOT NULL," +
                        "date TEXT NOT NULL," + // armazenando LocalDate como String "YYYY-MM-DD"
                        "FOREIGN KEY(habitId) REFERENCES habit(id) ON DELETE CASCADE" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}