package com.example.habittracker.presentation.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habittracker.R;
import com.example.habittracker.di.AppContainer;
import com.example.habittracker.domain.alarms.Alarm;
import com.example.habittracker.domain.habit.Habit;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> requestPermissionLauncher;
    private MainViewModel viewModel;
    private MainAdapter adapter;
    private AppContainer container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        // Permissão concedida, você pode criar notificações
                        Toast.makeText(this, "Permissão concedida!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Permissão negada
                        Toast.makeText(this, "Permissão de notificação negada!", Toast.LENGTH_SHORT).show();
                    }
                });


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


        LinearLayout btnGenerateMock = findViewById(R.id.btnGenerateMock);
        btnGenerateMock.setOnClickListener(v -> {
            createTest();
        });

        checkNotificationPermission();
    }


    private void createTest() {


        int habitId = 1;
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE) + 1;


        if (minute >= 60) {
            minute -= 60;
            hour = (hour + 1) % 24;
        }


        Alarm alarm = container.getCreateAlarmUseCase().execute(habitId, hour, minute);
        container.getScheduleAlarmUseCase().execute(alarm);


        viewModel.loadData();
    }

    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { //
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
}

