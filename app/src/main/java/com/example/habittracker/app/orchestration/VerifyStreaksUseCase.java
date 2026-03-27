package com.example.habittracker.app.orchestration;

import com.example.habittracker.app.DateProvider;
import com.example.habittracker.app.StreakManager;
import com.example.habittracker.app.dailyRecord.DailyRecordRepository;
import com.example.habittracker.app.habit.HabitRepository;
import com.example.habittracker.domain.dailyRecord.Record;
import com.example.habittracker.domain.habit.Habit;

import java.util.List;

import kotlin.io.encoding.StreamEncodingKt;

public class VerifyStreaksUseCase {

    private final HabitRepository habitRepository;

    private final DailyRecordRepository dailyRecordRepository;

    private final StreakManager streakManager;
    private DateProvider dateProvider;
    public VerifyStreaksUseCase(
            HabitRepository habitRepository,
            DailyRecordRepository dailyRecordRepository,
            StreakManager streakManager,
            DateProvider dateProvider
    ) {
        this.habitRepository = habitRepository;
        this.dailyRecordRepository = dailyRecordRepository;
        this.streakManager = streakManager;
        this.dateProvider = dateProvider;
    }

    public void execute() {

        List<Habit> habits = habitRepository.findAll();

        int failed = 0;

        if (!habits.isEmpty()) {

            for (Habit habit : habits) {

                Record record = dailyRecordRepository.findByHabitAndDate(habit.getId(), dateProvider.today().minusDays(1));

                if (record.getNumberOfTimes() != habit.getDailyGoal()) {

                    habit.resetStreak();
                    habitRepository.save(habit);
                    failed +=1;

                }

            }

            if (failed > 0) {

                streakManager.saveStreak(0);

            }

        }

    }

}
