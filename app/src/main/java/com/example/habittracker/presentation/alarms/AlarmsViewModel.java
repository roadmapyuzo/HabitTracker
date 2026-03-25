package com.example.habittracker.presentation.alarms;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.habittracker.app.alarms.useCases.CreateAlarmUseCase;
import com.example.habittracker.app.alarms.useCases.DeleteAlarmUseCase;
import com.example.habittracker.app.alarms.useCases.GetAlarmsByHabitUseCase;
import com.example.habittracker.app.alarms.useCases.ScheduleAlarmUseCase;
import com.example.habittracker.app.dailyRecord.useCases.GetRecordsByHabitUseCase;
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

    public AlarmsViewModel(
            GetHabitsUseCase getHabitsUseCase,
            GetAlarmsByHabitUseCase getAlarmsByHabitUseCase,
            CreateAlarmUseCase createAlarmUseCase,
            DeleteAlarmUseCase deleteAlarmUseCase,
            ScheduleAlarmUseCase scheduleAlarmUseCase) {
        this.getHabitsUseCase = getHabitsUseCase;
        this.getAlarmsByHabitUseCase = getAlarmsByHabitUseCase;
        this.createAlarmUseCase = createAlarmUseCase;
        this.deleteAlarmUseCase = deleteAlarmUseCase;
        this.scheduleAlarmUseCase = scheduleAlarmUseCase;
    }

    MutableLiveData<List<AlarmsDisplayDataHolder>> _displayData = new MutableLiveData<>();

    LiveData<List<AlarmsDisplayDataHolder>> displayData = _displayData;

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

        createAlarmUseCase.execute(habitId, hour, minute);

        loadData();

    }

    public void deleteAlarm(Alarm alarm) {

        deleteAlarmUseCase.execute(alarm.getId());

        loadData();

    }

}
