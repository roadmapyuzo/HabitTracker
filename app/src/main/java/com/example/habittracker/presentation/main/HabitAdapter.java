package com.example.habittracker.presentation.main;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.recyclerview.widget.RecyclerView;

import com.example.habittracker.R;
import com.example.habittracker.app.DateProvider;
import com.example.habittracker.app.dailyRecord.useCases.GetHabitWeekStatusUseCase;
import com.example.habittracker.app.dailyRecord.useCases.GetRecordByHabitAndDateUseCase;
import com.example.habittracker.app.dailyRecord.useCases.RegisterHabitExecutionUseCase;
import com.example.habittracker.domain.dailyRecord.Record;
import com.example.habittracker.domain.habit.Habit;
import com.example.habittracker.infra.DateProviderImpl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder> {

    private List<Habit> habits;

    private final RegisterHabitExecutionUseCase registerExecution;
    private final GetHabitWeekStatusUseCase weekStatusUseCase;

    private final DateProvider date = new DateProviderImpl();

    private final GetRecordByHabitAndDateUseCase getRecordByHabitAndDateUseCase;

    private Set<Integer> expandedItems = new HashSet<>();

    public HabitAdapter(
            List<Habit> habits,
            RegisterHabitExecutionUseCase registerExecution,
            GetRecordByHabitAndDateUseCase getRecordByHabitAndDateUseCase,
            GetHabitWeekStatusUseCase weekStatusUseCase
    ) {
        this.habits = habits;
        this.registerExecution = registerExecution;
        this.getRecordByHabitAndDateUseCase = getRecordByHabitAndDateUseCase;
        this.weekStatusUseCase = weekStatusUseCase;
    }

    public void update(List<Habit> habits) {
        this.habits = habits;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView goal;

        TextView todayCount;

        ImageView completedIcon;

        Button markButton;

        LinearLayout weekContainer;

        LinearLayout headerArea;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.habitName);
            goal = view.findViewById(R.id.habitGoal);
            todayCount = view.findViewById(R.id.habitTodayCount);

            completedIcon = view.findViewById(R.id.completedIcon);

            markButton = view.findViewById(R.id.markDoneButton);

            weekContainer = view.findViewById(R.id.weekContainer);

            headerArea = view.findViewById(R.id.headerArea);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_habit, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Habit habit = habits.get(position);

        holder.name.setText(habit.getName());
        holder.goal.setText("Goal: " + habit.getDailyGoal());

        Record todayRecord = getRecordByHabitAndDateUseCase.execute(habit.getId(),date.today());

        int todayCount;
        if (todayRecord == null) {
            todayCount = 0;
        } else {
            todayCount = todayRecord.getNumberOfTimes();
        }

        holder.todayCount.setText(
                "Today: " + todayCount + " / " + habit.getDailyGoal()
        );

        List<Boolean> week = weekStatusUseCase.execute(habit.getId());

        boolean completedToday = week.get(6);

        holder.completedIcon.setImageResource(
                completedToday ? R.drawable.circle_green : R.drawable.circle_gray
        );

        holder.markButton.setEnabled(!completedToday);

        holder.markButton.setOnClickListener(v -> {

            registerExecution.execute(habit.getId());

            notifyItemChanged(holder.getAdapterPosition());
        });

        boolean expanded = expandedItems.contains(habit.getId());

        holder.weekContainer.setVisibility(
                expanded ? View.VISIBLE : View.GONE
        );

        holder.headerArea.setOnClickListener(v -> {

            if (expanded) {
                expandedItems.remove(habit.getId());
            } else {
                expandedItems.add(habit.getId());
            }

            notifyItemChanged(holder.getAdapterPosition());
        });

        holder.weekContainer.removeAllViews();

        for (Boolean done : week) {

            View circle = LayoutInflater
                    .from(holder.itemView.getContext())
                    .inflate(R.layout.circle_day, holder.weekContainer, false);

            circle.setBackgroundResource(
                    done ? R.drawable.circle_green : R.drawable.circle_gray
            );

            holder.weekContainer.addView(circle);
        }
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }
}
