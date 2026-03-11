package com.example.habittracker.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.habittracker.MyApplication;
import com.example.habittracker.R;
import com.example.habittracker.di.AppContainer;
import com.example.habittracker.domain.habit.Habit;
import com.example.habittracker.app.habit.useCases.CreateHabitUseCase;
import com.example.habittracker.app.habit.useCases.GetHabitsUseCase;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CreateHabitUseCase createHabitUseCase;
    private GetHabitsUseCase getHabitsUseCase;

    private TextView textHabits;
    private int habitCounter = 1; // apenas para criar nomes de teste

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recupera o AppContainer da aplicação
        AppContainer container = ((MyApplication) getApplicationContext()).container;

        // Pega os UseCases diretamente do container
        createHabitUseCase = container.getCreateHabitUseCase();
        getHabitsUseCase = container.getGetHabitsUseCase();

        // Referência aos elementos do layout
        textHabits = findViewById(R.id.textHabits);
        Button buttonCreate = findViewById(R.id.buttonCreateHabit);

        // Clique no botão: cria um hábito de teste
        buttonCreate.setOnClickListener(v -> {
            createHabitUseCase.execute("Habit " + habitCounter, 1, false);
            habitCounter++;
            showHabits(); // atualiza lista
        });

        // Mostrar hábitos existentes na inicialização
        showHabits();
    }

    private void showHabits() {
        List<Habit> habits = getHabitsUseCase.execute();
        StringBuilder sb = new StringBuilder();
        for (Habit h : habits) {
            sb.append(h.getId())
                    .append(": ").append(h.getName())
                    .append(" - Streak: ").append(h.getStreak())
                    .append("\n");
        }
        textHabits.setText(sb.toString());
    }
}