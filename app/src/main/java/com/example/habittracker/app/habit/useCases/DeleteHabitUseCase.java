package com.example.habittracker.app.habit.useCases;

import com.example.habittracker.app.habit.HabitRepository;

public class DeleteHabitUseCase {

    private final HabitRepository repository;

    public DeleteHabitUseCase(HabitRepository repository) {
        this.repository = repository;
    }

    public void execute(int habitId) {
        repository.delete(habitId);
    }
}