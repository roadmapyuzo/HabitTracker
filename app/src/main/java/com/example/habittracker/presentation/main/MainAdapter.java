package com.example.habittracker.presentation.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.habittracker.R;
import com.example.habittracker.domain.habit.Habit;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<MainDisplayDataHolder> dataList;
    private MainViewModel viewModel;


    public MainAdapter(List<MainDisplayDataHolder> dataList,MainViewModel viewModel) {
        this.dataList = dataList;
        this.viewModel = viewModel;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_item_list, parent, false);

        return new MainViewHolder(view);

    }

    public void updateData(List<MainDisplayDataHolder> newDataList) {
        this.dataList = newDataList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        MainDisplayDataHolder data = dataList.get(position);

        holder.txtTitle.setText(data.getHabit().getName());

        holder.progressDotsContainer.removeAllViews();

        int dailyGoal = data.getHabit().getDailyGoal();
        int goalStatus = data.getGoalStatus();

        for (int i = 0; i < dailyGoal; i++) {

            if (goalStatus > 0) {

                ImageView dot = new ImageView(holder.itemView.getContext());
                dot.setImageResource(R.drawable.active_circle);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(4, 0, 4, 0); // espaço entre dots
                dot.setLayoutParams(params);

                holder.progressDotsContainer.addView(dot);

                goalStatus = goalStatus - 1;

            } else {
                ImageView dot = new ImageView(holder.itemView.getContext());
                dot.setImageResource(R.drawable.circle);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(4, 0, 4, 0);
                dot.setLayoutParams(params);

                holder.progressDotsContainer.addView(dot);
            }

            holder.button.setOnClickListener(v -> {
                viewModel.incrementHabit(data.getHabit());
            });

        }


    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        LinearLayout progressDotsContainer;

        LinearLayout button;

        public MainViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtHabitName);
            progressDotsContainer = itemView.findViewById(R.id.progressDotsContainer);
            button = itemView.findViewById(R.id.area3);
        }

    }



}
