package com.example.habittracker.app.dailyRecord;

import com.example.habittracker.domain.dailyRecord.Record;
import java.util.List;

public interface DailyRecordRepository {

    void save(Record record);
    Record findById(int id);
    List<Record> findByHabitId(int habitId);
    void delete(int id);


}
