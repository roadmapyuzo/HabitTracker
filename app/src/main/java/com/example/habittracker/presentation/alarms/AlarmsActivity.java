package com.example.habittracker.presentation.alarms;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habittracker.R;
import com.example.habittracker.di.AppContainer;
import com.example.habittracker.domain.habit.Habit;
import com.example.habittracker.presentation.cards.CardsActivity;
import com.example.habittracker.presentation.main.MainActivity;
import com.example.habittracker.presentation.main.MainDisplayDataHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AlarmsActivity extends AppCompatActivity {

    private TextView txtClock;
    private Handler handler = new Handler();

    private AlarmsViewModel alarmsViewModel;
    private AlarmsAdapter alarmsAdapter;
    private RecyclerView recyclerView;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            String currentTime = sdf.format(new Date());
            txtClock.setText(currentTime);

            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarms_layout);


        txtClock = findViewById(R.id.txtClock);
        handler.post(runnable);


        recyclerView = findViewById(R.id.recyclerAlarms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        AppContainer appContainer = new AppContainer(this);
        alarmsViewModel = appContainer.getAlarmsViewModel();
        alarmsAdapter = appContainer.getAlarmsAdapter();

        recyclerView.setAdapter(alarmsAdapter);

        alarmsViewModel.displayData.observe(this, new Observer<List<AlarmsDisplayDataHolder>>() {
            @Override
            public void onChanged(List<AlarmsDisplayDataHolder> displayDataHolders) {
                alarmsAdapter.updateData(displayDataHolders);
            }
        });

        LinearLayout btnCards = findViewById(R.id.buttonCards);
        LinearLayout btnHome = findViewById(R.id.buttonHome);

        btnCards.setOnClickListener(v -> {
            Intent intent = new Intent(AlarmsActivity.this, CardsActivity.class);
            startActivity(intent);
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(AlarmsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        LinearLayout btnNewAlarm = findViewById(R.id.btnNewAlarm);

        btnNewAlarm.setOnClickListener(v -> {

            Dialog dialog = new Dialog(AlarmsActivity.this);
            dialog.setContentView(R.layout.alarm_modal);

            NumberPicker picker1 = dialog.findViewById(R.id.numberPicker1);
            NumberPicker picker2 = dialog.findViewById(R.id.numberPicker2);

            picker1.setMinValue(0);
            picker1.setMaxValue(23);
            picker1.setValue(1);
            picker1.setWrapSelectorWheel(true);

            picker2.setMinValue(1);
            picker2.setMaxValue(59);
            picker2.setValue(1);
            picker2.setWrapSelectorWheel(true);

            picker1.setTextColor(Color.WHITE);
            picker2.setTextColor(Color.WHITE);
            

            picker1.setFormatter(value -> String.format("%02d", value));
            picker2.setFormatter(value -> String.format("%02d", value));

            List<Habit> habits = alarmsViewModel.getHabits();

            ArrayAdapter<Habit> adapter = new ArrayAdapter<>(
                    this,
                    R.layout.spinner_item_layout,
                    habits
            );

            Spinner spinner = dialog.findViewById(R.id.spinnerHabits);

            spinner.setPopupBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.alarm_popup_background));

            adapter.setDropDownViewResource(R.layout.spinner_item_layout);
            spinner.setAdapter(adapter);



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

            Button btnConfirm = dialog.findViewById(R.id.btnCreateAlarm);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);

            btnConfirm.setOnClickListener(v1 -> {

                int hour = picker1.getValue();
                int minute = picker2.getValue();

                Habit selectHabit = (Habit) spinner.getSelectedItem();

                alarmsViewModel.createAlarm(selectHabit.getId(), hour, minute);

                dialog.dismiss();
            });

            btnCancel.setOnClickListener(v12 -> {
                dialog.dismiss();
            });

            dialog.show();

        });

       alarmsViewModel.loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

}