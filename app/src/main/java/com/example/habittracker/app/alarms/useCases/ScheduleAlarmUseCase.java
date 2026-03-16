package com.example.habittracker.app.alarms.useCases;

import com.example.habittracker.domain.alarms.Alarm;
import com.example.habittracker.app.alarms.NotificationScheduler;

public class ScheduleAlarmUseCase {

    private final NotificationScheduler scheduler;

    public ScheduleAlarmUseCase(NotificationScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void execute(Alarm alarm) {
        if (alarm == null) {
            throw new IllegalArgumentException("Alarm cannot be null");
        }
        scheduler.schedule(alarm);
    }
}
