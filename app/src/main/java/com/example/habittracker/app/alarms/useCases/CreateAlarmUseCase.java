package com.example.habittracker.app.alarms.useCases;

import com.example.habittracker.app.ResultClass;
import com.example.habittracker.app.alarms.AlarmRepository;
import com.example.habittracker.domain.alarms.Alarm;

import java.util.List;

public class CreateAlarmUseCase {

    private final AlarmRepository repository;

    public CreateAlarmUseCase(AlarmRepository repository) {
        this.repository = repository;
    }

    public ResultClass<Alarm> execute(int habitId, int hour, int minute) {

        List<Alarm> alarms = repository.findByHabitId(habitId);

        if (alarms.size() == 5) {
            return ResultClass.failure("You can only have 5 alarms per habit");
        }

        Alarm alarm = new Alarm(null, habitId, hour, minute);
        repository.save(alarm);
        return ResultClass.success(alarm);
    }
}
