package com.example.habittracker.presentation.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.habittracker.app.DateProvider;
import com.example.habittracker.app.dailyRecord.useCases.GetHabitWeekStatusUseCase;
import com.example.habittracker.app.dailyRecord.useCases.GetRecordByHabitAndDateUseCase;
import com.example.habittracker.app.dailyRecord.useCases.RegisterHabitExecutionUseCase;
import com.example.habittracker.app.habit.useCases.GetHabitsUseCase;
import com.example.habittracker.domain.dailyRecord.Record;
import com.example.habittracker.domain.habit.Habit;
import com.example.habittracker.infra.repository.StreakRepository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

   private GetHabitsUseCase getHabitsUseCase;
   private GetRecordByHabitAndDateUseCase getRecordByHabitAndDateUseCase;
   private GetHabitWeekStatusUseCase getHabitWeekStatusUseCase;
   private DateProvider dateProvider;
   private RegisterHabitExecutionUseCase registerHabitExecutionUseCase;

   private StreakRepository streakRepository;

    private MutableLiveData<List<MainDisplayDataHolder>> _displayData = new MutableLiveData<>();
    public LiveData<List<MainDisplayDataHolder>> displayData = _displayData;
    private final MutableLiveData<int[]> _progressBarData = new MutableLiveData<>();
    public LiveData<int[]> progressBarData = _progressBarData;
    private MutableLiveData<Integer> _streak = new MutableLiveData<>();
    public LiveData<Integer> streak = _streak;


    public MainViewModel (GetHabitsUseCase getHabitsUseCase, GetRecordByHabitAndDateUseCase getRecordByHabitAndDateUseCase, GetHabitWeekStatusUseCase getHabitWeekStatusUseCase, DateProvider dateProvider, RegisterHabitExecutionUseCase registerHabitExecutionUseCase, StreakRepository streakRepository) {
       this.getHabitsUseCase = getHabitsUseCase;
       this.getHabitWeekStatusUseCase = getHabitWeekStatusUseCase;
       this.getRecordByHabitAndDateUseCase = getRecordByHabitAndDateUseCase;
       this.dateProvider = dateProvider;
       this.registerHabitExecutionUseCase = registerHabitExecutionUseCase;
       this.streakRepository = streakRepository;
   }

   public void loadData() {

       List<MainDisplayDataHolder> list = new ArrayList<>();

       List<Habit> habits = getHabitsUseCase.execute();

       for (Habit habit : habits) {

           MainDisplayDataHolder data = new MainDisplayDataHolder();

           Record dailyRecord = getRecordByHabitAndDateUseCase.execute(habit.getId(), dateProvider.today());

           int goalStatus;

           if (dailyRecord != null) {
               goalStatus = dailyRecord.getNumberOfTimes();
           } else {
               goalStatus = 0;
           }

           List<Boolean> weekStatus = getHabitWeekStatusUseCase.execute(habit.getId());

           data.setHabit(habit);
           data.setGoalStatus(goalStatus);
           data.setWeekStatus(weekStatus);

           list.add(data);

       }

       _displayData.setValue(list);

       updateProgressBarData();

       loadStreak();

   }

    public void incrementHabit(Habit habit) {


        registerHabitExecutionUseCase.execute(habit.getId());

        updateDisplayDataForHabit(habit);

        verifyStreak();
    }

    private void updateDisplayDataForHabit(Habit habit) {

        if (_displayData.getValue() == null) return;

        List<MainDisplayDataHolder> currentList = new ArrayList<>(_displayData.getValue());

        for (MainDisplayDataHolder item : currentList) {
            if (item.getHabit().getId().equals(habit.getId())) {

                List<Boolean> weekStatus = getHabitWeekStatusUseCase.execute(habit.getId());

                item.setWeekStatus(weekStatus);

                Record record = getRecordByHabitAndDateUseCase.execute(habit.getId(), dateProvider.today());

                item.setGoalStatus(record != null ? record.getNumberOfTimes() : 0);
                break;
            }
        }

        _displayData.setValue(currentList);

        updateProgressBarData();

    }

    private void updateProgressBarData() {
        List<MainDisplayDataHolder> currentList = _displayData.getValue();
        if (currentList == null || currentList.isEmpty()) {
            _progressBarData.setValue(new int[]{0, 0});
            return;
        }

        int total = currentList.size();
        int completed = 0;

        for (MainDisplayDataHolder item : currentList) {
            if (item.getGoalStatus() >= item.getHabit().getDailyGoal()) completed++;
        }

        _progressBarData.setValue(new int[]{total, completed});
    }

    public void loadStreak() {
        int current = streakRepository.getStreak();
        _streak.setValue(current);
    }

    public void incrementarStreak() {
        int newStreak = streakRepository.getStreak() + 1;
        streakRepository.saveStreak(newStreak);
        _streak.setValue(newStreak);
    }

    public void verifyStreak() {
        List<MainDisplayDataHolder> lista = _displayData.getValue();
        if (lista == null || lista.isEmpty()) {
            return;
        }

        for (MainDisplayDataHolder item : lista) {
            Habit habit = item.getHabit();
            int goalStatus = item.getGoalStatus();

            if (goalStatus < habit.getDailyGoal()) {
                return;
            }

        }

        incrementarStreak();

    }

}
