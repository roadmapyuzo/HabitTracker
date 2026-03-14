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

    private final CreateRecordUseCase createRecordUseCase;

    public RegisterHabitExecutionUseCase(
            DailyRecordRepository repository,
            DateProvider dateProvider,
            CreateRecordUseCase createRecordUseCase
    ) {
        this.repository = repository;
        this.dateProvider = dateProvider;
        this.createRecordUseCase = createRecordUseCase;

    }

    public void execute(int habitId) {

        LocalDate today = dateProvider.today();

        Record record =
                repository.findByHabitAndDate(habitId, today);

        if (record == null) {

            record = createRecordUseCase.execute(habitId, today);

            repository.save(record);

        } else {

            record.increment();
            repository.save(record);

        }
    }
}
