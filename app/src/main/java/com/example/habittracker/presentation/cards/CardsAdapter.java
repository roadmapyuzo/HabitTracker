package com.example.habittracker.presentation.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.habittracker.R;
import com.example.habittracker.app.DateProvider;
import com.example.habittracker.domain.habit.Habit;
import com.example.habittracker.presentation.calendar.CalendarAdapter;
import com.example.habittracker.presentation.main.MainDisplayDataHolder;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.HabitViewHolder> {

    private List<CardsDisplayDataHolder> dataList;
    private DateProvider dateProvider;
    private CardsViewModel viewModel;
    public CardsAdapter(List<CardsDisplayDataHolder> dataList, DateProvider dateProvider, CardsViewModel viewModel) {
        this.dataList = dataList;
        this.dateProvider = dateProvider;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_card, parent, false);
        return new HabitViewHolder(view);
    }

    public void updateData(List<CardsDisplayDataHolder> newDataList) {
        this.dataList = newDataList;
        notifyDataSetChanged();
    }

    public void updateSingleItem(CardsDisplayDataHolder newItem) {
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getHabit().getId().equals(newItem.getHabit().getId())) {
                dataList.set(i, newItem);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {


        CardsDisplayDataHolder item = dataList.get(position);

        Habit habit = item.getHabit();

        holder.habitName.setText(habit.getName());
        holder.calendarAdapter = new CalendarAdapter(holder.context, item, dateProvider);
        holder.calendarViewPager.setAdapter(holder.calendarAdapter);
        holder.calendarViewPager.setOffscreenPageLimit(3);


        holder.setFirst();


        holder.dailyProgress.post(() -> {
            int parentWidth = holder.dailyProgress.getWidth();
            int itemWidth = (int) (parentWidth * 0.15f);

            holder.dailyProgress.removeAllViews();

            int goalStatus = item.getGoalStatus();
            int dailyGoal = habit.getDailyGoal();

            for (int i = 0; i < dailyGoal; i++) {

                ImageView barItem = new ImageView(holder.itemView.getContext());

                if (goalStatus > 0) {
                    barItem.setImageResource(R.drawable.loading_bar_item);
                    goalStatus--;
                } else {
                    barItem.setImageResource(R.drawable.loading_bar_item_empty);
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        itemWidth,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );

                params.setMargins(5, 0, 5, 0);
                barItem.setLayoutParams(params);

                holder.dailyProgress.addView(barItem);
            }
        });


        holder.buttonPrev.setOnClickListener(v -> {
            int current = holder.calendarViewPager.getCurrentItem();
            if (current > 0) {
                holder.calendarViewPager.setCurrentItem(current - 1, true);
            }
        });


        holder.buttonNext.setOnClickListener(v -> {
            int current = holder.calendarViewPager.getCurrentItem();
            if (current < holder.calendarAdapter.getItemCount() - 1) {
                holder.calendarViewPager.setCurrentItem(current + 1, true);
            }
        });

        holder.switchHabit.setOnCheckedChangeListener(null);
        holder.switchHabit.setChecked(habit.isActiveAlarms());
        holder.switchHabit.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                viewModel.activateNotifications(habit);
            } else {
                viewModel.deactivateNotifications(habit);
            }


            CardsDisplayDataHolder updatedItem = viewModel.updateDisplayDataForHabit(habit);
            if (updatedItem != null) {
                updateSingleItem(updatedItem);
            }
        });

        holder.buttonIncrement.setOnClickListener(v -> {

            viewModel.incrementHabit(habit);

            CardsDisplayDataHolder updatedItem = viewModel.updateDisplayDataForHabit(habit);
            if (updatedItem != null) {
                updateSingleItem(updatedItem);
                holder.updateDailyProgress(updatedItem.getGoalStatus(), habit.getDailyGoal());

                if (updatedItem.getGoalStatus() == habit.getDailyGoal()) {
                    holder.updateCalendar(item);
                }

            }

        });


    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class HabitViewHolder extends RecyclerView.ViewHolder {
        TextView habitName;
        SwitchMaterial switchHabit;
        ViewPager2 calendarViewPager;
        CalendarAdapter calendarAdapter;
        LinearLayout buttonPrev;
        LinearLayout buttonNext;
        LinearLayout dailyProgress;
        Context context;
        LinearLayout buttonDelete;
        LinearLayout buttonIncrement;


        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            habitName = itemView.findViewById(R.id.habitName);
            switchHabit = itemView.findViewById(R.id.switchHabit);
            calendarViewPager = itemView.findViewById(R.id.viewPager);
            dailyProgress = itemView.findViewById(R.id.progressBar);
            context = itemView.getContext();
            buttonPrev = itemView.findViewById(R.id.buttonPrevMonth);
            buttonNext = itemView.findViewById(R.id.buttonNextMonth);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonIncrement = itemView.findViewById(R.id.buttonCheck);

        }

        void setFirst() {

            calendarViewPager.setCurrentItem(calendarAdapter.getItemCount()-1, false);

        }

        void updateDailyProgress(int goalStatus, int dailyGoal) {
            dailyProgress.post(() -> {
                int parentWidth = dailyProgress.getWidth();
                int itemWidth = (int) (parentWidth * 0.15f);

                dailyProgress.removeAllViews();

                for (int i = 0; i < dailyGoal; i++) {
                    ImageView barItem = new ImageView(itemView.getContext());
                    barItem.setImageResource(i < goalStatus ?
                            R.drawable.loading_bar_item : R.drawable.loading_bar_item_empty);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            itemWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                    params.setMargins(5, 0, 5, 0);
                    barItem.setLayoutParams(params);

                    dailyProgress.addView(barItem);
                }
            });
        }

        void updateCalendar(CardsDisplayDataHolder updatedItem) {
            if (calendarAdapter != null) {
                calendarAdapter.updateData(updatedItem);
                calendarViewPager.setCurrentItem(calendarAdapter.getItemCount() - 1, false);
            }
        }

    }


}