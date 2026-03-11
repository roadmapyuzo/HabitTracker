package com.example.habittracker.app.dailyRecord.useCases;

import com.example.habittracker.app.dailyRecord.DailyRecordRepository;
import com.example.habittracker.domain.dailyRecord.Record;

import java.util.List;

public class GetRecordsByHabitUseCase {

    private final DailyRecordRepository repository;

    public GetRecordsByHabitUseCase(DailyRecordRepository repository) {
        this.repository = repository;
    }

    public List<Record> execute(int habitId) {
        return repository.findByHabitId(habitId);
    }
}