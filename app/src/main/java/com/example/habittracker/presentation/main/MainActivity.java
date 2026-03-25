package com.example.habittracker.presentation.main;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habittracker.R;
import com.example.habittracker.di.AppContainer;
import com.example.habittracker.domain.alarms.Alarm;
import com.example.habittracker.domain.habit.Habit;
import com.example.habittracker.presentation.alarms.AlarmsActivity;
import com.example.habittracker.presentation.cards.CardsActivity;

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

        LinearLayout progressBar = findViewById(R.id.progressBar);
        TextView streakValue = findViewById(R.id.streakValue);

        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        Toast.makeText(this, "Permissão concedida!", Toast.LENGTH_SHORT).show();
                    } else {
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

        viewModel.progressBarData.observe(this, progress -> {
            progressBar.post(() -> {
                int total = progress[0];
                int completed = progress[1];

                int parentWidth = progressBar.getWidth();
                int itemWidth = (int) (parentWidth * 0.18f);

                progressBar.removeAllViews();

                for (int i = 0; i < total; i++) {
                    ImageView barItem = new ImageView(this);
                    barItem.setImageResource(R.drawable.loading_bar_item_empty);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            itemWidth,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    );

                    params.setMargins(5, 0, 5, 0);
                    barItem.setLayoutParams(params);

                    if (i < completed) {
                        barItem.setImageResource(R.drawable.loading_bar_item);
                    }

                    progressBar.addView(barItem);
                }
            });
        });


        viewModel.streak.observe(this, value -> {
            streakValue.setText(String.valueOf(value));
        });

        viewModel.loadData();


        LinearLayout btnGenerateMock = findViewById(R.id.btnGenerateMock);
        LinearLayout btnCards = findViewById(R.id.buttonCards);
        LinearLayout btnAlarms = findViewById(R.id.buttonClock);

        btnCards.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CardsActivity.class);
            startActivity(intent);
        });

        btnAlarms.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AlarmsActivity.class);
            startActivity(intent);
        });

        btnGenerateMock.setOnClickListener(v -> {

            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.habit_modal);

            EditText edtHabitName = dialog.findViewById(R.id.edtHabitName);
            EditText edtDailyGoal = dialog.findViewById(R.id.edtDailyGoal);

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }


            Window window = dialog.getWindow();
            if (window != null) {
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int larguraTela = metrics.widthPixels;

                int larguraDialog = (int) (larguraTela * 0.8);
                window.setLayout(larguraDialog, ViewGroup.LayoutParams.WRAP_CONTENT);

            }

            Button btnConfirm = dialog.findViewById(R.id.btnCreateHabit);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);

            btnConfirm.setOnClickListener(v1 -> {

                String habitName = edtHabitName.getText().toString().trim();
                String goalText = edtDailyGoal.getText().toString().trim();


                if (habitName.isEmpty()) {
                    Toast.makeText(this, "Digite o nome do hábito", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (goalText.isEmpty()) {
                    Toast.makeText(this, "Digite a meta diária", Toast.LENGTH_SHORT).show();
                    return;
                }

                int goal = Integer.parseInt(goalText);

                viewModel.createHabit(habitName, goal);

                dialog.dismiss();
            });

            btnCancel.setOnClickListener(v12 -> {
                dialog.dismiss();
            });

            dialog.show();

        });

        checkNotificationPermission();
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

