package com.example.habittracker.app.dailyRecord.useCases;

import com.example.habittracker.app.dailyRecord.DailyRecordRepository;
import com.example.habittracker.domain.dailyRecord.Record;

import java.time.LocalDate;

public class GetRecordByHabitAndDateUseCase {

    private final DailyRecordRepository repository;

    public GetRecordByHabitAndDateUseCase (DailyRecordRepository repository) {
        this.repository = repository;
    }

    public Record execute(int habitId, LocalDate date) {

        return repository.findByHabitAndDate(habitId, date);

    }



}
