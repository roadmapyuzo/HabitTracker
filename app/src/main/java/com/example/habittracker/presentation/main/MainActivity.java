package com.example.habittracker.presentation.main;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habittracker.R;
import com.example.habittracker.di.AppContainer;
import com.example.habittracker.domain.habit.Habit;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private MainAdapter adapter;
    private AppContainer container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        container = new AppContainer(getApplicationContext());


        viewModel = container.getMainViewModel();
        adapter = container.getMainAdapter();


        RecyclerView recyclerView = findViewById(R.id.recyclerViewHabits);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        viewModel.displayData.observe(this, new Observer<List<MainDisplayDataHolder>>() {
            @Override
            public void onChanged(List<MainDisplayDataHolder> displayDataHolders) {
                adapter.updateData(displayDataHolders);
            }
        });


        viewModel.loadData();


        Button btnGenerateMock = findViewById(R.id.btnGenerateMock);
        btnGenerateMock.setOnClickListener(v -> {
            createMockHabit();
        });
    }


    private void createMockHabit() {

        String name = "Hábito Mock";
        int dailyGoal = 2;
        boolean alarms = false;


        container.getCreateHabitUseCase().execute(name, dailyGoal, alarms);


        viewModel.loadData();
    }
}

