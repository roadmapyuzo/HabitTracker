package com.example.habittracker.app.habit;

import com.example.habittracker.domain.habit.Habit;

import java.util.List;

public interface HabitRepository {

    void save(Habit habit);
    Habit findById(int id);
    List<Habit> findAll();
    void delete(int id);


}
