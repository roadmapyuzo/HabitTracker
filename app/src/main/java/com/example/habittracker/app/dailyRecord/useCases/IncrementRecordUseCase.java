package com.example.habittracker.app.dailyRecord.useCases;

import com.example.habittracker.app.dailyRecord.DailyRecordRepository;
import com.example.habittracker.domain.dailyRecord.Record;

public class IncrementRecordUseCase {

    private final DailyRecordRepository repository;

    public IncrementRecordUseCase(DailyRecordRepository repository) {
        this.repository = repository;
    }

    public void execute(int id) {

        Record record = repository.findById(id);
        record.increment();
        repository.save(record);


    }
}
