package com.example.habittracker.app.alarms.useCases;

import com.example.habittracker.app.alarms.NotificationScheduler;
import com.example.habittracker.domain.alarms.Alarm;

public class CancelAlarmUseCase {

    private final NotificationScheduler scheduler;

    public CancelAlarmUseCase(NotificationScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void execute(Alarm alarm) {
        if (alarm == null) {
            throw new IllegalArgumentException("Alarm cannot be null");
        }
        scheduler.cancel(alarm);
    }

}
