package com.example.habittracker.presentation.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.habittracker.app.DateProvider;
import com.example.habittracker.app.alarms.useCases.CancelAlarmUseCase;
import com.example.habittracker.app.alarms.useCases.GetAlarmsByHabitUseCase;
import com.example.habittracker.app.alarms.useCases.ScheduleAlarmUseCase;
import com.example.habittracker.app.dailyRecord.useCases.GetHabitWeekStatusUseCase;
import com.example.habittracker.app.dailyRecord.useCases.GetRecordByHabitAndDateUseCase;
import com.example.habittracker.app.dailyRecord.useCases.RegisterHabitExecutionUseCase;
import com.example.habittracker.app.habit.useCases.ActivateHabitAlarmUseCase;
import com.example.habittracker.app.habit.useCases.CreateHabitUseCase;
import com.example.habittracker.app.habit.useCases.DeactivateHabitAlarmUseCase;
import com.example.habittracker.app.habit.useCases.GetHabitByIdUseCase;
import com.example.habittracker.app.habit.useCases.GetHabitsUseCase;
import com.example.habittracker.app.habit.useCases.IncrementHabitStreakUseCase;
import com.example.habittracker.domain.alarms.Alarm;
import com.example.habittracker.domain.dailyRecord.Record;
import com.example.habittracker.domain.habit.Habit;
import com.example.habittracker.infra.repository.StreakRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

   private GetHabitsUseCase getHabitsUseCase;
   private GetRecordByHabitAndDateUseCase getRecordByHabitAndDateUseCase;
   private GetHabitWeekStatusUseCase getHabitWeekStatusUseCase;
   private DateProvider dateProvider;
   private CreateHabitUseCase createHabitUseCase;
   private RegisterHabitExecutionUseCase registerHabitExecutionUseCase;
   private ActivateHabitAlarmUseCase activateHabitAlarmUseCase;
   private DeactivateHabitAlarmUseCase deactivateHabitAlarmUseCase;
   private GetHabitByIdUseCase getHabitByIdUseCase;
   private IncrementHabitStreakUseCase incrementHabitStreakUseCase;
   private GetAlarmsByHabitUseCase getAlarmsByHabitUseCase;
   private ScheduleAlarmUseCase scheduleAlarmUseCase;
   private CancelAlarmUseCase cancelAlarmUseCase;

   private StreakRepository streakRepository;

    private MutableLiveData<List<MainDisplayDataHolder>> _displayData = new MutableLiveData<>();
    public LiveData<List<MainDisplayDataHolder>> displayData = _displayData;
    private final MutableLiveData<int[]> _progressBarData = new MutableLiveData<>();
    public LiveData<int[]> progressBarData = _progressBarData;
    private MutableLiveData<Integer> _streak = new MutableLiveData<>();
    public LiveData<Integer> streak = _streak;


    public MainViewModel (GetHabitsUseCase getHabitsUseCase, GetRecordByHabitAndDateUseCase getRecordByHabitAndDateUseCase, GetHabitWeekStatusUseCase getHabitWeekStatusUseCase, DateProvider dateProvider, RegisterHabitExecutionUseCase registerHabitExecutionUseCase, StreakRepository streakRepository, CreateHabitUseCase createHabitUseCase, ActivateHabitAlarmUseCase activateHabitAlarmUseCase, DeactivateHabitAlarmUseCase deactivateHabitAlarmUseCase, GetHabitByIdUseCase getHabitByIdUseCase, IncrementHabitStreakUseCase incrementHabitStreakUseCase, GetAlarmsByHabitUseCase getAlarmsByHabitUseCase, ScheduleAlarmUseCase scheduleAlarmUseCase, CancelAlarmUseCase cancelAlarmUseCase) {
       this.getHabitsUseCase = getHabitsUseCase;
       this.getHabitWeekStatusUseCase = getHabitWeekStatusUseCase;
       this.getRecordByHabitAndDateUseCase = getRecordByHabitAndDateUseCase;
       this.dateProvider = dateProvider;
       this.registerHabitExecutionUseCase = registerHabitExecutionUseCase;
       this.streakRepository = streakRepository;
       this.createHabitUseCase = createHabitUseCase;
       this.activateHabitAlarmUseCase = activateHabitAlarmUseCase;
       this.deactivateHabitAlarmUseCase = deactivateHabitAlarmUseCase;
       this.getHabitByIdUseCase = getHabitByIdUseCase;
       this.incrementHabitStreakUseCase = incrementHabitStreakUseCase;
       this.getAlarmsByHabitUseCase = getAlarmsByHabitUseCase;
       this.scheduleAlarmUseCase = scheduleAlarmUseCase;
       this.cancelAlarmUseCase = cancelAlarmUseCase;
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

                Habit newHabit = getHabitByIdUseCase.execute(item.getHabit().getId());

                if (newHabit.isActiveAlarms() != item.getHabit().isActiveAlarms()) {

                    if (newHabit.isActiveAlarms()) {
                        item.getHabit().activateAlarms();
                    } else {
                        item.getHabit().deactivateAlarms();
                    }

                }

                List<Boolean> weekStatus = getHabitWeekStatusUseCase.execute(habit.getId());

                item.setWeekStatus(weekStatus);

                Record record = getRecordByHabitAndDateUseCase.execute(habit.getId(), dateProvider.today());

                item.setGoalStatus(record != null ? record.getNumberOfTimes() : 0);

                if (item.getGoalStatus() == item.getHabit().getDailyGoal()) {
                    incrementHabitStreak(item.getHabit());
                }

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
        streakRepository.saveLastStreakDate(dateProvider.today().toString());
        _streak.setValue(newStreak);
    }

    public void decrementStreak() {

        int newStreak = streakRepository.getStreak() - 1;
        streakRepository.saveStreak(newStreak);
        streakRepository.saveLastStreakDate(dateProvider.today().minusDays(1).toString());
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

        if (!LocalDate.parse(streakRepository.getLastStreakDate()).equals(dateProvider.today())) {

            incrementarStreak();

        }


    }

    public void createHabit(String name, int goal) {

        createHabitUseCase.execute(name, goal, false);

        if (LocalDate.parse(streakRepository.getLastStreakDate()).equals(dateProvider.today())) {
            decrementStreak();
        }

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

    public void incrementHabitStreak (Habit habit) {

        incrementHabitStreakUseCase.execute(habit.getId());

    }

}
