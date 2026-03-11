package com.example.habittracker.app.alarms.useCases;

import com.example.habittracker.app.alarms.AlarmRepository;

public class DeleteAlarmUseCase {

    private final AlarmRepository repository;

    public DeleteAlarmUseCase(AlarmRepository repository) {
        this.repository = repository;
    }

    public void execute(int alarmId) {
        repository.delete(alarmId);
    }
}