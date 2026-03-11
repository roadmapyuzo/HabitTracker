package com.example.habittracker.app.dailyRecord.useCases;

import com.example.habittracker.app.dailyRecord.DailyRecordRepository;
import com.example.habittracker.domain.dailyRecord.Record;

import java.time.LocalDate;

public class CreateRecordUseCase {

    private final DailyRecordRepository repository;

    public CreateRecordUseCase(DailyRecordRepository repository) {
        this.repository = repository;
    }

    public Record execute(int habitId, LocalDate date) {
        Record record = new Record(null, habitId, 1, date);
        repository.save(record);
        return record;
    }
}
