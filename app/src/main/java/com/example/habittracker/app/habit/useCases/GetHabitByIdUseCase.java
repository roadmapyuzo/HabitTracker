package com.example.habittracker.app.habit.useCases;

import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.domain.habit.Habit;

public class GetHabitByIdUseCase {

    private final HabitRepository repository;

    public GetHabitByIdUseCase(HabitRepository repository) {
        this.repository = repository;
    }

    public Habit execute(int habitId) {
        return repository.findById(habitId);
    }
}
