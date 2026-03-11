package com.example.habittracker.app.dailyRecord.useCases;

import com.example.habittracker.app.dailyRecord.DailyRecordRepository;

public class DeleteRecordUseCase {

    private final DailyRecordRepository repository;

    public DeleteRecordUseCase(DailyRecordRepository repository) {
        this.repository = repository;
    }

    public void execute(int recordId) {
        repository.delete(recordId);
    }
}