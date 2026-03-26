package com.example.habittracker.app.habit.useCases;

import com.example.habittracker.app.DateProvider;
import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.domain.habit.Habit;

public class CreateHabitUseCase {

    private final HabitRepository repository;
    private final DateProvider dateProvider;

    public CreateHabitUseCase(HabitRepository repository, DateProvider dateProvider) {
        this.repository = repository;
        this.dateProvider = dateProvider;
    }

    public Habit execute(String name, int dailyGoal, boolean alarms) {

        Habit habit = new Habit(null, name, dailyGoal, alarms, 0, dateProvider.today());
        repository.save(habit);

        return habit;
    }
}