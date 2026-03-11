package com.example.habittracker.app.habit.useCases;

import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.domain.habit.Habit;

public class ResetHabitStreakUseCase {

    private final HabitRepository repository;

    public ResetHabitStreakUseCase(HabitRepository repository) {
        this.repository = repository;
    }

    public void execute(int habitId) {
        Habit habit = repository.findById(habitId);
        habit.resetStreak();
        repository.save(habit);
    }
}
