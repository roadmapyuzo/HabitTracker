package com.example.habittracker.app.alarms;

import com.example.habittracker.domain.alarms.Alarm;
import java.util.List;

public interface AlarmRepository {

    void save(Alarm alarm);
    Alarm findById(int id);
    List<Alarm> findByHabitId(int habitId);
    void delete(int id);
    void update(Alarm alarm);

}
