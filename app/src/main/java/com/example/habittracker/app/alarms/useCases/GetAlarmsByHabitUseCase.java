package com.example.habittracker.app.alarms.useCases;

import com.example.habittracker.app.alarms.AlarmRepository;
import com.example.habittracker.domain.alarms.Alarm;

import java.util.List;

public class GetAlarmsByHabitUseCase {

    private final AlarmRepository repository;

    public GetAlarmsByHabitUseCase(AlarmRepository repository) {
        this.repository = repository;
    }

    public List<Alarm> execute(int habitId) {
        return repository.findByHabitId(habitId);
    }
}
