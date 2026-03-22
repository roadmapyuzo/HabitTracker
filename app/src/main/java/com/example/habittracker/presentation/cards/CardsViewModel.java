package com.example.habittracker.presentation.cards;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.habittracker.app.DateProvider;
import com.example.habittracker.app.dailyRecord.useCases.GetRecordByHabitAndDateUseCase;
import com.example.habittracker.app.dailyRecord.useCases.GetRecordsByHabitUseCase;
import com.example.habittracker.app.dailyRecord.useCases.IncrementRecordUseCase;
import com.example.habittracker.app.dailyRecord.useCases.RegisterHabitExecutionUseCase;
import com.example.habittracker.app.habit.useCases.ActivateHabitAlarmUseCase;
import com.example.habittracker.app.habit.useCases.DeactivateHabitAlarmUseCase;
import com.example.habittracker.app.habit.useCases.DeleteHabitUseCase;
import com.example.habittracker.app.habit.useCases.GetHabitByIdUseCase;
import com.example.habittracker.app.habit.useCases.GetHabitsUseCase;
import com.example.habittracker.app.habit.useCases.IncrementHabitStreakUseCase;
import com.example.habittracker.domain.dailyRecord.Record;
import com.example.habittracker.domain.habit.Habit;
import com.example.habittracker.infra.repository.StreakRepository;
import com.example.habittracker.presentation.main.MainDisplayDataHolder;

import java.util.ArrayList;
import java.util.List;

public class CardsViewModel {

    private ActivateHabitAlarmUseCase activateHabitAlarmUseCase;
    private GetHabitsUseCase getHabitsUseCase;
    private DeleteHabitUseCase deleteHabitUseCase;
    private IncrementHabitStreakUseCase incrementHabitStreakUseCase;
    private RegisterHabitExecutionUseCase registerHabitExecutionUseCase;
    private GetRecordsByHabitUseCase getRecordsByHabitUseCase;
    private StreakRepository streakRepository;
    private GetRecordByHabitAndDateUseCase getRecordByHabitAndDateUseCase;
    private DateProvider dateProvider;
    private GetHabitByIdUseCase getHabitByIdUseCase;
    private DeactivateHabitAlarmUseCase deactivateHabitAlarmUseCase;


    MutableLiveData<List<CardsDisplayDataHolder>> _displayData = new MutableLiveData<>();

    LiveData<List<CardsDisplayDataHolder>> displayData = _displayData;

    public CardsViewModel(
            ActivateHabitAlarmUseCase activateHabitAlarmUseCase,
            GetHabitsUseCase getHabitsUseCase,
            DeleteHabitUseCase deleteHabitUseCase,
            IncrementHabitStreakUseCase incrementHabitStreakUseCase,
            RegisterHabitExecutionUseCase registerHabitExecutionUseCase,
            GetRecordsByHabitUseCase getRecordsByHabitUseCase,
            StreakRepository streakRepository,
            GetRecordByHabitAndDateUseCase getRecordByHabitAndDateUseCase,
            DateProvider dateProvider,
            GetHabitByIdUseCase getHabitByIdUseCase,
            DeactivateHabitAlarmUseCase deactivateHabitAlarmUseCase
    ) {
        this.activateHabitAlarmUseCase = activateHabitAlarmUseCase;
        this.getHabitsUseCase = getHabitsUseCase;
        this.deleteHabitUseCase = deleteHabitUseCase;
        this.incrementHabitStreakUseCase = incrementHabitStreakUseCase;
        this.registerHabitExecutionUseCase = registerHabitExecutionUseCase;
        this.getRecordsByHabitUseCase = getRecordsByHabitUseCase;
        this.streakRepository = streakRepository;
        this.getRecordByHabitAndDateUseCase = getRecordByHabitAndDateUseCase;
        this.dateProvider = dateProvider;
        this.getHabitByIdUseCase = getHabitByIdUseCase;
        this.deactivateHabitAlarmUseCase = deactivateHabitAlarmUseCase;
    }

    public void loadData() {

        List<CardsDisplayDataHolder> list = new ArrayList<>();

        List<Habit> habits = getHabitsUseCase.execute();

        for (Habit habit : habits) {


            CardsDisplayDataHolder dataHolder = new CardsDisplayDataHolder();

            dataHolder.setHabit(habit);

            List<Record> allRecords = getRecordsByHabitUseCase.execute(habit.getId());

            Record todayRecord = getRecordByHabitAndDateUseCase.execute(habit.getId(), dateProvider.today());

            int goalStatus;

            if (todayRecord != null) {
                goalStatus = todayRecord.getNumberOfTimes();
            } else {
                goalStatus = 0;
            }

            dataHolder.setGoalStatus(goalStatus);

            dataHolder.setRecords(allRecords);

            list.add(dataHolder);

        }

        _displayData.setValue(list);

    }

    public void incrementHabit(Habit habit) {

        registerHabitExecutionUseCase.execute(habit.getId());

    }

    public void activateNotifications(Habit habit) {

        activateHabitAlarmUseCase.execute(habit.getId());

    }

    public void deactivateNotifications(Habit habit) {

        deactivateHabitAlarmUseCase.execute(habit.getId());

    }

    public CardsDisplayDataHolder updateDisplayDataForHabit(Habit habit) {

        if (_displayData.getValue() == null) return null;

        List<CardsDisplayDataHolder> currentList = new ArrayList<>(_displayData.getValue());

        for (CardsDisplayDataHolder item : currentList) {
            if (item.getHabit().getId().equals(habit.getId())) {

                Habit newHabit = getHabitByIdUseCase.execute(habit.getId());

                List<Record> records = getRecordsByHabitUseCase.execute(habit.getId());

                item.setRecords(records);

                Record todayRecord = getRecordByHabitAndDateUseCase.execute(habit.getId(), dateProvider.today());

                int goalStatus;

                if (todayRecord != null) {
                    goalStatus = todayRecord.getNumberOfTimes();
                } else {
                    goalStatus = 0;
                }

                item.setGoalStatus(goalStatus);

                item.setHabit(newHabit);

                return item;
            }
        }
        return null;
    }




}
