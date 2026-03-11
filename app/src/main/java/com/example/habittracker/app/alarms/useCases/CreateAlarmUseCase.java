package com.example.habittracker.app.alarms.useCases;

import com.example.habittracker.app.alarms.AlarmRepository;
import com.example.habittracker.domain.alarms.Alarm;

public class CreateAlarmUseCase {

    private final AlarmRepository repository;

    public CreateAlarmUseCase(AlarmRepository repository) {
        this.repository = repository;
    }

    public Alarm execute(int habitId, int hour, int minute) {
        Alarm alarm = new Alarm(null, habitId, hour, minute);
        repository.save(alarm);
        return alarm;
    }
}
