package com.example.habittracker.presentation.alarms;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.habittracker.app.ResultClass;
import com.example.habittracker.app.alarms.useCases.CancelAlarmUseCase;
import com.example.habittracker.app.alarms.useCases.CreateAlarmUseCase;
import com.example.habittracker.app.alarms.useCases.DeleteAlarmUseCase;
import com.example.habittracker.app.alarms.useCases.GetAlarmsByHabitUseCase;
import com.example.habittracker.app.alarms.useCases.ScheduleAlarmUseCase;
import com.example.habittracker.app.dailyRecord.useCases.GetRecordsByHabitUseCase;
import com.example.habittracker.app.habit.useCases.ActivateHabitAlarmUseCase;
import com.example.habittracker.app.habit.useCases.DeactivateHabitAlarmUseCase;
import com.example.habittracker.app.habit.useCases.GetHabitByIdUseCase;
import com.example.habittracker.app.habit.useCases.GetHabitsUseCase;
import com.example.habittracker.domain.alarms.Alarm;
import com.example.habittracker.domain.dailyRecord.Record;
import com.example.habittracker.domain.habit.Habit;
import com.example.habittracker.presentation.cards.CardsDisplayDataHolder;

import java.util.ArrayList;
import java.util.List;

public class AlarmsViewModel {

    private GetHabitsUseCase getHabitsUseCase;
    private GetAlarmsByHabitUseCase getAlarmsByHabitUseCase;
    private CreateAlarmUseCase createAlarmUseCase;
    private DeleteAlarmUseCase deleteAlarmUseCase;
    private ScheduleAlarmUseCase scheduleAlarmUseCase;
    private ActivateHabitAlarmUseCase activateHabitAlarmUseCase;
    private DeactivateHabitAlarmUseCase deactivateHabitAlarmUseCase;
    private GetHabitByIdUseCase getHabitByIdUseCase;
    private CancelAlarmUseCase cancelAlarmUseCase;

    public AlarmsViewModel(
            GetHabitsUseCase getHabitsUseCase,
            GetAlarmsByHabitUseCase getAlarmsByHabitUseCase,
            CreateAlarmUseCase createAlarmUseCase,
            DeleteAlarmUseCase deleteAlarmUseCase,
            ScheduleAlarmUseCase scheduleAlarmUseCase,
            ActivateHabitAlarmUseCase activateHabitAlarmUseCase,
            DeactivateHabitAlarmUseCase deactivateHabitAlarmUseCase,
            GetHabitByIdUseCase getHabitByIdUseCase,
            CancelAlarmUseCase cancelAlarmUseCase) {
        this.getHabitsUseCase = getHabitsUseCase;
        this.getAlarmsByHabitUseCase = getAlarmsByHabitUseCase;
        this.createAlarmUseCase = createAlarmUseCase;
        this.deleteAlarmUseCase = deleteAlarmUseCase;
        this.scheduleAlarmUseCase = scheduleAlarmUseCase;
        this.activateHabitAlarmUseCase = activateHabitAlarmUseCase;
        this.deactivateHabitAlarmUseCase = deactivateHabitAlarmUseCase;
        this.getHabitByIdUseCase = getHabitByIdUseCase;
        this.cancelAlarmUseCase = cancelAlarmUseCase;
    }

    MutableLiveData<List<AlarmsDisplayDataHolder>> _displayData = new MutableLiveData<>();

    LiveData<List<AlarmsDisplayDataHolder>> displayData = _displayData;
    private MutableLiveData<String> _error = new MutableLiveData<>();
    public LiveData<String> error = _error;

    public void loadData() {

        List<AlarmsDisplayDataHolder> list = new ArrayList<>();

        List<Habit> habits = getHabitsUseCase.execute();

        for (Habit habit : habits) {

            AlarmsDisplayDataHolder dataHolder = new AlarmsDisplayDataHolder();

            dataHolder.setHabit(habit);

            List<Alarm> allAlarms = getAlarmsByHabitUseCase.execute(habit.getId());

            dataHolder.setAlarms(allAlarms);

            list.add(dataHolder);

        }

        _displayData.setValue(list);

    }

    public List<Habit> getHabits() {

        return getHabitsUseCase.execute();

    }

    public void createAlarm(int habitId, int hour, int minute) {

        Habit habit = getHabitByIdUseCase.execute(habitId);

        ResultClass<Alarm> result = createAlarmUseCase.execute(habitId, hour, minute);

        if (result.isFailure()) {
            _error.setValue(result.getError());
            return;
        }

        if (habit.isActiveAlarms()) {
            scheduleAlarmUseCase.execute(result.getData());
        }

        loadData();

    }

    public void deleteAlarm(Alarm alarm) {

        cancelAlarmUseCase.execute(alarm);

        deleteAlarmUseCase.execute(alarm.getId());

        loadData();

    }

    public void activateAlarms(Habit habit) {

        activateHabitAlarmUseCase.execute(habit.getId());

        List<Alarm> alarms = getAlarmsByHabitUseCase.execute(habit.getId());

        for (Alarm alarm : alarms) {

            scheduleAlarmUseCase.execute(alarm);

        }

        loadData();

    }

    public void deactivateAlarms(Habit habit) {

        deactivateHabitAlarmUseCase.execute(habit.getId());

        List<Alarm> alarms = getAlarmsByHabitUseCase.execute(habit.getId());

        for (Alarm alarm : alarms) {

            cancelAlarmUseCase.execute(alarm);

        }

        loadData();

    }

    public void clearError() {
        _error.setValue(null);
    }

}
