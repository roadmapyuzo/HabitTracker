package com.example.habittracker.app.alarms.useCases;

import com.example.habittracker.app.alarms.AlarmRepository;
import com.example.habittracker.domain.alarms.Alarm;

public class UpdateAlarmUseCase {

    private final AlarmRepository repository;

    public UpdateAlarmUseCase(AlarmRepository repository) {
        this.repository = repository;
    }

    public void execute(Alarm alarm, int newHour, int newMinute) {
        alarm.change(newHour, newMinute);
        repository.save(alarm);
    }
}
