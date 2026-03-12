package com.example.habittracker.app.dailyRecord.useCases;

import com.example.habittracker.app.DateProvider;
import com.example.habittracker.app.dailyRecord.DailyRecordRepository;
import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.domain.dailyRecord.Record;
import com.example.habittracker.domain.habit.Habit;

import java.time.LocalDate;

public class RegisterHabitExecutionUseCase {

    private final DailyRecordRepository repository;
    private final DateProvider dateProvider;

    public RegisterHabitExecutionUseCase(
            DailyRecordRepository repository,
            DateProvider dateProvider
    ) {
        this.repository = repository;
        this.dateProvider = dateProvider;

    }

    public void execute(int habitId) {

        LocalDate today = dateProvider.today();

        Record record =
                repository.findByHabitAndDate(habitId, today);

        if (record == null) {

            record = new Record(
                    null,
                    habitId,
                    1,
                    today
            );

            repository.save(record);

        } else {

            record.increment();
            repository.save(record);

        }
    }
}
