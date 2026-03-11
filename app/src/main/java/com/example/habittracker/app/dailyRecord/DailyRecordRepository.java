package com.example.habittracker.app.dailyRecord;

import com.example.habittracker.domain.dailyRecord.Record;

import java.time.LocalDate;
import java.util.List;

public interface DailyRecordRepository {

    void save(Record record);
    Record findById(int id);
    List<Record> findByHabitId(int habitId);
    void delete(int id);
    Record findByHabitAndDate(int habitId, LocalDate date);


}
