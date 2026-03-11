package com.example.habittracker.app.habit.useCases;

import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.domain.habit.Habit;

public class ChangeHabitGoalUseCase {

    private final HabitRepository repository;

    public ChangeHabitGoalUseCase(HabitRepository repository) {
        this.repository = repository;
    }

    public void execute(int habitId, int newGoal) {
        Habit habit = repository.findById(habitId);
        habit.changeGoal(newGoal);
        repository.save(habit);
    }
}
