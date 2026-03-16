package com.example.habittracker.app.alarms;

import com.example.habittracker.domain.alarms.Alarm;

public interface NotificationScheduler {

    void schedule(Alarm alarm);

    void cancel(Alarm alarm);



}
