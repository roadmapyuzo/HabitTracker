package com.example.habittracker.di;

import android.content.Context;

import com.example.habittracker.app.alarms.AlarmRepository;
import com.example.habittracker.app.dailyRecord.DailyRecordRepository;
import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.app.DateProvider;

import com.example.habittracker.app.habit.useCases.ActivateHabitAlarmUseCase;
import com.example.habittracker.app.habit.useCases.ChangeHabitGoalUseCase;
import com.example.habittracker.app.habit.useCases.ChangeHabitNameUseCase;
import com.example.habittracker.app.habit.useCases.CreateHabitUseCase;
import com.example.habittracker.app.habit.useCases.DeactivateHabitAlarmUseCase;
import com.example.habittracker.app.habit.useCases.DeleteHabitUseCase;
import com.example.habittracker.app.habit.useCases.GetHabitByIdUseCase;
import com.example.habittracker.app.habit.useCases.GetHabitsUseCase;
import com.example.habittracker.app.habit.useCases.IncrementHabitStreakUseCase;
import com.example.habittracker.app.habit.useCases.ResetHabitStreakUseCase;

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

    /// habit use cases
    private CreateHabitUseCase createHabitUseCase;
    private DeleteHabitUseCase deleteHabitUseCase;
    private GetHabitsUseCase getHabitsUseCase;
    private GetHabitByIdUseCase getHabitByIdUseCase;
    private ChangeHabitNameUseCase changeHabitNameUseCase;
    private ChangeHabitGoalUseCase changeHabitGoalUseCase;
    private ActivateHabitAlarmUseCase activateHabitAlarmsUseCase;
    private DeactivateHabitAlarmUseCase deactivateHabitAlarmsUseCase;
    private IncrementHabitStreakUseCase incrementHabitStreakUseCase;
    private ResetHabitStreakUseCase resetHabitStreakUseCase;

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

    /// habits use cases

    public CreateHabitUseCase getCreateHabitUseCase() {
        if (createHabitUseCase == null) {
            createHabitUseCase = new CreateHabitUseCase(getHabitRepository());
        }
        return createHabitUseCase;
    }

    public DeleteHabitUseCase getDeleteHabitUseCase() {
        if (deleteHabitUseCase == null) {
            deleteHabitUseCase = new DeleteHabitUseCase(getHabitRepository());
        }
        return deleteHabitUseCase;
    }

    public GetHabitsUseCase getGetHabitsUseCase() {
        if (getHabitsUseCase == null) {
            getHabitsUseCase = new GetHabitsUseCase(getHabitRepository());
        }
        return getHabitsUseCase;
    }

    public GetHabitByIdUseCase getGetHabitByIdUseCase() {
        if (getHabitByIdUseCase == null) {
            getHabitByIdUseCase = new GetHabitByIdUseCase(getHabitRepository());
        }
        return getHabitByIdUseCase;
    }

    public ChangeHabitNameUseCase getChangeHabitNameUseCase() {
        if (changeHabitNameUseCase == null) {
            changeHabitNameUseCase = new ChangeHabitNameUseCase(getHabitRepository());
        }
        return changeHabitNameUseCase;
    }

    public ChangeHabitGoalUseCase getChangeHabitGoalUseCase() {
        if (changeHabitGoalUseCase == null) {
            changeHabitGoalUseCase = new ChangeHabitGoalUseCase(getHabitRepository());
        }
        return changeHabitGoalUseCase;
    }

    public ActivateHabitAlarmUseCase getActivateHabitAlarmsUseCase() {
        if (activateHabitAlarmsUseCase == null) {
            activateHabitAlarmsUseCase = new ActivateHabitAlarmUseCase(getHabitRepository());
        }
        return activateHabitAlarmsUseCase;
    }

    public DeactivateHabitAlarmUseCase getDeactivateHabitAlarmsUseCase() {
        if (deactivateHabitAlarmsUseCase == null) {
            deactivateHabitAlarmsUseCase = new DeactivateHabitAlarmUseCase(getHabitRepository());
        }
        return deactivateHabitAlarmsUseCase;
    }

    public IncrementHabitStreakUseCase getIncrementHabitStreakUseCase() {
        if (incrementHabitStreakUseCase == null) {
            incrementHabitStreakUseCase = new IncrementHabitStreakUseCase(getHabitRepository());
        }
        return incrementHabitStreakUseCase;
    }

    public ResetHabitStreakUseCase getResetHabitStreakUseCase() {
        if (resetHabitStreakUseCase == null) {
            resetHabitStreakUseCase = new ResetHabitStreakUseCase(getHabitRepository());
        }
        return resetHabitStreakUseCase;
    }

}