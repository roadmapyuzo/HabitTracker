package com.example.habittracker.presentation.main;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.habittracker.R;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private TextView textMessage;
    private Button buttonAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textMessage = findViewById(R.id.textMessage);
        buttonAction = findViewById(R.id.buttonAction);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        observeViewModel();
        setupListeners();
    }

    private void observeViewModel() {
        viewModel.getMessage().observe(this, message -> {
            textMessage.setText(message);
        });
    }

    private void setupListeners() {
        buttonAction.setOnClickListener(v -> {
            viewModel.onButtonClicked();
        });
    }
}