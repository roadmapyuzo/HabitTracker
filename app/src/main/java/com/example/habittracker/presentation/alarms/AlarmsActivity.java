package com.example.habittracker.presentation.alarms;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.habittracker.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmsActivity extends AppCompatActivity {

    private TextView txtClock;
    private Handler handler = new Handler();

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}