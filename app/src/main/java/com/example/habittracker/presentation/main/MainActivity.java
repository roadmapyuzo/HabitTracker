package com.example.habittracker.presentation.main;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habittracker.MyApplication;
import com.example.habittracker.R;

import com.example.habittracker.app.dailyRecord.useCases.GetRecordByHabitAndDateUseCase;
import com.example.habittracker.app.habit.useCases.CreateHabitUseCase;
import com.example.habittracker.app.habit.useCases.GetHabitsUseCase;

import com.example.habittracker.app.dailyRecord.useCases.RegisterHabitExecutionUseCase;
import com.example.habittracker.app.dailyRecord.useCases.GetHabitWeekStatusUseCase;

import com.example.habittracker.domain.habit.Habit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CreateHabitUseCase createHabitUseCase;
    private GetHabitsUseCase getHabitsUseCase;

    private RegisterHabitExecutionUseCase registerHabitExecutionUseCase;
    private GetHabitWeekStatusUseCase getHabitWeekStatusUseCase;

    private GetRecordByHabitAndDateUseCase getRecordByHabitAndDateUseCase;

    private HabitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApplication app = (MyApplication) getApplication();

        createHabitUseCase = app.container.getCreateHabitUseCase();
        getHabitsUseCase = app.container.getGetHabitsUseCase();
        getRecordByHabitAndDateUseCase = app.container.getRecordByHabitAndDateUseCase();

        registerHabitExecutionUseCase = app.container.getRegisterHabitExecutionUseCase();
        getHabitWeekStatusUseCase = app.container.getGetHabitWeekStatusUseCase();

        EditText inputName = findViewById(R.id.inputName);
        EditText inputGoal = findViewById(R.id.inputGoal);
        Switch switchAlarm = findViewById(R.id.switchAlarm);
        Button buttonCreate = findViewById(R.id.buttonCreate);
        RecyclerView recyclerView = findViewById(R.id.habitList);

        adapter = new HabitAdapter(
                new ArrayList<>(),
                registerHabitExecutionUseCase,
                getRecordByHabitAndDateUseCase,
                getHabitWeekStatusUseCase
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadHabits();

        buttonCreate.setOnClickListener(v -> {

            String name = inputName.getText().toString();
            int goal = Integer.parseInt(inputGoal.getText().toString());
            boolean alarms = switchAlarm.isChecked();

            createHabitUseCase.execute(name, goal, alarms);

            loadHabits();
        });
    }

    private void loadHabits() {

        List<Habit> habits = getHabitsUseCase.execute();
        adapter.update(habits);

    }
}