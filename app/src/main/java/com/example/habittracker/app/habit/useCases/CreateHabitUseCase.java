package com.example.habittracker.app.habit.useCases;

import com.example.habittracker.app.DateProvider;
import com.example.habittracker.app.ResultClass;
import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.domain.habit.Habit;

import java.util.List;

public class CreateHabitUseCase {

    private final HabitRepository repository;
    private final DateProvider dateProvider;

    public CreateHabitUseCase(HabitRepository repository, DateProvider dateProvider) {
        this.repository = repository;
        this.dateProvider = dateProvider;
    }

    public ResultClass<Habit> execute(String name, int dailyGoal, boolean alarms) {

        List<Habit> habits = repository.findAll();

        if (habits.size() == 5) {
            return ResultClass.failure("You can only register 5 habits");
        }

        Habit habit = new Habit(null, name, dailyGoal, alarms, 0, dateProvider.today());
        repository.save(habit);

        return ResultClass.success(habit);
    }
}