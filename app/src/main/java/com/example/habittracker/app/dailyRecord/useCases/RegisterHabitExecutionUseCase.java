package com.example.habittracker.app.dailyRecord.useCases;

import com.example.habittracker.app.DateProvider;
import com.example.habittracker.app.dailyRecord.DailyRecordRepository;
import com.example.habittracker.domain.dailyRecord.Record;

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

            System.out.println("chegou aqui");
            System.out.println(record.getId());
            System.out.println(record.getNumberOfTimes());

            record.increment();

            System.out.println(record.getNumberOfTimes());

            repository.save(record);

            Record record2 =
                    repository.findById(2);

            System.out.println(record2.getNumberOfTimes());

        }
    }
}
