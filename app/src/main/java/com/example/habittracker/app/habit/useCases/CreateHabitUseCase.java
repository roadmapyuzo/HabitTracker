package com.example.habittracker.app.habit.useCases;

import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.domain.habit.Habit;

public class CreateHabitUseCase {

    private final HabitRepository repository;

    public CreateHabitUseCase(HabitRepository repository) {
        this.repository = repository;
    }

    public Habit execute(String name, int dailyGoal, boolean alarms) {

        Habit habit = new Habit(null, name, dailyGoal, alarms);
        repository.save(habit);

        return habit;
    }
}