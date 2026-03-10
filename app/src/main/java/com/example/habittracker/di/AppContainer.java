package com.example.habittracker.di;

import android.content.Context;

import com.example.habittracker.app.alarms.AlarmRepository;
import com.example.habittracker.app.dailyRecord.DailyRecordRepository;
import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.app.DateProvider;

import com.example.habittracker.infra.DateProviderImpl;
import com.example.habittracker.infra.db.DatabaseHelper;

import com.example.habittracker.infra.repository.AlarmRepositoryImpl;
import com.example.habittracker.infra.repository.DailyRecordRepositoryImpl;
import com.example.habittracker.infra.repository.HabitRepositoryImpl;

import com.example.habittracker.infra.repository.cursor.AlarmCursorMapper;
import com.example.habittracker.infra.repository.cursor.RecordCursorMapper;
import com.example.habittracker.infra.repository.cursor.HabitCursorMapper;

public class AppContainer {

    private final Context context;


    private DatabaseHelper databaseHelper;
    private DateProvider dateProvider;


    private AlarmCursorMapper alarmCursorMapper;
    private RecordCursorMapper recordCursorMapper;
    private HabitCursorMapper habitCursorMapper;


    private AlarmRepository alarmRepository;
    private DailyRecordRepository dailyRecordRepository;
    private HabitRepository habitRepository;

    public AppContainer(Context context) {
        this.context = context;
    }


    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }


    public DateProvider getDateProvider() {
        if (dateProvider == null) {
            dateProvider = new DateProviderImpl();
        }
        return dateProvider;
    }


    public AlarmCursorMapper getAlarmCursorMapper() {
        if (alarmCursorMapper == null) {
            alarmCursorMapper = new AlarmCursorMapper();
        }
        return alarmCursorMapper;
    }

    public RecordCursorMapper getRecordCursorMapper() {
        if (recordCursorMapper == null) {
            recordCursorMapper = new RecordCursorMapper();
        }
        return recordCursorMapper;
    }

    public HabitCursorMapper getHabitCursorMapper() {
        if (habitCursorMapper == null) {
            habitCursorMapper = new HabitCursorMapper();
        }
        return habitCursorMapper;
    }


    public AlarmRepository getAlarmRepository() {
        if (alarmRepository == null) {
            alarmRepository = new AlarmRepositoryImpl(
                    getDatabaseHelper(),
                    getAlarmCursorMapper()
            );
        }
        return alarmRepository;
    }

    public DailyRecordRepository getDailyRecordRepository() {
        if (dailyRecordRepository == null) {
            dailyRecordRepository = new DailyRecordRepositoryImpl(
                    getDatabaseHelper(),
                    getRecordCursorMapper()
            );
        }
        return dailyRecordRepository;
    }

    public HabitRepository getHabitRepository() {
        if (habitRepository == null) {
            habitRepository = new HabitRepositoryImpl(
                    getDatabaseHelper(),
                    getHabitCursorMapper()
            );
        }
        return habitRepository;
    }
}