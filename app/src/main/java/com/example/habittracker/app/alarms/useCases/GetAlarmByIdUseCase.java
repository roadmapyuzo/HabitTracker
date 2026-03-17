package com.example.habittracker.app.alarms.useCases;

import com.example.habittracker.app.alarms.AlarmRepository;
import com.example.habittracker.domain.alarms.Alarm;

public class GetAlarmByIdUseCase {

    private final AlarmRepository repository;
    public GetAlarmByIdUseCase (AlarmRepository repository) {this.repository = repository;}

    public Alarm execute(int id) {return repository.findById(id);}


}


