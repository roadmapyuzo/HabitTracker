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
        record.setNumberOfTimes(2);
        repository.save(record);

        Record record2 = repository.findById(id);
        System.out.println("AAAAA: "+record2.getNumberOfTimes());
        System.out.println("AAAAA do record 1: "+record.getNumberOfTimes());
    }
}
