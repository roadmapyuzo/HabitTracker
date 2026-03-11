package com.example.habittracker.app.habit.useCases;

import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.domain.habit.Habit;

public class ChangeHabitNameUseCase {

    private final HabitRepository repository;

    public ChangeHabitNameUseCase(HabitRepository repository) {
        this.repository = repository;
    }

    public void execute(int habitId, String newName) {
        Habit habit = repository.findById(habitId);
        habit.changeName(newName);
        repository.save(habit);
    }
}
