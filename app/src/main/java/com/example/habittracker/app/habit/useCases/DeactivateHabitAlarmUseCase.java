package com.example.habittracker.app.habit.useCases;

import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.domain.habit.Habit;

public class DeactivateHabitAlarmUseCase {

    private final HabitRepository repository;

    public DeactivateHabitAlarmUseCase(HabitRepository repository) {
        this.repository = repository;
    }

    public void execute(int habitId) {
        Habit habit = repository.findById(habitId);
        habit.deactivateAlarms();
        repository.save(habit);
    }
}
