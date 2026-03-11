package com.example.habittracker.app.habit.useCases;

import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.domain.habit.Habit;

import java.util.List;

public class GetHabitsUseCase {

    private final HabitRepository repository;

    public GetHabitsUseCase(HabitRepository repository) {
        this.repository = repository;
    }

    public List<Habit> execute() {
        return repository.findAll();
    }
}
