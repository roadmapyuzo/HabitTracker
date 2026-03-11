package com.example.habittracker.app.dailyRecord.useCases;

import com.example.habittracker.app.DateProvider;
import com.example.habittracker.app.dailyRecord.DailyRecordRepository;
import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.domain.dailyRecord.Record;
import com.example.habittracker.domain.habit.Habit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GetHabitWeekStatusUseCase {

    private final DailyRecordRepository recordRepository;
    private final HabitRepository habitRepository;
    private final DateProvider dateProvider;

    public GetHabitWeekStatusUseCase(
            DailyRecordRepository recordRepository,
            HabitRepository habitRepository,
            DateProvider dateProvider
    ) {
        this.recordRepository = recordRepository;
        this.habitRepository = habitRepository;
        this.dateProvider = dateProvider;
    }

    public List<Boolean> execute(int habitId) {

        List<Boolean> result = new ArrayList<>();

        Habit habit = habitRepository.findById(habitId);
        int goal = habit.getDailyGoal();

        LocalDate today = dateProvider.today();

        for (int i = 6; i >= 0; i--) {

            LocalDate day = today.minusDays(i);

            Record record =
                    recordRepository.findByHabitAndDate(habitId, day);

            if (record == null) {
                result.add(false);
            } else {
                result.add(record.getNumberOfTimes() >= goal);
            }
        }

        return result;
    }
}
