package com.example.habittracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.habittracker.R;
import com.example.habittracker.domain.habit.Habit;
import com.example.habittracker.presentation.calendar.CalendarAdapter;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.HabitViewHolder> {

    private final List<Habit> habitList;

    public CardsAdapter(List<Habit> habitList) {
        this.habitList = habitList;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_card, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = habitList.get(position);
        holder.bind(habit);
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    static class HabitViewHolder extends RecyclerView.ViewHolder {

        TextView habitName;
        SwitchMaterial switchHabit;
        ViewPager2 calendarViewPager;
        CalendarAdapter calendarAdapter;

        Button buttonPrev;
        Button buttonNext;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            habitName = itemView.findViewById(R.id.habitName);
            switchHabit = itemView.findViewById(R.id.switchHabit);
            calendarViewPager = itemView.findViewById(R.id.viewPager);

            // Botões simples para navegar pelo calendário
            buttonPrev = itemView.findViewById(R.id.buttonPrevMonth);
            buttonNext = itemView.findViewById(R.id.buttonNextMonth);

            // Inicializa o CalendarAdapter uma vez
            calendarAdapter = new CalendarAdapter(itemView.getContext(), 3);
            calendarViewPager.setAdapter(calendarAdapter);
            calendarViewPager.setOffscreenPageLimit(3);

            // Botão "Anterior"
            buttonPrev.setOnClickListener(v -> {
                int current = calendarViewPager.getCurrentItem();
                if (current > 0) {
                    calendarViewPager.setCurrentItem(current - 1, true);
                }
            });

            // Botão "Próximo"
            buttonNext.setOnClickListener(v -> {
                int current = calendarViewPager.getCurrentItem();
                if (current < calendarAdapter.getItemCount() - 1) {
                    calendarViewPager.setCurrentItem(current + 1, true);
                }
            });
        }

        void bind(Habit habit) {
            habitName.setText(habit.getName());
            switchHabit.setChecked(habit.isActiveAlarms());
            // Calendário já está configurado, não precisa recriar
        }
    }
}