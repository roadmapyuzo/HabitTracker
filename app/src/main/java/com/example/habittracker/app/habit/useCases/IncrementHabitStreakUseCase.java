package com.example.habittracker.app.habit.useCases;

import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.domain.habit.Habit;

public class IncrementHabitStreakUseCase {

    private final HabitRepository repository;

    public IncrementHabitStreakUseCase(HabitRepository repository) {
        this.repository = repository;
    }

    public void execute(int habitId) {
        Habit habit = repository.findById(habitId);
        habit.incrementStreak();
        repository.save(habit);
    }
}
